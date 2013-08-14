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

/**
 * Tipos de role según la aplicación.<br/>
 * También usados para saber el tipo de personal
 * @author katsu
 */
public enum TypeRole {
    /**
     * Administrador del sistema. Tiene acceso a todo.
     */
    SYSTEM_ADMINISTRATOR,
    /**
     * Administrativo.<br/>
     * Tiene acceso a:<br/>
     * <ul>
     * <li>Gesti&oacute;n del personal</li>
     * <li>Gesti&oacute;n de tomas</li>
     * <li>Gesti&oacute;n de Tags de pacientes</li>
     * <li>Gesti&oacute;n de Tags de camas/recursos</li>
     * <ul>
     */
    ADMINISTRATIVE,
    /**
     * Enfermera/o de Enfermería.<br/>
     * Tiene acceso a:<br/>
     * <ul>
     * <li>Historial Personal de Administración de medicación</li>
     * <li>Incidencias Personal de Administración de medicación</li>
     * <li>Administración de mediación</li>
     * <ul>
     */
    NURSE_PLANT,
    /**
     * Supervisor de Enfermería.<br/>
     * Tiene acceso a:<br/>
     * <ul>
     * <li>Gesti&oacute;n de Dosis</li>
     * <li>Historial de Administración de medicación</li>
     * <li>Incidencias de Administración de medicación</li>
     * <li>Historial Personal de Administración de medicación</li>
     * <li>Incidencias Personal de Administración de medicación</li>
     * <li>Administración de mediación</li>
     * <ul>
     */
    NURSE_PLANT_SUPERVISOR,
    /**
     * Enfermera/o de farmacia.<br/>
     * Tiene acceso a:<br/>
     * <ul>
     * <li>Historial Personal de Preparacion de Cajetines</li>
     * <li>Incidencias Personales de Preparación de Cajetines</li>
     * <li>Gesti&oacute;n de Plantillas</li>
     * <li>Asignación de Plantillas a Especialidades</li>
     * <li>Gesti&oacute;n de Dosis</li>
     * <li>Preparación de Cajetin</li>
     * <ul>
     */
    NURSE_PHARMACY,
    /**
     * Farmaceutico.<br/>
     * Tiene acceso a:<br/>
     * <ul>
     * <li>Gesti&oacute;n de especialidades</li>
     * <li>Historial de Preparacion de Cajetines</li>
     * <li>Incidencias de Preparación de Cajetines</li>
     * <li>Historial Personal de Preparacion de Cajetines</li>
     * <li>Incidencias Personales de Preparación de Cajetines</li>
     * <li>Gesti&oacute;n de Plantillas</li>
     * <li>Asignación de Plantillas a Especialidades</li>
     * <li>Gesti&oacute;n de Dosis</li>
     * <li>Preparación de Cajetin</li>
     * <ul>
     */
    PHARMACIST;
}
