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
 * Cancelar Admisión de un paciente
 */
public class ADT_A11Action implements MessageAction {

    private static final Log logger = LogFactory.getLog(ADT_A11Action.class);
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
        return "ADT_A11";
    }

    /**
     * mensaje ADT_A11 cancelación de admisión (Ingreso) de un paciente
     * @param message
     * @throws HL7Exception
     */
    public void exec(Message message) throws HL7Exception {
        Patient patient = parser.toPatient(message);
        patientDao.cancelAdmission(patient);

    }

    /**
     * return the code of message Action ADT_A11
     * @return
     */
    public String getMessageCode() {
        return "ADT";
    }

    /**
     * return Trigger Event of message Action ADT_A11
     * @return
     */
    public String getMessageTriggerEvent() {
        return "A11";
    }

    /**
     * return the structure of message  Action ADT_A11
     * @return
     */
    public String getMessageStructure() {
        return "ADT_A09";
    }

    public String getMessageProcedence() {
        return null;
    }
}
