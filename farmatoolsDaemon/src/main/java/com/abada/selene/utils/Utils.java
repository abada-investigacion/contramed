/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.selene.utils;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/**
 * Clase de utilidades con funciones para parsear valores de datos de los mensajes
 * HL7 a objetos java.<br/>
 * Es especifico para el formato de los datos de Selene
 * @author katsu
 */
public class Utils {

    /**
     * Parsea una fecha completa. En formato yyyyMMddHHmmss
     * @param Date Fecha
     * @return {@link String} en formato yyyyMMddHHmmss
     */
    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(date);
    }

    /**
     * Parsea una hora.
     * @param hora Hora en formato HHmm
     * @return {@link String}
     */
    public static String timeToTime(Date hora) {
        SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
        return sdf.format(hora);
    }

    /**
     * Parsea valores booleanos representados como Y o N.
     * @param boleano Entrada Y o N
     * @return {@link String} = "Y" si true o "N" en otro caso
     */
    public static String booleanToString(boolean boleano) {
        if (boleano) {
            return "Y";
        } else {
            return "N";
        }
    }

    /**
     * Parsea valores booleanos representados como S o N.
     * @param boleano Entrada S o N
     * @return {@link String} = "Y" si "S" o "N" en otro caso
     */
    public static String booleanToString(String boleano) {
        if (boleano.equalsIgnoreCase("S")) {
            return "Y";
        } else {
            return "N";
        }
    }

    public static String[] collectionToString(Collection c, String separator, boolean betweenColons) {
        String[] result = new String[(int) Math.ceil(c.size() / 1000.0)];
        if (c.size() > 0) {
            Object[] array = c.toArray();
            int start = 0;
            int end = 0;
            for (int i = 0; i < result.length; i++) {
                result[i] = "";
                start = i * 1000;
                end = Math.min(array.length, (i + 1) * 1000);
                for (int j = start; j < end - 1; j++) {
                    if (betweenColons) {
                        result[i] += "'";
                    }
                    result[i] += array[j].toString();
                    if (betweenColons) {
                        result[i] += "'";
                    }
                    result[i] += separator;
                }
                if (betweenColons) {
                    result[i] += "'";
                }
                result[i] += array[end - 1].toString();
                if (betweenColons) {
                    result[i] += "'";
                }
            }
        }
        return result;
    }

    public static String replaceWeirdCharacters(String s) {
        if (s != null) {
            s = s.replace("Ã¡", "á");
            s = s.replace("Ã©", "é");
            s = s.replace("Ã‰", "É");
            s = s.replace("Ã*", "í");
            s = s.replace("Ã¿", "Í");
            s = s.replace("Ã³", "ó");
            s = s.replace("Ã“", "Ó");
            s = s.replace("Ãº", "ú");
            s = s.replace("Ã¼", "ü");
            s = s.replace("Ã±", "ñ");
            s = s.replace("Ã‘", "Ñ");
        }
        return s;
    }
}
