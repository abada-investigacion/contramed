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

/**
 *
 * @author david
 *
 * Modificación de los datos de información de un paciente (no clinicos)
 */
public class ADT_A08Action implements MessageAction {

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
        return "ADT_A08";
    }

    /**
     * modificación de los datos paciente información
     * @param message
     * @throws HL7Exception
     */
    public void exec(Message message) throws HL7Exception {
        Patient patient = parser.toPatient(message);
        patientDao.update(patient,false);
    }

    /**
     * return the code of message Action ADT_A08
     * @return
     */
    public String getMessageCode() {
        return "ADT";
    }

    /**
     * return Trigger Event of message Action ADT_A08
     * @return
     */
    public String getMessageTriggerEvent() {
        return "A08";
    }

    /**
     * return the structure of message  Action ADT_A08
     * @return
     */
    public String getMessageStructure() {
        return "ADT_A01";
    }

    public String getMessageProcedence() {
        return null;
    }
}
