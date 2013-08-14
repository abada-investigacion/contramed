/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abada.contramed.persistence.dao.givethreatment;

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
import com.abada.contramed.persistence.entity.enums.TypeGetPatient;
import com.abada.contramed.persistence.entity.enums.TypeGivesHistoric;
import com.abada.webcontramed.exception.WebContramedException;
import java.util.Date;

/**
 *
 * @author katsu
 */
public interface GiveThreatmentDao {
     /**
      * Ejecuta el proceso de administrar una dosis al paciente.
      * Cualquier problema con la dosis sera devuelto en la excepcion.
      * @param idDose Identificador de la dosis a administrar
      * @param patient Paciente al que se administra la dosis
      * @param startDate Fecha de comienzo de rango de la toma
      * @param endDate Fecha de fin de rango de la toma
      * @param staff Personal del hospital que administra la dosis
      * @param typeGivesHistoric tipo de administracion de medicacion
      * @param typeGetPatient metodo por el que le leyo el paciente
      * @param observations Observaciones escritas por parte del personal hospitalario
      * @throws WebContramedException posible excepcion si no se puede administrar la dosis por alguna razon
      */
    public void giveDose(Long idDose, Patient patient, Date startDate, Date endDate,Staff staff,TypeGivesHistoric typeGivesHistoric, TypeGetPatient typeGetPatient,String... observations) throws WebContramedException;
    /**
     * quita una dosis administrada o no administra una dosis
      * @param idDose Identificador de la dosis a administrar
      * @param patient Paciente al que se administra la dosis
      * @param startDate Fecha de comienzo de rango de la toma
      * @param endDate Fecha de fin de rango de la toma
      * @param staff Personal del hospital que administra la dosis      
      * @param typeGetPatient metodo por el que le leyo el paciente
      * @param observations Observaciones escritas por parte del personal hospitalario
     * @param patientReadMethod
     */
    public void removeDoseNursing(Long idDose, Patient patient, Date startDate, Date endDate, Staff staff, TypeGetPatient typeGetPatient, String... observations) throws WebContramedException;
    /**
     * FIXME Cutrada
     * Administra todo lo que no este administrado de manera forzada. Aceptar todo.
     * @param patient Paciente al que se administra la dosis
     * @param startDate Fecha de comienzo de rango de la toma
     * @param endDate Fecha de fin de rango de la toma
     * @param staff Personal del hospital que administra la dosis
     * @param typeGetPatient metodo por el que le leyo el paciente
     */
    public void giveAll(Patient patient,Date startDate,Date endDate,Staff staff, TypeGetPatient typeGetPatient) throws WebContramedException;;
}
