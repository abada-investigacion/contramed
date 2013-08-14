package com.abada.contramed.persistence.dao.measureUnit;

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

import com.abada.contramed.persistence.entity.MeasureUnit;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author david
 *
 * Dao de la entidad MeasureUnit, datos de unidad de medida
 */
@Repository("measureUnitDao")
public class MeasureUnitDaoImpl extends JpaDaoSupport implements MeasureUnitDao {

    @Resource(name = "entityManager")
    public void setEntityManagerFactory2(EntityManagerFactory entityManagerFactory) {
        setEntityManagerFactory(entityManagerFactory);
    }

    /**
     *obtenemos la lista de todos los MeasureUnit {@link MeasureUnit}
     * @return list {@link MeasureUnit}
     */
    @Transactional(readOnly = true)
    @Override
    public List<MeasureUnit> findAll() {
        return this.getJpaTemplate().find("SELECT m FROM MeasureUnit m ORDER BY name ASC");
    }

    /**
     *obtenemos la lista de MeasureUnit {@link MeasureUnit} a partir de idmeasureUnit
     * @param idmeasureUnit
     * @return list {@link MeasureUnit}
     */
    @Transactional(readOnly = true)
    @Override
    public List<MeasureUnit> findByIdmeasureUnit(Integer idmeasureUnit) {
        Map<String, Integer> parametros = new HashMap<String, Integer>();
        parametros.put("idmeasureUnit", idmeasureUnit);
        return this.getJpaTemplate().findByNamedQueryAndNamedParams("MeasureUnit.findByIdmeasureUnit", parametros);
    }

    /**
     *obtenemos la lista de MeasureUnit {@link MeasureUnit} a partir de description
     * @param description
     * @return list {@link MeasureUnit}
     */
    @Transactional(readOnly = true)
    @Override
    public List<MeasureUnit> findByName(String name) {
        Map<String, String> parametros = new HashMap<String, String>();
        parametros.put("name", name);
        return this.getJpaTemplate().findByNamedQueryAndNamedParams("MeasureUnit.findByName", parametros);
    }
}

