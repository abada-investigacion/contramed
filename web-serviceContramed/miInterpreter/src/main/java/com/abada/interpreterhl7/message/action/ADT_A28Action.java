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
 * Añadir un historial clinico de un paciente por primera vez en nuestra base de datos
 */
public class ADT_A28Action implements MessageAction {

    private static final Log logger = LogFactory.getLog(ADT_A28Action.class);
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
        return "ADT_A28";
    }

    /**
     * mensaje ADT_A28 del inserción de un nuevo paciente en nuestra base de datos
     * @param message
     * @throws HL7Exception
     */
    public void exec(Message message) throws HL7Exception {

        Patient patient = parser.toPatient(message);
        patientDao.save(patient);



    }

    /**
     * return the code of message Action ADT_A28
     * @return
     */
    public String getMessageCode() {
        return "ADT";
    }

    /**
     * return Trigger Event of message Action ADT_A28
     * @return
     */
    public String getMessageTriggerEvent() {
        return "A28";
    }

    /**
     * return the structure of message  Action ADT_A28
     * @return
     */
    public String getMessageStructure() {
        return "ADT_A05";
    }

    public String getMessageProcedence() {
        return null;
    }
}
