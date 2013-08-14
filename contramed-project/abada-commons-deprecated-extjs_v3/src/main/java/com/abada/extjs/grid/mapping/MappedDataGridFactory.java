/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.extjs.grid.mapping;

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
import com.abada.web.exjs.ExtjsStore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Factoria para crear listas de {@link MappedDataGrid}
 * @author katsu
 */
public class MappedDataGridFactory<T> {

    /**
     * Genera lista de {@link MappedDataGrid} para usarse como respuesta de
     * {@link ExtjsStore} para ser enviados a los gridpanel de Extjs.
     * @param listData listado de datos
     * @param fieldsForMap lista con los nombre de los campos que queremos mapea.
     * @return
     */
    public List<MappedDataGrid<T>> create(List<T> listData, List<String> fieldsForMap) {
        List<MappedDataGrid<T>> result = new ArrayList<MappedDataGrid<T>>(listData.size());
        if (listData != null) {
            for (T t : listData) {
                result.add(new MappedDataGrid<T>(t, fieldsForMap));
            }
        }
        return result;
    }

    /**
     * Devuelve todos los posibles valores de nombres de los campos
     * @return
     */
    public Collection<String> getFieldsName(){
        MappedDataGrid<T> mappedDataGrid=new MappedDataGrid<T>(null, null);
        return mappedDataGrid.getFieldsName();
    }

    /**
     * Devuelve una instancia vacia solo sirve para usarla en los filtros
     * de {@link GridRequest_Extjs_v3}
     * @return
     */
    public MappedDataGrid<T> getEmptyClass(){
        return new MappedDataGrid<T>(null, null);
    }
}
