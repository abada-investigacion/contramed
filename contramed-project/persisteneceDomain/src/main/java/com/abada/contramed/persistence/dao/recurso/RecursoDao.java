package com.abada.contramed.persistence.dao.recurso;

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

import com.abada.contramed.persistence.entity.Recurso;
import com.abada.web.exjs.ComboBoxResponse;
import com.abada.webcontramed.entities.BedDataView;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author david
 *
 * Dao de la entidad Recurso, trabajamos con los datos de la cama y Recurso
 */
public interface RecursoDao {

    /**
     * Devuelve la lista con el siguiente nivel de las rutas hijas distintas
     * que comienzan por rootPath o las camas si es la hoja
     * @param rootPath
     * @return
     */
    public List<ComboBoxResponse> findRecusoByPath(String rootPath);
    /**
     * Devuelve la lista con el siguiente nivel de las rutas hijas distintas
     * que comienzan por rootPath o las camas si es la hoja
     * @param rootPath
     * @return
     */
    public List<ComboBoxResponse> findRecusoByLocalizador(String rootPath);

    /**
     * Devuelve los datos de los recursos que se muestran en el mapa de camas
     * @param rootPath
     * @return
     */
    public List<BedDataView> findRecursoDataViewByLocalizador(String rootPath);
    /**
     *obtenemos la lista de todos los Recurso
     * @return List {@link Recurso}
     */
    public List<Recurso> findAll();

    /**
     *obtenemos la lista de Recurso a partir de idRecurso
     * @param idRecurso
     * @return List {@link Recurso}
     */
    public List<Recurso> findByIdRecurso(Long idRecurso);

    /**
     *obtenemos la lista de Recurso a partir de nr
     * @param nr cama del paciente
     * @return List {@link Recurso}
     */
    public List<Recurso> findByNr(String nr);
}

