/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abada.trazability.dao.observation;

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

import com.abada.trazability.entity.Observation;
import com.abada.trazability.entity.Patient;
import com.abada.trazability.entity.Staff;
import com.abada.springframework.orm.jpa.support.JpaDaoUtils;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author maria
 */

//@Repository("observationDao")
public class ObservationDaoImpl extends JpaDaoUtils implements ObservationDao{
    @PersistenceContext(unitName = "trazabilityPU")
    private EntityManager entityManager;
    
   @Transactional(value="trazability-txm",readOnly = true)
    public Long loadSizeAll(GridRequest filters) {
        List<Long> result = this.find(this.entityManager,"select count(*) from Observation o" + filters.getQL("o", true), filters.getParamsValues());
        return result.get(0);
    }


    @Transactional(value="trazability-txm",readOnly = true)
    public List<Observation> loadAll(GridRequest filters) {
       return this.find(this.entityManager,"from Observation o" + filters.getQL("o", true), filters.getParamsValues(), filters.getStart(), filters.getLimit());
        
    }
    @Transactional(value="trazability-txm")
    public void insert(Date eventTime,String observation, Long patient,Long staff){
         
        Observation ob = new Observation();
        ob.setEventTime(eventTime);
        ob.setObservation(observation);
        Staff s = this.entityManager.find(Staff.class,staff );
        ob.setStaffId(s);
        s.addObservation(ob);
        Patient p = this.entityManager.find(Patient.class, patient);
        ob.setPatientId(p);
        p.addObservation(ob);
        this.entityManager.persist(ob);
       
    }
    
}

    

