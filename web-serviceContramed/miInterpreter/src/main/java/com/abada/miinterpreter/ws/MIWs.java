/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abada.miinterpreter.ws;


import ca.uhn.hl7v2.HL7Exception;
import com.abada.interpreterhl7.interpreter.Interpreter;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 * @author david
 */
@WebService()
public class MIWs {
    private Interpreter interpreter;

     public MIWs(Interpreter interpreter){
         this.interpreter=interpreter;
     }

    @WebMethod(action="parse")
    public String parse(String xml) throws HL7Exception{
        return interpreter.exec(xml);
    }
}
