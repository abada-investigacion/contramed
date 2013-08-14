/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abada.interpreterhl7.message;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v25.message.ACK;
import java.io.IOException;

/**
 *
 * @author katsu
 */
public interface HL7Utils {
    public ACK generateACK(Message message,String acknowledgmentCode, HL7Exception hL7Exception) throws HL7Exception, IOException;
}
