package com.abada.contramed.persistence.dao.patientId;

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
import com.abada.contramed.persistence.dao.tablez029.Tablez029Dao;
import com.abada.contramed.persistence.entity.Patient;
import com.abada.contramed.persistence.entity.PatientId;
import com.abada.contramed.persistence.entity.Tablez029;
import java.util.ArrayList;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author david
 *
 * Dao de la entidad PatientId, trabajamos con los datos de ids de pacientes
 */
@Repository("patientIdDao")
public class PatientIdDaoImpl extends JpaDaoSupport implements PatientIdDao {

    private Tablez029Dao tablez029Dao;

    /**
     *
     * @param entityManagerFactory
     */
    @Resource(name = "entityManager")
    public void setEntityManagerFactory2(EntityManagerFactory entityManagerFactory) {
        setEntityManagerFactory(entityManagerFactory);
    }

    /**
     *
     * @param tablez029Dao
     */
    @Resource(name = "tablez029Dao")
    public void setTablez029Dao(Tablez029Dao tablez029Dao) {
        this.tablez029Dao = tablez029Dao;
    }

    /**
     *obtenemos la lista de todos los PatientId
     * @return list {@link PatientId}
     */
    @Transactional(readOnly = true)
    @Override
    public List<PatientId> findAll() {
        return this.getJpaTemplate().findByNamedQuery("PatientId.findAll");
    }

    /**
     *Añadimos PatientId
     * @param patientId
     */
    @Transactional
    @Override
    public void save(PatientId patientId) {
        this.getJpaTemplate().persist(patientId);
    }

    /**
     * mira si se repiten id
     * @param patientId
     * @return int
     */
    @Transactional
    @Override
    public int patientIdRepe(List<PatientId> patientId) {
        for (int i = 0; i < patientId.size(); i++) {
            for (int a = i; a < patientId.size(); a++) {
                if (patientId.get(i).getType() != null && patientId.get(a).getType() != null
                        && patientId.get(i).getType().getCode().equals(patientId.get(a).getType().getCode())
                        && patientId.get(i).getValue().equals(patientId.get(a).getValue()) && a != i) {
                    return i;
                }

            }
        }
        return -1;
    }

    /**
     * encuentra en nuestra base de datos los pacientes que tengan
     * los id que nos pasan como el dni numero seguridad social, pasaporte etc
     * @param PatientsId
     * @return List {@link PatientId}
     */
    @Transactional
    @Override
    public List<Patient> findPatients(List<PatientId> PatientsId) {
        int append = 0;
        StringBuilder query = new StringBuilder();
        query.append("from Patient p where p.id in (select distinct pid.patientId.id from PatientId pid where ");
        for (PatientId p : PatientsId) {
            if (p.getValue() != null && !"".equals(p.getValue()) && p.getType() != null && p.getType().getCode() != null && !"".equals(p.getType().getCode())) {
                append++;
                if (append != 1) {
                    query.append(" or ");
                }
                query.append("pid.value='").append(p.getValue()).append("' and pid.type.code='").append(p.getType().getCode()).append("'");
            }
        }
        if (append != 0) {
            query.append(")");
            List<Patient> p = this.getJpaTemplate().find(query.toString());

            return p;

        }
        return null;
    }

    /**
     * encuentra en nuestra base de datos los pacientes que tengan los id que nos pasan como
     * el dni numero seguridad social, pasaporte etc y a elegir entre vivos o muertos
     * @param PatientsId
     * @param exitus
     * @return List {@link PatientId}
     */
    @Transactional
    @Override
    public List<Patient> findPatienstExitus(List<PatientId> PatientsId, boolean exitus) {
        List<Patient> patientexitus = new ArrayList();
        List<Patient> patients = findPatients(PatientsId);
        if (patients != null && !patients.isEmpty()) {
            for (Patient p : patients) {
                if (p.getExitus() == null && patients.size() == 1) {
                    patientexitus.add(p);//le añadimos por ser el unico encontrado
                } else if (p.getExitus() != null) {
                    if (p.getExitus().equals(exitus)) {
                        patientexitus.add(p);

                    }
                }
            }
        }
        return patientexitus;

    }

    /**
     * obtenemos de patientId el tipo de identificador dni numero seguridad social
     * y vamos a por su id para setearselo al paciente
     * @param patientid
     * @return {@link PatientId}
     * @throws HL7Exception
     */
    @Override
    @Transactional(readOnly = true)
    public PatientId patientIdbd(PatientId patientid) throws HL7Exception {
        if (patientid != null) {
            Tablez029 tipo = patientid.getType();
            if (tipo != null && tipo.getCode() != null) {
                List<Tablez029> ltipos = tablez029Dao.findByCode(tipo.getCode());
                if (ltipos != null) {
                    if (ltipos.size() == 1) {
                        patientid.setType(ltipos.get(0));
                    } else {
                        throw new HL7Exception(" Failure to add Identification Patient "
                                + "TYPE: " + patientid.getType().getDetails()
                                + "VALUE: " + patientid.getValue()
                                + "; TYPE in master table not found ", HL7Exception.APPLICATION_INTERNAL_ERROR);
                    }
                }

            }
        }
        return patientid;
    }

    /**
     *obtenemos la lista de PatientId a partir de id
     * @param id
     * @return list <PatientId>
     */
    @Transactional(readOnly = true)
    @Override
    public List<PatientId> findById(Long id) {
        Map<String, Long> parametros = new HashMap<String, Long>();
        parametros.put("id", id);
        return this.getJpaTemplate().findByNamedQueryAndNamedParams("PatientId.findById", parametros);
    }

    /**
     *obtenemos la lista de PatientId a partir de value
     * @param value
     * @return list <PatientId>
     */
    @Transactional(readOnly = true)
    @Override
    public List<PatientId> findByValue(String value) {
        Map<String, String> parametros = new HashMap<String, String>();
        parametros.put("value", value);
        return this.getJpaTemplate().findByNamedQueryAndNamedParams("PatientId.findByValue", parametros);
    }

    /**
     * obtenemos lista de PatientId a partir del id del paciente
     * @param patient
     * @return List {@link PatientId}
     */
    @Transactional(readOnly = true)
    public List<PatientId> findByPatientId(Long patient) {
        return this.getJpaTemplate().find("from PatientId p where p.patientId.id =?", patient);
    }
}
