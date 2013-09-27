package com.abada.trazability.dao.tablez029;

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
import com.abada.trazability.entity.Tablez029;
import org.springframework.stereotype.Repository;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author david
 *
 * Dao de la entidad tablez029 , trabajamos con los datos de los distintos
 * Id de un paciente
 */
//@Repository("tablez029Dao")
public class Tablez029DaoImpl extends JpaDaoUtils implements Tablez029Dao {
    @PersistenceContext(unitName = "trazabilityPU")
    private EntityManager entityManager;
    /**
     *obtenemos la lista de todos los Tablez029
     * @return List {@link Tablez029}
     */
    @Transactional(value="trazability-txm",readOnly = true)
    @Override
    public List<Tablez029> findAll() {
        return this.entityManager.createNamedQuery("Tablez029.findAll").getResultList();
    }

    /**
     *obtenemos la lista de Tablez029 a partir de code
     * @param code
     * @return List {@link Tablez029}
     */
    @Transactional(value="trazability-txm",readOnly = true)
    @Override
    public List<Tablez029> findByCode(String code) {
        return this.entityManager.createNamedQuery("Tablez029.findByCode").setParameter("code", code).getResultList();
    }
}

