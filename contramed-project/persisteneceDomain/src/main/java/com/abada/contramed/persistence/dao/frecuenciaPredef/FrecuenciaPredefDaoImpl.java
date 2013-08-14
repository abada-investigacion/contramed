/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.contramed.persistence.dao.frecuenciaPredef;

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

import com.abada.contramed.persistence.entity.FrecuenciaPredef;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import com.abada.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mmartin
 *
 * Dao de la entidad {@link FrecuenciaPredef}. Trabaja con los datos de las distintas frecuencias.
 */
@Repository("frecuenciaPredefDao")
public class FrecuenciaPredefDaoImpl extends JpaDaoSupport implements FrecuenciaPredefDao {

    /**
     * Constructor
     * @param entityManagerFactory
     */
    @Resource(name = "entityManager")
    public void setEntityManagerFactory2(EntityManagerFactory entityManagerFactory) {
        setEntityManagerFactory(entityManagerFactory);
    }

    /**
     * Devuelve una lista de {@link FrecuenciaPredef} <br/>
     * @return List<{@link FrecuenciaPredef}>
     */
    @Transactional(readOnly = true)
    @Override
    public List<FrecuenciaPredef> listFrecuenciaPredef() {
        List<FrecuenciaPredef> result = (List<FrecuenciaPredef>) this.getJpaTemplate().find("SELECT f FROM FrecuenciaPredef f ORDER BY Nombre ASC");
        return result;
    }
}
