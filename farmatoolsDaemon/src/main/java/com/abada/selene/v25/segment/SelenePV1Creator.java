/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.selene.v25.segment;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.v25.datatype.PL;
import ca.uhn.hl7v2.model.v25.segment.PV1;
import com.abada.farmatools.object.Farmatools_Patient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author david
 *
 * Segmento Pv1 Cama del paciente
 */
public class SelenePV1Creator implements ISegment<PV1, Farmatools_Patient> {

    private static Log log = LogFactory.getLog(SeleneMSHCreator.class);

    /**
     * creación Segmento con la cama del paciente
     * @param p
     * @param pv1
     * @return
     */
    public PV1 createSegment(Farmatools_Patient p, PV1 pv1) {
        try {
            if (p.getPaciente_ncama() != null) {
                PL pl = pv1.getPv13_AssignedPatientLocation();
                pl.getPl3_Bed().setValue(p.getPaciente_ncama());
            }
        } catch (HL7Exception ex) {
            log.error(ex.toString());
        } finally {
            return pv1;
        }


    }
}
