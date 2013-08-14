/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.contramed.properties;

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

/**
 *
 * @author katsu
 */
public class ContramedProperties {
    private int administrationLowLimit;
    private int administrationUpLimit;
    private int prepareLowLimit;
    private int prepareUpLimit;

    public int getAdministrationLowLimit() {
        return administrationLowLimit;
    }

    public void setAdministrationLowLimit(int administrationLowLimit) {
        this.administrationLowLimit = administrationLowLimit;
    }

    public int getAdministrationUpLimit() {
        return administrationUpLimit;
    }

    public void setAdministrationUpLimit(int administrationUpLimit) {
        this.administrationUpLimit = administrationUpLimit;
    }

    public int getPrepareLowLimit() {
        return prepareLowLimit;
    }

    public void setPrepareLowLimit(int prepareLowLimit) {
        this.prepareLowLimit = prepareLowLimit;
    }

    public int getPrepareUpLimit() {
        return prepareUpLimit;
    }

    public void setPrepareUpLimit(int prepareUpLimit) {
        this.prepareUpLimit = prepareUpLimit;
    }
}
