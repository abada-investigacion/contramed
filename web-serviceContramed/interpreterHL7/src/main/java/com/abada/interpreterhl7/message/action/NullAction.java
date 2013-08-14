/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abada.interpreterhl7.message.action;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import com.abada.interpreterhl7.message.MessageAction;

/**
 *
 * @author katsu
 */
public class NullAction implements MessageAction{

    public String getMessageType() {
        return "";
    }

    public void exec(Message message) throws HL7Exception {
        
    }

    public String getMessageCode() {
        return null;
    }

    public String getMessageTriggerEvent() {
        return null;
    }

    public String getMessageStructure() {
         return null;
    }

    public String getMessageProcedence() {
        return null;
    }

}
