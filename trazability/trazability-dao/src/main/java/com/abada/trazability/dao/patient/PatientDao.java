/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.trazability.dao.patient;

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

import ca.uhn.hl7v2.HL7Exception;
import com.abada.trazability.entity.Patient;
import com.abada.trazability.entity.PatientId;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest;
import java.util.List;

/**
 *
 * @author david
 *
 * Dao de la entidad Patient, trabajamos con los datos del paciente
 */
public interface PatientDao {

    /**
     * buscamos el paciente en nuestra base de datos
     * @param patient
     * @return
     * @throws HL7Exception
     */
    public List<Patient> findPatients(Patient patient) throws HL7Exception;

    /**
     * insertamos un paciente nuevo Adt_28
     * @param patient
     * @throws HL7Exception
     */
    public void save(Patient patient) throws HL7Exception;

    /**
     * modificamos un paciente actualizando los datos que nos vienen
     * de nuestra base de datos Adt_31 cambia tambien la cama y Adt_08 no cama
     * @param patient
     * @param bed
     * @throws HL7Exception
     */
    public void update(Patient patient, boolean bed) throws HL7Exception;

    /**
     * modificamos la cama del paciente
     * de nuestra base de datos Adt_69
     * @param patient
     * @param bed
     * @throws HL7Exception
     */
    public void updatebed(Patient patient) throws HL7Exception;

    /**
     * añadimos a patient los datos de oldpatientids vivos
     * @param patient
     * @param oldPatientIds
     * @throws HL7Exception
     */
    public void merge(Patient patient, List<PatientId> oldPatientIds) throws HL7Exception;

    /**
     * Modificamos los id de un paciente dado si updateid es true vamos a modificar los id de patient actuales <BR>
     *
     * @param patientchange paciente con los cambios que hay que hacer
     * @param patientsId el paciente con los id a encontra en nuestra base de datos
     * @param updateid
     * @throws HL7Exception
     */
    public void updateId(Patient patientchange, List<PatientId> patientsId, boolean updateid) throws HL7Exception;

    /**
     * intercambio de 2 camas dado dos pacientes
     * @param patients
     */
    public void swapBed(List<Patient> patients) throws HL7Exception;

    /**
     * borramos todos los pacientes
     */
    public void deleteAll();

    /**
     * devuelve el paciente que hay en una cama identificada con tag
     * si lo hay null si no hay nadie
     * @param tag Identificador de la cama
     * @return {@link Patient}
     */
    public Patient getPatientByBedTag(String tag);

    /**
     * devuelve el paciente que esta identificado con un tag
     * si lo hay null si no hay nadie
     * @param tag Identificador de la cama
     * @return {@link Patient}
     */
    public Patient getPatientByTag(String tag);

    /**
     *obtenemos la lista de todos los Patient
     * @return list {@link Patient}
     */
    public List<Patient> findAll();

    /**
     * borrado de patient a partir de una lista de id y si esta vivo o muerto
     * @param patientIds
     * @param exitus
     */
    public void deleteexitus(List<PatientId> patientIds, boolean exitus) throws HL7Exception;

    /**
     * borramos un paciente a partir de su lista de id
     * @param patient
     */
    public void deletepatientId(List<PatientId> patientIds) throws HL7Exception;

    /**
     * obtengo todos los pacientes vivos
     * @param filters
     * @return Long
     */
    public Long loadSizeAll(GridRequest filters);

    /**
     * cuenta los Patient
     * @param filters
     * @return List {@link Patient}
     */
    public List<Patient> loadAll(GridRequest filters);

    /**
     * translado de un paciente a otra cama
     * @param patient
     * @throws HL7Exception
     */
    public void TransferBed(Patient patient) throws HL7Exception;

    /**
     *  dar de alta a un paciente dejamos la cama libre donde esta
     * @param patient
     * @throws HL7Exception
     */
    public void alta(Patient patient) throws HL7Exception;

    /**
     *cancelamos el alta de un paciente al cual se le acaban de dar; es asociarle su cama de ingreso
     * @param patient
     * @throws HL7Exception
     */
    public void cancelAlta(Patient patient) throws HL7Exception;

    /**
     * admisión de un paciente asociandole una cama para el ingreso
     * @param patient
     * @throws HL7Exception
     */
    public void admission(Patient patient) throws HL7Exception;

    /**
     * cancelación admisión de un paciente
     * @param patient
     * @throws HL7Exception
     */
    public void cancelAdmission(Patient patient) throws HL7Exception;

    /**
     * transpaso a otra cama.
     * @param patient
     * @param patientsIdChanges
     * @throws HL7Exception
     */
    public void relayed(Patient patient, List<PatientId> patientsIdChanges) throws HL7Exception;

    /**
     * devuelve el paciente que esta identificado con un tag
     * si lo hay null si no hay nadie
     * @param tag Identificador de la cama
     * @return {@link Patient}
     */
    public Patient getPatientByBedId(Long id);
}
