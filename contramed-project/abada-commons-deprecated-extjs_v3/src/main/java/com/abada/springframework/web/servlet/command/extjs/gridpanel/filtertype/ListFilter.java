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
import com.abada.utils.enumerates.Utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementa el filtro de tipo list
 * @author katsu
 */
public class ListFilter extends FilterRequest {

    private List<String> value;
    private Class enumClass;

    public ListFilter(List<String> value, String fieldName, Class enumClass,int index) {
        super(ComparisonType.EQUAL, fieldName,index);
        this.value = value;
        this.enumClass = enumClass;
    }

    @Override
    public String getQL(String entityName,MappedDataGrid ...mapping) {
        String result = this.getMappedFieldName(entityName,mapping);
        result += " IN (:";
        result += this.generateParamName(0);
        result+=")";
        return result;
    }

    @Override
    public Map<String, Object> getValue() {
        Map<String, Object> result = new HashMap<String, Object>();
        List addList;
        if (enumClass == null) {
            addList=value;
        } else {
            addList=new ArrayList();
            for (String s:value){
                addList.add(Utils.get(s, enumClass));
            }
        }
        result.put(this.generateParamName(0), addList);
        return result;
    }
}
