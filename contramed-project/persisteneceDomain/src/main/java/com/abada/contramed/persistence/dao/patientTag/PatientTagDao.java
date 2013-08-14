package com.abada.contramed.persistence.dao.patientTag;

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
import com.abada.contramed.persistence.entity.PatientTag;
import java.util.List;

/**
 *
 * @author david
 */
public interface PatientTagDao {

    /**
     *obtenemos la lista de todos los PatientTag
     * @return list <PatientTag>
     */
    public List<PatientTag> findAll();

    /**
     *Añadimos PatientTag
     * @param PatientTag
     */
    public void save(PatientTag patientTag);

    /**
     *obtenemos la lista de PatientTag a partir de patientId
     * @param patientId
     * @return list <PatientTag>
     */
    public List<PatientTag> findByPatientId(Long patientId);

    /**
     *obtenemos la lista de PatientTag a partir de tag
     * @param tag
     * @return list <PatientTag>
     */
    public List<PatientTag> findByTag(String tag);

    /**
     * Inserccion de un nuevo PatientTag
     * @param pat
     */
    public void insertPatientTag(Long idenpati,Patient p,String tag) throws Exception;

    /**
     * Modificacion del Tag de un Patient_id
     * @param pat
     */
    public void updatePatientTag(PatientTag pat);

    /**
     * Borrar PatientTag
     * @param patientTag
     */
    public void removePatientTag(Long patientTag) throws Exception;

    /**
     * Borrar PatientTag
     * @param patientTag
     */
    public void removePatientId(Long patientId);
}
