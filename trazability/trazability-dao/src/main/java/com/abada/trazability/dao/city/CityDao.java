package com.abada.trazability.dao.city;

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

import com.abada.trazability.entity.City;
import java.util.List;

/**
 *
 * @author david
 */
public interface CityDao {

    /**
     *obtenemos la lista de todas las ciudades {@link City}
     * @return list <City>{@link City}
     */
    public List<City> findAll();

    /**
     *obtenemos la lista de ciudades {@link City} a partir de idcity
     * @param idcity
     * @return list <City>{@link City}
     */
    public List<City> findByIdcity(Integer idcity);
}

