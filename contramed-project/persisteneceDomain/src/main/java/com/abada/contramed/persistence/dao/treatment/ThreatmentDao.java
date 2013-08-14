/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.contramed.persistence.dao.treatment;

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

import com.abada.contramed.persistence.entity.GivesHistoric;
import com.abada.contramed.persistence.entity.PrepareHistoric;
import com.abada.webcontramed.entities.ThreatmentInfo;
import com.abada.webcontramed.entities.ThreatmentInfoHistoric;
import com.abada.webcontramed.exception.WebContramedException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author katsu
 */
public interface ThreatmentDao {
    /**
     * Devuelve la informacion necesaria sobre el tratamiento de un paciente en un periodo concreto
     * @param idPatient
     * @param startDate
     * @param endDate
     * @return
     */
    public List<ThreatmentInfo> getActiveThreatmentByPharmacy(Long idPatient, Date startDate, Date endDate) throws WebContramedException;
    /**
     * Devuelve la informacion necesaria sobre el tratamiento de un paciente desde un día concreto
     * Controla los fines de semana
     * @param idPatient
     * @param startTime
     * @return
     */
    public List<ThreatmentInfo> getActiveThreatmentByNursing(Long idPatient, Date startTime,Date endTime) throws WebContramedException;
    /**
     * Devuelve los PrepareHistoric que cuentan como añadidos. Elimina
     * todas las entradas de outbox e inbox que le correspondan
     * @param threatmentInfo
     * @return
     */
    public List<PrepareHistoric> getPrepareHistoric(ThreatmentInfo threatmentInfo);
    /**
     * Devuelve los GivesHistoric que cuentan como añadidos o los que no se han
     * llegado a administrar y se dejo constancia de ello. Elimina
     * todas las entradas duplicadas del trabajo de administrar o quitar
     * @param threatmentInfo
     * @return
     */
    public List<GivesHistoric> getGivesHistoric(ThreatmentInfo threatmentInfo);
    /***
     * Devuelve el historial de preparado del cajetin. a partir del ordertiming y
     * la fecha en la que se debe administrar el medicamento, no confundir con la
     * hora en la que se preparó.
     *
     * @param idOrderTiming
     * @param giveDate
     * @return
     */
    public List<ThreatmentInfoHistoric> getPrepareHistoric(Long idOrderTiming, Date giveDate);

    /***
     * Devuelve el historial de preparado del cajetin. a partir del ordertiming y
     * la fecha en la que se debe administrar el medicamento, no confundir con la
     * hora en la que se administró.
     *
     * @param idOrderTiming
     * @param giveDate
     * @return
     */
    public List<ThreatmentInfoHistoric> getGiveHistoric(Long idOrderTiming, Date giveDate);
}
