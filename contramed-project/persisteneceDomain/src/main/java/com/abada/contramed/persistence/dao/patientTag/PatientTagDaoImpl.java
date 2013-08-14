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
import com.abada.springframework.orm.jpa.support.JpaDaoSupport;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author david
 */
@Repository("patientTagDao")
public class PatientTagDaoImpl extends JpaDaoSupport implements PatientTagDao {

    @Resource(name = "entityManager")
    public void setEntityManagerFactory2(EntityManagerFactory entityManagerFactory) {
        setEntityManagerFactory(entityManagerFactory);
    }

    /**
     *obtenemos la lista de todos los PatientTag
     * @return list <PatientTag>
     */
    @Transactional(readOnly = true)
    @Override
    public List<PatientTag> findAll() {
        return this.getJpaTemplate().findByNamedQuery("PatientTag.findAll");
    }

    /**
     *Añadimos PatientTag
     * @param PatientTag
     */
    @Transactional
    @Override
    public void save(PatientTag patientTag) {
        this.getJpaTemplate().persist(patientTag);
    }

    /**
     *obtenemos la lista de PatientTag a partir de patientId
     * @param patientId
     * @return list <PatientTag>
     */
    @Transactional(readOnly = true)
    @Override
    public List<PatientTag> findByPatientId(Long patientId) {
        Map<String, Long> parametros = new HashMap<String, Long>();
        parametros.put("patientId", patientId);
        return this.getJpaTemplate().findByNamedQueryAndNamedParams("PatientTag.findByPatientId", parametros);
    }

    /**
     *obtenemos la lista de PatientTag a partir de tag
     * @param tagcom.abada.contramed.persistence.dao.patientTag
     * @return list <PatientTag>
     */
    @Transactional(readOnly = true)
    @Override
    public List<PatientTag> findByTag(String tag) {
        Map<String, String> parametros = new HashMap<String, String>();
        parametros.put("tag", tag);
        return this.getJpaTemplate().findByNamedQueryAndNamedParams("PatientTag.findByTag", parametros);
    }

    /**
     * Inserccion de un nuevo PatientTag
     * @return
     */
    @Transactional
    public void insertPatientTag( Long idenpati,Patient p,String tag)throws Exception {
        
         PatientTag pat = new PatientTag();
         List tags = this.findByTag(tag);
         if (tags.isEmpty()) {
            //no Hay algun tag con ese valor
            pat.setTag(tag);
            pat.setPatientId(idenpati);
            pat.setPatient(p);
             //Comprobar si el paciente un tag asignado y si lo tiene modificar
            List<PatientTag> patientTags = this.findByPatientId(pat.getPatient().getId());
             if (patientTags.isEmpty()) {
                 this.getJpaTemplate().persist(pat);
             } else {
                 PatientTag aux = patientTags.get(0);
                 aux.setTag(pat.getTag());
             }
        }else{

             throw new Exception("Tag ya asignado anteriormente a otra persona");
        }

    }

    /**
     * Modificacion del Tag de un Patient_id
     * @param pat
     */
    @Transactional
    public void updatePatientTag(PatientTag pat) {
        this.getJpaTemplate().merge(pat);
    }

    /**
     * Borrar PatientTag
     * @param patientTag
     */
    @Transactional
    @Override
    public void removePatientTag(Long patientTag) throws Exception {
        PatientTag pat = this.getJpaTemplate().find(PatientTag.class, patientTag);
        //Referencia a Patient a null
        if (pat != null) {
            pat.getPatient().setPatientTag(null);
            this.getJpaTemplate().remove(pat);
        } else {
            throw new Exception("El paciente no tiene tag asignado");
        }
    }

    /**
     * Borrar PatientTag
     * @param patientTag
     */
    @Transactional
    @Override
    public void removePatientId(Long patientId) {
        PatientTag pat = this.getJpaTemplate().find(PatientTag.class, patientId);
        //Referencia a Patient a null
        if (pat != null) {
            pat.getPatient().setPatientTag(null);
            this.getJpaTemplate().remove(pat);
        }
    }
}
