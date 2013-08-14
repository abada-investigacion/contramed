/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.contramed.persistence.dao.login;

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

import com.abada.authentication.ldap.Authentication;
import com.abada.contramed.persistence.entity.Staff;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author katsu
 */
@Repository("loginDao")
public class LoginDaoImpl extends JpaDaoSupport implements LoginDao {

    private Authentication authentication;

    @Resource(name = "authenticationContramed")
    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    @Resource(name = "entityManager")
    public void setEntityManagerFactory2(EntityManagerFactory entityManagerFactory) {
        setEntityManagerFactory(entityManagerFactory);
    }

    /**
     * Devueleve el objecto con la informacion del personal
     * @param user nombre de usuario
     * @param password contraseña en texto plano
     * @return
     */
    @Transactional(readOnly = true)
    public Staff login(String user, String password) {
        Staff result = null;
        Map userValues = authentication.authenticate(user, password);
        if (userValues != null && userValues.size() > 0) {
            List<Staff> resultAux = this.getJpaTemplate().find("from Staff s where s.username = ?", user);
            if (resultAux != null && resultAux.size() == 1) {
                result = resultAux.get(0);
            }
        }
        return result;
    }

    /***
     * Devuelve una lista con el nombre de usuario, del personal con el tag 'tag'
     * @param tag
     * @return
     */
    @Transactional(readOnly = true)
    public List<String> getUsernameByTag(String tag) {
        return this.getJpaTemplate().find("select s.username from Staff s where s.tag = ?",tag);
    }
}
