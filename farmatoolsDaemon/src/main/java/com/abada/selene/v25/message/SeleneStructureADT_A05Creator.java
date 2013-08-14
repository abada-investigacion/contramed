/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.selene.v25.message;

import ca.uhn.hl7v2.model.v25.message.ADT_A05;
import ca.uhn.hl7v2.model.v25.segment.MSH;
import ca.uhn.hl7v2.model.v25.segment.PID;
import ca.uhn.hl7v2.model.v25.segment.PV1;
import com.abada.farmatools.object.Farmatools_Patient;
import com.abada.selene.v25.segment.SeleneMSHCreator;
import com.abada.selene.v25.segment.SelenePIDCreator;
import com.abada.selene.v25.segment.SelenePV1Creator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author david
 *
 * Generando Adt_A05 estructura del mensajes A28 Creado historia clinica y A31 modificado datos demograficos paciente
 * estructura que tiene el segmento Msh,Pid,pv1
 */
public class SeleneStructureADT_A05Creator  implements IMessage<ADT_A05, Farmatools_Patient> {

    private static Log log = LogFactory.getLog(SeleneOMP09Creator.class);

    public ADT_A05 exec(Farmatools_Patient object) {
        ADT_A05 message = new ADT_A05();
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
