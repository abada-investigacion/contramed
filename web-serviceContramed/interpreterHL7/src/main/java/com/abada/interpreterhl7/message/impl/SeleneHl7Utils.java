/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abada.interpreterhl7.message.impl;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v25.message.ACK;
import com.abada.interpreterhl7.message.HL7Utils;
import java.io.IOException;

/**
 * HL7Utils is some group of methods with a especific implementations for SELENE
 * @author katsu
 */
public class SeleneHl7Utils implements HL7Utils{

    /***
     * Generate an ACK Message
     *
     * @param errorType
     * @param errorMessage
     * @return
     */
    public ACK generateACK(Message message,String acknowledgmentCode, HL7Exception hL7Exception) throws HL7Exception, IOException {
        ACK result;
        if (message==null){
            result=new ACK();
        }
        else
            result=(ACK) message.generateACK(acknowledgmentCode, hL7Exception);
        return result;
    }


}
