/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.contramed.domain.parser.selene;

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

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Structure;
import ca.uhn.hl7v2.model.Group;
import com.abada.contramed.persistence.entity.PatientId;
import com.abada.contramed.persistence.entity.Tablez029;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Clase de utilidades con funciones para parsear valores de datos de los mensajes
 * HL7 a objetos java.<br/>
 * Es especifico para el formato de los datos de Selene
 * @author katsu
 */
public class Utils {

    private static final Log logger = LogFactory.getLog(Utils.class);

    /**
     * Parsea una fecha completa. En formato yyyyMMddHHmmss
     * @param date Fecha en formato yyyyMMddHHmmss
     * @return {@link Date}
     */
    public static Date toDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            return sdf.parse(date);
        } catch (ParseException ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    /**
     * Parsea una hora.
     * @param hora Hora en formato HHmm
     * @return {@link Date}
     */
    public static Date toTime(String hora) {
        SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
        try {
            return sdf.parse(hora);
        } catch (ParseException ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    /**
     * Parsea valores booleanos representados como Y o N.
     * @param boleano Entrada Y o N
     * @return true si boleano = "Y" o false en otro caso
     */
    public static Boolean toBoolean(String boleano) {
        if (boleano.equalsIgnoreCase("Y")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Parsea un String a entero si se puede. En caso de error
     * devuelve -1
     * @param entero
     * @return
     */
    public static int toInteger(String entero) {
        try {
            return Integer.parseInt(entero);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return -1;
        }
    }

    /**
     * Devuelve la estructura de Segmentos HL7 que forman un mensaje HL7
     * @param group Mensaje HL7 de HAPI
     * @return Estructura dividida en Segmentos HL7 de HAPI, en el mismo orden que del mensaje.
     * @throws HL7Exception
     */
    public static List<Structure> getAllMessageStructure(Group group) throws HL7Exception {
        List<Structure> result = new ArrayList<Structure>();
        String[] namesStructure = group.getNames();

        for (String s : namesStructure) {
            result.addAll(getAllMessageStructure(group, s));
        }

        return result;
    }

    /**
     * Devuelve la estructura de Segmentos HL7 de un Segmento particular de un mensaje HL7
     * @param group Mensaje HL7 de HAPI
     * @param nameSegment
     * @return Estructura dividida en Segmentos HL7 de HAPI, en el mismo orden que del mensaje.
     * @throws HL7Exception
     */
    public static List<Structure> getAllMessageStructure(Group group, String nameSegment) throws HL7Exception {
        List<Structure> result = new ArrayList<Structure>();

        Structure[] structures = group.getAll(nameSegment);

        for (Structure s : structures) {
            try {
                Group auxGroup = (Group) s;
                String[] auxNames = auxGroup.getNames();
                for (String s1 : auxNames) {
                    result.addAll(getAllMessageStructure(auxGroup, s1));
                }
            } catch (ClassCastException e) {
                result.add(s);
            }
        }

        return result;
    }

    /**
     * Usado para filtrar en la lista de Segmentos los que sean de un tipo
     * @param messageStructure
     * @param clazz tipo por el que filtrar
     * @return
     */
    public static Object get(List<Structure> messageStructure, Class clazz) {
        for (Structure s : messageStructure) {
            if (clazz.isInstance(s)) {
                return s;
            }
        }
        return null;
    }

    /**
     * parseo de patientId añadiendo la lista
     * @param patientIds
     * @param value
     * @param type
     */
    public static void addPatientId(List<PatientId> patientIds, String value, String type) {
        PatientId patientId = new PatientId();
        patientId.setValue(value);
        Tablez029 tablez029 = new Tablez029();
        tablez029.setCode(type);
        patientId.setType(tablez029);
        patientId.setPatientId(null);
        patientIds.add(patientId);

    }
}
