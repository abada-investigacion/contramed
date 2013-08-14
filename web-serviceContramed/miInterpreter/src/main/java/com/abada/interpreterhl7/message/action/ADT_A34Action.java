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
import com.abada.contramed.persistence.entity.PatientId;
import com.abada.interpreterhl7.message.MessageAction;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author david
 *
 * Fusion de 2 pacientes, solo los id de identificación que es todo
 */
public class ADT_A34Action implements MessageAction {

    private static final Log logger = LogFactory.getLog(ADT_A34Action.class);
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
        return "ADT_A34";
    }

    /**
     * mensaje ADT_A34  igual que A40 en la version 2.5 no hace nada en la 2.3.1 hacia la fusion
     * Fusión de historia clínica<BR>
     * fusionamos ambos datos en patient despues borramos el segundo patient
     * que obtenemos con el segmento MRG para posteriormente pasar sus id a patient
     * @param message
     * @throws HL7Exception
     */
    public void exec(Message message) throws HL7Exception {
        //List<PatientId> patient2Ids = parser.toPatientIdsMRGSegment(message);
        //Patient patient = parser.toPatient(message);
        //patientDao.merge(patient, patient2Ids);
        //patientDao.deletepatientId(patient2Ids);
        //patientDao.updateId(patient, patient2Ids,false);
}

    /**
     * return the code of message Action ADT_A34
     * @return
     */
    public String getMessageCode() {
        return "ADT";
    }

    /**
     * return Trigger Event of message Action ADT_A34
     * @return
     */
    public String getMessageTriggerEvent() {
        return "A34";
    }

    /**
     * return the structure of message  Action ADT_A34
     * @return
     */
    public String getMessageStructure() {
        return "ADT_A30";
    }

    public String getMessageProcedence() {
        return null;
    }
}
