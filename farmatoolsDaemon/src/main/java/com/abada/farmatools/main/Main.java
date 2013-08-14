/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.farmatools.main;

import ca.uhn.hl7v2.HL7Exception;

import com.abada.farmatools.domain.CreateConection;
import com.abada.farmatools.enums.Adt;
import com.abada.farmatools.enums.OrderControl;
import com.abada.farmatools.object.Farmatools_Order;
import com.abada.farmatools.object.Farmatools_Patient;
import com.abada.selene.v25.message.SeleneMessagesMaker;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author david
 */
public class Main {

    private static Log log = LogFactory.getLog(Main.class);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {

        log.info("[START] Starting farmatools daemon at" + Calendar.getInstance().toString());
        StatisticsModule statistics = new StatisticsModule();
        statistics.pickStartTime();
        CreateConection BD = new CreateConection();
        boolean validado;
        Properties property = BD.getProperty();
        URL url = new URL(property.getProperty("urlwebservice"));
        String opcion = property.getProperty("farmatools.generarADTPrescription");
        String logOriginalOnInvalid = property.getProperty("LogOriginalOnInvalid");
        String bedrecursomaster = property.getProperty("bedFileMaster");
        SeleneMessagesMaker messagesCreator = new SeleneMessagesMaker();
        if (opcion.equalsIgnoreCase("ADT") || opcion.equalsIgnoreCase("ADTPrescription")) {
            List<Farmatools_Patient> farmatools_patient = BD.getFarmatools_Patient47();
            try {
                for (Farmatools_Patient farmatoolpatient : farmatools_patient) {
                    try {
                        validado = messagesCreator.createListADT(farmatoolpatient, url, logOriginalOnInvalid);
                        if (validado) {

                            if (farmatoolpatient.getTriggerEvent().equals(Adt.A47.toString())) {
                                BD.updatePatientA47(farmatoolpatient);
                                statistics.incOKADT(Adt.A47);
                            }
                        } else {
                            if (farmatoolpatient.getTriggerEvent().equals(Adt.A47.toString())) {
                                statistics.incKOADT(Adt.A47);
                            }
                        }
                    } catch (HL7Exception ex) {
                        log.error("\n" + ex.getMessage());
                        if (farmatoolpatient.getTriggerEvent().equals(Adt.A47.toString())) {
                            statistics.incInvalidADT(Adt.A47);
                        }
                    }
                }
            } catch (Exception ex) {
                log.error("\n" + ex.getMessage());
            }

        }
        if (opcion.equalsIgnoreCase("ADT") || opcion.equalsIgnoreCase("ADTPrescription")) {
            List<Farmatools_Patient> farmatools_patient = BD.getFarmatools_Patient();
            //chapuza para dar de alta a pacientes que pasen a una cama que no se encuentre en maestro recurso
            if (bedrecursomaster.equalsIgnoreCase("true")) {
                for (Farmatools_Patient farmatoolpatient : farmatools_patient) {
                    if (farmatoolpatient.getTriggerEvent().equalsIgnoreCase(Adt.A69.toString())) {
                        if (!BD.findrecurso(farmatoolpatient.getPaciente_ncama())) {
                            log.error("\n Error, message ADT_A69 ( BED) Patient numhc: " + farmatoolpatient.getNumerohc()
                                    + " (not found in Table Master) bed: " + farmatoolpatient.getPaciente_ncama() + " Generated ADT_A03 (ALTA Patient)");
                            farmatoolpatient.setTriggerEvent(Adt.A03.toString());
                            farmatoolpatient.setMessageStructure("ADT_A03");
                        }
                    }
                }
            }
            try {
                for (Farmatools_Patient farmatoolpatient : farmatools_patient) {
                    try {
                        validado = messagesCreator.createListADT(farmatoolpatient, url, logOriginalOnInvalid);
                        if (validado) {
                            if (farmatoolpatient.getTriggerEvent().equals(Adt.A01.toString())) {
                                BD.updatePatientbed(farmatoolpatient.getPaciente_ncama(), farmatoolpatient.getNumerohc(), farmatoolpatient.getCod_centro());
                                statistics.incOKADT(Adt.A01);
                            } else if (farmatoolpatient.getTriggerEvent().equals(Adt.A02.toString())) {
                                BD.updatePatientbed(farmatoolpatient.getPaciente_ncama(), farmatoolpatient.getNumerohc(), farmatoolpatient.getCod_centro());
                                statistics.incOKADT(Adt.A02);
                            } else if (farmatoolpatient.getTriggerEvent().equals(Adt.A03.toString())) {
                                BD.updatePatientbed(farmatoolpatient.getPaciente_ncama(), farmatoolpatient.getNumerohc(), farmatoolpatient.getCod_centro());
                                statistics.incOKADT(Adt.A03);
                            } else if (farmatoolpatient.getTriggerEvent().equals(Adt.A17.toString())) {
                                BD.updatePatientbed(farmatoolpatient.getPaciente_ncama(), farmatoolpatient.getNumerohc(), farmatoolpatient.getCod_centro());
                                BD.updatePatientbed(farmatoolpatient.getPatientswapcama(), farmatoolpatient.getPatientswapnumerohc(), farmatoolpatient.getCod_centro());
                                statistics.incOKADT(Adt.A17);
                            } else if (farmatoolpatient.getTriggerEvent().equals(Adt.A28.toString())) {
                                BD.insertPatient(farmatoolpatient);
                                statistics.incOKADT(Adt.A28);
                            } else if (farmatoolpatient.getTriggerEvent().equals(Adt.A29.toString())) {
                                BD.deletePatient(farmatoolpatient.getNumerohc());
                                statistics.incOKADT(Adt.A29);
                            } else if (farmatoolpatient.getTriggerEvent().equals(Adt.A31.toString())) {
                                BD.updatePatientA31(farmatoolpatient);
                                statistics.incOKADT(Adt.A31);
                            } else if (farmatoolpatient.getTriggerEvent().equals(Adt.A47.toString())) {
                                BD.updatePatientA47(farmatoolpatient);
                                statistics.incOKADT(Adt.A47);
                            } else if (farmatoolpatient.getTriggerEvent().equals(Adt.A69.toString())) {
                                BD.updatePatientbed(farmatoolpatient.getPaciente_ncama(), farmatoolpatient.getNumerohc(), farmatoolpatient.getCod_centro());
                                statistics.incOKADT(Adt.A69);
                            }
                        } else {
                            if (farmatoolpatient.getTriggerEvent().equals(Adt.A01.toString())) {
                                statistics.incKOADT(Adt.A01);
                            } else if (farmatoolpatient.getTriggerEvent().equals(Adt.A02.toString())) {
                                statistics.incKOADT(Adt.A02);
                            } else if (farmatoolpatient.getTriggerEvent().equals(Adt.A03.toString())) {
                                statistics.incKOADT(Adt.A03);
                            } else if (farmatoolpatient.getTriggerEvent().equals(Adt.A17.toString())) {
                                statistics.incKOADT(Adt.A17);
                            } else if (farmatoolpatient.getTriggerEvent().equals(Adt.A28.toString())) {
                                statistics.incKOADT(Adt.A28);
                            } else if (farmatoolpatient.getTriggerEvent().equals(Adt.A29.toString())) {
                                statistics.incKOADT(Adt.A29);
                            } else if (farmatoolpatient.getTriggerEvent().equals(Adt.A31.toString())) {
                                statistics.incKOADT(Adt.A31);
                            } else if (farmatoolpatient.getTriggerEvent().equals(Adt.A47.toString())) {
                                statistics.incKOADT(Adt.A47);
                            } else if (farmatoolpatient.getTriggerEvent().equals(Adt.A69.toString())) {
                                statistics.incKOADT(Adt.A69);
                            }
                        }
                    } catch (HL7Exception ex) {
                        log.error("\n" + ex.getMessage());
                        if (farmatoolpatient.getTriggerEvent().equals(Adt.A01.toString())) {
                            statistics.incInvalidADT(Adt.A01);
                        } else if (farmatoolpatient.getTriggerEvent().equals(Adt.A02.toString())) {
                            statistics.incInvalidADT(Adt.A02);
                        } else if (farmatoolpatient.getTriggerEvent().equals(Adt.A03.toString())) {
                            statistics.incInvalidADT(Adt.A03);
                        } else if (farmatoolpatient.getTriggerEvent().equals(Adt.A17.toString())) {
                            statistics.incInvalidADT(Adt.A17);
                        } else if (farmatoolpatient.getTriggerEvent().equals(Adt.A28.toString())) {
                            statistics.incInvalidADT(Adt.A28);
                        } else if (farmatoolpatient.getTriggerEvent().equals(Adt.A29.toString())) {
                            statistics.incInvalidADT(Adt.A29);
                        } else if (farmatoolpatient.getTriggerEvent().equals(Adt.A31.toString())) {
                            statistics.incInvalidADT(Adt.A31);
                        } else if (farmatoolpatient.getTriggerEvent().equals(Adt.A47.toString())) {
                            statistics.incInvalidADT(Adt.A47);
                        } else if (farmatoolpatient.getTriggerEvent().equals(Adt.A69.toString())) {
                            statistics.incInvalidADT(Adt.A69);
                        }
                    }
                }
                //borramos los numerohc que no esten en contramed ni en paci_his
                BD.deleteFarmatoolsPatient();
            } catch (Exception ex) {
                log.error("\n" + ex.getMessage());
            }

        }



        if (opcion.equalsIgnoreCase("Prescription") || opcion.equalsIgnoreCase("ADTPrescription")) {
            List<Farmatools_Order> farmatools_order = BD.getFarmatools_Order();
            try {
                for (Farmatools_Order farmatoolOrder : farmatools_order) {
                    try {
                        validado = messagesCreator.createListOMP_O09(farmatoolOrder, url, logOriginalOnInvalid);
                        if (validado) {
                            if (farmatoolOrder.getControl().equals(OrderControl.NW.toString())) {
                                BD.inserteorder(farmatoolOrder.getOrderid());
                                statistics.incOKOMP(OrderControl.NW);
                            } else if (farmatoolOrder.getControl().equals(OrderControl.XO.toString())) {
                                BD.updateorder(farmatoolOrder.getOrderid());
                                statistics.incOKOMP(OrderControl.XO);
                            } else if (farmatoolOrder.getControl().equals(OrderControl.HD.toString())) {
                                BD.updateorder(farmatoolOrder.getOrderid());
                                statistics.incOKOMP(OrderControl.HD);
                            } else if (farmatoolOrder.getControl().equals(OrderControl.RL.toString())) {
                                BD.updateorder(farmatoolOrder.getOrderid());
                                statistics.incOKOMP(OrderControl.RL);
                            } else if (farmatoolOrder.getControl().equals(OrderControl.CA.toString())) {
                                BD.deleteorder(farmatoolOrder.getOrderid());
                                statistics.incOKOMP(OrderControl.CA);
                            }
                        } else {
                            if (farmatoolOrder.getControl().equals(OrderControl.NW.toString())) {
                                statistics.incKOOMP(OrderControl.NW);
                            } else if (farmatoolOrder.getControl().equals(OrderControl.XO.toString())) {
                                statistics.incKOOMP(OrderControl.XO);
                            } else if (farmatoolOrder.getControl().equals(OrderControl.HD.toString())) {
                                statistics.incKOOMP(OrderControl.HD);
                            } else if (farmatoolOrder.getControl().equals(OrderControl.RL.toString())) {
                                statistics.incKOOMP(OrderControl.RL);
                            } else if (farmatoolOrder.getControl().equals(OrderControl.CA.toString())) {
                                statistics.incKOOMP(OrderControl.CA);
                            }
                        }
                    } catch (HL7Exception ex) {
                        log.error("\n" + ex.getMessage());
                        if (farmatoolOrder.getControl().equals(OrderControl.NW.toString())) {
                            statistics.incInvalidOMP(OrderControl.NW);
                        } else if (farmatoolOrder.getControl().equals(OrderControl.XO.toString())) {
                            statistics.incInvalidOMP(OrderControl.XO);
                        } else if (farmatoolOrder.getControl().equals(OrderControl.HD.toString())) {
                            statistics.incInvalidOMP(OrderControl.HD);
                        } else if (farmatoolOrder.getControl().equals(OrderControl.RL.toString())) {
                            statistics.incInvalidOMP(OrderControl.RL);
                        } else if (farmatoolOrder.getControl().equals(OrderControl.CA.toString())) {
                            statistics.incInvalidOMP(OrderControl.CA);
                        }
                    }

                }
            } catch (Exception ex) {
                log.error("\n" + ex.getMessage());
            }
        }
        BD.closeBDConnections();
        statistics.pickEndTime();
        log.info(statistics.toString());
        log.info("[END] farmatools daemon ended at " + Calendar.getInstance().toString());
    }
}
