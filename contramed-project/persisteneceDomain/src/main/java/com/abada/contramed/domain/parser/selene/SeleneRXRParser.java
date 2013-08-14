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
import ca.uhn.hl7v2.model.v25.segment.RXR;
import com.abada.contramed.persistence.entity.Order1;
import com.abada.contramed.persistence.entity.OrderMedication;
import com.abada.contramed.persistence.entity.Table0162;

/**
 * Parseador especifico par el segmento RXR de un mensaje HL7, es especifico de
 * la información con la que trabaja el HIS Selene.<br/>
 * El segmento RXR contiene la de la via de administracion del tratamiento.
 * @author katsu
 */
class SeleneRXRParser {

    /**
     * parseamos el segmento RXR en order
     * @param rxr
     * @param order
     */
    void parseRXR(RXR rxr, Order1 order) {

        OrderMedication orderMedication;
        if (order.getOrderMedication() == null) {
            orderMedication = new OrderMedication();
        } else {
            orderMedication = order.getOrderMedication();
        }

        //1.1 order.order_medication administration_type Vía de administración

        CE ce = rxr.getRxr1_Route();
        if (ce != null && ce.getCe1_Identifier() != null &&ce.getCe1_Identifier().getValue()!=null&& !"".equals(ce.getCe1_Identifier().getValue())) {
            Table0162 table0162 = new Table0162();
            table0162.setCode(ce.getCe1_Identifier().getValue());
            orderMedication.setAdministrationType(table0162);
        }else   if (ce != null && ce.getCe2_Text() != null &&ce.getCe2_Text().getValue()!=null&& !"".equals(ce.getCe2_Text().getValue())) {
            Table0162 table0162 = new Table0162();
            table0162.setDetails(ce.getCe2_Text().getValue());
            orderMedication.setAdministrationType(table0162);
        }

        order.setOrderMedication(orderMedication);
    }
}
