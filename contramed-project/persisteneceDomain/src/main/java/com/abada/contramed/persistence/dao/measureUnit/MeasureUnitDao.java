package com.abada.contramed.persistence.dao.measureUnit;

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

import com.abada.contramed.persistence.entity.MeasureUnit;
import java.util.List;

/**
 *
 * @author david
 *
 * Dao de la entidad MeasureUnit, datos de unidad de medida
 */
public interface MeasureUnitDao {

    /**
     *obtenemos la lista de todos los MeasureUnit {@link MeasureUnit}
     * @return list {@link MeasureUnit}
     */
    public List<MeasureUnit> findAll();

    /**
     *obtenemos la lista de MeasureUnit {@link MeasureUnit} a partir de idmeasureUnit
     * @param idmeasureUnit
     * @return list {@link MeasureUnit}
     */
    public List<MeasureUnit> findByIdmeasureUnit(Integer idmeasureUnit);

    /**
     *obtenemos la lista de MeasureUnit {@link MeasureUnit} a partir de su nombre
     * @param name
     * @return list {@link MeasureUnit}
     */
    public List<MeasureUnit> findByName(String name);
}

