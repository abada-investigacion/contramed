/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.selene.v25.message;

import ca.uhn.hl7v2.model.v25.message.ADT_A30;
import ca.uhn.hl7v2.model.v25.segment.MRG;
import ca.uhn.hl7v2.model.v25.segment.MSH;
import ca.uhn.hl7v2.model.v25.segment.PID;
import com.abada.farmatools.object.Farmatools_Patient;
import com.abada.selene.v25.segment.SeleneMSHCreator;
import com.abada.selene.v25.segment.SelenePIDCreator;
import com.abada.selene.v25.segment.SeleneMRGCreator;

/**
 *
 * @author david
 *
 * Generando Adt_A30 estructura del mensajes A47 modificado de id de pacientes
 * estructura que tiene el segmento Msh,Pid,Mrg
 */
class SeleneStructureADT_A30Creator implements IMessage<ADT_A30, Farmatools_Patient> {

    public ADT_A30 exec(Farmatools_Patient object) {
        ADT_A30 message = new ADT_A30();
        MSH mshSegment = message.getMSH();
        PID pidSegment = message.getPID();
        MRG mrgSegment = message.getMRG();
        SeleneMSHCreator mshCreator = new SeleneMSHCreator();
        mshCreator.createSegment(object, mshSegment);
        SelenePIDCreator pidCreator = new SelenePIDCreator();
        pidCreator.createSegment(object, pidSegment);
        SeleneMRGCreator mrgCreator = new SeleneMRGCreator();
        mrgCreator.createSegment(object, mrgSegment);
        return message;
    }
}
