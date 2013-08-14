/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abada.contramed.persistence.dao.incidence;

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

import com.abada.contramed.persistence.entity.Dose;
import com.abada.contramed.persistence.entity.GivesIncidence;
import com.abada.contramed.persistence.entity.Patient;
import com.abada.contramed.persistence.entity.PrepareIncidence;
import com.abada.contramed.persistence.entity.Staff;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest_Extjs_v3;
import com.abada.webcontramed.exception.WebContramedException;
import java.util.List;

/**
 *
 * @author katsu
 */
public interface IncidenceDao {
    /**
     * Inserta la incidencia MedicationNotGiven.
     * @param staff
     * @param idDose
     * @param patient
     */
    public void generateMedicationNotGivenException(Staff staff, Long idDose, Patient patient);
    /**
     * Crea una Gives Incidence
     * @param staff
     * @param dose
     * @param patient
     * @param idOrderTiming
     * @param exception
     */
    public void generateGiveIncidence(Staff staff,Dose dose,Patient patient,Long idOrderTiming,WebContramedException exception);
    /**
     * Crea una Prepare Incidence
     * @param staff
     * @param dose
     * @param patient
     * @param idOrderTiming
     * @param exception
     */
    public void generatePrepareIncidence(Staff staff,Dose dose,Patient patient,Long idOrderTiming,WebContramedException exception);
    /***
     * Devuelve el numvero total de Gives incidences que cumplen el filtro
     * @param filters
     * @return
     */
    public Long loadGivesIncidencesSizeAll(GridRequest_Extjs_v3 filters);
    /***
     * Devuelve el listado filtrado de GivesIncidences
     * @param filters
     * @return
     */
    public List<GivesIncidence> loadGivesIncidencesAll(GridRequest_Extjs_v3 filters);
    /***
     * Devuelve el numvero total de Prepare incidences que cumplen el filtro
     * @param filters
     * @return
     */
    public Long loadPrepareIncidencesSizeAll(GridRequest_Extjs_v3 filters);
    /***
     * Devuelve el listado filtrado de Prepare incidence
     * @param filters
     * @return
     */
    public List<PrepareIncidence> loadPrepareIncidencesAll(GridRequest_Extjs_v3 filters);
}
