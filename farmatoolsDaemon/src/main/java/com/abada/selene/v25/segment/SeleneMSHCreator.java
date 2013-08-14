/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.selene.v25.segment;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.v25.segment.MSH;
import com.abada.farmatools.object.Farmatools_Msh;
import com.abada.selene.utils.Utils;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author david
 *
 * Segmento Msh identificar los mensajes
 */
public class SeleneMSHCreator implements ISegment<MSH, Farmatools_Msh> {

    private static Log log = LogFactory.getLog(SeleneMSHCreator.class);

    /**
     * creado segmento msh
     * Code,tigger,structure
     *
     * @param farma
     * @param mshSegment
     * @return
     */
    public MSH createSegment(Farmatools_Msh farma, MSH mshSegment) {
        try {

            mshSegment.getFieldSeparator().setValue("|");
            mshSegment.getEncodingCharacters().setValue("^~\\&");
            mshSegment.getDateTimeOfMessage().getTime().setValue(Utils.dateToString(new Date()));
            mshSegment.getSendingApplication().getNamespaceID().setValue("Farmatools_Daemon");
            if (farma.getMessageCode() != null) {
                mshSegment.getMsh9_MessageType().getMessageCode().setValue(farma.getMessageCode());
            }
            if (farma.getTriggerEvent() != null) {
                mshSegment.getMsh9_MessageType().getTriggerEvent().setValue(farma.getTriggerEvent());
            }
            if (farma.getMessageStructure() != null) {
                mshSegment.getMsh9_MessageType().getMessageStructure().setValue(farma.getMessageStructure());
            }
            mshSegment.getMsh11_ProcessingID().getPt1_ProcessingID().setValue("P");
            //version
            mshSegment.getMsh12_VersionID().getVid1_VersionID().setValue("2.5");
            mshSegment.getMsh13_SequenceNumber().setValue("1");
            mshSegment.getMsh16_ApplicationAcknowledgmentType().setValue("AL");
            mshSegment.getMsh18_CharacterSet(0).setValue("ASCII");

        } catch (HL7Exception ex) {
            log.error(ex.toString());
        } finally {
            return mshSegment;
        }
    }
}
