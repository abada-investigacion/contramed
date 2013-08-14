/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abada.jasperCommons.units;

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

import java.util.ResourceBundle;

/**
 * Enum with posible measute units conversions for convenience (72ppp)
 * @author aroldan
 */
public enum MeasureUnit {

    CM (28.346456693d,"cm"),
    MM(2.8346456693d,"mm"),
    INCH(72.0d,"inch"),
    PIX(1,"pix");

    private final String key;
    private final double value;

    public String getkey(){
        return key;
    }

    public double getValue() {
        return value;
    }

    private MeasureUnit(double value, String key){
        this.value = value;
        this.key = ResourceBundle.getBundle("com/abada/jasperCommons/units/measureUnit").getString(key);
    }
    
    @Override
    public String toString(){
        return key;
    }
}
