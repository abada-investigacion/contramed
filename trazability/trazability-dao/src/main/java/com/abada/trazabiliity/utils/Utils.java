/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.trazabiliity.utils;

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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author katsu
 */
public class Utils {

    private final static Log logger = LogFactory.getLog(Utils.class);

    /**
     * Se usa para determinar si es fin de semana
     * @param day
     * @return
     */
    public static boolean isWeekend(Date day) {
        if (day.getDay() == 6 || day.getDay() == 0) {
            return true;
        }
        return false;
    }

    /**
     * Devuelve los días de diferencia entre
     * @param startDate
     * @param endDate
     * @return
     */
    private static int getDiffInDays(Date startDate, Date endDate) {
        long fechaInicialMs = startDate.getTime();
        long fechaFinalMs = endDate.getTime();
        long diferencia = fechaFinalMs - fechaInicialMs;
        double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
        return ((int) dias);
    }

    /**
     * Devuelve los días de diferencia entre
     * @param startDate
     * @param endDate
     * @return
     */
    private static int getDiffInWeek(Date startDate, Date endDate) {
        long fechaInicialMs = startDate.getTime();
        long fechaFinalMs = endDate.getTime();
        long diferencia = fechaFinalMs - fechaInicialMs;
        double week = Math.floor(diferencia / (1000 * 60 * 60 * 24 * 7));
        return ((int) week);
    }

    /**
     * Devuelve rangos de fechas contenidos entre la fecha de comienzo y la de fin
     * @param startTime fecha comienzo
     * @param endTime fecha fin
     * @return
     */
    public static List<RangeItem> generateRange(Date startTime, Date endTime) {
        List<RangeItem> result = new ArrayList<RangeItem>();
        int diffDays = getDiffInDays(startTime, endTime);

        //FIXME Chapuza para soportar rangos inferiores a un día
        if (diffDays == 0 && startTime.getDate() != endTime.getDate() && startTime.before(endTime)) {
            diffDays++;
        }

        Date auxStart = new Date(startTime.getTime());
        Date auxEnd;
        for (int i = 0; i < diffDays; i++) {
            auxEnd = new Date(auxStart.getTime());
            auxEnd.setDate(auxEnd.getDate() + 1);
            auxEnd.setHours(0);
            auxEnd.setMinutes(0);
            auxEnd.setSeconds(0);
            result.add(new RangeItem(auxStart, auxEnd));
            logger.debug(auxStart + "-->" + auxEnd);
            auxStart = auxEnd;
        }
        //if (endTime.getHours() >0 || endTime.getMinutes()>0){
        result.add(new RangeItem(auxStart, endTime));
        logger.debug(auxStart + "---->" + endTime);
        //}

        return result;
    }

    /**
     * Filtra una lista de rangos de fechas, para que cumpla el patrón de repetición que puede ser:<br/>
     * Q+D siendo el + un numero indeterminado de días <br/>
     * H+S siendo el + un numero indeterminado de horas <br/>
     * QxJy siendo x el numero de semana e y una serie de numero del 1-7 que son los días de las semana<br/>
     * Si el patron de repeticion o la fecha de inicio es nula entonce se no se filtra nada
     * @param ranges Rangos de fechas
     * @param startTime hora que empieza el patron de repeticion
     * @param repetitionPatern patrón de repeticion
     * @return 
     */
    public static List<RangeItem> filterRange(List<RangeItem> ranges, Date startTime, String repetitionPatern) {
        if (repetitionPatern != null && !repetitionPatern.equals("") && startTime != null) {
            repetitionPatern = repetitionPatern.trim();
            if (repetitionPatern.startsWith("Q") && repetitionPatern.endsWith("D")) {
                repetitionPatern = repetitionPatern.substring(1, repetitionPatern.length() - 1);
                int days = Integer.parseInt(repetitionPatern);
                return filterByDayRange(ranges, startTime, days);
            } else if (repetitionPatern.startsWith("Q") && repetitionPatern.contains("J")) {
                return filterByWeekDay(ranges, startTime, repetitionPatern);
            } else if (repetitionPatern.startsWith("Q") && repetitionPatern.endsWith("W")) {
                repetitionPatern = repetitionPatern.substring(1, repetitionPatern.length() - 1);
                int days = Integer.parseInt(repetitionPatern) * 7;
                return filterByDayRange(ranges, startTime, days);
            }
        }
        return ranges;
    }

    /**
     * Descarta rango que no se correspondan con los días de la semana correctos
     * @param ranges
     * @param repetitionPatern
     * @return
     */
    private static List<RangeItem> filterByWeekDay(List<RangeItem> ranges, Date startTime, String repetitionPatern) {
        List<RangeItem> result = new ArrayList<RangeItem>();

        int nWeek, nDay, auxDay, dweek;
        Calendar aux = Calendar.getInstance();

        //primero saco el número de la semana
        if (repetitionPatern.indexOf('J') > 1) {
            nWeek = Integer.parseInt(repetitionPatern.substring(1, repetitionPatern.indexOf('J')));
        } else {
            nWeek = -1;
        }
        repetitionPatern = repetitionPatern.substring(repetitionPatern.indexOf('J') + 1);
        for (int i = 0; i < repetitionPatern.length(); i++) {
            nDay = Integer.parseInt(repetitionPatern.charAt(i) + "");

            for (RangeItem r : ranges) {
                aux.setTime(r.getStartTime());
                //Compruebo que conincide con el día de la semana correcto
                auxDay = aux.get(Calendar.DAY_OF_WEEK);
                if ((auxDay > 1 && auxDay == nDay + 1) || (auxDay == 1 && nDay == 7)) {//Chapuza para cuadrar los días de la semana
                    if (nWeek == -1) {
                        result.add(r);
                    } else {
                        dweek = getDiffInWeek(startTime, aux.getTime());
                        if (dweek >= 0) {
                            if (dweek % nWeek == 0) {
                                result.add(r);
                            }
                        }
                        /*if (aux.get(Calendar.WEEK_OF_MONTH) == nWeek) {
                        result.add(r);
                        }*/
                    }
                }
            }
        }
        return result;
    }

    /**
     * Descarta rangos cada x days
     * @param ranges
     * @param startTime
     * @param days
     * @return
     */
    private static List<RangeItem> filterByDayRange(List<RangeItem> ranges, Date startTime, int days) {
        List<RangeItem> result = new ArrayList<RangeItem>();
        startTime.setHours(0);
        startTime.setMinutes(0);
        startTime.setSeconds(0);
        Date aux;
        for (RangeItem r : ranges) {
            aux = new Date(r.getStartTime().getTime());
            aux.setHours(0);
            aux.setMinutes(0);
            aux.setSeconds(0);
            int ddays = getDiffInDays(startTime, aux);
            if (ddays >= 0) {
                if (ddays % days == 0) {
                    result.add(r);
                }
            }
        }
        return result;
    }

    /**
     * Une las cadenas de un array separados por retornos de carro
     * @param array
     * @return
     */
    public static String append(String... array) {
        if (array != null) {
            StringBuilder result = new StringBuilder();
            for (String s : array) {
                result.append(s);
                result.append(System.getProperty("line.separator"));
            }
            return result.toString();
        }
        return null;
    }

    /**
     * Devuelve si todos los giveshistoric son del mismo tipo (given or not given) y el tipo que son:<br/>
     * -1 --> no son del mismo tipo<br/>
     * 0--> historics es null o vacia<br/>
     * 1 --> given<br/>
     * 2 -- not given<br/>
     * @param historics
     * @return
     */
    /*public static short isSameTypeGivesHistoric(List<GivesHistoric> historics) {
        if (historics != null) {
            Boolean given = null;
            for (GivesHistoric h : historics) {
                if (given == null) {
                    if (TypeGivesHistoric.isNotGiven(h.getType())) {
                        given = false;
                    } else {
                        given = true;
                    }
                }

                if (given && TypeGivesHistoric.isNotGiven(h.getType())) {
                    return -1;
                }
                if (!given && TypeGivesHistoric.isGiven(h.getType())) {
                    return -1;
                }
            }
            if (given != null) {
                if (given) {
                    return 1;
                } else {
                    return 2;
                }
            }
        }
        return 0;
    }*/
}
