/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.printDose;

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
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mmartin
 */
public class CustomPageSizeImpl implements CustomPageSize {

    public Map<String, String> getSizePages() {

        Field[] declaredFields = PageSize.class.getDeclaredFields();
        Map<String, String> map = new HashMap<String, String>();
        double width = 0;
        double height = 0;
        int decimalPlaces = 1;


        for (int i = 0; i < declaredFields.length - 1; i++) {
            if (Modifier.isFinal(PageSize.class.getDeclaredFields()[i].getModifiers())) {
                if (Modifier.isStatic(PageSize.class.getDeclaredFields()[i].getModifiers())) {
                    Rectangle r = PageSize.getRectangle(declaredFields[i].getName());
                    width = com.abada.jasperCommons.Utils.pixelsToMeasureUnit((int) r.getWidth(), MeasureUnit.CM);
                    height = com.abada.jasperCommons.Utils.pixelsToMeasureUnit((int) r.getHeight(), MeasureUnit.CM);

                    //Dejamos un decimal en width y height
                    BigDecimal bdWidth = new BigDecimal(width);
                    BigDecimal bdHeight = new BigDecimal(height);
                    bdWidth = bdWidth.setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP);
                    bdHeight = bdHeight.setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP);
                    width = bdWidth.doubleValue();
                    height = bdHeight.doubleValue();

                    map.put((int) r.getWidth()+ "x" + (int) r.getHeight(), declaredFields[i].getName() + " (" + width + "x" + height + ")");
                }
            }

        }

        Properties prop = new Properties();
        //Carga de pagesizes del archivo pagesize.properties
        try {
            prop.load(this.getClass().getResourceAsStream("/pagesize.properties"));
        } catch (IOException ex) {
            Logger.getLogger(CustomPageSizeImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        String[] aux;

        for (Enumeration e = prop.keys(); e.hasMoreElements();) {
            // Obtenemos el objeto
            Object obj = e.nextElement();
            aux = prop.getProperty(obj.toString()).split("x");
            int pWidth= com.abada.jasperCommons.Utils.measureUnitToPixels((Double.valueOf(aux[0]).doubleValue()), MeasureUnit.MM);
            int pHeight= com.abada.jasperCommons.Utils.measureUnitToPixels((Double.valueOf(aux[1]).doubleValue()), MeasureUnit.MM);

            map.put(pWidth+"x"+pHeight, obj + " (" + (Double.valueOf(aux[0]).doubleValue()) / 10 + "x" + (Double.valueOf(aux[1]).doubleValue()) / 10 + ")");

        }

        return map;

    }

    public Map sortByValue(Map map) {
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {

            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
            }
        });
        Map result = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
