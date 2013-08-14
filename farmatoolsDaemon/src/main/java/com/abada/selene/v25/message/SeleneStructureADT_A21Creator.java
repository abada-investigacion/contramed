/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.selene.v25.message;

import ca.uhn.hl7v2.model.v25.message.ADT_A21;
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
 * Generando Adt_A21 estructura del mensajes A29 borrado una historia clinica
 * estructura que tiene el segmento Msh,Pid,pv1
 */
class SeleneStructureADT_A21Creator  implements IMessage<ADT_A21, Farmatools_Patient> {


    public ADT_A21 exec(Farmatools_Patient object) {
        ADT_A21 message = new ADT_A21();
        MSH mshSegment = message.getMSH();
        PID pidSegment = message.getPID();
        PV1 pv1Segment = message.getPV1();
        SeleneMSHCreator mshCreator = new SeleneMSHCreator();
        mshCreator.createSegment(object, mshSegment);
        SelenePIDCreator pidCreator = new SelenePIDCreator();
        pidCreator.createSegment(object, pidSegment);
        SelenePV1Creator pv1Creator = new SelenePV1Creator();
        pv1Creator.createSegment(object, pv1Segment);

        return message;
    }
}
