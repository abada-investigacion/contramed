/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.springframework.web.servlet.command.extjs.gridpanel.filtertype;

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

import com.abada.extjs.grid.mapping.MappedDataGrid;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.FilterRequest;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.factory.ComparisonType;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementa el filtro de tipo date
 * @author katsu
 */
public class DateFilter extends FilterRequest {

    private Date value;

    public DateFilter(Date value, String fieldName, ComparisonType operation,int index) {
        super(operation, fieldName,index);
        this.value = value;
    }

    @Override
    public String getQL(String entityName,MappedDataGrid ...mapping) {
        String result = this.getMappedFieldName(entityName,mapping);
        switch (this.getOperation()) {
            case EQUAL:
                result += " BETWEEN :";
                break;
            case BEFORE:
                result += " < :";
                break;
            case AFTER:
                result += " > :";
                break;
        }
        result += this.generateParamName(0);
        switch (this.getOperation()) {
            case EQUAL:
                result += " AND :";
                result += this.generateParamName(1);
                break;
        }
        return result;
    }

    @Override
    public Map<String, Object> getValue() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(this.generateParamName(0), value);
        switch (this.getOperation()) {
            case EQUAL:
                Date value2 = new Date(value.getTime());
                ((Date) value2).setHours(23);
                ((Date) value2).setMinutes(59);
                ((Date) value2).setSeconds(59);
                result.put(this.generateParamName(1), value2);
                break;
        }

        return result;
    }
}
