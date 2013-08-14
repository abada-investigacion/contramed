/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abada.interpreterhl7.interpreter;

import ca.uhn.hl7v2.HL7Exception;

/**
 *
 * @author katsu
 */
public interface Interpreter {
    /***
     * Execute the entrance message and return the ack message
     * @param message
     * @return
     */
    public String exec(String message) throws HL7Exception;
}
