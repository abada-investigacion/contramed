/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abada.interpreterhl7.message;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;

/**
 * Interface that must implement all knows HL7 Messages
 *
 * @author katsu
 */
public interface MessageAction {
    /***
     * Must return the code of message that implement his action. MSH.9.1
     * Return null if is not necessary
     * @return
     */
    public String getMessageCode();
    /***
     * Must return the code of message that implement his action. MSH.9.2
     * Return null if is not necessary
     * @return
     */
    public String getMessageTriggerEvent();
    /***
     * Must return the structure of message that implement his action. MSH.9.3
     * Return null if is not necessary
     * @return
     */
    public String getMessageStructure();
    /**
     * Must return the application who generate the message that implement his action. MSH.4.1
     * Return null if is not necessary
     * @return
     */
    public String getMessageProcedence();
    /***
     * Execute the action
     * @param message
     * @throws HL7Exception
     * @return Message ACK HL7
     */
    public void exec(Message message) throws HL7Exception;
}
