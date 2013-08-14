/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.contramed.domain.parser;

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

import ca.uhn.hl7v2.model.Message;
import com.abada.contramed.persistence.entity.Order1;
import com.abada.contramed.persistence.entity.Patient;
import com.abada.contramed.persistence.entity.PatientId;
import java.util.List;

/**
 * Interfaz que debe implementar el parseador de un mensaje HL7 de HAPI a nuestras
 * entidades de Contramed.
 * @author katsu
 */
public interface Parser {

    /**
     * Parsea un Mensaje HAPI y devuelve la entidad Patient con la informacion contenida en
     * el mensaje
     * @param message Mensaje HL7 de HAPI
     * @return {@link Patient}
     */
    public Patient toPatient(Message message);

    /**
     * Parsea un Mensaje HAPI y devuelve un listado de Tratamientos({@link Order1}) con la informacion
     * contenida en el mensaje.<br/>
     * Usado en mensajes de tipo OMP y ORP
     * @param message Mensaje HL7 de HAPI pueden se del tipo OMP o ORP
     * @return Listado de Tratamientos
     */
    public List<Order1> toOrders(Message message);

    /**
     * Parsea el segemento MRG de un mensaje HL7 de HAPI, para sacar los posibles
     * identificadores del paciente contenidos en el.<br/>
     * Los identificadores se usan para poder buscar el paciente sobre el que se debe
     * ejecutar el mensaje.
     * @param message Mensaje HL7 de Hapi
     * @return Listado de identificadores de un paciente
     */
    public List<PatientId> toPatientIdsMRGSegment(Message message);

    /**
     * Parsea un Mensaje HAPI y devuelve una lista de entidades Patient con la informacion contenida en
     * el mensaje
     * @param message Mensaje HL7 de HAPI
     * @return {@link Patient}
     */
    public List<Patient> toPatients(Message message);
}
