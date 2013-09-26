/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.trazability.dao.timingrange;

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

import com.abada.trazability.entity.TimingRange;
import com.abada.springframework.orm.jpa.support.JpaDaoUtils;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author maria
 *
 * Dao de la entidad TimingRange , trabajamos con los datos de las diferentes franjas horarias
 */
@Repository("TimingRangeDao")
public class TimingRangeDaoImpl extends JpaDaoUtils implements TimingRangeDao {

    /**
     *
     * @param entityManagerFactory
     */
    @Resource(name = "entityManager")
    public void setEntityManagerFactory2(EntityManagerFactory entityManagerFactory) {
        setEntityManagerFactory(entityManagerFactory);
    }

    /**obtenemos las lista de todos los timingRange
     *obtenemos la lista de todos los TimingRange
     * @return List {@link TimingRange}
     */
    @Transactional(value="trazability-txm",readOnly = true)
    public List<TimingRange> listTimingRange() {
        List<TimingRange> result = (List<TimingRange>) this.entityManager.findByNamedQuery("TimingRange.findAll");
        return result;
    }

    /**
     *obtenemos una cuenta de todos los timingRange selecionados por el filters
     * @param filters
     * @return List {@link TimingRange}
     */
    @Transactional(value="trazability-txm",readOnly = true)
    public Long loadSizeAll(GridRequest filters) {
        List<Long> result = this.find("select count(*) from TimingRange tr" + filters.getQL("tr", true), filters.getParamsValues());
        return result.get(0);
    }

    /**
     *obtenemos la lista de todos los TimingRange selecionado por el filters
     * @param filters
     * @return List {@link TimingRange}
     */
    @Transactional(value="trazability-txm",readOnly = true)
    public List<TimingRange> loadAll(GridRequest filters) {
        List<TimingRange> timingRange = this.find("from TimingRange tr" + filters.getQL("tr", true), filters.getParamsValues(), filters.getStart(), filters.getLimit());
        return timingRange;
    }

    /**
     *insertamos timing Range
     * @param startTime
     * @param endTime
     * @param name
     */
    @Transactional
    public void insertTimingRange(Date startTime, Date endTime, String name) {
        TimingRange tr = new TimingRange();
        tr.setStartTime(startTime);
        tr.setEndTime(endTime);
        tr.setName(name);
        this.entityManager.persist(tr);
    }

    /**
     * modificamos timing Range
     * @param startTime
     * @param endTime
     * @param name
     * @param idtimingRange
     */
    @Transactional
    public void updateTimingRange(Date startTime, Date endTime, String name, Long idtimingRange) {

        TimingRange tr = (TimingRange) this.entityManager.find(TimingRange.class, new Long(idtimingRange));

        if (tr.getIdtimingRange().compareTo(idtimingRange) == 0) {
            tr.setEndTime(endTime);
            tr.setStartTime(startTime);
            tr.setName(name);
            this.entityManager;

        }
    }

    /**
     * borramos un TimingRange dado su id
     * @param timingRange
     */
    @Transactional
    public void removeTimingRange(Long timingRange) {

        TimingRange tr = (TimingRange) this.entityManager.find(TimingRange.class, new Long(timingRange));
        this.entityManager.remove(tr);
    }

    /**
     *obtenemos la lista de TimingRange a partir de idtimingRange
     * @param idtimingRange
     * @return List {@link TimingRange}
     */
    @Transactional(value="trazability-txm",readOnly = true)
    @Override
    public List<TimingRange> findByIdtimingRange(Long idtimingRange) {
        Map<String, Long> parametros = new HashMap<String, Long>();
        parametros.put("idtimingRange", idtimingRange);
        return this.entityManager.findByNamedQueryAndNamedParams("TimingRange.findByIdtimingRange", parametros);
    }
}


