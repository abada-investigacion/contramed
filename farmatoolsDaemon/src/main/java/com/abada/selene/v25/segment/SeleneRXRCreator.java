/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.selene.v25.segment;

import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.v25.datatype.CE;
import ca.uhn.hl7v2.model.v25.segment.RXR;
import com.abada.farmatools.object.Farmatools_Order;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author aroldan
 *
 * Segmento RXR via Administración Medicamento
 */
public class SeleneRXRCreator implements ISegment<RXR, Farmatools_Order> {

    private static Log log = LogFactory.getLog(SeleneMSHCreator.class);

    /**
     * creación segmento RXR via de administración medicamento
     * @param order
     * @param rxr
     * @return
     */
    public RXR createSegment(Farmatools_Order order, RXR rxr) {

        try {
            //1.1 order.order_medication administration_type Vía de administración
            if (order.getCod_via() != null) {
                CE route = rxr.getRxr1_Route();
                route.getCe1_Identifier().setValue(Integer.toString(order.getCod_via())); //VIA ORAL
            }
        } catch (DataTypeException ex) {
            log.error(ex.toString());
        } finally {
            return rxr;
        }

    }
}
