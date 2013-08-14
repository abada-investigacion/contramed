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

import com.abada.contramed.persistence.entity.Staff;
import java.util.List;

/**
 *
 * @author katsu
 */
public interface LoginDao {
    /**
     * Devueleve el objecto con la informacion del personal
     * @param user nombre de usuario
     * @param password contraseña en texto plano
     * @return
     */
    public Staff login(String user,String password);
    /***
     * Devuelve una lista con el nombre de usuario, del personal con el tag 'tag'
     * @param tag
     * @return
     */
    public List<String> getUsernameByTag(String tag);
}
