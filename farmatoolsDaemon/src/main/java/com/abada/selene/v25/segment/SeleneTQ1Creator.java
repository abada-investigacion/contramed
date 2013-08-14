/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.selene.v25.segment;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.v25.datatype.CQ;
import ca.uhn.hl7v2.model.v25.datatype.RPT;
import ca.uhn.hl7v2.model.v25.datatype.TM;
import ca.uhn.hl7v2.model.v25.datatype.TS;
import ca.uhn.hl7v2.model.v25.datatype.TX;
import ca.uhn.hl7v2.model.v25.segment.TQ1;
import com.abada.farmatools.object.Farmatools_Order;
import com.abada.farmatools.object.Farmatools_OrderTiming;
import com.abada.selene.utils.Utils;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author aroldan
 *
 * Segmento Tq1 tiempos de medicación
 */
public class SeleneTQ1Creator implements ISegment<TQ1, Farmatools_Order> {

    private static Log log = LogFactory.getLog(SeleneMSHCreator.class);

    /**
     * creacion Segmento Tq1
     * @param order
     * @param tq1
     * @return
     */
    public TQ1 createSegment(Farmatools_Order order, TQ1 tq1) {
        try {

            //2.1 order_timing.give_amount  Cantidad a administrar
            //2.2 order_timing measure_unit_idmeasure_unit Unidad de administración
            CQ quantity = tq1.getTq12_Quantity();
            if (order.getGive_amount() != null) {
                quantity.getCq1_Quantity().setValue(Double.toString(order.getGive_amount()));
            }
            if (order.getId_measure_unit() != null) {
                quantity.getCq2_Units().getCe1_Identifier().setValue(Integer.toString(order.getId_measure_unit())); //Mg
            }
            //3.1 order_timing.repetition_pattern
            if (order.getRepetition_pattern() != null) {
                RPT repeatPattern = tq1.insertTq13_RepeatPattern(0);
                repeatPattern.getRpt1_RepeatPatternCode().getCwe1_Identifier().setValue(order.getRepetition_pattern());
            }

            //5.1 order_timing give_amount
            //5.2 order_timing measure_unit_idmeasure_unit
            //Debe ser igual que 2.1 y 2.2 respectivamente. O no aparecer
            if (order.getGive_amount() != null) {
                CQ relativeTimeAndUnits = tq1.insertTq15_RelativeTimeAndUnits(0);
                relativeTimeAndUnits.getCq1_Quantity().setValue(Double.toString(order.getGive_amount()));
                relativeTimeAndUnits.getCq2_Units().getCe1_Identifier().setValue(Integer.toString(order.getId_measure_unit()));//Mg
            }

            //6.1 order_timing duration_time Duración de tratamiento en horas
            if (order.getHora_duracion() != null) {
                CQ serviceDuration = tq1.getTq16_ServiceDuration();
                serviceDuration.getCq1_Quantity().setValue(Integer.toString(order.getHora_duracion()));
            }

            //7.1 order_timing start_date Fecha de comienzo del tratamiento
            if(order.getFecha_ini()!=null){
            TS startDateTime = tq1.getTq17_StartDateTime();
            startDateTime.getTs1_Time().setValue(Utils.dateToString(order.getFecha_ini()));
            }
            //8.1 order_timing end_date Fecha de fin del tratamiento
            if (order.getFecha_fin() != null) {
                TS endDateTime = tq1.getTq18_EndDateTime();
                endDateTime.getTs1_Time().setValue(Utils.dateToString(order.getFecha_fin()));
            }

            //10.1 order_timing if_necesary Condición de solo administrar el tratamiento en caso de necesidad
            if (order.getIf_necesary() != null) {
                TX conditionText = tq1.getTq110_ConditionText();
                conditionText.setValue(order.getIf_necesary()); //IF_NECESARY
            }

            //11.1 order_timin Instruction Texto de instrucciones para la administración
            if (order.getInstruccion() != null) {
                String instructionText = order.getInstruccion();
                if(order.getDescripcion_secuencia() != null) {
                    instructionText += "<br/>" + order.getDescripcion_secuencia();
                }
                TX textInstruction = tq1.getTq111_TextInstruction();
                textInstruction.setValue(instructionText);
            }

            // 4.1 order_timing Time
            List<Farmatools_OrderTiming> orderTimings = order.getOrderTimings();
            for (int i = 0; i < orderTimings.size(); i++) {
                TM explicitTime = tq1.insertTq14_ExplicitTime(i);
                explicitTime.setValue(Utils.timeToTime(orderTimings.get(i).getPre_paut_hora()));

            }

        } catch (HL7Exception ex) {
            log.error(ex.toString());
        } finally {
            return tq1;
        }
    }
}
