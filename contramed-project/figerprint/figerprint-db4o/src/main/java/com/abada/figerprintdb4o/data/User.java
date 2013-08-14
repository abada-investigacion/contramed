/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.figerprintdb4o.data;

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

import com.abada.commons.fingerprint.enums.FingerIndex;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author katsu
 */
public class User{

    private String username;
    private Map<FingerIndex, byte[]> templates;

    public User(){
        this.username=null;
        this.templates=new HashMap<FingerIndex, byte[]>();
    }

    public Map<FingerIndex, byte[]> getTemplates() {
        return templates;
    }

    public void setTemplates(Map<FingerIndex, byte[]> templates) {
        this.templates = templates;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAll(User user){
        this.setUsername(user.getUsername());
        this.setTemplates(user.getTemplates());
    }
}
