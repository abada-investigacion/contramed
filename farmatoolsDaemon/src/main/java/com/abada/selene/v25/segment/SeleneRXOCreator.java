/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.selene.v25.segment;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.v25.datatype.CE;
import ca.uhn.hl7v2.model.v25.datatype.ID;
import ca.uhn.hl7v2.model.v25.segment.RXO;
import com.abada.farmatools.object.Farmatools_Order;
import com.abada.selene.utils.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author aroldan
 *
 * segmento RXO medicamentos
 */
public class SeleneRXOCreator implements ISegment<RXO, Farmatools_Order> {

    private static Log log = LogFactory.getLog(SeleneMSHCreator.class);

    /**
     * creación del Segmento RXO medicamento de un pacient
     * @param order
     * @param rxo
     * @return
     */
    public RXO createSegment(Farmatools_Order order, RXO rxo) {
        try {
            /*if (order.getModo_prescripcion() != null && order.getModo_prescripcion().equals("P")) {
            //24.1 order.order_medication Principio Activo.
            if (order.getCod_principioActivo() != null) {
            rxo.getRxo24_SupplementaryCode(0).getCe1_Identifier().setValue(Integer.toString(order.getCod_principioActivo()));
            }

            } else { //1.1 order.order_medication catalogo_medicamentos_CODIGO Código nacional del medicamento.
            if (order.getCod_articulo() != null) {
            rxo.getRxo1_RequestedGiveCode().getCe1_Identifier().setValue(order.getCod_articulo()); //ALPRAZOLAM
            }
            }*/

            //Ignorar modo de prescripcion : Si hay articulo cogerlo y sino el principio activo
            //24.1 order.order_medication Principio Activo.
            if (order.getCod_principioActivo() != null) {
                rxo.getRxo24_SupplementaryCode(0).getCe1_Identifier().setValue(Integer.toString(order.getCod_principioActivo()));
            }
            //1.1 order.order_medication catalogo_medicamentos_CODIGO Código nacional del medicamento.
            if (order.getCod_articulo() != null) {
                rxo.getRxo1_RequestedGiveCode().getCe1_Identifier().setValue(order.getCod_articulo()); //ALPRAZOLAM
            }

            //TODO si viene el codigo de articulo a null el principio activo tambien mal
            //2.1 Order.order_medication give_amount Número de dosis a administrar.
            if (order.getGive_amount() != null) {
                rxo.getRxo2_RequestedGiveAmountMinimum().setValue(Double.toString(order.getGive_amount()));
            }

            if (order.getId_measure_unit() != null) {
                //4.1 o 4.2 Order.order_medication measure_unit_idmeasure_unit Unidad de administración. Mg, ml, etc
                rxo.getRxo4_RequestedGiveUnits().getCe1_Identifier().setValue(Integer.toString(order.getId_measure_unit())); //U (Unidad)
            }

            //6.2 Order.order_medication_observation Observation Observaciones médicas
            if (order.getObs_medico() != null) {
                CE providerSPharmacyTreatmentInstructions = rxo.insertRxo6_ProviderSPharmacyTreatmentInstructions(0);
                providerSPharmacyTreatmentInstructions.getCe1_Identifier().setValue(order.getObs_medico());
            }

            int i = 0;
            CE providerSAdministrationInstructions;
            //7.2  Order.order_medication_instruction Instruction Instrucciones de administración
            if (order.getInstruccion() != null) {
                providerSAdministrationInstructions = rxo.insertRxo7_ProviderSAdministrationInstructions(i++);
                providerSAdministrationInstructions.getCe1_Identifier().setValue(order.getInstruccion());
            }
            if(order.getDescripcion_secuencia() != null) {
                providerSAdministrationInstructions = rxo.insertRxo7_ProviderSAdministrationInstructions(i++);
                providerSAdministrationInstructions.getCe1_Identifier().setValue(order.getDescripcion_secuencia());
                }
            i = 0;
            CE providerSAdministrationobservaciones;
            if (order.getObs_dispensa() != null) {
                providerSAdministrationobservaciones = rxo.insertRxo6_ProviderSPharmacyTreatmentInstructions(i++);
                providerSAdministrationobservaciones.getCe1_Identifier().setValue(order.getObs_dispensa());
            }
            if (order.getObs_enferme() != null) {
                providerSAdministrationobservaciones = rxo.insertRxo6_ProviderSPharmacyTreatmentInstructions(i++);
                providerSAdministrationobservaciones.getCe1_Identifier().setValue(order.getObs_enferme());
            }
            if (order.getObs_medico() != null) {
                providerSAdministrationobservaciones = rxo.insertRxo6_ProviderSPharmacyTreatmentInstructions(i++);
                providerSAdministrationobservaciones.getCe1_Identifier().setValue(order.getObs_medico());
            }



            //8.3 Patient Bed
            /*LA1 deliverLocation = rxo.getRxo8_DeliverToLocation();
            deliverLocation.getLa13_Bed().setValue(order.getNR()); //NR de la cama*/

            //16.1 order_medication  Alergy
            if (order.getAlergia_s_n() != null) {
                ID needsHumanReview = rxo.getRxo16_NeedsHumanReview();
                needsHumanReview.setValue(Utils.booleanToString(order.getAlergia_s_n()));
            }

        } catch (HL7Exception ex) {
            log.error(ex.toString());
        } finally {
            return rxo;
        }

    }
}
