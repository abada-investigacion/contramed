/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.interpreterhl7.message.action;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import com.abada.contramed.domain.parser.Parser;
import com.abada.contramed.persistence.entity.Order1;
import com.abada.interpreterhl7.message.MessageAction;
import com.abada.contramed.persistence.dao.order.OrderDao;
import com.abada.contramed.persistence.entity.Patient;
import java.util.List;
import javax.annotation.Resource;

/**
 *
 * @author david
 *
 * Mensaje originales de Mambrino hacia farmacia sobre el tratamiento del paciente
 */
public class OMP_O09Action implements MessageAction {

    private Parser parser;
    @Resource(name = "orderDao")
    private OrderDao orderDao;

    /**
     *
     * @param orderDao
     */
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    /**
     *
     * @param parser
     */
    public void setParser(Parser parser) {
        this.parser = parser;
    }

    public String getMessageType() {
        return "OMP_O09";
    }

    /**
     *  tratamientos de un paciente que según venga el tratamiento de
     * control modificamos insertamos, cancelamos etc.
     * @param message
     * @throws HL7Exception
     */
    public void exec(Message message) throws HL7Exception {
        List<Order1> orders = parser.toOrders(message);
        Patient patient = parser.toPatient(message);
        for (Order1 order : orders) {
            if (order != null) {
                if (order.getControl() != null && order.getControl().getCode() != null) {
                    orderDao.controlOrder(order, patient);
                } else {
                    throw new HL7Exception("order control not given; idorder: " + order.getOrderId(), HL7Exception.APPLICATION_INTERNAL_ERROR);
                }
            }
        }
        if(orders.isEmpty()){
             throw new HL7Exception("order not given" , HL7Exception.APPLICATION_INTERNAL_ERROR);

        }
    }

    /**
     * return the code of message Action OMP_010
     * @return
     */
    public String getMessageCode() {
        return "OMP";
    }

    /**
     * return Trigger Event of message Action OMP_010
     * @return
     */
    public String getMessageTriggerEvent() {
        return "O09";
    }

    /**
     * return the structure of message  ActionOMP_010
     * @return
     */
    public String getMessageStructure() {
        return null;
    }

    public String getMessageProcedence() {
        return null;
    }
}
