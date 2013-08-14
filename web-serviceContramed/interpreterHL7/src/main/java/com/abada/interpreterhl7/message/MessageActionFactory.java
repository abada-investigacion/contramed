/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abada.interpreterhl7.message;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;

/**
 *
 * @author katsu
 */
public interface MessageActionFactory {
    public MessageAction getMessageAction(Message message) throws HL7Exception;
}
