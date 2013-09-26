package com.abada.trazability.dao.measureUnit;

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

import com.abada.springframework.orm.jpa.support.JpaDaoUtils;
import com.abada.trazability.entity.MeasureUnit;
import org.springframework.stereotype.Repository;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author david
 *
 * Dao de la entidad MeasureUnit, datos de unidad de medida
 */
@Repository("measureUnitDao")
public class MeasureUnitDaoImpl extends JpaDaoUtils implements MeasureUnitDao {

    @PersistenceContext(unitName = "trazabilityPU")
    private EntityManager entityManager;

    /**
     *obtenemos la lista de todos los MeasureUnit {@link MeasureUnit}
     * @return list {@link MeasureUnit}
     */
    @Transactional(value="trazability-txm",readOnly = true)
    @Override
    public List<MeasureUnit> findAll() {
        return this.entityManager.createQuery("SELECT m FROM MeasureUnit m ORDER BY name ASC").getResultList();
    }

    /**
     *obtenemos la lista de MeasureUnit {@link MeasureUnit} a partir de idmeasureUnit
     * @param idmeasureUnit
     * @return list {@link MeasureUnit}
     */
    @Transactional(value="trazability-txm",readOnly = true)
    @Override
    public List<MeasureUnit> findByIdmeasureUnit(Integer idmeasureUnit) {        
        return this.entityManager.createNamedQuery("MeasureUnit.findByIdmeasureUnit").setParameter("idmeasureUnit", idmeasureUnit).getResultList();
    }

    /**
     *obtenemos la lista de MeasureUnit {@link MeasureUnit} a partir de description
     * @param description
     * @return list {@link MeasureUnit}
     */
    @Transactional(value="trazability-txm",readOnly = true)
    @Override
    public List<MeasureUnit> findByName(String name) {        
        return this.entityManager.createNamedQuery("MeasureUnit.findByName").setParameter("name", name).getResultList();
    }
}

