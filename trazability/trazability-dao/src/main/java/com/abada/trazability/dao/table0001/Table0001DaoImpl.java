package com.abada.trazability.dao.table0001;

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
import com.abada.trazability.entity.Table0001;
import org.springframework.stereotype.Repository;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author david
 *
 * Dao de la entidad table0001 , trabajamos con los datos de sexo de un paciente
 *
 */
//@Repository("table0001Dao")
public class Table0001DaoImpl extends JpaDaoUtils implements Table0001Dao {
    @PersistenceContext(unitName = "trazabilityPU")
    private EntityManager entityManager;

    /**
     *obtenemos la lista de Table0001 a partir de code
     * @param code
     * @return List {@link Table0001}
     */
    @Transactional(value="trazability-txm",readOnly = true)
    @Override
    public List<Table0001> findByCode(String code) {
        return this.entityManager.createNamedQuery("Table0001.findByCode").setParameter("code", code).getResultList();
    }
}
