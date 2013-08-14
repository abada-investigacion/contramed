/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.jasperCommons;

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

import com.abada.jasperCommons.units.MeasureUnit;
import net.sf.jasperreports.engine.design.JasperDesign;

/**
 *
 * @author aroldan
 */
public class Utils {

    /**
     * JasperReports inner working meassure are pixels.
     * It's uses a standard java 72 dots per inch resolution. That's is :
     * 1 inch = 72 pixels
     * 2,54 cm = 1 inch
     * Finally : x cm = (72/2,54)*x pixels = 28,346456693*x pixels
     * @param cmAmount
     * @return
     */
    public static int measureUnitToPixels(double measureUnitAmount,MeasureUnit unit){
        return (int) Math.floor(measureUnitAmount*unit.getValue());
    }

    /**
     * JasperReports inner working meassure are pixels.
     * It's uses a standard java 72 dots per inch resolution. That's is :
     * 1 inch = 72 pixels
     * 2,54 cm = 1 inch
     * Finally : x pixels = (2,54/72)*x cm = x/28,346456693 cms
     * @param cmAmount
     * @return
     */
    public static double pixelsToMeasureUnit(int pixelsAmount,MeasureUnit unit){
        return pixelsAmount/unit.getValue();
    }

    /**
     * Gets the Labels that conforms a blister
     * multi-row element
     * @param element
     * @return
     */
    public static int getLabelsPerBlister(JasperDesign design) {
        int labelsPerBlister = 10;
        try {
            String sLabelsPerBlister = design.getPropertiesMap().getProperty("abada.labelsPerBlister");
            if (sLabelsPerBlister != null) {
                labelsPerBlister = Math.max(1, Integer.parseInt(sLabelsPerBlister));
            }
        } catch (Exception ex) {
            labelsPerBlister = 10;
        }
        return labelsPerBlister;
    }

}
