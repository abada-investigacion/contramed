package com.abada.trazability.dao.recursoTag;

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

import com.abada.trazability.entity.RecursoTag;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest;
import java.util.List;

/**
 *
 * @author david
 *
 * Dao de la entidad RecursoTag, trabajamos con los datos de la cama y el tag
 */
public interface RecursoTagDao {

    /**
     * obtenemos count de los datos filtrados
     * @param filters
     * @return Long
     */
    public Long loadSizeAll(GridRequest filters);

    /**
     * obtenemos la lista de RecursoTag por el filtro
     * @param filters
     * @return List {@link RecursoTag}
     */
    public List<RecursoTag> loadAll(GridRequest filters);

    /**
     *obtenemos la lista de todos los RecursoTag
     * @return List {@link RecursoTag}
     */
    public List<RecursoTag> findAll();

    /**
     *Añadimos RecursoTag
     * @param tag
     * @param idrecurso
     * @return boolean
     */
    public boolean save(String tag, Long idrecurso);

    /**
     *modificamos RecursoTag a partir del recurso traido de nuestra base de datos
     * @param recursoTag
     * @param idrecursoOrigen
     * @param tagorigen
     */
    public void update(RecursoTag recursoTag, Long idrecursoOrigen, String tagorigen);

    /**
     *borrado de un RecursoTag
     */
    public void delete(Long id);

    /**
     *obtenemos la lista de RecursoTag a partir de recursoIDRECURSO y el tag
     * @param recursoIDRECURSO
     * @param tag
     * @return List {@link RecursoTag}
     */
    public List<RecursoTag> findByRecursotag(Long recursoIDRECURSO, String tag);

    /**
     * contamos los recursos que tiene asociados un tag
     * @param recursoIDRECURSO
     * @return Long
     */
    public Long findByRecursoCount(Long recursoIDRECURSO);

    /**
     * dado el tag obtenemos su Recurso tag; y si no existe devulve null
     * @param tag
     * @return {@link RecursoTag}
     */
    public RecursoTag findtagunique(String tag);
}

