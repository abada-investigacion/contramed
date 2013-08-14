/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.springframework.web.servlet.command.extjs.gridpanel.impl;

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

import com.abada.extjs.grid.mapping.FieldInformation;
import com.abada.extjs.grid.mapping.MappedDataGrid;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.FilterRequest;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest_Extjs_v3;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase usada para crear de manera dinámica la clausula WHERE de los JPQL.<br/>
 * Usada para representar la peticion de filtros de los grid panel de extjs
 * @author katsu
 */
public class GridRequest_Extjs_v3Impl implements GridRequest_Extjs_v3 {
    /**
     * posicion desde la que se muestran los resultados de la busqueda
     */
    private Integer start;
    /**
     * maxima logitud de los resultados
     */
    private Integer limit;
    /**
     * Direccion de la ordenacion de los resultados
     */
    private String dir;
    /**
     * campo por el que se ordena
     */
    private String sort;
    /**
     * Lista de filtros, uno por cada campo a filtrar
     */
    private List<FilterRequest> filters;

    /**
     * Añade un filtro
     * @param filterRequest
     */
    public void addFilterRequest(FilterRequest filterRequest) {
        if (filters == null) {
            filters = new ArrayList<FilterRequest>();
        }
        if (filterRequest != null) {
            filters.add(filterRequest);
        }
    }

    /**
     * Devuelve la clausula WHERE de la query JPQL
     * @param entityName Nombre de la entidad que se han de crear los filtros
     * @param withWhere si se añade o no la clausula WHERE, util cuando la sentencia
     * que se va a concatenar ya posee el WHERE
     * @param mapping La ruta de la variable si se usaron objectos {@link MappedDataGrid}
     * @return
     */
    public String getQL(String entityName, boolean withWhere,MappedDataGrid ...mapping) {
        String result=" ";
        if (filters != null) {
            if (withWhere && filters.size() > 0) {
                result+= "WHERE ";
            } else if (!withWhere && filters.size() > 0) {
                result+= "AND ";
            }
            
            for (FilterRequest aux : filters) {
                result += aux.getQL(entityName) + " AND ";
            }
            if (filters.size() > 0) {
                result = result.substring(0, result.length() - 5);
            }
        }
        if (sort != null && dir != null) {
            result += " ORDER BY " + this.getMappedFieldName(entityName, sort,mapping) + " " + dir;
        }

        return result;
    }

    private String getMappedFieldName(String entityName,String sort, MappedDataGrid... mapping) {
        return entityName + "." + getMappedFieldName(sort,mapping);
    }

    private String getMappedFieldName(String sort,MappedDataGrid... mapping) {
        if (mapping != null && mapping.length == 1) {
            FieldInformation fi = (FieldInformation) mapping[0].getTargetInformation().get(sort);
            if (fi != null) {
                return fi.getMapping();
            }
        }
        return this.sort.replace('_', '.');
    }

    /**
     * Devuelve los valores para sustituirlos en el JPQL
     * @return
     */
    public Map<String, Object> getParamsValues() {
        return this.getNamedParametersObjects(null);
    }

    private Map<String, Object> getNamedParametersObjects(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (map != null) {
            result.putAll(map);
        }
        if (filters != null) {
            for (FilterRequest f : filters) {
                result.putAll(f.getValue());
            }
        }
        return result;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    /**
     * Posicion de comienzo de los resultados de la busqueda, util para paginacion
     * @return
     */
    public int getStart() {
        return start;
    }

    /**
     * Maxima cantidad de resultado en la busqueda, util para paginacion
     * @return
     */
    public void setStart(Integer start) {
        this.start = start;
    }

    public int countFilters() {
        if (this.filters!=null)
            return this.filters.size();
        return 0;
    }
}
