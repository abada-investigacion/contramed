/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.springframework.web.servlet.command.extjs.gridpanel.factory;

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

import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest_Extjs_v3;

/**
 * Clase que posee la misma estructura que los datos que envia extjs en los filtros
 * usado para poder crear el objecto {@link GridRequest_Extjs_v3}
 * @author katsu
 */
class FilterRequestPriv {
    /**
     * valor para filtrar
     */
    private String value;
    /**
     * tipo de filtro numeric, date, etc
     */
    private String type;
    /**
     * tipo de comparacion
     */
    private String comparison;
    /**
     * nombre del campo
     */
    private String field;
    /**
     * en el caso de un filtro list que se corresponda con un enum el nombre completo
     * de la clase del enum
     */
    private String enumType;

    FilterRequestPriv() {
        value = "";
        type = "";
        comparison = "";
        field = "";
        enumType = null;
    }

    public String getEnumType() {
        return enumType;
    }

    public void setEnumType(String enumType) {
        this.enumType = enumType;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getComparison() {
        return comparison;
    }

    public void setComparison(String comparison) {
        this.comparison = comparison;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
