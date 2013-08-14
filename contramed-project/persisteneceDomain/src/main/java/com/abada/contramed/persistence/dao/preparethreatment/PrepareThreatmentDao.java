/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.contramed.persistence.dao.preparethreatment;

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

import com.abada.contramed.persistence.entity.Patient;
import com.abada.contramed.persistence.entity.Staff;
import com.abada.webcontramed.exception.WebContramedException;
import java.util.Date;

/**
 *
 * @author katsu
 */
public interface PrepareThreatmentDao {

    /***
     * Ejecuta el proceso de añadir una dosis al cajetin de un paciente.
     * Cualquier problema con la dosis sera devuelto en la excepcion.
     * @param idDose Identificador de la dosis a preparar
     * @param patient Paciente al que se prepara la dosis
     * @param startDate Fecha de comienzo de rango de la toma
     * @param endDate Fecha de fin de rango de la toma
     * @param staff Personal del hospital que prepara la dosis
     * @param observations Observaciones escritas por parte del personal hospitalario
     * @throws WebContramedException posible excepcion si no se puede preparar la dosis por alguna razon
     */
    public void prepareDose(Long idDose, Patient patient, Date startDate, Date endDate, Staff staff, String... observations) throws WebContramedException;

    /***
     * Elimina una dosis del cajetin del tratamiento de un paciente en un periodo dado.
     * 
     * @param idDose Identificador de la dosis a prepara
     * @param patient Paciente al que se retira la dosis
     * @param startDate Fecha de comienzo de rango de la toma
     * @param endDate Fecha de fin de rango de la toma
     * @param staff Personal del hospital que retira la dosis
     * @param observations Observaciones escritas por parte del personal hospitalario
     */
    public void removeDosePharmacy(Long idDose, Patient patient, Date startDate, Date endDate, Staff staff, String... observations) throws WebContramedException;
    //FIXME Chapuza
    /***
     * Añade todo al cajetín si no esta añadido.
     * @param patient Paciente al que se prepara la dosis
     * @param startDate Fecha de comienzo de rango de la toma
     * @param endDate Fecha de fin de rango de la toma
     * @param staff Personal del hospital que prepara la dosis
     * @throws WebContramedException posible excepcion si no se puede preparar la dosis por alguna razon
     */
    public void prepareAll(Patient patient, Date startDate, Date endDate, Staff staff) throws WebContramedException;
}
