/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abada.trazability.dao.measureunitconversion;

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

import com.abada.trazability.entity.MeasureUnit;
import com.abada.trazability.entity.MeasureUnitConversion;
import java.math.BigDecimal;

/**
 *
 * @author katsu
 */
public interface MeasureUnitConversionDao {
    /**
     * Return the multiplier conversion of 2 measure unit 
     * @param from
     * @param to
     * @return
     */
    public MeasureUnitConversion getMeasureUnitConversion(MeasureUnit from, MeasureUnit to);

    /**
     * Convert between MeasureUnits
     * @param valueFrom
     * @param from
     * @param valueTo
     * @param to
     * @return
     */
    public BigDecimal convert(BigDecimal valueFrom,MeasureUnit from, MeasureUnit to);
}
