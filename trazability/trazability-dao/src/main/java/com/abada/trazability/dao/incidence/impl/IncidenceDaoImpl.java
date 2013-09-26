/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.trazability.dao.incidence.impl;

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

import com.abada.trazability.dao.incidence.IncidenceDao;
import com.abada.trazability.entity.Dose;
import com.abada.trazability.entity.GivesIncidence;
import com.abada.trazability.entity.OrderTiming;
import com.abada.trazability.entity.Patient;
import com.abada.trazability.entity.PrepareIncidence;
import com.abada.trazability.entity.Staff;
import com.abada.springframework.orm.jpa.support.JpaDaoUtils;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest;
import com.abada.trazability.exception.MedicacitionNotGivenException;
import com.abada.trazability.exception.WebContramedException;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author katsu
 */
@Repository("incidenceDao")
public class IncidenceDaoImpl extends JpaDaoUtils implements IncidenceDao {

    @PersistenceContext(unitName = "trazabilityPU")
    private EntityManager entityManager;


    @Transactional
    public void generateGiveIncidence(Staff staff, Dose dose, Patient patient, Long idOrderTiming, WebContramedException exception) {
        OrderTiming orderTiming = null;
        if (idOrderTiming != null) {
            orderTiming = this.entityManager.find(OrderTiming.class, idOrderTiming);
        }
        Patient patAux = this.entityManager.find(Patient.class, patient.getId());
        Dose doseAux = null;
        if (dose != null) {
            doseAux = this.entityManager.find(Dose.class, dose.getIddose());
        }

        GivesIncidence givesIncidence = new GivesIncidence();
        givesIncidence.setEventDate(new Date());
        givesIncidence.setOrderTimingIdorderTiming(orderTiming);
        givesIncidence.setStaffIdstaff(staff);
        givesIncidence.setIncidence(exception.getFullMessage());
        givesIncidence.setPatient(patAux);
        givesIncidence.setBed(patAux.getBed());
        givesIncidence.setTypeIncidence(exception.getType());
        givesIncidence.setDose(doseAux);

        if (orderTiming != null) {
            orderTiming.addGivesIncidence(givesIncidence);
        }
        patAux.addGivesIncidence(givesIncidence);
        patAux.getBed().addGivesIncidence(givesIncidence);
        if (doseAux != null) {
            doseAux.addGivesIncidence(givesIncidence);
        }

        this.entityManager.persist(givesIncidence);
    }

    @Transactional
    public void generatePrepareIncidence(Staff staff, Dose dose, Patient patient, Long idOrderTiming, WebContramedException exception) {
        OrderTiming orderTiming = null;
        if (idOrderTiming != null) {
            orderTiming = this.entityManager.find(OrderTiming.class, idOrderTiming);
        }
        Patient patAux = this.entityManager.find(Patient.class, patient.getId());
        Dose doseAux = null;
        if (dose != null) {
            doseAux = this.entityManager.find(Dose.class, dose.getIddose());
        }

        PrepareIncidence prepareIncidence = new PrepareIncidence();
        prepareIncidence.setEventDate(new Date());
        prepareIncidence.setOrderTimingIdorderTiming(orderTiming);
        prepareIncidence.setStaffIdstaff(staff);
        prepareIncidence.setIncidence(exception.getFullMessage());
        prepareIncidence.setPatient(patAux);
        prepareIncidence.setBed(patAux.getBed());
        prepareIncidence.setTypeIncidence(exception.getType());
        prepareIncidence.setDose(doseAux);

        if (orderTiming != null) {
            orderTiming.addPrepareIncidence(prepareIncidence);
        }
        patAux.addPrepareIncidence(prepareIncidence);
        patAux.getBed().addPrepareIncidence(prepareIncidence);
        if (doseAux != null) {
            doseAux.addPrepareIncidence(prepareIncidence);
        }

        this.entityManager.persist(prepareIncidence);
    }

    @Transactional(value="trazability-txm",readOnly = true)
    public Long loadGivesIncidencesSizeAll(GridRequest filters) {
        List<Long> result = this.find(this.entityManager,"select count(*) from GivesIncidence gi " + filters.getQL("gi", true), filters.getParamsValues());
        return result.get(0);
    }

    @Transactional(value="trazability-txm",readOnly = true)
    public List<GivesIncidence> loadGivesIncidencesAll(GridRequest filters) {
        return this.find(this.entityManager,"from GivesIncidence gi " + filters.getQL("gi", true), filters.getParamsValues(),filters.getStart(), filters.getLimit());
    }

    @Transactional(value="trazability-txm",readOnly = true)
    public Long loadPrepareIncidencesSizeAll(GridRequest filters) {
        List<Long> result = this.find(this.entityManager,"select count(*) from PrepareIncidence pi " + filters.getQL("pi", true), filters.getParamsValues());
        return result.get(0);
    }

    @Transactional(value="trazability-txm",readOnly = true)
    public List<PrepareIncidence> loadPrepareIncidencesAll(GridRequest filters) {
        return this.find(this.entityManager,"from PrepareIncidence pi " + filters.getQL("pi", true), filters.getParamsValues(),filters.getStart(), filters.getLimit());
    }

    @Transactional
    public void generateMedicationNotGivenException(Staff staff, Long idDose, Patient patient) {
        Dose dose=this.entityManager.find(Dose.class, idDose);
        if (dose!=null){
            this.generateGiveIncidence(staff, dose, patient, null, new MedicacitionNotGivenException(dose));
        }
    }
}
