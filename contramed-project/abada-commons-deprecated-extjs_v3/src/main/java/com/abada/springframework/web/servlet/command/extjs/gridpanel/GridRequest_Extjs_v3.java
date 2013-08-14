/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abada.springframework.web.servlet.command.extjs.gridpanel;

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
import java.util.Map;

/**
 * Clase usada para crear de manera dinámica la clausula WHERE de los JPQL.<br/>
 * Usada para representar la peticion de filtros de los grid panel de extjs
 * @author katsu
 */
public interface GridRequest_Extjs_v3 {
    /**
     * 
     * @return
     */
    int countFilters();
    /**
     * Añade un el filtro de un campo
     * @param filterRequest
     */
    void addFilterRequest(FilterRequest filterRequest);

    /**
     * Devuelve la clausula WHERE de la query JPQL
     * @param entityName Nombre de la entidad que se han de crear los filtros
     * @param withWhere si se añade o no la clausula WHERE, util cuando la sentencia
     * que se va a concatenar ya posee el WHERE
     * @param mapping La ruta de la variable si se usaron objectos {@link MappedDataGrid}
     * @return
     */
    String getQL(String entityName, boolean withWhere,MappedDataGrid ...mapping);

    /**
     * Devuelve los valores para sustituirlos en el JPQL
     * @return
     */
    Map<String,Object> getParamsValues();

    /**
     * Posicion de comienzo de los resultados de la busqueda, util para paginacion
     * @return
     */
    int getStart();

    /**
     * Maxima cantidad de resultado en la busqueda, util para paginacion
     * @return
     */
    int getLimit();
}
