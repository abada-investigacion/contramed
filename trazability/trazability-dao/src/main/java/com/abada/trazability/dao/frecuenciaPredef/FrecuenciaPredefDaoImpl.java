/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.trazability.dao.frecuenciaPredef;

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

import com.abada.trazability.entity.FrecuenciaPredef;
import java.util.List;
import com.abada.springframework.orm.jpa.support.JpaDaoUtils;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mmartin
 *
 * Dao de la entidad {@link FrecuenciaPredef}. Trabaja con los datos de las distintas frecuencias.
 */
@Repository("frecuenciaPredefDao")
public class FrecuenciaPredefDaoImpl extends JpaDaoUtils implements FrecuenciaPredefDao {

    @PersistenceContext(unitName = "trazabilityPU")
    private EntityManager entityManager;
    /**
     * Devuelve una lista de {@link FrecuenciaPredef} <br/>
     * @return List<{@link FrecuenciaPredef}>
     */
    @Transactional(value="trazability-txm",readOnly = true)
    @Override
    public List<FrecuenciaPredef> listFrecuenciaPredef() {
        List<FrecuenciaPredef> result = (List<FrecuenciaPredef>) this.entityManager.createQuery("SELECT f FROM FrecuenciaPredef f ORDER BY Nombre ASC").getResultList();
        return result;
    }
}
