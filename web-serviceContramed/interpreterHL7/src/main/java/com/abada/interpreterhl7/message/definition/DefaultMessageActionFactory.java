/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.interpreterhl7.message.definition;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v25.datatype.HD;
import ca.uhn.hl7v2.model.v25.datatype.MSG;
import ca.uhn.hl7v2.model.v25.segment.MSH;
import com.abada.interpreterhl7.message.MessageAction;
import com.abada.interpreterhl7.message.MessageActionFactory;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Find in applicaction context all message action
 * @author katsu
 */
public class DefaultMessageActionFactory implements MessageActionFactory {

    private static final Log logger = LogFactory.getLog(DefaultMessageActionFactory.class);
    private List<String> messageActionClasses;
    private List<MessageAction> messageActions;

    public void setMessageActions(List<MessageAction> messageActions) {
        this.messageActions = messageActions;
    }

    public void setMessageActionClasses(List<String> messageActionClasses) {

        this.messageActionClasses = messageActionClasses;
        generateMessageActions();
    }

    public DefaultMessageActionFactory() {
        super();
    }

    //@SuppressWarnings("static-access")
    public MessageAction getMessageAction(Message message) throws HL7Exception {
        MessageType messageType = this.getMessageType(message);
        logger.debug("Looking Message Action for " + messageType);
        for (MessageAction ma : messageActions) {
            if (isCorrectMessageAction(ma, messageType)) {
                return ma;
            }
        }
        return null;
    }

    private void generateMessageActions() {
        logger.debug("Generating Message Actions.");
        if (messageActions == null) {
            messageActions = new ArrayList<MessageAction>();

        } else {
            messageActions.clear();
        }
        if (messageActionClasses != null && messageActionClasses.size() > 0) {
            for (String clazz : messageActionClasses) {
                addNewInstance(clazz);
            }
        }
    }

    private MessageType getMessageType(Message message) throws HL7Exception {
        MessageType result = null;
        MSH msh = (MSH) message.get("MSH");
        if (msh != null) {
            MSG msg = msh.getMsh9_MessageType();
            if (msg != null) {
                result = new MessageType();
                if (msg.getMsg1_MessageCode() != null) {
                    result.setCode(msg.getMsg1_MessageCode().getValue());
                }
                if (msg.getMsg2_TriggerEvent() != null) {
                    result.setEvent(msg.getMsg2_TriggerEvent().getValue());
                }
                if (msg.getMsg3_MessageStructure() != null) {
                    result.setStructure(msg.getMsg3_MessageStructure().getValue());
                }

                HD hd=msh.getMsh4_SendingFacility();
                if (hd!=null){
                    if (hd.getHd1_NamespaceID()!=null && hd.getHd1_NamespaceID().getValue()!=null){
                        result.setGeneratedByApplication(hd.getHd1_NamespaceID().getValue());
                    }
                }
            }
        }
        return result;
    }

    private void addNewInstance(String clazz) {
        logger.debug("Generating " + clazz);
        try {
            Class add = this.getClass().getClassLoader().loadClass(clazz);
            if (add != null) {
                logger.debug("Instanciating " + clazz);
                try {
                    Object addObject = add.newInstance();
                    this.messageActions.add((MessageAction) addObject);

                } catch (IllegalAccessException e) {
                    logger.error(e);
                } catch (InstantiationException e1) {
                    logger.error(e1);
                } catch (ClassCastException e2) {
                    logger.error(e2);
                }
            }
        } catch (ClassNotFoundException e) {
            logger.error(e);
        }
    }

    private boolean isCorrectMessageAction(MessageAction ma, MessageType messageType) {
        //Code
        if (ma.getMessageCode() == null) {
            if (messageType.getCode()!=null) {
                return false;
            }
        } else {
            if (!ma.getMessageCode().equals(messageType.getCode())) {
                return false;
            }
        }
        //Structure
        if (ma.getMessageStructure() == null) {
            if (messageType.getStructure()!=null) {
                return false;
            }
        } else {
            if (!ma.getMessageStructure().equals(messageType.getStructure())) {
                return false;
            }
        }
        //Trigger event
        if (ma.getMessageTriggerEvent() == null) {
            if (messageType.getEvent()!=null) {
                return false;
            }
        } else {
            if (!ma.getMessageTriggerEvent().equals(messageType.getEvent())) {
                return false;
            }
        }
        //Sended by
        if (ma.getMessageProcedence() == null) {
            if (messageType.getGeneratedByApplication()!=null) {
                return false;
            }
        } else {
            if (!ma.getMessageProcedence().equals(messageType.getGeneratedByApplication())) {
                return false;
            }
        }
        return true;
    }
}

class MessageType {

    private String code;
    private String event;
    private String structure;
    private String generatedByApplication;

    public MessageType() {
        this.setCode(null);
        this.setEvent(null);
        this.setStructure(null);
        this.setGeneratedByApplication(null);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public String getGeneratedByApplication() {
        return generatedByApplication;
    }

    public void setGeneratedByApplication(String generatedByApplication) {
        this.generatedByApplication = generatedByApplication;
    }


}
