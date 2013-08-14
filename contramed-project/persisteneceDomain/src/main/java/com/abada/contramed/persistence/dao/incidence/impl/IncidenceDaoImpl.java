/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.contramed.persistence.dao.incidence.impl;

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

import com.abada.contramed.persistence.dao.incidence.IncidenceDao;
import com.abada.contramed.persistence.entity.Dose;
import com.abada.contramed.persistence.entity.GivesIncidence;
import com.abada.contramed.persistence.entity.OrderTiming;
import com.abada.contramed.persistence.entity.Patient;
import com.abada.contramed.persistence.entity.PrepareIncidence;
import com.abada.contramed.persistence.entity.Staff;
import com.abada.springframework.orm.jpa.support.JpaDaoSupport;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest_Extjs_v3;
import com.abada.webcontramed.exception.MedicacitionNotGivenException;
import com.abada.webcontramed.exception.WebContramedException;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author katsu
 */
@Repository("incidenceDao")
public class IncidenceDaoImpl extends JpaDaoSupport implements IncidenceDao {

    @Resource(name = "entityManager")
    public void setEntityManagerFactory2(EntityManagerFactory entityManagerFactory) {
        setEntityManagerFactory(entityManagerFactory);
    }

    @Transactional
    public void generateGiveIncidence(Staff staff, Dose dose, Patient patient, Long idOrderTiming, WebContramedException exception) {
        OrderTiming orderTiming = null;
        if (idOrderTiming != null) {
            orderTiming = this.getJpaTemplate().find(OrderTiming.class, idOrderTiming);
        }
        Patient patAux = this.getJpaTemplate().find(Patient.class, patient.getId());
        Dose doseAux = null;
        if (dose != null) {
            doseAux = this.getJpaTemplate().find(Dose.class, dose.getIddose());
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

        this.getJpaTemplate().persist(givesIncidence);
    }

    @Transactional
    public void generatePrepareIncidence(Staff staff, Dose dose, Patient patient, Long idOrderTiming, WebContramedException exception) {
        OrderTiming orderTiming = null;
        if (idOrderTiming != null) {
            orderTiming = this.getJpaTemplate().find(OrderTiming.class, idOrderTiming);
        }
        Patient patAux = this.getJpaTemplate().find(Patient.class, patient.getId());
        Dose doseAux = null;
        if (dose != null) {
            doseAux = this.getJpaTemplate().find(Dose.class, dose.getIddose());
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

        this.getJpaTemplate().persist(prepareIncidence);
    }

    @Transactional(readOnly = true)
    public Long loadGivesIncidencesSizeAll(GridRequest_Extjs_v3 filters) {
        List<Long> result = this.find("select count(*) from GivesIncidence gi " + filters.getQL("gi", true), filters.getParamsValues());
        return result.get(0);
    }

    @Transactional(readOnly = true)
    public List<GivesIncidence> loadGivesIncidencesAll(GridRequest_Extjs_v3 filters) {
        return this.find("from GivesIncidence gi " + filters.getQL("gi", true), filters.getParamsValues(),filters.getStart(), filters.getLimit());
    }

    @Transactional(readOnly = true)
    public Long loadPrepareIncidencesSizeAll(GridRequest_Extjs_v3 filters) {
        List<Long> result = this.find("select count(*) from PrepareIncidence pi " + filters.getQL("pi", true), filters.getParamsValues());
        return result.get(0);
    }

    @Transactional(readOnly = true)
    public List<PrepareIncidence> loadPrepareIncidencesAll(GridRequest_Extjs_v3 filters) {
        return this.find("from PrepareIncidence pi " + filters.getQL("pi", true), filters.getParamsValues(),filters.getStart(), filters.getLimit());
    }

    @Transactional
    public void generateMedicationNotGivenException(Staff staff, Long idDose, Patient patient) {
        Dose dose=this.getJpaTemplate().find(Dose.class, idDose);
        if (dose!=null){
            this.generateGiveIncidence(staff, dose, patient, null, new MedicacitionNotGivenException(dose));
        }
    }
}
