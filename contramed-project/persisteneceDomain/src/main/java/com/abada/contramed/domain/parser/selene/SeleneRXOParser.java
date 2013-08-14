/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.contramed.domain.parser.selene;

/*
 * #%L
 * Contramed
 * %%
 * Copyright (C) 2013 Abada Servicios Desarrollo (investigacion@abadasoft.com)
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import ca.uhn.hl7v2.model.v25.datatype.CE;
import ca.uhn.hl7v2.model.v25.datatype.ID;
import ca.uhn.hl7v2.model.v25.datatype.LA1;
import ca.uhn.hl7v2.model.v25.datatype.NM;
import ca.uhn.hl7v2.model.v25.segment.RXO;
import com.abada.contramed.persistence.entity.CatalogoMedicamentos;
import com.abada.contramed.persistence.entity.MeasureUnit;
import com.abada.contramed.persistence.entity.Order1;
import com.abada.contramed.persistence.entity.OrderMedication;
import com.abada.contramed.persistence.entity.OrderMedicationInstruction;
import com.abada.contramed.persistence.entity.OrderMedicationObservation;
import com.abada.contramed.persistence.entity.Patient;
import com.abada.contramed.persistence.entity.PrincipioActivo;
import com.abada.contramed.persistence.entity.Recurso;
import java.math.BigDecimal;

/**
 * Parseador especifico par el segmento RXO de un mensaje HL7, es especifico de
 * la información con la que trabaja el HIS Selene.<br/>
 * El segmento RXO contiene la informacion especifica de la especialidad que se administra
 * en un tratamiento.
 * @author katsu
 */
class SeleneRXOParser {

    /**
     * parseo del segmento RXO a la entidad order
     * @param rxo
     * @param order
     */
    void parseRXO(RXO rxo, Order1 order) {
        /*
        //    1.1      1.2                                 1.3  1.4    2.1  4.1 4.2   8.1    8.2 8.3  8.4       16.1
        //RXO|601161^TERMALGIN COD 300-14MG CAP (PARAC+COD)^ESP^601161|1.0||169^MU||||UE-1A^122^122-2^HSB||||||||N
        //  1.1    1.2                       1.3  1.4   2.1   4.1 4.2    8.1   8.2  8.3   8.4       16.1
        //RXO|603472^AMOXICILINA EFG 500MG CAPS^ESP^603472|500.0||150^mg||||UE-1A^122^122-2^HSB||||||||N

        // message23.txt                                                //esto peta 4.1 no es un string
        //    1.1  1.2                                1.3 1.4    2.1  3.1      4.1      4.2 5.1  5.2   6.1                                       
        //RXO|645903^ESPIDIFEN 400mg SOBRE (IBUPROFENO)^ESP^645903|1.00|400.00|MILIGRAMOS^150|SOB^SOBRES|{Validación FARMACIA: - Se sugiere cambiar la dos
        //  7.1                                            8.3     11.1 12.1     16.1
        //|Cada 1 dia/s a las 08:00, 12:00, 20:00, 22:00|^^122-2|||1.00|SOBRES||||N
         */
        OrderMedication orderMedication;
        if (order.getOrderMedication() == null) {
            orderMedication = new OrderMedication();
        } else {
            orderMedication = order.getOrderMedication();
        }

        //1.1 order.order_medication catalogo_medicamentos_CODIGO Código nacional del medicamento.
        CE ce = rxo.getRxo1_RequestedGiveCode();
        if (ce != null && ce.getCe1_Identifier() != null && ce.getCe1_Identifier().getValue() != null && !"".equals(ce.getCe1_Identifier().getValue())) {
            CatalogoMedicamentos catalogomedicamentosCODIGO = new CatalogoMedicamentos();
            catalogomedicamentosCODIGO.setCodigo(ce.getCe1_Identifier().getValue());
            orderMedication.setCatalogomedicamentosCODIGO(catalogomedicamentosCODIGO);

        } else { //a veces no viene el 1.1 y viene el 24 principio activo
            CE[] cearray = rxo.getRxo24_SupplementaryCode();
            if (cearray != null && cearray.length > 0) {
                if (cearray[0] != null && cearray[0].getCe1_Identifier() != null && cearray[0].getCe1_Identifier().getValue() != null && !"".equals(cearray[0].getCe1_Identifier().getValue())) {
                    PrincipioActivo principioActivo = new PrincipioActivo();
                    principioActivo.setCodigo(cearray[0].getCe1_Identifier().getValue());
                    orderMedication.setPrincipioActivo(principioActivo);
                }
            }

        }
        //2.1 Order.order_medication give_amount Número de dosis a administrar.

        NM nm = rxo.getRxo2_RequestedGiveAmountMinimum();
        if (nm != null && nm.getValue() != null && !"".equals(nm.getValue())) {
            orderMedication.setGiveAmount(new BigDecimal(nm.getValue()));

        }

        //4.1 o 4.2 Order.order_medication measure_unit_idmeasure_unit Unidad de administración. Mg, ml, etc
        ce = rxo.getRxo4_RequestedGiveUnits();
        if (ce != null && ce.getCe1_Identifier() != null && ce.getCe1_Identifier().getValue() != null && !"".equals(ce.getCe1_Identifier().getValue())) {
            MeasureUnit measureUnit = new MeasureUnit();
            measureUnit.setIdmeasureUnit(Utils.toInteger(ce.getCe1_Identifier().getValue()));
            orderMedication.setMeasureUnitIdmeasureUnit(measureUnit);
        } else if (ce != null && ce.getCe2_Text() != null && ce.getCe2_Text().getValue() != null && !"".equals(ce.getCe2_Text().getValue())) {
            MeasureUnit measureUnit = new MeasureUnit();
            measureUnit.setName(ce.getCe2_Text().getValue());
            orderMedication.setMeasureUnitIdmeasureUnit(measureUnit);
        } else {
            MeasureUnit measureUnit = new MeasureUnit();
            measureUnit.setName("U");
            orderMedication.setMeasureUnitIdmeasureUnit(measureUnit);
        }



        //6.2 Order.order_medication_observation Observation Observaciones médicas
        CE[] cearray = rxo.getRxo6_ProviderSPharmacyTreatmentInstructions();
        if (cearray != null && cearray.length > 0) {
            for (CE ceaux : cearray) {
                addObservacion(ceaux, orderMedication);
            }
        }

        //7.2  Order.order_medication_instruction Instruction Instrucciones de administración
        cearray = rxo.getRxo7_ProviderSAdministrationInstructions();
        if (cearray != null && cearray.length > 0) {
            for (CE ceaux : cearray) {
                addInstruction(ceaux, orderMedication);
            }
        }

        //8.3 Patient Bed
        LA1 la1 = rxo.getRxo8_DeliverToLocation();
        if (la1 != null && la1.getLa13_Bed() != null && la1.getLa13_Bed().getValue() != null && !"".equals(la1.getLa13_Bed().getValue())) {
            Patient patientId;
            if (order.getPatientId() == null) {
                patientId = new Patient();
            } else {
                patientId = order.getPatientId();
            }
            Recurso bed;
            if (patientId.getBed() == null) {
                bed = new Recurso();
            } else {
                bed = patientId.getBed();
            }

            bed.setNr(la1.getLa13_Bed().getValue());
            patientId.setBed(bed);
            order.setPatientId(patientId);
        }



        //16.1 order_medication  Alergy
        ID id = rxo.getRxo16_NeedsHumanReview();
        if (id != null && id.getValue() != null && !"".equals(id.getValue())) {
            orderMedication.setAlergy(Utils.toBoolean(id.getValue()));
        }

        //lo seteamos todo en order
        order.setOrderMedication(orderMedication);

    }

    /**
     * añadimos a una lista las observaciones del paciente
     * @param observador
     * @param orderMedication
     */
    private void addObservacion(CE observador, OrderMedication orderMedication) {
        if (observador != null) {
            OrderMedicationObservation orderMedicationObservation;
            if (observador.getCe2_Text() != null && observador.getCe2_Text().getValue() != null && !"".equals(observador.getCe2_Text().getValue())) {
                orderMedicationObservation = new OrderMedicationObservation();
                orderMedicationObservation.setObservation(observador.getCe2_Text().getValue());
                orderMedication.addOrderMedicationObservation(orderMedicationObservation);
            } else if (observador.getCe1_Identifier() != null && observador.getCe1_Identifier().getValue() != null && !"".equals(observador.getCe1_Identifier().getValue())) {
                orderMedicationObservation = new OrderMedicationObservation();
                orderMedicationObservation.setObservation(observador.getCe1_Identifier().getValue());
                orderMedication.addOrderMedicationObservation(orderMedicationObservation);
            }

        }
    }

    /**
     * añadimos en una lista las instruciones del paciente
     * @param instruction
     * @param orderMedication
     */
    private void addInstruction(CE instruction, OrderMedication orderMedication) {
        if (instruction != null) {
            OrderMedicationInstruction orderMedicationInstruction;
            if (instruction.getCe2_Text() != null && instruction.getCe2_Text().getValue() != null && !"".equals(instruction.getCe2_Text().getValue())) {
                orderMedicationInstruction = new OrderMedicationInstruction();
                orderMedicationInstruction.setInstruction(instruction.getCe2_Text().getValue());
                orderMedication.addOrderMedicationInstruction(orderMedicationInstruction);
            } else if (instruction.getCe1_Identifier() != null && instruction.getCe1_Identifier().getValue() != null && !"".equals(instruction.getCe1_Identifier().getValue())) {
                orderMedicationInstruction = new OrderMedicationInstruction();
                orderMedicationInstruction.setInstruction(instruction.getCe1_Identifier().getValue());
                orderMedication.addOrderMedicationInstruction(orderMedicationInstruction);
            }

        }
    }
}
