/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.selene.v25.segment;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.v25.datatype.CX;
import ca.uhn.hl7v2.model.v25.segment.MRG;
import com.abada.farmatools.object.Farmatools_Patient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author david
 *
 * Segmento MRG id de pacientes
 */
public class SeleneMRGCreator implements ISegment<MRG, Farmatools_Patient> {

    private static Log log = LogFactory.getLog(SeleneMSHCreator.class);

    public MRG createSegment(Farmatools_Patient farma, MRG mrgSegment) {
        try {
            if (farma.getNumerohc() != null) {
                mrgSegment.getMrg1_PriorPatientIdentifierList();
                CX cx = mrgSegment.insertMrg1_PriorPatientIdentifierList(0);
                if (farma.getNumerohcanterior() == null) {
                    cx.getCx1_IDNumber().setValue(Integer.toString(farma.getNumerohc()));
                    cx.getCx5_IdentifierTypeCode().setValue("PI");
                } else {
                    cx.getCx1_IDNumber().setValue(Integer.toString(farma.getNumerohcanterior()));
                    cx.getCx5_IdentifierTypeCode().setValue("PI");
                }
            }
        } catch (HL7Exception ex) {
            log.error(ex.toString());
        } finally {
            return mrgSegment;
        }
    }
}
