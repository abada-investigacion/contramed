package com.abada.contramed.persistence.dao.table0002;

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

import com.abada.contramed.persistence.entity.Table0002;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author david
 *
 * Dao de la entidad table0021 , trabajamos con los datos del estado civil de un paciente
 */
@Repository("table0002Dao")
public class Table0002DaoImpl extends JpaDaoSupport implements Table0002Dao {

    /**
     *
     * @param entityManagerFactory
     */
    @Resource(name = "entityManager")
    public void setEntityManagerFactory2(EntityManagerFactory entityManagerFactory) {
        setEntityManagerFactory(entityManagerFactory);
    }

    /**
     *obtenemos la lista de Table0002 a partir de code
     * @param code
     * @return List {@link Table0002}
     */
    @Transactional(readOnly = true)
    @Override
    public List<Table0002> findByCode(String code) {
        Map<String, String> parametros = new HashMap<String, String>();
        parametros.put("code", code);
        return this.getJpaTemplate().findByNamedQueryAndNamedParams("Table0002.findByCode", parametros);
    }
}

