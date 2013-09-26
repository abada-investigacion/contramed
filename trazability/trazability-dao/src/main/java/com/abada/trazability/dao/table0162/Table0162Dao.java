package com.abada.trazability.dao.table0162;

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

import com.abada.trazability.entity.Table0162;
import java.util.List;

/**
 *
 * @author david
 *
 * Dao de la entidad table0162 , trabajamos con los datos de las distintas
 * vias de administración de medicación
 */
public interface Table0162Dao {

    /**
     *obtenemos la lista de todos los Table0162
     * @return List {@link Table0162}
     */
    public List<Table0162> findAll();

    /**
     *obtenemos la lista de Table0162 a partir de code
     * @param code
     * @return List {@link Table0162}
     */
    public List<Table0162> findByCode(String code);

    /**
     *obtenemos la lista de Table0162 a partir de details
     * @param details
     * @return List {@link Table0162}
     */
    public List<Table0162> findByDetails(String details);
}

