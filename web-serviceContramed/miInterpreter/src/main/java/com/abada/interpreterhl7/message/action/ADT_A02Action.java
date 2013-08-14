/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.interpreterhl7.message.action;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import com.abada.contramed.domain.parser.Parser;
import com.abada.contramed.persistence.dao.patient.PatientDao;
import com.abada.contramed.persistence.entity.Patient;
import com.abada.interpreterhl7.message.MessageAction;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author david
 *
 * Translado de cama de un paciente
 */
public class ADT_A02Action implements MessageAction {

    private static final Log logger = LogFactory.getLog(ADT_A02Action.class);
    private Parser parser;
    private PatientDao patientDao;

    /**
     *
     * @param parser
     */
    public void setParser(Parser parser) {
        this.parser = parser;
    }

    /**
     *
     * @param patientDao
     */
    @Resource(name = "patientDao")
    public void setPatientDao(PatientDao patientDao) {
        this.patientDao = patientDao;
    }

    public String getMessageType() {
        return "ADT_A02";
    }

    /**
     * mensaje ADT_A02 Translado de cama de un paciente
     * @param message
     * @throws HL7Exception
     */
    public void exec(Message message) throws HL7Exception {
        Patient patient = parser.toPatient(message);
        patientDao.TransferBed(patient);

    }

    /**
     * return the code of message Action ADT_A02
     * @return
     */
    public String getMessageCode() {
        return "ADT";
    }

    /**
     * return Trigger Event of message Action ADT_A02
     * @return
     */
    public String getMessageTriggerEvent() {
        return "A02";
    }

    /**
     * return the structure of message  Action ADT_A02
     * @return
     */
    public String getMessageStructure() {
        return "ADT_A01";
    }

    public String getMessageProcedence() {
        return null;
    }
}
