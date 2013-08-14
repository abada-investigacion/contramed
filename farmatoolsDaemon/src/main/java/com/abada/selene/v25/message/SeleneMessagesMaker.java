/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.selene.v25.message;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v25.datatype.CE;
import ca.uhn.hl7v2.model.v25.datatype.ELD;
import ca.uhn.hl7v2.model.v25.segment.ERR;
import ca.uhn.hl7v2.model.v25.segment.MSA;
import ca.uhn.hl7v2.parser.DefaultXMLParser;
import ca.uhn.hl7v2.parser.Parser;
import com.abada.miinterpreter.ws.MIWsService;
import com.abada.farmatools.object.Farmatools_Order;
import com.abada.farmatools.object.Farmatools_Patient;
import com.abada.farmatools.object.checks.ArticuloChecks;
import com.abada.farmatools.object.checks.DuracionChecks;
import com.abada.farmatools.object.checks.PrincipioActivoChecks;
import com.abada.farmatools.enums.Adt;
import com.abada.farmatools.object.checks.Adt17Checks;
import com.abada.farmatools.object.checks.AdtCamaChecks;
import java.net.URL;
import java.util.Iterator;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import javax.xml.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author aroldan
 *
 * Generamos mensaje omp_009 y ADT
 */
public class SeleneMessagesMaker {

    private static Log log = LogFactory.getLog(SeleneMessagesMaker.class);

    /**
     * creado de mensaje Omp_009 y validación y lanzados al webservice
     * @param farmatoolOrder
     * @return
     * @throws HL7Exception
     * @throws Exception
     */
    public boolean createListOMP_O09(Farmatools_Order farmatoolOrder, URL urlwebservice, String logOriginalOnInvalid) throws HL7Exception, Exception {

        SeleneOMP09Creator OMP09Creator = new SeleneOMP09Creator();
        Parser parser = new DefaultXMLParser();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        boolean validate = true;

        if (!validateOrder(validator, farmatoolOrder)) {
            validate = false;
        }
        if (farmatoolOrder.getModo_prescripcion() != null) {
            if (farmatoolOrder.getModo_prescripcion().equalsIgnoreCase("A")) {
                if (!validateOrder(validator, farmatoolOrder, ArticuloChecks.class)) {
                    validate = false;
                }
            } else if (farmatoolOrder.getModo_prescripcion().equalsIgnoreCase("P")) {
                if (!validateOrder(validator, farmatoolOrder, PrincipioActivoChecks.class)) {
                    validate = false;
                }
            }
        }
        if (farmatoolOrder.getFecha_fin() != null && farmatoolOrder.getHora_duracion() != null) {
            if (!validateOrder(validator, farmatoolOrder, DuracionChecks.class)) {
                validate = false;
            }
        }
        //If measure unit is assumed => warning the user
        if (farmatoolOrder.getId_measure_unit() != null) {
            if (farmatoolOrder.getMeasure_unit_en_mambrino() != null && farmatoolOrder.getMeasure_unit_en_mambrino().equalsIgnoreCase("N")) {
                log.warn("Asuming " + farmatoolOrder.getId_measure_unit() + " for id_measure_unit in message OMP_O09 with orderid: " + farmatoolOrder.getOrderid());
            }
        }
        if (validate) {
            String encodedXmlMessage = parser.encode(OMP09Creator.exec(farmatoolOrder));
            log.info("Generated OK message OMP_O09 orderid: " + farmatoolOrder.getOrderid() + " Control: " + farmatoolOrder.getControl());
            //conexion con client webservice
            clientwebService(encodedXmlMessage, urlwebservice, logOriginalOnInvalid);

        }
        return validate;
    }

    /**
     * validado por defecto de prescripcion
     * @param validator
     * @param farmatoolOrder
     * @return
     */
    private boolean validateOrder(Validator validator, Farmatools_Order farmatoolOrder) {
        return validateOrder(validator, farmatoolOrder, Default.class);
    }

    /**
     * Validado de una prescripción
     * @param validator
     * @param farmatoolOrder
     * @param group
     * @return
     */
    private boolean validateOrder(Validator validator, Farmatools_Order farmatoolOrder, Class<?> group) {
        boolean validate = true;
        Set<ConstraintViolation<Farmatools_Order>> constraintViolations = validator.validate(farmatoolOrder, group);
        if (constraintViolations.size() > 0) {
            validate = false;
            Iterator<ConstraintViolation<Farmatools_Order>> iterator = constraintViolations.iterator();
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append("\nError KO farmatoolOrder: ").append(farmatoolOrder.getOrderid()).append(" numerohc_nproceso_linea");

            while (iterator.hasNext()) {
                ConstraintViolation<Farmatools_Order> consViolation = iterator.next();
                errorMessage.append("\nError on ").append(consViolation.getRootBeanClass().getCanonicalName()).append(" ").append(consViolation.getPropertyPath()).append(" ").append(consViolation.getMessage());
                errorMessage.append("\n");
            }
            errorMessage.append("\n");
            log.error(errorMessage);
        }
        return validate;
    }

    /**
     * validado por defecto un paciente
     * @param validator
     * @param farmatoolPatient
     * @return
     */
    private boolean validatePatient(Validator validator, Farmatools_Patient farmatoolPatient) {
        return validatePatient(validator, farmatoolPatient, Default.class);
    }

    /**
     * Validado de un paciente
     * @param validator
     * @param farmatoolPatient
     * @param group
     * @return
     */
    private boolean validatePatient(Validator validator, Farmatools_Patient farmatoolPatient, Class<?> group) {
        boolean validate = true;
        Set<ConstraintViolation<Farmatools_Patient>> constraintViolations = validator.validate(farmatoolPatient, group);
        if (constraintViolations.size() > 0) {
            validate = false;
            Iterator<ConstraintViolation<Farmatools_Patient>> iterator = constraintViolations.iterator();
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append("\nError KO farmatoolPatient; numerohc : ").append(farmatoolPatient.getNumerohc());

            while (iterator.hasNext()) {
                ConstraintViolation<Farmatools_Patient> consViolation = iterator.next();
                errorMessage.append("\nError on ").append(consViolation.getRootBeanClass().getCanonicalName()).append(" ").append(consViolation.getPropertyPath()).append(" ").append(consViolation.getMessage());
                errorMessage.append("\n");
            }
            errorMessage.append("\n");
            log.error(errorMessage);
        }
        return validate;
    }

    /**
     * creación de los distintos mensajes Adt segun venga y lanzados al webservice
     * @param farmatoolpatient
     * @return
     * @throws HL7Exception
     * @throws Exception
     */
    public boolean createListADT(Farmatools_Patient farmatoolpatient, URL urlwebservice, String logOriginalOnInvalid) throws HL7Exception, Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        SeleneStructureADT_A01Creator seleneStructureADT_A01Creator = new SeleneStructureADT_A01Creator();
        SeleneStructureADT_A03Creator seleneStructureADT_A03Creator = new SeleneStructureADT_A03Creator();
        SeleneStructureADT_A05Creator seleneStructureADT_A05Creator = new SeleneStructureADT_A05Creator();
        SeleneStructureADT_A17Creator seleneStructureADT_A17Creator = new SeleneStructureADT_A17Creator();
        SeleneStructureADT_A21Creator seleneStructureADT_A21Creator = new SeleneStructureADT_A21Creator();
        SeleneStructureADT_A30Creator seleneStructureADT_A30Creator = new SeleneStructureADT_A30Creator();
        Parser parser = new DefaultXMLParser();
        String encodedXmlMessage = "";
        boolean valid = false;
        if (validatePatient(validator, farmatoolpatient)) {
            if (farmatoolpatient.getTriggerEvent().equals(Adt.A01.toString()) && validatePatient(validator, farmatoolpatient, AdtCamaChecks.class)) {
                log.info("Generated OK message ADT_A01 (ADMISSION) Patient numhc: " + farmatoolpatient.getNumerohc() + " bed: " + farmatoolpatient.getPaciente_ncama());
                valid = true;
                encodedXmlMessage = parser.encode(seleneStructureADT_A01Creator.exec(farmatoolpatient));
            } else if (farmatoolpatient.getTriggerEvent().equals(Adt.A02.toString()) && validatePatient(validator, farmatoolpatient, AdtCamaChecks.class)) {
                if (farmatoolpatient.getPatientswapcama() == null) {
                    log.info("Generated OK message ADT_A02 (TRANSFER BED) Patient numhc: " + farmatoolpatient.getNumerohc() + " bed: " + farmatoolpatient.getPaciente_ncama());
                } else {
                    log.info("Generated OK message ADT_A02 (TRANSFER BED) Patient numhc: " + farmatoolpatient.getNumerohc() + " old bed: " + farmatoolpatient.getPatientswapcama() + "-> bed: " + farmatoolpatient.getPaciente_ncama());
                }
                valid = true;
                encodedXmlMessage = parser.encode(seleneStructureADT_A01Creator.exec(farmatoolpatient));
            } else if (farmatoolpatient.getTriggerEvent().equals(Adt.A03.toString())) {
                log.info("Generated OK message ADT_A03 (ALTA) Patient numhc: " + farmatoolpatient.getNumerohc() + " free bed: " + farmatoolpatient.getPatientswapcama());
                valid = true;
                encodedXmlMessage = parser.encode(seleneStructureADT_A03Creator.exec(farmatoolpatient));
            } else if (farmatoolpatient.getTriggerEvent().equals(Adt.A17.toString())
                    && validatePatient(validator, farmatoolpatient, Adt17Checks.class) && validatePatient(validator, farmatoolpatient, AdtCamaChecks.class)) {
                log.info("Generated OK message ADT_A17 (BED SWAP) Patients numhc: " + farmatoolpatient.getNumerohc() + " bed: " + farmatoolpatient.getPaciente_ncama() + ", numhc: " + farmatoolpatient.getPatientswapnumerohc() + " bed: " + farmatoolpatient.getPatientswapcama());
                valid = true;
                encodedXmlMessage = parser.encode(seleneStructureADT_A17Creator.exec(farmatoolpatient));
            } else if (farmatoolpatient.getTriggerEvent().equals(Adt.A28.toString())) {
                if (farmatoolpatient.getPaciente_ncama() == null) {
                    log.info("Generated OK message ADT_A28 (NEW PATIENT) Patient numhc: " + farmatoolpatient.getNumerohc());
                } else {
                    log.info("Generated OK message ADT_A28 (NEW PATIENT) Patient numhc: " + farmatoolpatient.getNumerohc() + " bed: " + farmatoolpatient.getPaciente_ncama());
                }
                valid = true;
                encodedXmlMessage = parser.encode(seleneStructureADT_A05Creator.exec(farmatoolpatient));
            } else if (farmatoolpatient.getTriggerEvent().equals(Adt.A29.toString())) {
                log.info("Generated OK message ADT_A29 (DELETE PATIENT) Patient numhc: " + farmatoolpatient.getNumerohc());
                valid = true;
                encodedXmlMessage = parser.encode(seleneStructureADT_A21Creator.exec(farmatoolpatient));
            } else if (farmatoolpatient.getTriggerEvent().equals(Adt.A31.toString())) {
                log.info("Generated OK message ADT_A31 (UPDATE PATIENT DEMOGRAFIC) Patient numhc: " + farmatoolpatient.getNumerohc());
                valid = true;
                encodedXmlMessage = parser.encode(seleneStructureADT_A05Creator.exec(farmatoolpatient));
            } else if (farmatoolpatient.getTriggerEvent().equals(Adt.A47.toString())) {
                if (farmatoolpatient.getNumerohcanterior() == null) {
                    log.info("Generated OK message ADT_A47 (UPDATE ID PATIENT) Patient numhc: " + farmatoolpatient.getNumerohc());
                } else {
                    log.info("Generated OK message ADT_A47 (UPDATE ID PATIENT) Patient numhc: " + farmatoolpatient.getNumerohcanterior() + " new id numhc: " + farmatoolpatient.getNumerohc());
                }
                valid = true;
                encodedXmlMessage = parser.encode(seleneStructureADT_A30Creator.exec(farmatoolpatient));
            } else if (farmatoolpatient.getTriggerEvent().equals(Adt.A69.toString())) {
                log.info("Generated OK message ADT_A69 (UPDATE BED PATIENT) Patient numhc: " + farmatoolpatient.getNumerohc()+ " bed: " + farmatoolpatient.getPaciente_ncama());
                valid = true;
                encodedXmlMessage = parser.encode(seleneStructureADT_A01Creator.exec(farmatoolpatient));
            }
            if (valid) {
                clientwebService(encodedXmlMessage, urlwebservice, logOriginalOnInvalid);
            }

        }
        return valid;
    }

    /**
     * lanzado de mensajes al webservice capturando excepciones hl7 o exception
     * @param menssage
     * @throws HL7Exception
     * @throws Exception
     */
    private void clientwebService(String menssage, URL urlwebservice, String logOriginalOnInvalid) throws HL7Exception, Exception {
        String response;
        //log.trace(menssage);
        //conexion con client webservice
        QName qname = new QName("http://ws.miinterpreter.abada.com/", "MIWsService");
        MIWsService service = new MIWsService(urlwebservice, qname);
        response = service.getMIWsPort().parse(menssage);
        DefaultXMLParser XParser = new DefaultXMLParser();
        Message message = XParser.parse(response);
        MSA msa = (MSA) message.get("MSA");
        if (msa.getMsa1_AcknowledgmentCode() != null) {
            if (msa.getMsa1_AcknowledgmentCode().getValue().equalsIgnoreCase("AE")) {
                String exception = response;
                if (logOriginalOnInvalid.equalsIgnoreCase("TODO")) {
                    exception = "Generated invalid message :\n" + menssage + "\n\nInvalid response :\n" + exception;
                } else if (logOriginalOnInvalid.equalsIgnoreCase("ERROR")) {
                    ERR err = (ERR) message.get("ERR");
                    if (err.getErr1_ErrorCodeAndLocation().length > 0) {
                        ELD eld = err.getErr1_ErrorCodeAndLocation(0);
                        if (eld.getEld4_CodeIdentifyingError() != null) {
                            CE ce = eld.getEld4_CodeIdentifyingError();
                            if (ce.getCe5_AlternateText() != null && !"".equals(ce.getCe5_AlternateText().getValue())) {
                                exception = ce.getCe5_AlternateText().getValue();
                            }
                        }
                    }
                }
                throw new HL7Exception(exception);
            }
        }
    }
}
