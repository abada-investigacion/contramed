/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.trazability.dao.managerthreatment.impl;

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

import com.abada.trazability.dao.givethreatment.GiveThreatmentDao;
import com.abada.trazability.dao.incidence.IncidenceDao;
import com.abada.trazability.dao.measureunitconversion.MeasureUnitConversionDao;
import com.abada.trazability.dao.preparethreatment.PrepareThreatmentDao;
import com.abada.trazability.dao.treatment.ThreatmentDao;
import com.abada.trazability.entity.CatalogoMedicamentos;
import com.abada.trazability.entity.Dose;
import com.abada.trazability.entity.GivesHistoric;
import com.abada.trazability.entity.OrderTiming;
import com.abada.trazability.entity.Patient;
import com.abada.trazability.entity.PrepareHistoric;
import com.abada.trazability.entity.Staff;
import com.abada.trazability.entity.enums.TypeGetPatient;
import com.abada.trazability.entity.enums.TypeGivesHistoric;
import com.abada.trazability.entity.enums.TypePrepareHistoric;
import com.abada.trazability.properties.Properties;
import com.abada.springframework.orm.jpa.support.JpaDaoUtils;
import com.abada.trazability.entity.temp.PrescriptionType;
import com.abada.trazability.entity.temp.ThreatmentInfo;
import com.abada.trazability.entity.temp.TreatmentActionMode;
import com.abada.trazability.entity.temp.TypeThreatmentInfoStatus;
import com.abada.trazability.exception.AllergyException;
import com.abada.trazability.exception.ExpiredDoseException;
import com.abada.trazability.exception.GenericWebContramedException;
import com.abada.trazability.exception.ImposibleRemoveThreatmentException;
import com.abada.trazability.exception.NoCorrectThreatmentException;
import com.abada.trazability.exception.NoExistDoseException;
import com.abada.trazability.exception.NoThreatmentException;
import com.abada.trazability.exception.RangeTimeOutException;
import com.abada.trazability.exception.WebContramedException;
import com.abada.trazabiliity.utils.Utils;
import com.abada.trazability.concurrent.BlockMonitor;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author katsu
 */
@Repository("managerThreatmentDao")
public class ManagerThreatmentDaoImpl extends JpaDaoUtils implements PrepareThreatmentDao, GiveThreatmentDao {

    @Resource(name = "threatmentDao")
    private ThreatmentDao threatmentDao;
    @Resource(name = "incidenceDao")
    private IncidenceDao incidenceDao;
    @Resource(name = "measureUnitConversionDao")
    private MeasureUnitConversionDao measureUnitConversionDao;
    @Resource(name = "contramedProperties")
    private Properties contramedProperties;
    @Resource(name = "monitor1")
    private BlockMonitor monitor1;
    @Resource(name = "monitor2")
    private BlockMonitor monitor2;
    @Resource(name = "monitor3")
    private BlockMonitor monitor3;
    @Resource(name = "monitor4")
    private BlockMonitor monitor4;

    @PersistenceContext(unitName = "trazabilityPU")
    private EntityManager entityManager;

// <editor-fold defaultstate="collapsed" desc="Administrar y preparar tratamientos">
    /**
     * FIXME Cutrada
     * Administra todo lo que no este administrado de manera forzada. Aceptar todo.
     * @param patient Paciente al que se administra la dosis
     * @param startDate Fecha de comienzo de rango de la toma
     * @param endDate Fecha de fin de rango de la toma
     * @param staff Personal del hospital que administra la dosis
     */
    @Transactional
    public void giveAll(Patient patient, Date startDate, Date endDate, Staff staff, TypeGetPatient typeGetPatient) throws WebContramedException {
        giveDose(null, patient, startDate, endDate, staff, TypeGivesHistoric.FORCED, typeGetPatient);
    }

    /**
     * Ejecuta el proceso de administrar una dosis al paciente.
     * Cualquier problema con la dosis sera devuelto en la excepcion.
     * @param idDose Identificador de la dosis a administrar
     * @param patient Paciente al que se administra la dosis
     * @param startDate Fecha de comienzo de rango de la toma
     * @param endDate Fecha de fin de rango de la toma
     * @param staff Personal del hospital que administra la dosis
     * @param typeGivesHistoric tipo de administracion de medicacion
     * @param observations Observaciones escritas por parte del personal hospitalario
     * @throws WebContramedException posible excepcion si no se puede administrar la dosis por alguna razon
     */
    @Transactional
    public void giveDose(Long idDose, Patient patient, Date startDate, Date endDate, Staff staff, TypeGivesHistoric typeGivesHistoric, TypeGetPatient typeGetPatient, String... observations) throws WebContramedException {
        OrderTimingInfo info = null;
        Dose dose = null;
        try {
            this.monitor1.lock();
            //Comprobación de la caducidad de la dosis
            if (typeGivesHistoric != TypeGivesHistoric.FORCED) {
                dose = this.getDose(idDose);
                //Comprobar que la dosis no esta caducada
                checkExpirationDate(dose);
            }
            //Administracion
            switch (typeGivesHistoric) {
                case EQUIVALENT:
                case REGULAR:
                    //Comprobamos que es el medicamento correcto
                    info = getOrderTimingCorrectNursisng(dose, patient, startDate, endDate, TreatmentActionMode.ADMINISTRATION_NURSING);
                    //Crear la entrada en Prepare historic
                    createGiveHistoric(info, dose, staff, patient, typeGetPatient, observations);
                    break;
                case OUT_ORDER:
                    //No se comprueba el order timing, porque el medicamento se esta dando bajo la
                    //responsabilidad de la enfermera
                    createGiveHistoric(info, dose, staff, typeGivesHistoric, patient, typeGetPatient, observations);
                    break;
                case FORCED:
                    //FIXME Chapuza del quince
                    List<ThreatmentInfo> threatmentInfos = this.threatmentDao.getActiveThreatmentByNursing(patient.getId(), startDate, endDate);
                    if (threatmentInfos == null || threatmentInfos.isEmpty()) {
                        throw new NoThreatmentException(dose, patient, startDate, endDate, TreatmentActionMode.ADMINISTRATION_NURSING);
                    }
                    RangeTimeOutException ex = null;
                    for (ThreatmentInfo th : threatmentInfos) {
                        if (th.getThreatmentInfoStatus().getStatus().equals(TypeThreatmentInfoStatus.NOT_COMPLETED) && -th.getThreatmentInfoStatus().getDifference() == th.getGiveAmount().doubleValue() && !th.isAlergy() && !th.isIfNecesary()) {
                            try {
                                createGiveHistoric(new OrderTimingInfo(th.getIdOrderTiming(), false, th.getGiveTime(), false), null, staff, TypeGivesHistoric.FORCED, patient, typeGetPatient);
                            } catch (RangeTimeOutException e) {
                                ex = e;
                            }
                        }
                    }
                    if (ex != null) {
                        throw ex;
                    }
                    break;
            }
        } catch (WebContramedException e) {
            if (info != null) {
                this.incidenceDao.generateGiveIncidence(staff, dose, patient, info.getIdOrderTiming(), e);
            } else {
                this.incidenceDao.generateGiveIncidence(staff, dose, patient, null, e);
            }
            throw e;
        } finally {
            this.monitor1.unlock();
        }
    }

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
    @Transactional
    public void removeDoseNursing(Long idDose, Patient patient, Date startDate, Date endDate, Staff staff, TypeGetPatient typeGetPatient, String... observations) throws WebContramedException {
        OrderTimingInfo info = null;
        Dose dose = null;
        try {
            this.monitor4.lock();
            dose = this.getDose(idDose);
            //Comprobar que la dosis no esta caducada
            checkExpirationDate(dose);
            TypeGivesHistoric typeGivesHistoric;
            //primero se comprueba si se quiere quitar una dosis que se haya añadido antes
            info = getOrderTimingForRemoveNursing(dose, patient, startDate, endDate);
            if (info == null) {
                info = getOrderTimingCorrectNursisng(dose, patient, startDate, endDate, TreatmentActionMode.REMOVE_NURSING);
                typeGivesHistoric = TypeGivesHistoric.NOT_GIVEN;
                //TODO Comprobar si hay una not_given anterior y quitarla
            } else {
                typeGivesHistoric = TypeGivesHistoric.REMOVE;
            }
            //Crear la entrada en Prepare historic
            createGiveHistoric(info, dose, staff, typeGivesHistoric, patient, typeGetPatient, observations);

        } catch (WebContramedException e) {
            if (info != null) {
                this.incidenceDao.generateGiveIncidence(staff, dose, patient, info.getIdOrderTiming(), e);
            } else {
                this.incidenceDao.generateGiveIncidence(staff, dose, patient, null, e);
            }
            throw e;
        } finally {
            this.monitor4.unlock();
        }
    }

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
    @Transactional
    public void removeDosePharmacy(Long idDose, Patient patient, Date startDate, Date endDate, Staff staff, String... observations) throws WebContramedException {
        OrderTimingInfo info = null;
        Dose dose = null;
        try {
            this.monitor2.lock();
            dose = this.getDose(idDose);
            //Comprobar que la dosis no esta caducada
            checkExpirationDate(dose);
            //Comprobamos que es el medicamento correcto
            info = getOrderTimingForRemovePharmacy(dose, patient, startDate, endDate);
            //Crear la entrada en Prepare historic
            createPrepareHistoric(info, TypePrepareHistoric.OUTBOX, dose, staff, observations);
        } catch (WebContramedException e) {
            if (info != null) {
                this.incidenceDao.generatePrepareIncidence(staff, dose, patient, info.getIdOrderTiming(), e);
            } else {
                this.incidenceDao.generatePrepareIncidence(staff, dose, patient, null, e);
            }
            throw e;
        } finally {
            this.monitor2.unlock();
        }
    }

    /***
     * Añade todo al cajetín si no esta añadido.
     * @param patient Paciente al que se prepara la dosis
     * @param startDate Fecha de comienzo de rango de la toma
     * @param endDate Fecha de fin de rango de la toma
     * @param staff Personal del hospital que prepara la dosis
     * @throws WebContramedException posible excepcion si no se puede preparar la dosis por alguna razon
     */
    @Transactional
    public void prepareAll(Patient patient, Date startDate, Date endDate, Staff staff) throws WebContramedException {
        try {
            this.monitor3.lock();
            List<ThreatmentInfo> threatmentInfos = this.threatmentDao.getActiveThreatmentByPharmacy(patient.getId(), startDate, endDate);
            if (threatmentInfos == null || threatmentInfos.isEmpty()) {
                throw new NoThreatmentException(null, patient, startDate, endDate, TreatmentActionMode.INBOX_PHARMACY);
            }
            RangeTimeOutException ex = null;
            for (ThreatmentInfo th : threatmentInfos) {
                if (th.getThreatmentInfoStatus().getStatus().equals(TypeThreatmentInfoStatus.NOT_COMPLETED) && -th.getThreatmentInfoStatus().getDifference() == th.getGiveAmount().doubleValue() && !th.isAlergy() && !th.isIfNecesary()) {
                    try {
                        createPrepareHistoric(new OrderTimingInfo(th.getIdOrderTiming(), false, th.getGiveTime(), false), TypePrepareHistoric.FORCEDBOX, null, staff);
                    } catch (RangeTimeOutException e) {
                        ex = e;
                    }
                }
            }
            if (ex != null) {
                throw ex;
            }
        } catch (WebContramedException e) {
            throw e;
        } finally {
            this.monitor3.unlock();
        }
    }

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
    @Transactional
    public void prepareDose(Long idDose, Patient patient, Date startDate, Date endDate, Staff staff, String... observations) throws WebContramedException {
        OrderTimingInfo info = null;
        Dose dose = null;
        try {
            this.monitor3.lock();
            dose = this.getDose(idDose);
            //Comprobar que la dosis no esta caducada
            checkExpirationDate(dose);
            //Comprobamos que es el medicamento correcto
            info = getOrderTimingCorrectPharmacy(dose, patient, startDate, endDate);
            //Crear la entrada en Prepare historic
            createPrepareHistoric(info, TypePrepareHistoric.INBOX, dose, staff, observations);
        } catch (WebContramedException e) {
            if (info != null) {
                this.incidenceDao.generatePrepareIncidence(staff, dose, patient, info.getIdOrderTiming(), e);
            } else {
                this.incidenceDao.generatePrepareIncidence(staff, dose, patient, null, e);
            }
            throw e;
        } finally {
            this.monitor3.unlock();
        }
    }

    @Transactional(value="trazability-txm",readOnly = true)
    private Dose getDose(Long idDose) throws NoExistDoseException {
        Dose dose = this.entityManager.find(Dose.class, idDose);
        if (dose == null) {
            throw new NoExistDoseException(idDose);
        }
        return dose;
    }

    /***
     * Genera y persiste una entrada en prepare historic
     * @param info
     * @param dose
     * @param staff
     */
    @Transactional
    private void createPrepareHistoric(OrderTimingInfo info, TypePrepareHistoric typePrepareHistoric, Dose dose, Staff staff, String... observations) throws RangeTimeOutException {

        Date now = new Date();
        Date downlimit = new Date(now.getTime());
        Date uplimit = new Date(now.getTime());
        downlimit.setHours(downlimit.getHours() - contramedProperties.getPrepareLowLimit());
        uplimit.setHours(uplimit.getHours() + contramedProperties.getPrepareUpLimit());
        if (downlimit.after(info.getOrderTimingDate()) || uplimit.before(info.getOrderTimingDate())) {
            throw new RangeTimeOutException(dose, downlimit, uplimit);
        }

        PrepareHistoric historic = new PrepareHistoric();
        historic.setDoseIddose(dose);
        historic.setEquivalent(info.isEquivalent());
        historic.setEventDate(now);
        historic.setOrderTimingDate(info.getOrderTimingDate());
        historic.setOrderTimingIdorderTiming(this.entityManager.find(OrderTiming.class, info.getIdOrderTiming()));
        historic.setType(typePrepareHistoric);
        historic.setStaffIdstaff(staff);
        historic.setObservation(Utils.append(observations));
        this.entityManager.persist(historic);
    }

    /***
     * Genera y persiste una entrada en prepare historic
     * @param info
     * @param dose
     * @param staff
     */
    @Transactional
    private void createGiveHistoric(OrderTimingInfo info, Dose dose, Staff staff, Patient patient, TypeGetPatient typeGetPatient, String... observations) throws RangeTimeOutException {
        TypeGivesHistoric typeGivesHistoric;
        if (info.isEquivalent()) {
            typeGivesHistoric = TypeGivesHistoric.EQUIVALENT;
        } else {
            typeGivesHistoric = TypeGivesHistoric.REGULAR;
        }
        this.createGiveHistoric(info, dose, staff, typeGivesHistoric, patient, typeGetPatient, observations);
    }

    /***
     * Genera y persiste una entrada en prepare historic
     * @param info
     * @param dose
     * @param staff
     */
    @Transactional
    private void createGiveHistoric(OrderTimingInfo info, Dose dose, Staff staff, TypeGivesHistoric typeGivesHistoric, Patient patient, TypeGetPatient typeGetPatient, String... observations) throws RangeTimeOutException {

        Date now = new Date();

        Patient pat = this.entityManager.find(Patient.class, patient.getId());

        GivesHistoric historic = new GivesHistoric();
        historic.setDoseIddose(dose);
        historic.setType(typeGivesHistoric);
        historic.setTypeGetPatient(typeGetPatient);
        historic.setEventDate(now);
        if (info != null) {
            historic.setOrderTimingDate(info.getOrderTimingDate());
        } else {
            historic.setOrderTimingDate(historic.getEventDate());
        }

        //Comprobación del rango
        Date downlimit = new Date(now.getTime());
        Date uplimit = new Date(now.getTime());
        downlimit.setHours(downlimit.getHours() - contramedProperties.getAdministrationLowLimit());
        uplimit.setHours(uplimit.getHours() + contramedProperties.getAdministrationUpLimit());
        if (downlimit.after(historic.getOrderTimingDate()) || uplimit.before(historic.getOrderTimingDate())) {
            throw new RangeTimeOutException(dose, downlimit, uplimit);
        }

        if (info != null) {
            historic.setOrderTimingIdorderTiming(this.entityManager.find(OrderTiming.class, info.getIdOrderTiming()));
        }
        historic.setStaffIdstaff(staff);
        historic.setObservation(Utils.append(observations));
        historic.setPatient(pat);
        pat.addGiveHistoric(historic);
        this.entityManager.persist(historic);
    }

    /***
     * Comprueba la fecha de caducidad
     * @param dose
     * @throws WebContramedException
     */
    @Transactional(value="trazability-txm",readOnly = true)
    private void checkExpirationDate(Dose dose) throws WebContramedException {
        Date now = new Date();
        if (dose.getExpirationDate().before(now)) {
            throw new ExpiredDoseException(dose);
        }
    }

    /**
     * Devuelve la orden timing correcta para eliminar la dosis en el caso de haber sido añadida primero
     *
     * @param dose
     * @param patient
     * @param startDate
     * @param endDate
     * @return
     */
    @Transactional(value="trazability-txm",readOnly = true)
    private OrderTimingInfo getOrderTimingForRemoveNursing(Dose dose, Patient patient, Date startDate, Date endDate) throws WebContramedException {
        List<ThreatmentInfo> threatmentInfos = this.threatmentDao.getActiveThreatmentByNursing(patient.getId(), startDate, endDate);
        if (threatmentInfos == null || threatmentInfos.isEmpty()) {
            throw new NoThreatmentException(null, patient, startDate, endDate, TreatmentActionMode.REMOVE_NURSING);
        }

        for (ThreatmentInfo threatmentInfo : threatmentInfos) {
            List<GivesHistoric> givesHistorics = this.threatmentDao.getGivesHistoric(threatmentInfo);
            if (givesHistorics != null && givesHistorics.size() > 0) {
                for (GivesHistoric gh : givesHistorics) {
                    if (gh.getDoseIddose() != null && gh.getDoseIddose().getIddose().equals(dose.getIddose()) && TypeGivesHistoric.isGiven(gh.getType())) {
                        OrderTimingInfo result = new OrderTimingInfo();
                        result.setIdOrderTiming(threatmentInfo.getIdOrderTiming());
                        result.setOrderTimingDate(threatmentInfo.getGiveTime());
                        result.setEquivalent(gh.getType().equals(TypeGivesHistoric.EQUIVALENT));
                        result.setAllergic(gh.getOrderTimingIdorderTiming().getOrderIdorder().getOrderMedication().getAlergy());
                        return result;
                    }
                }
            }
        }

        //throw new ImposibleRemoveThreatmentException(dose, patient, startDate, endDate);
        return null;
    }

    /***
     * Devuelve la informacion del ordertiming que tiene asignada la dosis que quieres quitar
     * @param dose
     * @param patient
     * @param startDate
     * @param endDate
     * @return
     * @throws WebContramedException
     */
    @Transactional(value="trazability-txm",readOnly = true)
    private OrderTimingInfo getOrderTimingForRemovePharmacy(Dose dose, Patient patient, Date startDate, Date endDate) throws WebContramedException {
        List<ThreatmentInfo> threatmentInfos = this.threatmentDao.getActiveThreatmentByPharmacy(patient.getId(), startDate, endDate);
        if (threatmentInfos == null || threatmentInfos.isEmpty()) {
            throw new NoThreatmentException(null, patient, startDate, endDate, TreatmentActionMode.OUTBOX_PHARMACY);
        }

        for (ThreatmentInfo threatmentInfo : threatmentInfos) {
            List<PrepareHistoric> prepareHistorics = this.threatmentDao.getPrepareHistoric(threatmentInfo);
            if (prepareHistorics != null && prepareHistorics.size() > 0) {
                for (PrepareHistoric ph : prepareHistorics) {
                    if (ph.getDoseIddose() != null && ph.getDoseIddose().getIddose().equals(dose.getIddose())) {
                        OrderTimingInfo result = new OrderTimingInfo();
                        result.setIdOrderTiming(threatmentInfo.getIdOrderTiming());
                        result.setOrderTimingDate(threatmentInfo.getGiveTime());
                        result.setEquivalent(ph.getEquivalent());
                        result.setAllergic(ph.getOrderTimingIdorderTiming().getOrderIdorder().getOrderMedication().getAlergy());
                        return result;
                    }
                }
            }
        }
        throw new ImposibleRemoveThreatmentException(dose, patient, startDate, endDate,TreatmentActionMode.OUTBOX_PHARMACY);
    }

    /***
     * Busca la inforacion del orden timing correcto para asignar la dosis a ella
     * para el caso de pharmacia
     * @param dose
     * @param patient
     * @param startDate
     * @param endDate
     * @return
     * @throws WebContramedException
     */
    @Transactional(value="trazability-txm",readOnly = true)
    private OrderTimingInfo getOrderTimingCorrectPharmacy(Dose dose, Patient patient, Date startDate, Date endDate) throws WebContramedException {
        List<ThreatmentInfo> threatmentInfos = this.threatmentDao.getActiveThreatmentByPharmacy(patient.getId(), startDate, endDate);
        if (threatmentInfos == null || threatmentInfos.isEmpty()) {
            throw new NoThreatmentException(null, patient, startDate, endDate, TreatmentActionMode.INBOX_PHARMACY);
        }
        return this.getOrderTimingCorrect(threatmentInfos, dose, patient, startDate, endDate, TreatmentActionMode.INBOX_PHARMACY);
    }

    /***
     * Busca la inforacion del orden timing correcto para asignar la dosis a ella
     * para el caso de enfermeria
     * @param dose
     * @param patient
     * @param startDate
     * @param endDate
     * @return
     * @throws WebContramedException
     */
    @Transactional(value="trazability-txm",readOnly = true)
    private OrderTimingInfo getOrderTimingCorrectNursisng(Dose dose, Patient patient, Date startDate, Date endDate, TreatmentActionMode mode) throws WebContramedException {
        List<ThreatmentInfo> threatmentInfos = this.threatmentDao.getActiveThreatmentByNursing(patient.getId(), startDate, endDate);
        if (threatmentInfos == null || threatmentInfos.isEmpty()) {
            throw new NoThreatmentException(dose, patient, startDate, endDate, mode);
        }

        OrderTimingInfo result = this.getOrderTimingCorrect(threatmentInfos, dose, patient, startDate, endDate, mode);
        return result;
    }

    @Transactional(value="trazability-txm",readOnly = true)
    private boolean isLastGivesHistoricTypeOf(Long idOrderTiming, Date giveDate, TypeGivesHistoric type) {
        List<GivesHistoric> result = this.entityManager.createQuery("from GivesHistoric gh where gh.orderTimingIdorderTiming.idorderTiming = :idOrderTiming and gh.orderTimingDate = :giveDate order by gh.eventDate desc").setParameter("idOrderTiming", idOrderTiming).setParameter("giveDate", giveDate).getResultList();//threatmentInfo.getIdOrderTiming(), threatmentInfo.getGiveTime());
        if (result != null && result.size() > 0) {
            return result.get(0).getType().equals(type);
        }
        return false;
    }

    /***
     * Busca la inforacion del orden timing correcto para asignar la dosis a ella
     * @param threatmentInfos
     * @param dose
     * @return
     * @throws WebContramedException
     */
    @Transactional(value="trazability-txm",readOnly = true)
    private OrderTimingInfo getOrderTimingCorrect(List<ThreatmentInfo> threatmentInfos, Dose dose, Patient patient, Date startDate, Date endDate, TreatmentActionMode mode) throws WebContramedException {
        OrderTimingInfo result = new OrderTimingInfo();
        result.setIdOrderTiming(null);

        double minDif = Double.NEGATIVE_INFINITY;
        double dif;
        //primera vuelta compruebo si existe uno con el mismo codigo nacional que coincida
        for (ThreatmentInfo threatmentInfo : threatmentInfos) {
            //Compruebo si es el medicamento correcto
            if ((threatmentInfo.getPrescriptionType() == PrescriptionType.ARTICLE && threatmentInfo.getCodigoNacionalMedicamento().equals(dose.getCatalogomedicamentosCODIGO().getCodigo()))
                    || (threatmentInfo.getPrescriptionType() == PrescriptionType.ACTIVE && threatmentInfo.getIdPrincipioActivo().equals(this.getIdPrincipioActivo(dose.getCatalogomedicamentosCODIGO())))) {
                //compruebo para el caso de estar quitando dosis en planta que el tratamiento no este ya en modo not_given
                //if (!mode.equals(TreatmentActionMode.REMOVE_NURSING) || !threatmentInfo.getThreatmentInfoStatus().getStatus().equals(TypeThreatmentInfoStatus.NOT_GIVEN) || !isLastGivesHistoricTypeOf(threatmentInfo.getIdOrderTiming(), threatmentInfo.getGiveTime(), TypeGivesHistoric.NOT_GIVEN)) {
                //Ahora compruebo que no se pasa en la dosis total
                if ((dif = isCorrectCuantityDose(threatmentInfo, dose)) <= 0) {
                    //si es la menor de la diferencias
                    if (dif > minDif) {
                        result.setIdOrderTiming(threatmentInfo.getIdOrderTiming());
                        if (threatmentInfo.getPrescriptionType() == PrescriptionType.ARTICLE) {
                            result.setEquivalent(false);
                        } else {
                            result.setEquivalent(true);
                        }
                        result.setOrderTimingDate(threatmentInfo.getGiveTime());
                        result.setAllergic(threatmentInfo.isAlergy());
                        minDif = dif;
                    }
                    //}
                }
                //}
            }
        }
        //segunda vuelta compruebo si se corresponde con uno del mismo principio activo
        if (result.getIdOrderTiming() == null) {
            String idPrincipioActivo;
            for (ThreatmentInfo threatmentInfo : threatmentInfos) {
                if (threatmentInfo.getPrescriptionType() == PrescriptionType.ARTICLE) {
                    //compruebo para el caso de estar quitando dosis en planta que el tratamiento no este ya en modo not_given
                    //if (!mode.equals(TreatmentActionMode.REMOVE_NURSING) || !threatmentInfo.getThreatmentInfoStatus().getStatus().equals(TypeThreatmentInfoStatus.NOT_GIVEN) || !isLastGivesHistoricTypeOf(threatmentInfo.getIdOrderTiming(), threatmentInfo.getGiveTime(), TypeGivesHistoric.NOT_GIVEN)) {
                    //Compruebo si es un medicamento con el mismo principio activo
                    idPrincipioActivo = this.getIdPrincipioActivo(dose.getCatalogomedicamentosCODIGO());
                    if (idPrincipioActivo != null && threatmentInfo.getIdPrincipioActivo() != null && threatmentInfo.getIdPrincipioActivo().equals(idPrincipioActivo)
                            && dose.getCatalogomedicamentosCODIGO().getVia() != null && threatmentInfo.getCodeVia().equals(dose.getCatalogomedicamentosCODIGO().getVia().getCode())) {
                        //Ahora compruebo que no se pasa en la dosis total
                        if ((dif = isCorrectCuantityDose(threatmentInfo, dose)) <= 0) {
                            //compruebo caso que no hay un caso de notgiven y que este no este inacabado
                            //if (!administrationMode || (administrationMode && threatmentInfo.getThreatmentInfoStatus().getStatus().equals(TypeThreatmentInfoStatus.NOT_COMPLETED))) {
                            //si es la menor de la diferencias
                            if (dif > minDif) {
                                result.setIdOrderTiming(threatmentInfo.getIdOrderTiming());
                                result.setEquivalent(true);
                                result.setOrderTimingDate(threatmentInfo.getGiveTime());
                                result.setAllergic(threatmentInfo.isAlergy());
                                minDif = dif;
                            }
                            //}
                        }
                    }
                    //}
                }
            }
        }

        if (result.getIdOrderTiming() == null) {
            //error que no coincide con ninguno o se pasa de dosis a entregar
            throw new NoCorrectThreatmentException(dose, patient, startDate, endDate, mode);
        } else {
            if (mode.equals(TreatmentActionMode.REMOVE_NURSING) && isLastGivesHistoricTypeOf(result.getIdOrderTiming(), result.getOrderTimingDate(), TypeGivesHistoric.NOT_GIVEN)) {
                throw new ImposibleRemoveThreatmentException(dose, patient, startDate, endDate,mode);
            } else {
                if (result.isAllergic()) {
                    throw new AllergyException(dose, patient);
                } else {
                    return result;
                }
            }
        }
    }

    /***
     * Devuelve el id del principio activo de un medicamento
     * @param catalogoMedicamentos
     * @return
     */
    @Transactional(value="trazability-txm",readOnly = true)
    private String getIdPrincipioActivo(CatalogoMedicamentos catalogoMedicamentos) {
        try {
            return catalogoMedicamentos.getPrincipioHasEspecialidadList().get(0).getCodigoPrincipio().getCodigo();
        } catch (Exception e) {
            return null;
        }
    }

    /***
     * Devuelve la diferencia entre la cantidad preparada ya de un tratamiento y la
     * cantidad de la dosis
     * @param threatmentInfo
     * @param dose
     * @return
     */
    @Transactional(value="trazability-txm",readOnly = true)
    private double isCorrectCuantityDose(ThreatmentInfo threatmentInfo, Dose dose) throws WebContramedException {
        if (threatmentInfo.getThreatmentInfoStatus().getStatus().equals(TypeThreatmentInfoStatus.COMPLETED)) {
            return Double.MAX_VALUE;
        }

        BigDecimal doseConverted = this.measureUnitConversionDao.convert(dose.getGiveAmount(), dose.getMeasureUnitIdmeasureUnit(), threatmentInfo.getMeasureUnitObject());
        if (doseConverted == null) {
            throw new GenericWebContramedException("Imposible Convertir entre unidades " + dose.getMeasureUnitIdmeasureUnit().getName() + "->" + threatmentInfo.getMeasureUnitObject().getName());
        }
        double rest = threatmentInfo.getThreatmentInfoStatus().getDifference() + doseConverted.doubleValue();
        if (rest > 0) {
            return Double.MAX_VALUE;
        }
        return rest;
    }
    // </editor-fold>
}

class OrderTimingInfo {

    private Long idOrderTiming;
    private boolean equivalent;
    private Date orderTimingDate;
    private boolean allergic;

    public OrderTimingInfo() {
    }

    public OrderTimingInfo(Long idOrderTiming, boolean equivalent, Date orderTimingDate, boolean allergic) {
        this.idOrderTiming = idOrderTiming;
        this.equivalent = equivalent;
        this.orderTimingDate = orderTimingDate;
        this.allergic = allergic;
    }

    public boolean isEquivalent() {
        return equivalent;
    }

    public void setEquivalent(boolean equivalent) {
        this.equivalent = equivalent;
    }

    public Long getIdOrderTiming() {
        return idOrderTiming;
    }

    public void setIdOrderTiming(Long idOrderTiming) {
        this.idOrderTiming = idOrderTiming;
    }

    public Date getOrderTimingDate() {
        return orderTimingDate;
    }

    public void setOrderTimingDate(Date orderTimingDate) {
        this.orderTimingDate = orderTimingDate;
    }

    public boolean isAllergic() {
        return allergic;
    }

    public void setAllergic(boolean allergic) {
        this.allergic = allergic;
    }
}
