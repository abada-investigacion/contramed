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
import ca.uhn.hl7v2.model.v25.datatype.DTM;
import ca.uhn.hl7v2.model.v25.datatype.EI;
import ca.uhn.hl7v2.model.v25.datatype.EIP;
import ca.uhn.hl7v2.model.v25.datatype.ID;
import ca.uhn.hl7v2.model.v25.datatype.IS;
import ca.uhn.hl7v2.model.v25.datatype.PL;
import ca.uhn.hl7v2.model.v25.datatype.ST;
import ca.uhn.hl7v2.model.v25.datatype.TQ;
import ca.uhn.hl7v2.model.v25.datatype.TS;
import ca.uhn.hl7v2.model.v25.datatype.XCN;
import ca.uhn.hl7v2.model.v25.datatype.XTN;
import ca.uhn.hl7v2.model.v25.segment.ORC;
import com.abada.contramed.persistence.entity.Order1;
import com.abada.contramed.persistence.entity.Table0119;
import com.abada.contramed.persistence.entity.TelephoneDoctor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Parseador especifico par el segmento ORC de un mensaje HL7, es especifico de
 * la información con la que trabaja el HIS Selene.<br/>
 * El segmento ORC contiene la informacion de general de un tratamiento.
 * @author katsu
 */
class SeleneORCParser {

    private static final Log log = LogFactory.getLog(SelenePIDParser.class);

    void parseORC(ORC orc, Order1 order) {

        //1.1 order control; última acción que se realizó con la orden.
        ID id = orc.getOrc1_OrderControl();
        if (id != null && id.getValue() != null && !"".equals(id.getValue())) {
            Table0119 table0119 = new Table0119();
            table0119.setCode(id.getValue());
            order.setControl(table0119);
        }

        //2.1 Order.order_id; Identificador interno y único para la orden para SELENE
        //3.1 Order.order_id; Identificador interno y único para la orden en la aplicación de farmacia. Debe ser igual al de SELENE

        EI ei = orc.getOrc2_PlacerOrderNumber();
        EI eifarma = orc.getOrc3_FillerOrderNumber();
        addOrderID(ei, eifarma, order);

        //4.1 Order.order_hospital_id Identificador del hostpital receptor de la orden
        ei = orc.getOrc4_PlacerGroupNumber();
        if (ei != null && ei.getEi1_EntityIdentifier() != null) {
            String order_hospital_id = ei.getEi1_EntityIdentifier().getValue();
            if (order_hospital_id != null && !"".equals(order_hospital_id)) {
                order.setOrderGroupId(order_hospital_id);
            }
        }

        //5.1 Order.order_status
        id = orc.getOrc5_OrderStatus();
        if (id != null && !"".equals(id.getValue())) {
            order.setOrderStatus(id.getValue());

        }

        //7.6 Order.Priority
        TQ[] tqarray = orc.getOrc7_QuantityTiming();
        if (tqarray != null && tqarray.length > 0) {
            if (tqarray[0].getTq6_Priority() != null && tqarray[0].getTq6_Priority().getValue() != null && !"".equals(tqarray[0].getTq6_Priority().getValue())) {
                order.setPriority(tqarray[0].getTq6_Priority().getValue());
            }
        }

        //8.1 Order.Order_parent peticion padre
        //TODO no estoy seguro si esta bien mirarlo
        EIP eip = orc.getOrc8_ParentOrder();
        if (eip != null) {
            ei = eip.getEip1_PlacerAssignedIdentifier();
            if (ei != null) {
                ST st = ei.getEi1_EntityIdentifier();

                if (st != null && st.getValue() != null && !"".equals(st.getValue())) {
                    order.setOrderFather(st.getValue());
                }
            }
        }
        //9.1 Order.event_date
        TS ts = orc.getOrc9_DateTimeOfTransaction();
        if (ts != null) {
            DTM dtm = ts.getTs1_Time();
            if (dtm != null && dtm.getValue() != null && !"".equals(dtm.getValue())) {
                order.setEventDate(Utils.toDate(dtm.getValue()));
            }
        }

        //10.1 Order.creation_user
        XCN[] xcnarray = orc.getOrc10_EnteredBy();
        if (xcnarray != null && xcnarray.length > 0) {
            if (xcnarray[0].getXcn1_IDNumber() != null && xcnarray[0].getXcn1_IDNumber().getValue() != null && !"".equals(xcnarray[0].getXcn1_IDNumber().getValue())) {
                order.setCreationEventUser(xcnarray[0].getXcn1_IDNumber().getValue());
            }
        }

        //11.1 Order.validation_user
        xcnarray = orc.getOrc11_VerifiedBy();
        if (xcnarray != null && xcnarray.length > 0) {
            if (xcnarray[0].getXcn1_IDNumber() != null &&xcnarray[0].getXcn1_IDNumber().getValue()!=null&& !"".equals(xcnarray[0].getXcn1_IDNumber().getValue())) {
                order.setValidationEventUser(xcnarray[0].getXcn1_IDNumber().getValue());
            }
        }

        //12.1 Order.event_doctor
        xcnarray = orc.getOrc12_OrderingProvider();
        if (xcnarray != null && xcnarray.length > 0) {
            if (xcnarray[0].getXcn1_IDNumber() != null &&xcnarray[0].getXcn1_IDNumber().getValue()!=null&& !"".equals(xcnarray[0].getXcn1_IDNumber().getValue())) {
                order.setEventDoctor(xcnarray[0].getXcn1_IDNumber().getValue());
            }
        }

        //13.1 Order.creation_system
        PL pl = orc.getOrc13_EntererSLocation();
        if (pl != null) {
            IS is = pl.getPl1_PointOfCare();
            if (is != null && is.getValue() != null && !"".equals(is.getValue())) {
                order.setCreationSystem(is.getValue());
            }
        }

        //14.1 order telephone_doctor.Telephone
        XTN[] xtnarray = orc.getOrc14_CallBackPhoneNumber();
        if (xtnarray != null && xtnarray.length > 0) {
            for (XTN xtn : xtnarray) {
                addTelephoneDoctor(order, xtn);
            }
        }

        //15.1 Order.start_date
        ts = orc.getOrc15_OrderEffectiveDateTime();
        if (ts != null) {
            if (ts.getTs1_Time() != null) {
                DTM dtm = ts.getTs1_Time();
                if (dtm != null && dtm.getValue() != null && !"".equals(dtm.getValue())) {
                    order.setStartDate(Utils.toDate(dtm.getValue()));
                }
            }
        }
        //16.1  Order.Reason  16.2 Order.Reason   Un append de ambos con ^
        CE ce = orc.getOrc16_OrderControlCodeReason();
        if (ce != null) {
            addReason(order, ce);
        }
    }

    /**
     * parseamos a la order el idorder
     * @param ei
     * @param eifarma
     * @param order
     */
    private void addOrderID(EI ei, EI eifarma, Order1 order) {
        String order_id = null;
        String order_idfar = null;
        if (ei != null && ei.getEi1_EntityIdentifier() != null) {
            order_id = ei.getEi1_EntityIdentifier().getValue();
        }

        if (eifarma != null && eifarma.getEi1_EntityIdentifier() != null) {
            order_idfar = eifarma.getEi1_EntityIdentifier().getValue();
        }

        if (order_id != null && order_idfar != null) {
            if (order_id.equals(order_idfar)) {
                if ((order_id != null && !"".equals(order_id)) && (order_idfar != null && !"".equals(order_idfar))) {
                    order.setOrderId(order_id);
                }

            } else {
                order.setOrderId(order_id);//seteamos el 2.1 pero no son iguales
                log.error(SeleneORCParser.class.getName() + " Order.order_id no son iguales");
            }
        } else if (order_id != null && order_idfar == null) {
            order.setOrderId(order_id);//el 2.1 si que tiene valor lo seteamos
        } else if (order_id == null && order_idfar != null) {
            order.setOrderId(order_idfar);//el 3.1 si que tiene valor lo seteamos
        }
    }

    /**
     * parseamos los datos de telefono del doctor
     * @param order
     * @param telefono
     * @param type
     */
    private void addTelephoneDoctor(Order1 order, XTN telefono) {
        String prefijo = "";
        TelephoneDoctor telephoneDoctor = new TelephoneDoctor();
        if (telefono.getXtn5_CountryCode() != null && telefono.getXtn5_CountryCode().getValue() != null) {
            prefijo = telefono.getXtn5_CountryCode().getValue();
        }
        if (telefono.getXtn1_TelephoneNumber() != null &&telefono.getXtn1_TelephoneNumber().getValue()!=null&& !"".equals(telefono.getXtn1_TelephoneNumber().getValue())) {
            telephoneDoctor.setTelephone(prefijo + telefono.getXtn1_TelephoneNumber().getValue());
            order.addTelephoneDoctor(telephoneDoctor);
        }

    }

    /**
     * añadimos la razon a la order order Un append de ambos con ^
     * @param order
     * @param orderReason
     */
    private void addReason(Order1 order, CE orderReason) {
        String reason = "";
        if (orderReason.getCe1_Identifier() != null && orderReason.getCe1_Identifier().getValue()!=null&&!"".equals(orderReason.getCe1_Identifier().getValue())) {
            reason = orderReason.getCe1_Identifier().getValue();
        }
        if (orderReason.getCe2_Text() != null &&orderReason.getCe2_Text().getValue()!=null&& !"".equals(orderReason.getCe2_Text().getValue())) {
            if (!"".equals(reason)) {
                reason = orderReason.getCe2_Text().getValue();
            } else {
                reason = reason + "^" + orderReason.getCe2_Text().getValue();
            }
        }
        if (!"".equals(reason)) {
            order.setReason(reason);
        }
    }
}
