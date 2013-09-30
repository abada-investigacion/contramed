package com.abada.trazability.dao.patientId;

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
import java.util.List;

/**
 *
 * @author david
 * 
 * Dao de la entidad PatientId, trabajamos con los datos de ids de pacientes
 */
public interface PatientIdDao {

    /**
     *obtenemos la lista de todos los PatientId
     * @return list <PatientId>
     */
    public List<PatientId> findAll();

    /**
     *Añadimos PatientId
     * @param patientId
     */
    public void save(PatientId patientId);

    /**
     * mira si se repiten id
     * @param patientId
     * @return int
     */
     public int patientIdRepe(List<PatientId> patientId);

    /**
     * encuentra en nuestra base de datos los pacientes que tengan
     * los id que nos pasan como el dni numero seguridad social, pasaporte etc
     * @param PatientsId
     * @return List {@link PatientId}
     */
    public List<Patient> findPatients(List<PatientId> PatientsId);

    /**
     * encuentra en nuestra base de datos los pacientes que tengan los id que nos pasan como
     * el dni numero seguridad social, pasaporte etc y a elegir entre vivos o muertos
     * @param PatientsId
     * @param exitus
     * @return List {@link PatientId}
     */
    public List<Patient> findPatienstExitus(List<PatientId> PatientsId, boolean exitus);

    /**
     * obtenemos de patientId el tipo de identificador dni numero seguridad social
     * y vamos a por su id para setearselo al paciente
     * @param patientid
     * @return {@link PatientId}
     * @throws HL7Exception
     */
    public PatientId patientIdbd(PatientId patientid) throws HL7Exception;

    /**
     *obtenemos la lista de PatientId a partir de id
     * @param id
     * @return List {@link PatientId}
     */
    public List<PatientId> findById(Long id);

    /**
     *obtenemos la lista de PatientId a partir de value
     * @param value
     * @return List {@link PatientId}
     */
    public List<PatientId> findByValue(String value);

    /**
     * obtenemos lista de PatientId a partir del id del paciente
     * @param patient
     * @return List {@link PatientId}
     */
    public List<PatientId> findByPatientId(Long patient);
}

