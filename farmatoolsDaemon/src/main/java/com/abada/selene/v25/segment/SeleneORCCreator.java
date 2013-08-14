/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.selene.v25.segment;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.v25.datatype.EIP;
import ca.uhn.hl7v2.model.v25.datatype.PL;
import ca.uhn.hl7v2.model.v25.datatype.XCN;
import ca.uhn.hl7v2.model.v25.segment.ORC;
import com.abada.farmatools.object.Farmatools_Order;
import com.abada.selene.utils.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author aroldan
 *
 * Segmento ORC datos prescripcion 
 */
public class SeleneORCCreator implements ISegment<ORC, Farmatools_Order> {

    private static Log log = LogFactory.getLog(SeleneMSHCreator.class);

    /**
     * creación del segmento 
     * @param order
     * @param orc
     * @return
     */
    public ORC createSegment(Farmatools_Order order, ORC orc) {
        try {
            //1.1 order control; última acción que se realizó con la orden.
            if (order.getControl() != null) {
                orc.getOrc1_OrderControl().setValue(order.getControl());//Validos NW,XO,AC
            }
            //OrderID (ambos iguales)
            //2.1 Order.order_id; Identificador interno y único para la orden para SELENE
            //3.1 Order.order_id; Identificador interno y único para la orden en la aplicación de farmacia. Debe ser igual al de SELENE
            if (order.getControl() != null) {
                orc.getOrc2_PlacerOrderNumber().getEi1_EntityIdentifier().setValue(order.getOrderid()); //NUMEROHC_NPROCESO_LINEA
                orc.getOrc3_FillerOrderNumber().getEi1_EntityIdentifier().setValue(order.getOrderid());
            }
            //4.1 Order.order_hospital_id Identificador del hospital receptor de la orden
            if (order.getCod_centro() != null) {
                orc.getOrc4_PlacerGroupNumber().getEi1_EntityIdentifier().setValue(Integer.toString(order.getCod_centro())); //COD_CENTRO
            }
            //8.1 Order.Order_parent peticion padre
            //TODO no estoy seguro si esta bien mirarlo
            if (order.getOrderid() != null) {
                EIP parentOrder = orc.getOrc8_ParentOrder();
                parentOrder.getEip1_PlacerAssignedIdentifier().getEi1_EntityIdentifier().setValue(order.getOrderid());
            }
            //9.1 Order.event_date
            if (order.getFh_ult_mod() != null) {
                orc.getOrc9_DateTimeOfTransaction().getTs1_Time().setValue(Utils.dateToString(order.getFh_ult_mod()));
            }

            if (order.getLogin_usu() != null) {
                //10.1 Order.creation_user
                XCN enteredBy1 = orc.insertOrc10_EnteredBy(0);
                enteredBy1.getXcn1_IDNumber().setValue(order.getLogin_usu());


                //11.1 Order.validation_user
                XCN verifieddBy1 = orc.insertOrc11_VerifiedBy(0);
                verifieddBy1.getXcn1_IDNumber().setValue(order.getLogin_usu());
            }

            if (order.getCod_medico() != null) {
                //12.1 Order.event_doctor
                XCN orderingProvider1 = orc.insertOrc12_OrderingProvider(0);
                orderingProvider1.getXcn1_IDNumber().setValue(Integer.toString(order.getCod_medico()));
            }

            //13.1 Order.creation_system
            PL enterSLocation = orc.getOrc13_EntererSLocation();
            enterSLocation.getPl1_PointOfCare().setValue("farmatoolsDaemon");

            //15.1 Order.start_date
            if (order.getFecha_ini() != null) {
                orc.getOrc15_OrderEffectiveDateTime().getTs1_Time().setValue(Utils.dateToString(order.getFecha_ini()));
            }

        } catch (HL7Exception ex) {
            log.error(ex.toString());
        } finally {
            return orc;
        }
    }
}
