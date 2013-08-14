/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abada.contramed.persistence.dao.observation;

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

import com.abada.contramed.persistence.dao.patient.PatientDao;
import com.abada.contramed.persistence.dao.staff.StaffDao;
import com.abada.contramed.persistence.entity.Observation;
import com.abada.contramed.persistence.entity.Patient;
import com.abada.contramed.persistence.entity.Staff;
import com.abada.springframework.orm.jpa.support.JpaDaoSupport;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest_Extjs_v3;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author maria
 */

@Repository("observationDao")
public class ObservationDaoImpl extends JpaDaoSupport implements ObservationDao{
     /**
     *
     * @param entityManagerFactory
     */
    @Resource(name = "entityManager")
    public void setEntityManagerFactory2(EntityManagerFactory entityManagerFactory) {
        setEntityManagerFactory(entityManagerFactory);
    }

    
   @Transactional(readOnly = true)
    public Long loadSizeAll(GridRequest_Extjs_v3 filters) {
        List<Long> result = this.find("select count(*) from Observation o" + filters.getQL("o", true), filters.getParamsValues());
        return result.get(0);
    }


    @Transactional(readOnly = true)
    public List<Observation> loadAll(GridRequest_Extjs_v3 filters) {
       return this.find("from Observation o" + filters.getQL("o", true), filters.getParamsValues(), filters.getStart(), filters.getLimit());
        
    }
    @Transactional
    public void insert(Date eventTime,String observation, Long patient,Long staff){
         
        Observation ob = new Observation();
        ob.setEventTime(eventTime);
        ob.setObservation(observation);
        Staff s = this.getJpaTemplate().find(Staff.class,staff );
        ob.setStaffId(s);
        s.addObservation(ob);
        Patient p = this.getJpaTemplate().find(Patient.class, patient);
        ob.setPatientId(p);
        p.addObservation(ob);
        this.getJpaTemplate().persist(ob);
       
    }
    
}

    

