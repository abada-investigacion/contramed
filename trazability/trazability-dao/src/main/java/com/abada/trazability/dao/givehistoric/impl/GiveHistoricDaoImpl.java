/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.trazability.dao.givehistoric.impl;

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

import com.abada.trazability.dao.givehistoric.GiveHistoricDao;
import com.abada.trazability.entity.GivesHistoric;
import com.abada.springframework.orm.jpa.support.JpaDaoUtils;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author katsu
 */
@Repository("giveHistoricDao")
public class GiveHistoricDaoImpl extends JpaDaoUtils implements GiveHistoricDao {

    @PersistenceContext(unitName = "trazabilityPU")
    private EntityManager entityManager;

    /**
     * Devuelve el numero de resultados que coinciden en el hisrtorial de Administración
     * con los filtros que se pasan
     * @param filters filtros para la JPQL
     * @return
     */
    @Transactional(value="trazability-txm",readOnly = true)
    public Long loadGiveHistoricSizeAll(GridRequest filters) {
        List<Long> result = this.find(this.entityManager,"select count(*) from GivesHistoric gi " + filters.getQL("gi", true), filters.getParamsValues());
        return result.get(0);
    }

    /**
     * Devuelve los resultados paginados que coinciden en el hisrtorial de Administración
     * con los filtros que se pasan
     * @param filters filtros para la JPQL
     * @return
     */
    @Transactional(value="trazability-txm",readOnly = true)
    public List<GivesHistoric> loadGiveHistoricAll(GridRequest filters) {
        return this.find(this.entityManager,"from GivesHistoric gi " + filters.getQL("gi", true), filters.getParamsValues(), filters.getStart(), filters.getLimit());
    }

    @Transactional(value="trazability-txm",readOnly = true)
    public long loadGiveHistoricAllPrecisaCount(GridRequest filters) {
        List<Long> result =this.find(this.entityManager,"select count(*) from GivesHistoric gi where gi.orderTimingIdorderTiming in (select idorderTiming from OrderTiming o where (o.ifNecesary = TRUE OR o.repetitionPattern = 'SP')) " + filters.getQL("gi", false), filters.getParamsValues(), filters.getStart(), filters.getLimit());
        if (result!=null && !result.isEmpty())
            return result.get(0);
        return Long.MIN_VALUE;
    }

    @Transactional(value="trazability-txm",readOnly = true)
    public List<GivesHistoric> loadGiveHistoricAllPrecisa(GridRequest filters) {
        return this.find(this.entityManager,"from GivesHistoric gi where gi.orderTimingIdorderTiming in (select idorderTiming from OrderTiming o where (o.ifNecesary = TRUE OR o.repetitionPattern = 'SP')) " + filters.getQL("gi", false), filters.getParamsValues(), filters.getStart(), filters.getLimit());
    }
}
