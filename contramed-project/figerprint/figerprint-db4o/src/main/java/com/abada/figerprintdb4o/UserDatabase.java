/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.figerprintdb4o;

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

import com.abada.commons.fingerprint.Verificator;
import com.abada.figerprintdb4o.data.User;
import java.util.List;

/**
 *
 * @author katsu
 */
public interface UserDatabase {

    /**
     * Adds a user to the database
     *
     * @param username user name
     * @return user added
     */
    public void addUser(User user);

    /**
     * Finds a user by name
     *
     * @param username user to find
     * @return user found, or <code>null</code> if not found)
     */
    public User getUser(String username);
    
    public List<User> getAllUser();

    public User getUserByFingerPrint(byte [] finger,Verificator verificator);
}
