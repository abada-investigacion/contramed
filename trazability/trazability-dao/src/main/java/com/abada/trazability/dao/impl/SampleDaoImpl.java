
package com.abada.trazability.dao.impl;

/*
 * #%L
 * Web Archetype
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

import com.abada.trazability.dao.SampleDao;
import com.abada.trazability.entity.SampleEntity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import com.abada.springframework.orm.jpa.support.JpaDaoUtils;


/**
 *
 * @author katsu
 */
public class SampleDaoImpl extends JpaDaoUtils implements SampleDao{
    @PersistenceContext(unitName = "trazabilityPU")
    private EntityManager entityManager;
    
    @Transactional(value = "trazability-txm", rollbackFor = {Exception.class})
    public void persist(SampleEntity entity){
        entityManager.persist(entity);
    }
}
