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
import com.abada.trazability.dao.city.CityDao;
import com.abada.trazability.dao.patientId.PatientIdDao;
import com.abada.trazability.dao.patientTag.PatientTagDao;
import com.abada.trazability.dao.recurso.RecursoDao;
import com.abada.trazability.dao.table0001.Table0001Dao;
import com.abada.trazability.dao.table0002.Table0002Dao;
import com.abada.trazability.dao.tablez029.Tablez029Dao;
import com.abada.trazability.entity.Address;
import com.abada.trazability.entity.City;
import com.abada.trazability.entity.Order1;
import com.abada.trazability.entity.Patient;
import com.abada.trazability.entity.PatientId;
import com.abada.trazability.entity.Recurso;
import com.abada.trazability.entity.Table0001;
import com.abada.trazability.entity.Table0002;
import com.abada.trazability.entity.Tablez029;
import com.abada.trazability.entity.Telephone;
import com.abada.springframework.orm.jpa.support.JpaDaoUtils;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

/**
 *
 * @author david
 *
 * Dao de la entidad Patient, trabajamos con los datos del paciente
 */
//@Repository("patientDao")
public class PatientDaoImpl extends JpaDaoUtils implements PatientDao {

    private CityDao cityDao;
    private Table0001Dao table0001Dao;
    private Table0002Dao table0002Dao;
    private PatientIdDao patientIdDao;
    private RecursoDao recursoDao;
    private PatientTagDao patientTagDao;
    private Tablez029Dao tablez029Dao;

    /**
     *
     * @param patientTagDao
     */
    @Resource(name = "patientTagDao")
    public void setPatientTagDao(PatientTagDao patientTagDao) {
        this.patientTagDao = patientTagDao;
    }

    /**
     *
     * @param cityDao
     */
    @Resource(name = "cityDao")
    public void setCityDao(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    /**
     *
     * @param patientIdDao
     */
    @Resource(name = "patientIdDao")
    public void setPatientIdDao(PatientIdDao patientIdDao) {
        this.patientIdDao = patientIdDao;
    }

    /**
     *
     * @param table0001Dao
     */
    @Resource(name = "table0001Dao")
    public void setTable0001Dao(Table0001Dao table0001Dao) {
        this.table0001Dao = table0001Dao;
    }

    /**
     *
     * @param table0002Dao
     */
    @Resource(name = "table0002Dao")
    public void setTable0002Dao(Table0002Dao table0002Dao) {
        this.table0002Dao = table0002Dao;
    }

    /**
     *
     * @param table0002Dao
     */
    @Resource(name = "tablez029Dao")
    public void setTablez029Dao(Tablez029Dao tablez029Dao) {
        this.tablez029Dao = tablez029Dao;
    }

    /**
     *
     * @param recursoDao
     */
    @Resource(name = "recursoDao")
    public void setRecursoDao(RecursoDao recursoDao) {
        this.recursoDao = recursoDao;
    }
    @PersistenceContext(unitName = "trazabilityPU")
    private EntityManager entityManager;
    

    /**
     * insertamos un paciente nuevo Adt_28
     * @param patient
     * @throws HL7Exception
     */
    @Transactional(value="trazability-txm")
    @Override
    public void save(Patient patient) throws HL7Exception {
        //TODO si el paciente viene su exitus a null le decimos que esta vivo
        if (patient.getExitus() == null) {
            patient.setExitus(Boolean.FALSE);
        }
        List<Patient> patients = findPatients(patient);
        if (patients.size() > 0 && patient.getExitus() == false) {
            if (patients.get(0).getPatientIdList() != null && !patients.get(0).getPatientIdList().isEmpty() && patient.getPatientIdList() != null && !patient.getPatientIdList().isEmpty()) {

                throw new HL7Exception("THERE IS MORE OF A PATIENT WITH THEIR DATA, lIVE "
                        + "PATIENT BD ID " + patients.get(0).getId() + ", " + patients.get(0).getPatientIdList().get(0).getValue()
                        + " (" + patientIdDescripcion(patients.get(0).getPatientIdList().get(0).getType().getCode()) + "); NEW PATIENT NO INSERT: " + patient.getPatientIdList().get(0).getValue()
                        + " (" + patientIdDescripcion(patient.getPatientIdList().get(0).getType().getCode()) + ")", HL7Exception.APPLICATION_INTERNAL_ERROR);
            }
        }
        int repe = patientIdDao.patientIdRepe(patient.getPatientIdList());
        if (repe != -1) {
            throw new HL7Exception("Patient Repeated with the same type Id " + patient.getPatientIdList().get(repe).getValue()
                    + "(" + patientIdDescripcion(patient.getPatientIdList().get(repe).getType().getCode()) + ")", HL7Exception.APPLICATION_INTERNAL_ERROR);
        }
        //si el paciente tiene cama vamos a por su idRecurso porque tenemos nr solo
        bedPatient(patient);
        //cambios en la ciudad del paciente
        if (patient.getAddressList() != null && !patient.getAddressList().isEmpty()) {
            cityAddress(patient);
        }
        if (patient.getBirthCity() != null) {
            List<City> lcity = cityDao.findByIdcity(patient.getBirthCity().getIdcity());
            if (lcity != null && lcity.size() == 1) {
                patient.setBirthCity(lcity.get(0));
            } else {
                patient.setBirthCity(null);
            }
        }
        if (patient.getPatientIdList() != null && !patient.getPatientIdList().isEmpty()) {

            //vamos a obtener el tipo de documento de idetificacion su id ej dni  NNESP id=1
            for (int i = 0; i < patient.getPatientIdList().size(); i++) {
                PatientId patientid = patientIdDao.patientIdbd(patient.getPatientIdList().get(i));
                patient.getPatientIdList().set(i, patientid);
            }
        } else {
            String p = "";
            if (patient.getName() != null) {
                p = patient.getName();
            }
            if (patient.getSurname1() != null) {
                p = p + " " + patient.getSurname1();
            }
            if (patient.getSurname2() != null) {
                p = p + " " + patient.getSurname2();
            }
            throw new HL7Exception("No Patient ID " + p, HL7Exception.APPLICATION_INTERNAL_ERROR);

        }
        //obtenemos id del genero de nuestra base de datos
        if (patient.getGenre() != null) {
            genrePatient(patient);
        }
        //obtenemos el id de nuestro estado civil de nuestra base de datos
        if (patient.getMaritalStatus() != null) {
            maritalStatus(patient);
        }
        this.entityManager.persist(patient);
    }

    /**
     * borramos un paciente a partir de su lista de id
     * @param patient
     */
    @Transactional(value="trazability-txm")
    public void deletepatientId(List<PatientId> patientIds) throws HL7Exception {
        delete(patientIdDao.findPatients(patientIds));

    }

    /**
     * borramos un paciente
     * @param patient
     */
    @Transactional(value="trazability-txm")
    private void delete(List<Patient> patient) throws HL7Exception {
        if (patient != null && patient.size() == 1) {
            Patient oldPatient = patient.get(0);
            if (oldPatient.getOrder1List() != null) {
                for (Order1 order : oldPatient.getOrder1List()) {
                    if (order.getOrderMedication() != null) {
                        this.entityManager.remove(order.getOrderMedication());
                    }
                }
            }
            this.entityManager.remove(oldPatient);
        } else {
            exception(patient, null, "for delete patient");
        }
    }

    /**
     * borramos todos los pacientes
     */
    @Transactional(value="trazability-txm")
    @Override
    public void deleteAll() {
        List<Patient> lpatient = this.entityManager.createNamedQuery("Patient.findAll").getResultList();
        for (Patient p : lpatient) {
            if (p.getOrder1List() != null) {
                for (Order1 order : p.getOrder1List()) {
                    if (order.getOrderMedication() != null) {
                        this.entityManager.remove(order.getOrderMedication());
                    }
                }
            }
            this.entityManager.remove(p);
        }
    }

    /**
     * modificamos la cama del paciente
     * de nuestra base de datos Adt_69
     * @param patient
     * @param bed
     * @throws HL7Exception
     */
    @Transactional(value="trazability-txm")
    @Override
    public void updatebed(Patient patient) throws HL7Exception {
        List<Recurso> rs;
        List<Patient> patients = findPatients(patient);
        if (patients != null && patients.size() == 1) {
            rs = this.entityManager.createQuery("select r from Recurso r where r.nr=:nr").setParameter("nr", patient.getBed().getNr()).getResultList();

            for (Recurso r : rs) {
                if (r.getPatientList() != null) {
                    for (Patient p : r.getPatientList()) {
                        p.setBed(null);
                    }
                    r.setPatientList(new ArrayList<Patient>());
                }
            }
            this.entityManager.merge(rs.get(0));
            this.entityManager.flush();

            if (patients.size() > 0 && rs.size() > 0) {
                rs.get(0).addPatient(patients.get(0));
            }
        } else {
            exception(patients, patient, "for update bed patient");
        }
    }

    /**
     * modificamos un paciente actualizando los datos que nos vienen
     * de nuestra base de datos Adt_31  
     * @param patient
     * @param bed
     * @throws HL7Exception
     */
    @Transactional(value="trazability-txm")
    @Override
    public void update(Patient patient, boolean bed) throws HL7Exception {
        List<Patient> patients = findPatients(patient);
        if (patients != null && patients.size() == 1) {
            Patient original = patients.get(0);
            updatePatientInformation(original, patient);
            updatePatientname(original, patient);// modificamos nombre y apellidos al paciente
        } else {
            exception(patients, patient, "for update patient");
        }
    }

    /**
     * fusión de 2 historiales clinicos
     * añadimos a patient los datos de oldpatientids vivos
     * @param patient
     * @param oldPatientIds
     * @throws HL7Exception
     */
    @Transactional(value="trazability-txm")
    @Override
    public void merge(Patient patient, List<PatientId> oldPatientIds) throws HL7Exception {
        List<Patient> newPatients = findPatients(patient);
        List<Patient> oldPatients = patientIdDao.findPatients(oldPatientIds);
        if (oldPatients != null && oldPatients.size() == 1) {
            Patient oldPatient = oldPatients.get(0);
            if (newPatients != null && newPatients.size() == 1) {
                Patient newPatient = newPatients.get(0);
                if (!newPatient.getId().equals(oldPatient.getId())) {
                    addAdress(newPatient, oldPatient);
                    addtelefono(newPatient, oldPatient);
                    if (oldPatient.getOrder1List() != null) {
                        for (Order1 o : oldPatient.getOrder1List()) {
                            newPatient.addOrder1(o);
                        }
                    }
                }
            } else {

                throw new HL7Exception(newPatients.size() + " patients instead of one found, for merge", HL7Exception.APPLICATION_INTERNAL_ERROR);
            }
        } else {
            throw new HL7Exception(oldPatients.size() + " old patients (MRG) instead of one found, for merge", HL7Exception.APPLICATION_INTERNAL_ERROR);
        }

    }

    /**
     * borrado de patient a partir de una lista de id y si esta vivo o muerto
     * @param patientIds
     * @param exitus
     */
    @Transactional(value="trazability-txm")
    public void deleteexitus(List<PatientId> patientIds, boolean exitus) throws HL7Exception {
        delete(patientIdDao.findPatienstExitus(patientIds, exitus));
    }

    /**
     * Modificamos los id de un paciente dado si updateid es true vamos a modificar los id de patient actuales <BR>
     * 
     * @param patientchange paciente con los cambios que hay que hacer
     * @param patientsId el paciente con los id a encontra en nuestra base de datos
     * @param updateid
     * @throws HL7Exception
     */
    @Transactional(value="trazability-txm")
    @Override
    public void updateId(Patient patientchange, List<PatientId> patientsId, boolean updateid)
            throws HL7Exception {
        List<Patient> patients = patientIdDao.findPatienstExitus(patientsId, false);
        if (patients != null && patients.size() == 1 && patients.get(0) != null) {
            Patient p = patients.get(0);
            updatePatientId(patientchange.getPatientIdList(), p, updateid);
        } else if (patients == null || patients.isEmpty()) {
            throw new HL7Exception("Patient not found for update ID ", HL7Exception.APPLICATION_INTERNAL_ERROR);
        } else if (patients != null && patients.size() > 1) {
            String nombre = "";
            String pnombre;
            for (Patient p : patients) {
                pnombre = "";
                if (p != null && p.getName() != null && p.getSurname1() != null) {
                    pnombre = patients.get(0).getName() + " " + p.getSurname1() + ",";
                }
                nombre = nombre + pnombre;
            }
            throw new HL7Exception("Found more than one patient can not be changed: " + nombre, HL7Exception.APPLICATION_INTERNAL_ERROR);
        }


    }

    /**
     * modifica los id de un paciente y si son nuevos los añadimos a su lista
     * @param patientsIdChanges
     * @param p
     * @param updatepatientid se modifican los id que ya existen en el pacienten si es true
     * @return
     * @throws HL7Exception
     */
    @Transactional(value="trazability-txm")
    private void updatePatientId(List<PatientId> patientsIdChanges, Patient p, boolean updatepatientid)
            throws HL7Exception {
        boolean add, update = false;
        List<PatientId> lpatientid;
        List<Patient> pa;
        for (PatientId pchanges : patientsIdChanges) {
            add = true;
            update = false;
            if (pchanges.getValue() != null && pchanges.getType() != null && pchanges.getType().getCode() != null && !"".equals(pchanges.getType().getCode())) {
                List<PatientId> pidOrigenlist = p.getPatientIdList();
                for (int i = 0; i < pidOrigenlist.size() && !update; i++) {
                    PatientId pOrigen = pidOrigenlist.get(i);
                    if (pOrigen.getType() != null && pOrigen.getType().getCode() != null && pchanges.getType().getCode().equals(pOrigen.getType().getCode())) {
                        if (!pchanges.getValue().equals(pOrigen.getValue())) {
                            update = true;
                            add = false;
                            if (updatepatientid) {
                                //vamos a buscar el id si ya existe para modificarlo
                                lpatientid = new ArrayList();
                                lpatientid.add(pchanges);
                                if (p.getExitus() != null) {
                                    pa = patientIdDao.findPatienstExitus(lpatientid, p.getExitus());
                                } else {
                                    pa = patientIdDao.findPatienstExitus(lpatientid, false);
                                }
                                if (pa.size() == 1 && pa.get(0).getId() == p.getId() || pa.isEmpty()) {
                                    pOrigen.setValue(pchanges.getValue());
                                } else {
                                    throw new HL7Exception(pa.size() + " patient were found with the "
                                            + "id equals in patient BD " + pa.get(0).getPatientIdList().get(0).getValue()
                                            + " (" + patientIdDescripcion(pa.get(0).getPatientIdList().get(0).getType().getCode()) + ")  for Update id " + pchanges.getValue() + " (" + patientIdDescripcion(pchanges.getType().getCode()) + ")", HL7Exception.APPLICATION_INTERNAL_ERROR);
                                }
                            }
                        }
                        if (pchanges.getValue().equals(pOrigen.getValue()) && pchanges.getType().getCode().equalsIgnoreCase(pOrigen.getType().getCode()) && !update) {
                            update = true;
                            add = false;
                        }
                    }
                }
                if (add) {//el tipo documento no esta lo añadimos a la lista
                    lpatientid = new ArrayList();
                    lpatientid.add(pchanges);
                    if (p.getExitus() != null) {
                        pa = patientIdDao.findPatienstExitus(lpatientid, p.getExitus());
                    } else {
                        pa = patientIdDao.findPatienstExitus(lpatientid, false);
                    }

                    if (pa.size() == 1 && pa.get(0).getId() == p.getId() || pa.isEmpty()) {
                        PatientId patientid = patientIdDao.patientIdbd(pchanges);
                        p.addPatientId(patientid);
                    } else {
                        throw new HL7Exception(pa.size() + " patient were found with the "
                                + "id equals in patient BD " + pa.get(0).getPatientIdList().get(0).getValue()
                                + " (" + patientIdDescripcion(pa.get(0).getPatientIdList().get(0).getType().getCode()) + ")  for Update id " + pchanges.getValue() + " (" + patientIdDescripcion(pchanges.getType().getCode()) + ")", HL7Exception.APPLICATION_INTERNAL_ERROR);
                    }
                }
            }
        }
    }

    /**
     * modificar nombre apellidos del paciente
     * @param original
     * @param patient
     * @throws HL7Exception
     */
    @Transactional(value="trazability-txm")
    private void updatePatientname(Patient original, Patient changes) throws HL7Exception {
        if (changes.getName() != null) {
            original.setName(changes.getName());
        }
        if (changes.getSurname1() != null) {
            original.setSurname1(changes.getSurname1());
        }
        if (changes.getSurname2() != null) {
            original.setSurname2(changes.getSurname2());
        }
    }

    /**
     * modificacion de datos demograficos del paciente
     * @param original
     * @param patient
     * @throws HL7Exception
     */
    @Transactional(value="trazability-txm")
    private void updatePatientInformation(Patient original, Patient changes) throws HL7Exception {

        if (changes.getExitus() != null) {
            original.setExitus(changes.getExitus());
        }
        if (changes.getWaitingExitus() != null) {
            original.setWaitingExitus(changes.getWaitingExitus());
        }
        if (changes.getBirthday() != null) {
            original.setBirthday(changes.getBirthday());
        }
        if (changes.getExitusDate() != null) {
            original.setExitusDate(changes.getExitusDate());
        }

        if (changes.getBirthCity() != null) {
            List<City> lcity = cityDao.findByIdcity(changes.getBirthCity().getIdcity());
            if (lcity != null && lcity.size() == 1) {
                original.setBirthCity(lcity.get(0));
            }
        }
        if (changes.getGenre() != null) {
            genrePatient(changes);
            original.setGenre(changes.getGenre());
        }
        if (changes.getMaritalStatus() != null) {
            maritalStatus(changes);
            original.setMaritalStatus(changes.getMaritalStatus());
        }

        if (changes.getAddressList() != null) {
            cityAddress(changes);
            addAdress(original, changes);
        }
        if (changes.getTelephoneList() != null) {
            addtelefono(original, changes);

        }
    }

    /**
     * intercambio de 2 camas dado dos pacientes
     * @param patients
     */
    @Transactional(value="trazability-txm")
    public void swapBed(List<Patient> patients) throws HL7Exception {
        if (patients.size() > 1) {
            List<Patient> lp1 = findPatients(patients.get(0));
            List<Patient> lp2 = findPatients(patients.get(1));
            if (lp1 != null && lp1.size() == 1 && lp2 != null && lp2.size() == 1) {

                Patient p1 = lp1.get(0);
                Patient p2 = lp2.get(0);

                String bebp0hl7 = patients.get(0).getBed().getNr();
                String bebp1hl7 = patients.get(1).getBed().getNr();
                String bebp0bd = p1.getBed().getNr();
                String bebp1bd = p2.getBed().getNr();

                if (bebp0hl7.equalsIgnoreCase(bebp1bd) && bebp1hl7.equalsIgnoreCase(bebp0bd)) {
                    Recurso bedaux = p1.getBed();
                    p1.setBed(p2.getBed());
                    p2.setBed(bedaux);
                } else {
                    throw new HL7Exception("are different beds in bd with h7 message", HL7Exception.APPLICATION_INTERNAL_ERROR);
                }
            } else {
                throw new HL7Exception("two patients is not found in bd for the swap of beds", HL7Exception.APPLICATION_INTERNAL_ERROR);
            }

        } else {
            throw new HL7Exception("two patients is not found for the swap of beds", HL7Exception.APPLICATION_INTERNAL_ERROR);
        }
    }

    /**
     * translado de un paciente a otra cama
     * @param patient
     * @throws HL7Exception
     */
    @Transactional(value="trazability-txm")
    public void TransferBed(Patient patient) throws HL7Exception {
        bedPatient(patient);
        List<Patient> lpatient = findPatients(patient);
        if (lpatient != null && lpatient.size() == 1) {
            if (patient.getBed() != null) {
                lpatient.get(0).setBed(patient.getBed());
            } else if (patient.getPatientIdList() != null && !patient.getPatientIdList().isEmpty()) {
                throw new HL7Exception("not found bed, for the transfer, patient  " + patient.getPatientIdList().get(0).getValue()
                        + " (" + patientIdDescripcion(patient.getPatientIdList().get(0).getType().getCode()) + ")", HL7Exception.APPLICATION_INTERNAL_ERROR);
            } else {
                throw new HL7Exception("not found bed, for the transfer patient", HL7Exception.APPLICATION_INTERNAL_ERROR);
            }

        } else {
            exception(lpatient, patient, "for Transfer bed");
        }

    }

    /**
     * dar de alta a un paciente dejamos la cama libre donde esta
     * @param patient
     * @throws HL7Exception
     */
    @Transactional(value="trazability-txm")
    public void alta(Patient patient) throws HL7Exception {
        List<Patient> lpatient = findPatients(patient);
        if (lpatient != null && lpatient.size() == 1) {
            lpatient.get(0).setBed(null);
            patientTagDao.removePatientId(lpatient.get(0).getId());

        } else {
            exception(lpatient, patient, "for alta");
        }
    }

    /**
     * cancelamos el alta de un paciente al cual se le acaban de dar; es asociarle su cama de ingreso
     * @param patient
     * @throws HL7Exception
     */
    @Transactional(value="trazability-txm")
    public void cancelAlta(Patient patient) throws HL7Exception {
        bedPatient(patient);
        List<Patient> lpatient = findPatients(patient);
        if (lpatient != null && lpatient.size() == 1) {
            if (patient.getBed() != null) {
                lpatient.get(0).setBed(patient.getBed());
            } else if (patient.getPatientIdList() != null && !patient.getPatientIdList().isEmpty()) {
                throw new HL7Exception("not found bed, for the remove high, patient " + patient.getPatientIdList().get(0).getValue()
                        + " (" + patientIdDescripcion(patient.getPatientIdList().get(0).getType().getCode()) + ")", HL7Exception.APPLICATION_INTERNAL_ERROR);
            } else {
                throw new HL7Exception("not found bed, for the remove high, patient", HL7Exception.APPLICATION_INTERNAL_ERROR);
            }
        } else {
            exception(lpatient, patient, "cancel alta");
        }
    }

    /**
     * admisión de un paciente asociandole una cama para el ingreso
     * @param patient
     * @throws HL7Exception
     */
    @Transactional(value="trazability-txm")
    public void admission(Patient patient) throws HL7Exception {
        bedPatient(patient);
        List<Patient> lpatient = findPatients(patient);
        if (lpatient != null && lpatient.size() == 1) {
            if (patient.getBed() != null) {
                lpatient.get(0).setBed(patient.getBed());
            } else if (patient.getPatientIdList() != null && !patient.getPatientIdList().isEmpty()) {
                throw new HL7Exception("not found bed, for the admission patient " + patient.getPatientIdList().get(0).getValue()
                        + " (" + patientIdDescripcion(patient.getPatientIdList().get(0).getType().getCode()) + ")", HL7Exception.APPLICATION_INTERNAL_ERROR);
            } else {
                throw new HL7Exception("not found bed, for the admission patient", HL7Exception.APPLICATION_INTERNAL_ERROR);
            }
        } else {
            exception(lpatient, patient, "for admission patient");
        }
    }

    /**
     * cancelación admisión de un paciente
     * @param patient
     * @throws HL7Exception
     */
    @Transactional(value="trazability-txm")
    public void cancelAdmission(Patient patient) throws HL7Exception {
        List<Patient> lpatient = findPatients(patient);
        if (lpatient != null && lpatient.size() == 1) {
            lpatient.get(0).setBed(null);
            patientTagDao.removePatientId(lpatient.get(0).getId());
        } else {
            exception(lpatient, patient, "cancel Admission");
        }
    }

    /**
     * transpaso a otra cama. ADT_45
     * @param patient
     * @param patientsIdChanges 
     * @throws HL7Exception
     */
    public void relayed(Patient patient, List<PatientId> patientsIdChanges) throws HL7Exception {
        bedPatient(patient);
        List<Patient> lpatient = patientIdDao.findPatienstExitus(patientsIdChanges, false);
        if (lpatient != null && lpatient.size() == 1) {
            if (patient.getBed() != null) {
                lpatient.get(0).setBed(patient.getBed());
            } else if (patient.getPatientIdList() != null && !patient.getPatientIdList().isEmpty()) {
                throw new HL7Exception("not found bed, for the transfer patient " + patient.getPatientIdList().get(0).getValue()
                        + " (" + patientIdDescripcion(patient.getPatientIdList().get(0).getType().getCode()) + ")", HL7Exception.APPLICATION_INTERNAL_ERROR);
            } else {
                throw new HL7Exception("not found bed, for the transfer patient", HL7Exception.APPLICATION_INTERNAL_ERROR);
            }
        } else {
            exception(lpatient, patient, "for relayed");
        }

    }

    /**
     * añadimos telefonos que no tenga el paciente original
     * @param original
     * @param changes
     */
    @Transactional(value="trazability-txm")
    private void addtelefono(Patient original, Patient changes) {
        if (original.getTelephoneList() != null && changes.getTelephoneList() != null) {
            List<Telephone> lTelephonenew = changes.getTelephoneList();
            for (Telephone tnew : lTelephonenew) {
                boolean add = true;
                for (Telephone telephonebd : original.getTelephoneList()) {
                    if ((telephonebd.getPhone() != null && tnew.getPhone() != null) && telephonebd.getPhone().equals(tnew.getPhone())) {
                        add = false;
                    }
                }
                if (add) {
                    original.addTelephone(tnew);
                }
            }
        } else if (changes.getTelephoneList() != null) {
            for (Telephone t : changes.getTelephoneList()) {
                original.addTelephone(t);
            }
        }
    }

    /**
     * añadimos una nueva direcion y si ese paciente ya la tiene no se la añadimos
     * @param original
     * @param changes
     */
    @Transactional(value="trazability-txm")
    private void addAdress(Patient original, Patient changes) {
        if (original.getAddressList() != null && changes.getAddressList() != null) {
            List<Address> laddressnew = changes.getAddressList();
            for (Address anew : laddressnew) {
                boolean add = true;
                for (Address addressbd : original.getAddressList()) {
                    if ((addressbd.getDirection() == null && anew.getDirection() == null)
                            && (addressbd.getNumber() == null && anew.getNumber() == null
                            && (addressbd.getCityIdcity().getIdcity().equals(anew.getCityIdcity().getIdcity())))) {
                        add = false;
                    } else if ((addressbd.getDirection() != null && anew.getDirection() != null) && (addressbd.getDirection().equals(anew.getDirection()))
                            && (addressbd.getNumber() == null && anew.getNumber() == null
                            && (addressbd.getCityIdcity().getIdcity().equals(anew.getCityIdcity().getIdcity())))) {
                        add = false;
                    } else if ((addressbd.getDirection() == null && anew.getDirection() == null)
                            && (addressbd.getNumber() != null && anew.getNumber() != null && (addressbd.getNumber().equals(anew.getNumber()))
                            && (addressbd.getCityIdcity().getIdcity().equals(anew.getCityIdcity().getIdcity())))) {
                        add = false;
                    } else if ((addressbd.getDirection() != null && anew.getDirection() != null) && (addressbd.getDirection().equals(anew.getDirection()))
                            && (addressbd.getNumber() != null && anew.getNumber() != null) && (addressbd.getNumber().equals(anew.getNumber()))
                            && (addressbd.getCityIdcity().getIdcity().equals(anew.getCityIdcity().getIdcity()))) {
                        add = false;
                    }
                }
                if (add) {
                    original.addAddress(anew);
                }
            }
        } else if (changes.getAddressList() != null) {
            for (Address a : changes.getAddressList()) {
                original.addAddress(a);
            }
        }
    }

    /**
     * muestra exception de cero o varios pacientes
     * @param patients
     * @throws HL7Exception
     */
    @Transactional(value="trazability-txm",readOnly = true)
    private void exception(List<Patient> patients, Patient p, String error) throws HL7Exception {
        if (patients.size() > 1) {
            if (p != null && p.getPatientIdList() != null && !p.getPatientIdList().isEmpty()) {
                throw new HL7Exception(patients.size() + " patients were found with the "
                        + "same id equals in patient BD " + p.getPatientIdList().get(0).getValue()
                        + " (" + patientIdDescripcion(p.getPatientIdList().get(0).getType().getCode()) + ") " + error, HL7Exception.APPLICATION_INTERNAL_ERROR);
            } else {
                throw new HL7Exception(patients.size() + " patients were found with the same id " + error, HL7Exception.APPLICATION_INTERNAL_ERROR);
            }
        } else {
            throw new HL7Exception("Patient not found " + error, HL7Exception.APPLICATION_INTERNAL_ERROR);
        }

    }

    /**
     * buscamos el paciente en nuestra base de datos
     * @param patient
     * @return List {@link Patient}
     * @throws HL7Exception
     */
    @Transactional(value="trazability-txm")
    @Override
    public List<Patient> findPatients(Patient patient) throws HL7Exception {
        List<Patient> patients = null;
        if (patient != null && patient.getPatientIdList() != null && !patient.getPatientIdList().isEmpty()) {
            if (patient.getExitus() != null) {
                patients = patientIdDao.findPatienstExitus(patient.getPatientIdList(), patient.getExitus());
            } else {
                //TODO suponemos que esta vivo a no darnos el dato
                patients = patientIdDao.findPatienstExitus(patient.getPatientIdList(), false);
            }
        } else {
            throw new HL7Exception("The patient has no identification data ", HL7Exception.APPLICATION_INTERNAL_ERROR);
        }
        return patients;
    }

    /**
     * asociamos a un paciente una cama, buscamos id de la cama en la tabla maestra recurso
     * y comprobamos que ningun otro paciente esta ocupando ya esa cama
     * @param patient
     * @return {@link Patient}
     * @throws HL7Exception
     */
    @Transactional(value="trazability-txm")
    private void bedPatient(Patient patient) throws HL7Exception {
        if (patient.getBed() != null && patient.getBed().getNr() != null && !"".equals(patient.getBed().getNr())) {
            Patient p = getPatientInBed(patient.getBed().getNr());
            if (p == null) {
                List<Recurso> lrecursos = recursoDao.findByNr(patient.getBed().getNr());
                if (lrecursos.size() == 1) {
                    patient.getBed().setIdRecurso(lrecursos.get(0).getIdRecurso());
                } else if (patient.getPatientIdList() != null && !patient.getPatientIdList().isEmpty()) {
                    throw new HL7Exception(" patient "
                            + patient.getPatientIdList().get(0).getValue()
                            + " (" + patientIdDescripcion(patient.getPatientIdList().get(0).getType().getCode())
                            + ") impossible to fill bed: " + patient.getBed().getNr()
                            + "; (bed in master table not found) ", HL7Exception.APPLICATION_INTERNAL_ERROR);
                } else {
                    throw new HL7Exception("Failure to add the bed " + patient.getBed().getNr() + "; bed in master table not found ", HL7Exception.APPLICATION_INTERNAL_ERROR);

                }
            } else if (patient.getPatientIdList() != null && !patient.getPatientIdList().isEmpty() && p.getPatientIdList() != null && !p.getPatientIdList().isEmpty()) {
                throw new HL7Exception("occupied bed " + patient.getBed().getNr() + " for the patient " + patient.getPatientIdList().get(0).getValue()
                        + " (" + patientIdDescripcion(patient.getPatientIdList().get(0).getType().getCode()) + ") is the patient " + p.getPatientIdList().get(0).getValue()
                        + " (" + patientIdDescripcion(p.getPatientIdList().get(0).getType().getCode()) + ")", HL7Exception.APPLICATION_INTERNAL_ERROR);
            } else {
                throw new HL7Exception("occupied bed " + patient.getBed().getNr(), HL7Exception.APPLICATION_INTERNAL_ERROR);
            }
        }

    }

    /**
     * añadimos el id del genero del paciente
     * @param patient
     * @throws HL7Exception
     */
    @Transactional(value="trazability-txm")
    private void genrePatient(Patient patient) throws HL7Exception {
        if (patient.getGenre().getCode() != null && !"".equals(patient.getGenre().getCode())) {
            List<Table0001> lgenero = table0001Dao.findByCode(patient.getGenre().getCode());
            if (lgenero.size() == 1) {
                patient.setGenre(lgenero.get(0));
            } else if (patient.getPatientIdList() != null && !patient.getPatientIdList().isEmpty()) {
                throw new HL7Exception(" patient "
                        + patient.getPatientIdList().get(0).getValue()
                        + " (" + patientIdDescripcion(patient.getPatientIdList().get(0).getType().getCode())
                        + ") impossible add genre " + patient.getGenre().getCode()
                        + "; (genre in master table not found) ", HL7Exception.APPLICATION_INTERNAL_ERROR);
            } else {
                throw new HL7Exception("Failure to add the genre " + patient.getGenre().getCode(), HL7Exception.APPLICATION_INTERNAL_ERROR);
            }
        }
    }

    /**
     * obtenemos la ciudad de nuestra base de datos
     * @param patient
     * @throws HL7Exception
     */
    @Transactional(value="trazability-txm")
    private void cityAddress(Patient patient) throws HL7Exception {
        if (patient.getAddressList() != null) {
            if (!patient.getAddressList().isEmpty()) {
                for (int i = 0; i < patient.getAddressList().size(); i++) {
                    if (patient.getAddressList().get(i) != null && patient.getAddressList().get(i).getCityIdcity() != null && patient.getAddressList().get(i).getCityIdcity().getIdcity() != null) {
                        //vamos a obtenener los id de ciudad provincia region y pais
                        List<City> lcity = cityDao.findByIdcity(patient.getAddressList().get(i).getCityIdcity().getIdcity());
                        if (lcity != null && lcity.size() == 1) {
                            patient.getAddressList().get(i).setCityIdcity(lcity.get(0));
                        } else {//lo borramos de la lista si no encontramos la ciudad en bbdd
                            patient.getAddressList().remove(i);
                        }
                    }
                }
            }
        }
    }

    /**
     * Obtenemos la Descripcion de patient id a partir de code
     * @param code
     * @return
     * @throws HL7Exception
     */
    @Transactional(value="trazability-txm",readOnly = true)
    private String patientIdDescripcion(String code) throws HL7Exception {
        List<Tablez029> tablez029 = tablez029Dao.findByCode(code);
        if (tablez029 != null && !tablez029.isEmpty()) {
            return tablez029.get(0).getDetails();
        }
        return "";
    }

    /**
     * añadimos al paciente el id del estado civil
     * @param patient
     * @throws HL7Exception
     */
    @Transactional(value="trazability-txm")
    private void maritalStatus(Patient patient) throws HL7Exception {
        if (patient.getMaritalStatus().getCode() != null && !"".equals(patient.getMaritalStatus().getCode())) {
            List<Table0002> lestadocivil = table0002Dao.findByCode(patient.getMaritalStatus().getCode());
            if (lestadocivil.size() == 1) {
                patient.setMaritalStatus(lestadocivil.get(0));
            } else if (patient.getPatientIdList() != null && !patient.getPatientIdList().isEmpty()) {
                throw new HL7Exception(" patient "
                        + patient.getPatientIdList().get(0).getValue()
                        + " (" + patientIdDescripcion(patient.getPatientIdList().get(0).getType().getCode())
                        + ") impossible add marital status " + patient.getMaritalStatus().getCode()
                        + "; (marital status in master table not found) ", HL7Exception.APPLICATION_INTERNAL_ERROR);
            } else {
                throw new HL7Exception("Failure to add the marital status " + patient.getMaritalStatus().getCode(), HL7Exception.APPLICATION_INTERNAL_ERROR);
            }
        }
    }

    /**
     * obtenemos a partir de la cama el paciente que esta en esa cama
     * @param bedId
     * @return {@link Patient}
     */
    @Transactional(value="trazability-txm",readOnly = true)
    private Patient getPatientInBed(String bedId) {
        List aux = this.entityManager.createQuery("from Patient p where p.bed in (select r.idRecurso from Recurso r where r.nr=:nr)").setParameter("nr", bedId).getResultList();
        if (aux != null && aux.size() == 1) {
            return (Patient) aux.get(0);
        }
        return null;
    }

    /**
     * Devuelve el paciente que hay en una cama identificada con tag
     * si no encuentra nada devuelve null
     * @param tag Identificador de la cama
     * @return {@link Patient}
     */
    @Transactional(value="trazability-txm",readOnly = true)
    public Patient getPatientByBedTag(String tag) {
        List aux = this.entityManager.createQuery("from Patient p where p.bed = (select r.recurso from RecursoTag r where r.tag=:tag)").setParameter("tag",tag).getResultList();
        if (aux != null && aux.size() == 1) {
            return (Patient) aux.get(0);
        }
        return null;
    }

    @Transactional(value="trazability-txm",readOnly = true)
    public Patient getPatientByBedId(Long id) {
        //List aux = this.entityManager.find("from Patient p where p.bed in (select r.idRecurso from Recurso r where r.nr=?)", id);
        List aux = this.entityManager.createQuery("from Patient p where p.bed.idRecurso=:id)").setParameter("id", id).getResultList();
        if (aux != null && aux.size() == 1) {
            return (Patient) aux.get(0);
        }
        return null;
    }

    /**
     * Devuelve el paciente que esta identificado con un tag
     * si no encuentra nada devuelve null
     * @param tag Identificador de la cama
     * @return {@link Patient}
     */
    @Transactional(value="trazability-txm",readOnly = true)
    public Patient getPatientByTag(String tag) {
        List aux = this.entityManager.createQuery("from Patient p where p.patientTag.tag=:tag").setParameter("tag", tag).getResultList();
        if (aux != null && aux.size() == 1) {
            return (Patient) aux.get(0);
        }
        return null;
    }

    /**
     *obtenemos la lista de todos los Patient
     * @return list <Patient>
     */
    @Transactional(value="trazability-txm",readOnly = true)
    @Override
    public List<Patient> findAll() {
        return this.entityManager.createNamedQuery("Patient.findAll").getResultList();
    }

    /**
     * Obtengo todos los pacientes vivos
     * @param filters
     * @return {@link Patient}
     */
    @Transactional(value="trazability-txm",readOnly = true)
    public List<Patient> loadAll(GridRequest filters) {
        List<Patient> patient = this.find(this.entityManager,"from Patient p where p.exitus=0 and p.bed is not null " + filters.getQL("p", false), filters.getParamsValues(), filters.getStart(), filters.getLimit());
        return patient;
    }

    /**
     * Cuenta los Patient
     * @param filters
     * @return Long
     */
    @Transactional(value="trazability-txm",readOnly = true)
    public Long loadSizeAll(GridRequest filters) {
        List<Long> result = this.find(this.entityManager,"select count(*) from Patient p where p.exitus=0  and p.bed is not null" + filters.getQL("p", false), filters.getParamsValues());
        return result.get(0);
    }
}
