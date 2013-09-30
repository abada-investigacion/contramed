/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abada.trazability.entity.enums;

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

/**
 * Tipos de incidencias que se pueden producir durante la administracion o preparacion de la
 * medicacion.
 * @author katsu
 */
public enum TypeIncidence {
    /**
     * No existe la identificacion de una dosis
     */
    NO_EXIST_DOSE,
    /**
     * Una dosis a caducado
     */
    EXPIRED_DOSE,
    /**
     * No existe un tratatamiento vinculado a una dosis
     */
    NO_THREATMENT,
    /**
     * Una dosis no se corresponde con el tratamiento que tiene el paciente
     */
    NO_CORRECT_THREATMENT,
    /**
     * No se puede quitar una dosis
     */
    IMPOSIBLE_REMOVE_DOSE,
    /**
     * Alegico
     */
    ALLERGY,
    /**
     * Indefinido
     */
    UNDEFINED,
    /**
     * Medicamento dado fuera de rango
     */
    RANGE_TIME_OUT,
    /**
     * Se produce cuando no se administra una medicación
     */
    MEDICATION_NOT_GIVEN
}
