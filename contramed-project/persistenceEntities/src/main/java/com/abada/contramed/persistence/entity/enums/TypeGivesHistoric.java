/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.contramed.persistence.entity.enums;

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

import java.util.Arrays;
import java.util.List;

/**
 * Tipos de eventos del historial de Administracion de farmacia
 * @author maria
 */
public enum TypeGivesHistoric {
    /**
     * Administracion normal
     */
    REGULAR,
    /**
     * Administracion de un medicamento sin estar previamente preescrita
     */
    OUT_ORDER,
    /**
     * Administracion de un medicamento que sin se el mismo que el preescrito es 
     * de similares caracteristicas
     */
    EQUIVALENT,
    /**
     * Administrado de manera forzada, con Aceptar todo.
     */
    FORCED,
    /**
     * Medicación no administrada
     */
    NOT_GIVEN,
    /**
     * Medicación removida
     */
    REMOVE;

    private static List<TypeGivesHistoric> given=Arrays.asList(TypeGivesHistoric.EQUIVALENT, TypeGivesHistoric.FORCED, TypeGivesHistoric.OUT_ORDER, TypeGivesHistoric.REGULAR);
    private static List<TypeGivesHistoric> notGivenList = Arrays.asList(TypeGivesHistoric.NOT_GIVEN);
    private static List<TypeGivesHistoric> removeList = Arrays.asList(TypeGivesHistoric.REMOVE);

    public static boolean isGiven(TypeGivesHistoric type){
        return given.contains(type);
    }

    public static boolean isNotGiven(TypeGivesHistoric type){
        return notGivenList.contains(type);
    }

    public static boolean isRemove(TypeGivesHistoric type){
        return removeList.contains(type);
    }
}
