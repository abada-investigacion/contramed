/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abada.contramed.persistence.dao.preparehistoric.impl;

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

import com.abada.contramed.persistence.dao.preparehistoric.PrepareHistoricDao;
import com.abada.contramed.persistence.entity.PrepareHistoric;
import com.abada.springframework.orm.jpa.support.JpaDaoSupport;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest_Extjs_v3;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author katsu
 */
@Repository("prepareHistoricDao")
public class PrepareHistoricDaoImpl extends JpaDaoSupport implements PrepareHistoricDao{

    @Resource(name = "entityManager")
    public void setEntityManagerFactory2(EntityManagerFactory entityManagerFactory) {
        setEntityManagerFactory(entityManagerFactory);
    }
    @Transactional(readOnly=true)
    public Long loadPrepareHistoricSizeAll(GridRequest_Extjs_v3 filters) {
        List<Long> result = this.find("select count(*) from PrepareHistoric gi " + filters.getQL("gi", true), filters.getParamsValues());
        return result.get(0);
    }

    @Transactional(readOnly=true)
    public List<PrepareHistoric> loadPrepareHistoricAll(GridRequest_Extjs_v3 filters) {
        return this.find("from PrepareHistoric gi " + filters.getQL("gi", true), filters.getParamsValues(),filters.getStart(), filters.getLimit());
    }
}
