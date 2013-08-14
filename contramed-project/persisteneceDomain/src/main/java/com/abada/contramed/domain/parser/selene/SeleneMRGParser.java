/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.contramed.domain.parser.selene;

/*
 * #%L
 * Contramed
 * %%
 * Copyright (C) 2013 Abada Servicios Desarrollo (investigacion@abadasoft.com)
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import ca.uhn.hl7v2.model.v25.datatype.CX;
import ca.uhn.hl7v2.model.v25.segment.MRG;
import com.abada.contramed.persistence.entity.PatientId;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Parseador especifico par el segmento MRG de un mensaje HL7, es especifico de
 * la información con la que trabaja el HIS Selene.<br/>
 * El segmento MRG contiene identificadore de paciente principalmente.
 * @author david
 */
public class SeleneMRGParser {
    /**
     *
     */
    private static final Log log = LogFactory.getLog(SeleneMRGParser.class);

    /**
     * Parseo de la entidad patientId a partir del segmento mrg
     * @param mrg
     * @param patientIds
     */
    public void parseMRG(MRG mrg, List<PatientId> patientIds) {
        //1.1  y 1.5--> news PatientId  se puede repetir
        CX[] cxarray = mrg.getMrg1_PriorPatientIdentifierList();
        if (cxarray != null && cxarray.length > 0) {
            for (CX cxx : cxarray) {
                Utils.addPatientId(patientIds, cxx.getCx1_IDNumber().getValue(), cxx.getCx5_IdentifierTypeCode().getValue());
            }
        }
    }
}
