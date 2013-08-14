/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.interpreterhl7.interpreter.impl;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.Parser;
import com.abada.interpreterhl7.interpreter.Interpreter;
import com.abada.interpreterhl7.message.HL7Utils;
import com.abada.interpreterhl7.message.MessageAction;
import com.abada.interpreterhl7.message.MessageActionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author katsu
 */
public class DefaultInterpreter implements Interpreter {

    private static final Log logger = LogFactory.getLog(DefaultInterpreter.class);
    private Parser parser;
    private MessageActionFactory messageActionFactory;
    private HL7Utils hL7Utils;

    public void setParser(Parser parser) {
        this.parser = parser;
    }

    public void setMessageActionFactory(MessageActionFactory messageActionFactory) {
        this.messageActionFactory = messageActionFactory;
    }

    public void sethL7Utils(HL7Utils hL7Utils) {
        this.hL7Utils = hL7Utils;
    }

    public String exec(String message) throws HL7Exception {
        logger.debug("Incomming message: " + message);
        Message resultACK = null;
        Message entranceMessage = null;
        try {
            //primero fase de creacion del objeto Message de HAPI
            //HAPI realizará la validación
            entranceMessage = validateMessage(message);
            if (entranceMessage != null) {
                logger.debug("Validate message: " + entranceMessage.getName());
                //Cogeremos el MessageAction adecuado
                MessageAction messageAction = messageActionFactory.getMessageAction(entranceMessage);
                if (messageAction == null) {
                    resultACK = hL7Utils.generateACK(entranceMessage, "AE", new HL7Exception("Message not supported", HL7Exception.UNSUPPORTED_MESSAGE_TYPE));
                } else {
                    logger.debug("Message Action: " + messageAction.getMessageCode()+" "+messageAction.getMessageTriggerEvent()+" "+messageAction.getMessageStructure());
                    //Ejecutaremos la acción
                    messageAction.exec(entranceMessage);
                    logger.debug("Message Action execution ok.");
                    //Devolvermos el ACK
                    resultACK = hL7Utils.generateACK(entranceMessage, "AA", null);
                    logger.debug("Created ok ACK");
                }
            }else{
                resultACK = hL7Utils.generateACK(entranceMessage, "AE", new HL7Exception("Is not a recognible message", HL7Exception.SEGMENT_SEQUENCE_ERROR));
            }
        } catch (HL7Exception e) {
            //el mensaje no se ejecutó correctamente
            logger.error(e);
            try {
                resultACK = hL7Utils.generateACK(entranceMessage, "AE", e);
            } catch (Exception e2) {
                //error irrecuperable
                logger.fatal(e2);
            }
            //throw e;
        } catch (Exception e1) {
            //Error desconocido
            logger.error(e1);
            try {
                resultACK = hL7Utils.generateACK(entranceMessage, "AE", new HL7Exception(e1.getMessage(), HL7Exception.APPLICATION_INTERNAL_ERROR));
            } catch (Exception e2) {
                //error irrecuperable
                logger.fatal(e2);
            }
            //throw new HL7Exception(e1);
        }
        String result;
        try {
            result = parser.encode(resultACK);
        } catch (Exception e) {
            //TODO crear mensaje generico de ack de error
            result = "";
        }
        logger.debug("ACK result: " + result);
        return result;
    }

    private Message validateMessage(String message) throws HL7Exception {
        Message result = parser.parse(message);
        return result;
    }
}
