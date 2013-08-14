/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.authentication.ldap;

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

import com.abada.authentication.ldap.exception.LDAPException;
import java.util.Map;

/**
 *
 * @author david
 *
 * autentificación de usuarios con ldap
 */
public interface Authentication {

    /**
     * conexion de usuarios con ldap y
     * nos devuelve un hashMap con los atributos de ese usuario
     * en caso que no existe nos devuelve null
     * @param user
     * @param pass
     * @return HashMap
     */
    public Map<String, String[]> authenticate(String user, String pass);

    /**
     * Obtenemos los datos de un usuario a partir de la conexion del administrador del ldap
     * @param adminUser
     * @param adminPassword
     * @param user
     * @return
     */
    public Map<String, String[]> getUserInformation(String adminUser, String adminPassword, String user) throws LDAPException;
}
