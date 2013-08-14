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

import com.abada.extjs.grid.mapping.FieldInformation;
import com.abada.extjs.grid.mapping.MappedDataGrid;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.factory.ComparisonType;
import java.util.Map;

/**
 * Clase usada para crear de manera dinámica la clausula WHERE de los JPQL.<br/>
 * Representa la informacion necesaria para cada campo por el que haya que filtrar.<br/>
 * Los filtros de los diferentes tipos deben extender de esta clase.
 * @author katsu
 */
public abstract class FilterRequest {
    /**
     * Operacion de comparacion
     */
    private ComparisonType operation;
    /**
     * Nombre del campo con puntos incluidos
     */
    private String fieldName;
    /**
     * Indice que tiene en el array de filtros y es usado
     * para impedir que varios campos tengan el mismo nombre en el SQL que se genera
     */
    private int index;

    /**
     * Constructor
     * @param operation Operacion de comparacion
     * @param fieldName Nombre del campo con puntos incluidos
     */
    protected FilterRequest(ComparisonType operation, String fieldName,int index) {
        this.operation = operation;
        this.fieldName = fieldName;
        this.index=index;
    }

    /**
     * Devuelve la ruta de la variable segun sea la entidad.<br/>
     * Ej: paciente.telefono.movil siendo paciente el nombre de la entidad en el JPQL
     * y telefono una entidad dentro del paciente que tiene un campo que es movil
     * @param entityName Nombre de la entidad en el JPQL
     * @param mapping La ruta de la variable si se usaron objectos {@link MappedDataGrid}
     * @return
     */
    protected String getMappedFieldName(String entityName, MappedDataGrid... mapping) {
        return entityName + "." + getMappedFieldName(mapping);
    }

    private String getMappedFieldName(MappedDataGrid... mapping) {
        if (mapping != null && mapping.length == 1) {
            FieldInformation fi = (FieldInformation) mapping[0].getTargetInformation().get(fieldName);
            if (fi != null) {
                return fi.getMapping();
            }
        }
        return this.fieldName;
    }

    /**
     * Devuelve la Operacion de comparacion
     * @return
     */
    protected ComparisonType getOperation() {
        return operation;
    }

    /**
     * Devuelve el nombre de la variable para la sustitucion en la JPQL.<br/>
     * Ej: si el nombre era telefono.movil devuelve telefono_movil para usarlo en el JPQL como
     * :telefono_movil y sustituirlo despues por su valor
     * @param index
     * @return
     */
    protected String generateParamName(int index) {
        return this.fieldName.replace(".", "_") +this.index+ index;
    }

    /**
     * Devuelve la parte del JPQL referente a este campo
     * @param entityName
     * @param mapping
     * @return
     */
    public abstract String getQL(String entityName, MappedDataGrid... mapping);

    /**
     * Devuelve el valor/es a sustituir en la JPQL
     * @return
     */
    public abstract Map<String, Object> getValue();
}
