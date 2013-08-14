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

import com.abada.springframework.web.servlet.command.extjs.gridpanel.FilterRequest;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest_Extjs_v3;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.filtertype.BooleanFilter;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.filtertype.DateFilter;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.filtertype.ListFilter;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.filtertype.NumericFilter;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.filtertype.StringFilter;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.impl.GridRequest_Extjs_v3Impl;
import com.abada.gson.GsonImpl;
import com.google.gson.reflect.TypeToken;
import com.abada.json.Json;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

/**
 * Factoria con metodos utiles para la creacion de {@link GridRequest_Extjs_v3}
 * @author katsu
 */
public class GridRequest_Extjs_v3Factory {

    private static final Json gson = new GsonImpl();

    /**
     * Devuelve el {@link GridRequest_Extjs_v3} que se representa con los valore que envia el grid panel de extjs
     * @param sort nombre del campo por el que se quiere ordenar
     * @param dir direccion del orden ASC DESC
     * @param start posicion desde la que se muestran los resultados
     * @param limit longitud maxima de los resultados
     * @param jsonFilter Json que representa los filtros de los campos para el JPQL
     * @return
     */
    public static GridRequest_Extjs_v3 parse(String sort, String dir, Integer start, Integer limit, String jsonFilter) throws UnsupportedEncodingException {        
        GridRequest_Extjs_v3Impl result = new GridRequest_Extjs_v3Impl();
        result.setDir(dir);
        result.setLimit(limit);
        result.setSort(sort);
        result.setStart(start);
        if (jsonFilter != null && !"".equals(jsonFilter)) {
            jsonFilter = URLDecoder.decode(jsonFilter, "utf-8");
            List<FilterRequestPriv> filters = gson.deserialize(jsonFilter, new TypeToken<List<FilterRequestPriv>>() {
            }.getType());
            if (filters != null) {
                for (FilterRequestPriv filterRequestPriv : filters) {
                    result.addFilterRequest(createFilterRequest(filterRequestPriv, result.countFilters()));
                }
            }
        }
        return result;

    }

    /**
     * Metodo utilizado para añadir un filtro por un campo a mano
     * @param GridRequest_Extjs_v3
     * @param comparison
     * @param type
     * @param value
     * @param fieldName
     * @param enumType
     */
    public static void addFilterRequest(GridRequest_Extjs_v3 GridRequest_Extjs_v3, String comparison, String type, String value, String fieldName, String enumType) {
        FilterRequestPriv fr = new FilterRequestPriv();
        fr.setComparison(comparison);
        fr.setType(type);
        fr.setValue(value);
        fr.setField(fieldName);
        fr.setEnumType(enumType);
        GridRequest_Extjs_v3.addFilterRequest(createFilterRequest(fr, GridRequest_Extjs_v3.countFilters()));
    }

    private static FilterRequest createFilterRequest(FilterRequestPriv filterRequestPriv, int index) {
        FilterRequest result = null;
        if (filterRequestPriv.getType().equalsIgnoreCase("numeric")) {
            Number value = null;
            if (filterRequestPriv.getValue().contains(".")) {
                value = new Double(filterRequestPriv.getValue().replace(".", ","));
            } else {
                value = new Long(filterRequestPriv.getValue());
            }
            if (value != null) {
                result = new NumericFilter(value, filterRequestPriv.getField(), getComparisonType(filterRequestPriv.getComparison()), index);
            }
        } else if (filterRequestPriv.getType().equalsIgnoreCase("decimal")) {
            result = new NumericFilter(new BigDecimal(filterRequestPriv.getValue()), filterRequestPriv.getField(), getComparisonType(filterRequestPriv.getComparison()), index);
        } else if (filterRequestPriv.getType().equalsIgnoreCase("string")) {
            result = new StringFilter(filterRequestPriv.getValue(), filterRequestPriv.getField(), index);
        } else if (filterRequestPriv.getType().equalsIgnoreCase("list")) {
            String[] value = filterRequestPriv.getValue().split(",");
            result = new ListFilter(Arrays.asList(value), filterRequestPriv.getField(), getEnumTypeClass(filterRequestPriv.getEnumType()), index);
        } else if (filterRequestPriv.getType().equalsIgnoreCase("boolean")) {
            result = new BooleanFilter(Boolean.parseBoolean(filterRequestPriv.getValue()), filterRequestPriv.getField(), index);
        } else if (filterRequestPriv.getType().equalsIgnoreCase("date")) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                result = new DateFilter(sdf.parse(filterRequestPriv.getValue()), filterRequestPriv.getField(), getComparisonType(filterRequestPriv.getComparison()), index);
            } catch (ParseException e) {
            }
        }
        return result;
    }

    private static ComparisonType getComparisonType(String comparison) {
        if (comparison.equalsIgnoreCase("gt")) {
            return ComparisonType.AFTER;
        } else if (comparison.equalsIgnoreCase("lt")) {
            return ComparisonType.BEFORE;
        }
        return ComparisonType.EQUAL;
    }

    private static Class getEnumTypeClass(String enumType) {
        try {
            return Class.forName(enumType);
        } catch (ClassNotFoundException e) {
        }
        return null;
    }
}
