/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.selene.v25.segment;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.v25.datatype.CE;
import ca.uhn.hl7v2.model.v25.datatype.CX;
import ca.uhn.hl7v2.model.v25.datatype.DLN;
import ca.uhn.hl7v2.model.v25.datatype.ID;
import ca.uhn.hl7v2.model.v25.datatype.IS;
import ca.uhn.hl7v2.model.v25.datatype.TS;
import ca.uhn.hl7v2.model.v25.datatype.XAD;
import ca.uhn.hl7v2.model.v25.datatype.XPN;
import ca.uhn.hl7v2.model.v25.datatype.XTN;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ca.uhn.hl7v2.model.v25.segment.PID;
import com.abada.farmatools.object.Farmatools_Patient;
import com.abada.selene.utils.Utils;

/**
 *
 * @author aroldan
 *
 * Segmento Pid datos del paciente
 */
public class SelenePIDCreator implements ISegment<PID, Farmatools_Patient> {

    private static Log log = LogFactory.getLog(SeleneMSHCreator.class);

    /**
     * creación segmento pid
     * @param p
     * @param pid
     * @return
     */
    public PID createSegment(Farmatools_Patient p, PID pid) {
        try {
            //3.1 --> news PatientId
            int i = 0;
            CX patientIdentifier = pid.insertPid3_PatientIdentifierList(i);
            if (p.getNumerohc() != null) {
                patientIdentifier.getCx1_IDNumber().setValue(Integer.toString(p.getNumerohc()));
                patientIdentifier.getCx5_IdentifierTypeCode().setValue("PI");
                i++;
            }
            patientIdentifier = pid.insertPid3_PatientIdentifierList(i);
            if (p.getCip() != null) {
                patientIdentifier.getCx1_IDNumber().setValue(p.getCip());
                patientIdentifier.getCx5_IdentifierTypeCode().setValue("CIP");
            }
            //5.123 --> news Patient nombre apellidos
            XPN xpn = pid.insertPid5_PatientName(0);
            if (p.getApellid1() != null) {
                xpn.getXpn1_FamilyName().getSurname().setValue(p.getApellid1());
            }
            if (p.getNombre() != null) {
                xpn.getXpn2_GivenName().setValue(p.getNombre());
            }
            if (p.getApellid2() != null) {
                xpn.getXpn3_SecondAndFurtherGivenNamesOrInitialsThereof().setValue(p.getApellid2());
            }

            //7.1-->news Patient fecha nacimiento
            if (p.getFechanac() != null) {
                TS ts = pid.getPid7_DateTimeOfBirth();
                ts.getTs1_Time().setValue(Utils.dateToString(p.getFechanac()));
            }
            //8.1-->news Patient sexo
            if (p.getSexo() != null) {
                IS is = pid.getPid8_AdministrativeSex();
                is.setValue(p.getSexo());
            }
            //11.1-->news Address
            //11.2 numero
            //11.3-8 city
            if (p.getDireccion() != null) {
                XAD xad = pid.insertPid11_PatientAddress(0);
                xad.getXad1_StreetAddress().getStreetName().setValue(p.getDireccion());
                if (p.getCod_postal() != null) {
                    xad.getXad5_ZipOrPostalCode().setValue(p.getCod_postal());
                }
                if (p.getCod_poblacion() != null) {
                    xad.getXad3_City().setValue(Integer.toString(p.getCod_poblacion()));
                }
                if (p.getProvincia() != null) {
                    xad.getXad4_StateOrProvince().setValue(p.getProvincia());
                }
                if (p.getCod_pais() != null) {
                    xad.getXad6_Country().setValue(p.getCod_pais());
                }
            }

            //13.1-->news telephone fijo
            //13.5 prefijo telefono
            if (p.getTelefono() != null) {
                XTN xtn = pid.insertPid13_PhoneNumberHome(0);
                xtn.getXtn1_TelephoneNumber().setValue(p.getTelefono());
            }

            //16.1-->news patient
            if (p.getEstadocivil() != null) {
                CE ce = pid.getPid16_MaritalStatus();
                ce.getCe1_Identifier().setValue(p.getEstadocivil());
            }
            //20.1-- news patient_id Número del DNI por tanto el type a NNESP
            if (p.getNifdni() != null) {
                DLN dln = pid.getPid20_DriverSLicenseNumberPatient();
                dln.getDln1_LicenseNumber().setValue(p.getNifdni());
            }
            //30.1 exitus del paciente
            //FIXME suponemos que todos los pacientes estan vivos
            ID id = pid.getPid30_PatientDeathIndicator();
            id.setValue("N");

            //19.1-- news patient_id numero seguridad social
            /*ST st = pid.getPid19_SSNNumberPatient();
            st.setValue(p.getNumeroseguridadSocial());
            
            // 29.1 -- news patient
            ts = pid.getPid29_PatientDeathDateAndTime();
            ts.getTs1_Time().getValue();

            // 30.1 exitus del paciente
            ID id = pid.getPid30_PatientDeathIndicator();
            id.setValue();

            // 31.1 -- news patient waiting_exitus
            id = pid.getPid31_IdentityUnknownIndicator();
            id.setValue()
             */


        } catch (HL7Exception ex) {
            log.error(ex.toString());
        } finally {
            return pid;
        }


    }
}
