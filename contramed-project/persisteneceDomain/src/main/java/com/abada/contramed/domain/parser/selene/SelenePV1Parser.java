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

import ca.uhn.hl7v2.model.v25.datatype.PL;
import ca.uhn.hl7v2.model.v25.segment.PV1;
import com.abada.contramed.persistence.entity.Patient;
import com.abada.contramed.persistence.entity.Recurso;

/**
 * Parseador especifico par el segmento PV1 de un mensaje HL7, es especifico de
 * la información con la que trabaja el HIS Selene.<br/>
 * El segmento PV1 contiene la informacion de la cama en la esta un paciente.
 * @author katsu
 */
class SelenePV1Parser {

    /**
     * parseo del segmento PV1 solo nos interesa la cama
     * @param pid
     * @param patient
     */
    public void parsePV1(PV1 pid, Patient patient) {
        //3.3 cama donde esta el paciente
        PL pl = pid.getPv13_AssignedPatientLocation();
        //6.3 cama donde estaba antes del cambio.
         //PL plaux = pid.getPv16_PriorPatientLocation();
        if (pl != null && pl.getPl3_Bed() != null) {
            String cama = pl.getPl3_Bed().getValue();
            if (cama != null && !"".equals(cama)) {
                Recurso bed;
                if (patient.getBed() == null) {
                    bed = new Recurso();
                } else {
                    bed = patient.getBed();
                }
                bed.setNr(cama);
                patient.setBed(bed);
            }
        }
    }
}
