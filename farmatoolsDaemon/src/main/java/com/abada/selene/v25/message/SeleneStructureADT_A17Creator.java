/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.selene.v25.message;

import ca.uhn.hl7v2.model.v25.message.ADT_A17;
import ca.uhn.hl7v2.model.v25.segment.MSH;
import ca.uhn.hl7v2.model.v25.segment.PID;
import ca.uhn.hl7v2.model.v25.segment.PV1;
import com.abada.farmatools.object.Farmatools_Patient;
import com.abada.selene.v25.segment.SeleneMSHCreator;
import com.abada.selene.v25.segment.SelenePIDCreator;
import com.abada.selene.v25.segment.SelenePV1Creator;

/**
 *
 * @author david
 *
 * Generando Estructura Adt_A17 intercambio de camas
 * estructura que tiene el segmento Msh,Pid,pv1
 */
class SeleneStructureADT_A17Creator implements IMessage<ADT_A17, Farmatools_Patient> {

    public ADT_A17 exec(Farmatools_Patient object) {
        ADT_A17 message = new ADT_A17();
        MSH mshSegment = message.getMSH();
        PID pidSegment = message.getPID();
        PV1 pv1Segment = message.getPV1();
        //segundo paciente
        Farmatools_Patient p = new Farmatools_Patient();
        p.setNumerohc(object.getPatientswapnumerohc());
        p.setPaciente_ncama(object.getPatientswapcama());

        SeleneMSHCreator mshCreator = new SeleneMSHCreator();
        mshCreator.createSegment(object, mshSegment);
        SelenePIDCreator pidCreator = new SelenePIDCreator();
        pidCreator.createSegment(object, pidSegment);
        SelenePV1Creator pv1Creator = new SelenePV1Creator();
        pv1Creator.createSegment(object, pv1Segment);
        //el otro paciente para el intercambio de camas
        pidSegment = message.getPID2();
        pidCreator.createSegment(p, pidSegment);
        pv1Segment = message.getPV12();
        pv1Creator.createSegment(p, pv1Segment);

        return message;
    }
}
