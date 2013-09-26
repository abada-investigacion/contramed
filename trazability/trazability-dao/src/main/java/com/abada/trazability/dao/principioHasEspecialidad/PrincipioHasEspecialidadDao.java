/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.trazability.dao.principioHasEspecialidad;

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

import com.abada.trazability.entity.PrincipioHasEspecialidad;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest;
import java.util.List;

/**
 *
 * @author mmartin
 */
public interface PrincipioHasEspecialidadDao {

    /**
     * Obtiene el tamaño de {@link PrincipioHasEspecialidad}
     * @param filters
     * @return
     */
    public Long loadSizeAll(GridRequest filters);

    /**
     * Obtiene la lista de {@link PrincipioHasEspecialidad} que cumplen el filtro <br/>
     * @param filters
     * @return List<{@link PrincipioHasEspecialidad}>
     */
    public List<PrincipioHasEspecialidad> loadAll(GridRequest filters);

    /**
     * Inserta en {@link PrincipioHasEspecialidad} a partir de los datos recibidos <br/>
     * @param codigoespec
     * @param codigoprincipio
     * @param id
     * @param composicion
     * @param id_unidad
     * @throws Exception
     */
    public void insertPrincipioHasEspecialidad(String codigoespec, String codigoprincipio, Long id, String composicion, String id_unidad) throws Exception;

    /**
     * Obtiene la lista de {@link PrincipioHasEspecialidad} a partir del codigo de especialidad <br/>
     * @param codigo
     * @return List<{@link PrincipioHasEspecialidad}>
     */
    public List<PrincipioHasEspecialidad> getPrincipioHasEspecialidadByEspecialidad(String codigo);

    /**
     * Obtiene la lista de {@link PrincipioHasEspecialidad} a partir del identificador <br/>
     * @param id
     * @return List<{@link PrincipioHasEspecialidad}>
     */
    public List<PrincipioHasEspecialidad> getPrincipioHasEspecialidadById(Long id);

    /**
     * Modifica {@link PrincipioHasEspecialidad} a partir de los datos recibidos <br/>
     * @param oldid
     * @param oldcodigoespec
     * @param codigoespec
     * @param codigoprincipio
     * @param id
     * @param composicion
     * @param id_unidad
     * @throws Exception
     */
    public void updatePrincipioHasEspecialidad(Long oldid, String oldcodigoespec, String codigoespec, String codigoprincipio, Long id, String composicion, String id_unidad) throws Exception;

    /**
     * Borra {@link PrincipioHasEspecialidad} a partir del identificador recibido <br/>
     * @param id
     */
    public void delete(Long id);
}
