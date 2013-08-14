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

import ca.uhn.hl7v2.model.v25.datatype.CQ;
import ca.uhn.hl7v2.model.v25.datatype.CWE;
import ca.uhn.hl7v2.model.v25.datatype.RPT;
import ca.uhn.hl7v2.model.v25.datatype.TM;
import ca.uhn.hl7v2.model.v25.datatype.TS;
import ca.uhn.hl7v2.model.v25.datatype.TX;
import ca.uhn.hl7v2.model.v25.segment.TQ1;
import com.abada.contramed.persistence.entity.MeasureUnit;
import com.abada.contramed.persistence.entity.Order1;
import com.abada.contramed.persistence.entity.OrderTiming;
import java.math.BigDecimal;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Parseador especifico par el segmento TQ1 de un mensaje HL7, es especifico de
 * la información con la que trabaja el HIS Selene.<br/>
 * El segmento TQ1 contiene la informacion especifica de cada toma relativa a tratamiento.
 * @author katsu
 */
class SeleneTQ1Parser {
    //   2.1   2.2  3.1                      4.1  5.1   5.2   7.1               11.1
//TQ1||575.0^150|Q1D&Cada 12 horas&&50218|0900|575.0^150||20090731100000||||Cada 12 horas

    private static final Log log = LogFactory.getLog(SelenePIDParser.class);

    void parseTQ1(TQ1 tq1, Order1 order) {

        OrderTiming orderTiming = new OrderTiming();

        //2.1 order_timing.give_amount  Cantidad a administrar
        CQ cq = tq1.getTq12_Quantity();
        String cantidadAdministrar = "";
        if (cq != null && cq.getCq1_Quantity() != null && cq.getCq1_Quantity().getValue() != null && !"".equals(cq.getCq1_Quantity().getValue())) {
            cantidadAdministrar = cq.getCq1_Quantity().getValue();
            orderTiming.setGiveAmount(new BigDecimal(cantidadAdministrar));
        }
        //2.2 order_timing measure_unit_idmeasure_unit Unidad de administración
        String unidadAdministracion = "";
        if (cq != null && cq.getCq2_Units() != null && cq.getCq2_Units().getCe1_Identifier() != null && cq.getCq2_Units().getCe1_Identifier().getValue() != null && !"".equals(cq.getCq2_Units().getCe1_Identifier().getValue())) {
            MeasureUnit measureUnit = new MeasureUnit();
            unidadAdministracion = cq.getCq2_Units().getCe1_Identifier().getValue();
            measureUnit.setIdmeasureUnit(Utils.toInteger(unidadAdministracion));
            orderTiming.setMeasureUnitIdmeasureUnit(measureUnit);

        }
        //3.1 order_timing.repetition_pattern
        RPT[] rptarray = tq1.getTq13_RepeatPattern();
        if (rptarray != null && rptarray.length > 0) {
            CWE cwe = rptarray[0].getRpt1_RepeatPatternCode();
            if (cwe != null && cwe.getCwe1_Identifier() != null && cwe.getCwe1_Identifier().getValue() != null && !"".equals(cwe.getCwe1_Identifier().getValue())) {
                orderTiming.setRepetitionPattern(cwe.getCwe1_Identifier().getValue());
            }
        }


        //5.1 order_timing give_amount
        //5.2 order_timing measure_unit_idmeasure_unit
        //Debe ser igual que 2.1 y 2.2 respectivamente. O no aparecer
        CQ[] cqarray = tq1.getTq15_RelativeTimeAndUnits();
        if (cqarray != null && cqarray.length > 0) {
            comprobariguales(cqarray[0], cantidadAdministrar, unidadAdministracion);
        }

        //6.1 order_timing duration_time Duración de tratamiento en horas
        cq = tq1.getTq16_ServiceDuration();
        if (cq != null && cq.getCq1_Quantity() != null && cq.getCq1_Quantity().getValue() != null && !"".equals(cq.getCq1_Quantity().getValue())) {
            orderTiming.setDurationTime(Utils.toInteger(cq.getCq1_Quantity().getValue()));
        }

        //7.1 order_timing start_date Fecha de comienzo del tratamiento
        TS ts = tq1.getTq17_StartDateTime();
        if (ts != null && ts.getTs1_Time() != null && ts.getTs1_Time().getValue() != null && !"".equals(ts.getTs1_Time().getValue())) {
            orderTiming.setStartDate(Utils.toDate(ts.getTs1_Time().getValue()));
        }

        //8.1 order_timing end_date Fecha de fin del tratamiento

        ts = tq1.getTq18_EndDateTime();
        if (ts != null && ts.getTs1_Time() != null && ts.getTs1_Time().getValue() != null && !"".equals(ts.getTs1_Time().getValue())) {
            orderTiming.setEndDate(Utils.toDate(ts.getTs1_Time().getValue()));
        }

        //10.1 order_timing if_necesary Condición de solo administrar el tratamiento en caso de necesidad
        TX tx = tq1.getTq110_ConditionText();
        if (tx != null && tx.getValue() != null && !"".equals(tx.getValue())) {
            orderTiming.setIfNecesary(Utils.toBoolean(tx.getValue()));
        }

        //11.1 order_timin Instruction Texto de instrucciones para la administración
        tx = tq1.getTq111_TextInstruction();
        if (tx != null && tx.getValue() != null && !"".equals(tx.getValue())) {
            orderTiming.setInstructions(tx.getValue());
        }

        // 4.1 order_timing Time
        TM[] tmarray = tq1.getTq14_ExplicitTime();
        if (tmarray != null) {
            for (TM tm : tmarray) {
                OrderTiming aux = (OrderTiming) orderTiming.clone();
                if (tm.getValue() != null && !tm.getValue().equals("")) {
                    aux.setTime(Utils.toTime(tm.getValue()));
                    order.addOrderTiming(aux);
                }
            }
        }

        //order.addOrderTiming(orderTiming);
    }

    /**
     * comprobamos que sean iguales a 2.1 y 2.2 si no son iguales a null y son distintos mostrar un mensaje de error
     * @param cq
     * @param cantidadAdministrar
     * @param unidadAdministracion
     */
    private void comprobariguales(CQ cq, String cantidadAdministrar, String unidadAdministracion) {
        if (cq.getCq1_Quantity() != null && cq.getCq1_Quantity().getValue() != null && !"".equals(cq.getCq1_Quantity().getValue())) {
            if (!"".equals(cantidadAdministrar)) {
                if (!cq.getCq1_Quantity().getValue().equals(cantidadAdministrar)) {
                    log.error(SeleneTQ1Parser.class.getName()
                            + " give_amount no es iguales 2.1: " + cantidadAdministrar
                            + " 5.1: " + cq.getCq1_Quantity().getValue());
                }
            }
        }
        if (cq != null && cq.getCq2_Units() != null && cq.getCq2_Units().getCe1_Identifier() != null && cq.getCq2_Units().getCe1_Identifier().getValue() != null && !"".equals(cq.getCq2_Units().getCe1_Identifier().getValue())) {
            if (!"".equals(unidadAdministracion)) {
                if (!cq.getCq2_Units().getCe1_Identifier().getValue().equals(unidadAdministracion)) {
                    log.error(SeleneTQ1Parser.class.getName()
                            + " measure_unit_idmeasure_unit no es iguales 2.2: " + unidadAdministracion
                            + " 5.2: " + cq.getCq2_Units().getCe1_Identifier().getValue());
                }
            }
        }
    }
}
