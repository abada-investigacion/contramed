/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.trazability.dao.login;

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

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author katsu
 */
//@Repository("loginDao")
public class LoginDaoImpl implements LoginDao {

    @PersistenceContext(unitName = "trazabilityPU")
    private EntityManager entityManager;

    /***
     * Devuelve una lista con el nombre de usuario, del personal con el tag 'tag'
     * @param tag
     * @return
     */
    @Transactional(value="trazability-txm",readOnly = true)
    public List<String> getUsernameByTag(String tag) {
        return this.entityManager.createQuery("select s.username from Staff s where s.tag = :tag").setParameter("tag", tag).getResultList();
    }
}
