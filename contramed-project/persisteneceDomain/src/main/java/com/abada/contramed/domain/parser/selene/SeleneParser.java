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
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.Structure;
import ca.uhn.hl7v2.model.v25.segment.MRG;
import ca.uhn.hl7v2.model.v25.segment.ORC;
import ca.uhn.hl7v2.model.v25.segment.PID;
import ca.uhn.hl7v2.model.v25.segment.PV1;
import ca.uhn.hl7v2.model.v25.segment.RXO;
import ca.uhn.hl7v2.model.v25.segment.RXR;
import ca.uhn.hl7v2.model.v25.segment.TQ1;
import com.abada.contramed.domain.parser.Parser;
import com.abada.contramed.persistence.entity.Order1;
import com.abada.contramed.persistence.entity.Patient;
import com.abada.contramed.persistence.entity.PatientId;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Implementacion del parseador especifico para Selene
 * @author katsu
 */
public class SeleneParser implements Parser {

    private static final Log logger = LogFactory.getLog(SeleneParser.class);

    /**
     * Parsea un Mensaje HAPI y devuelve la entidad Patient con la informacion contenida en
     * el mensaje
     * @param message Mensaje HL7 de HAPI
     * @return {@link Patient}
     */
    public Patient toPatient(Message message) {
        //comprobar lo que llega
        Patient result = new Patient();

        try {
            List<Structure> messageStructure = Utils.getAllMessageStructure(message);

            //PID
            PID pid = (PID) Utils.get(messageStructure, PID.class);
            if (pid != null) {
                SelenePIDParser spidp = new SelenePIDParser();
                spidp.parsePID(pid, result);
            }
            //PV1
            PV1 pv1 = (PV1) Utils.get(messageStructure, PV1.class);
            if (pv1 != null) {
                SelenePV1Parser spvp = new SelenePV1Parser();
                spvp.parsePV1(pv1, result);
            }

        } catch (HL7Exception e) {
            logger.error(e);
        }
        return result;
    }

    /**
     * Parsea un Mensaje HAPI y devuelve un listado de Tratamientos({@link Order1}) con la informacion
     * contenida en el mensaje.<br/>
     * Usado en mensajes de tipo OMP y ORP
     * @param message Mensaje HL7 de HAPI pueden se del tipo OMP o ORP
     * @return Listado de Tratamientos
     */
    public List<Order1> toOrders(Message message) {
        //comprobar lo que llega
        List<Order1> result = new ArrayList<Order1>();

        try {
            List<Structure> messageStructure = Utils.getAllMessageStructure(message);

            SeleneORCParser sorcp = new SeleneORCParser();
            SeleneTQ1Parser stqp = new SeleneTQ1Parser();
            SeleneRXOParser srxop = new SeleneRXOParser();
            SeleneRXRParser srxrp = new SeleneRXRParser();

            Order1 order1 = null;
            for (Structure structure : messageStructure) {
                if (ORC.class.isInstance(structure)) {
                    if (order1 != null) {
                        result.add(order1);
                    }
                    order1 = new Order1();
                    ORC orc = (ORC) structure;
                    if (orc != null) {
                        sorcp.parseORC(orc, order1);
                    }
                } else if (TQ1.class.isInstance(structure)) {
                    TQ1 tq1 = (TQ1) structure;
                    if (tq1 != null) {
                        stqp.parseTQ1(tq1, order1);
                    }
                } else if (RXO.class.isInstance(structure)) {
                    RXO rxo = (RXO) structure;
                    if (rxo != null) {
                        srxop.parseRXO(rxo, order1);
                    }
                } else if (RXR.class.isInstance(structure)) {
                    RXR rxr = (RXR) structure;
                    if (rxr != null) {
                        srxrp.parseRXR(rxr, order1);
                    }
                }
            }

            if (order1 != null) {
                result.add(order1);
            }

        } catch (HL7Exception e) {
            logger.error(e);
        }

        return result;
    }

    /**
     * Parsea el segemento MRG de un mensaje HL7 de HAPI, para sacar los posibles
     * identificadores del paciente contenidos en el.<br/>
     * Los identificadores se usan para poder buscar el paciente sobre el que se debe
     * ejecutar el mensaje.
     * @param message Mensaje HL7 de Hapi
     * @return Listado de identificadores de un paciente
     */
    public List<PatientId> toPatientIdsMRGSegment(Message message) {
        try {
            List<Structure> messageStructure = Utils.getAllMessageStructure(message);

            MRG meg = (MRG) Utils.get(messageStructure, MRG.class);

            SeleneMRGParser parser = new SeleneMRGParser();

            List<PatientId> result = new ArrayList<PatientId>();
            parser.parseMRG(meg, result);
            return result;

        } catch (HL7Exception e) {
            logger.error(e);
        }
        return null;
    }

    /**
     * Parsea un Mensaje HAPI y devuelve una lista de entidades Patient con la informacion contenida en
     * el mensaje
     * @param message Mensaje HL7 de HAPI
     * @return {@link Patient}
     */
    public List<Patient> toPatients(Message message) {
        List<Patient> result = new ArrayList<Patient>();
        try {
            List<Structure> messageStructure = Utils.getAllMessageStructure(message);
            Patient patient = null;
            SelenePIDParser spidp = new SelenePIDParser();
            SelenePV1Parser spvp = new SelenePV1Parser();
            for (Structure structure : messageStructure) {
                //PID
                if (PID.class.isInstance(structure)) {
                    if (patient != null) {
                        result.add(patient);
                    }
                    patient = new Patient();
                    PID pid = (PID) structure;
                    if (pid != null) {
                        spidp.parsePID(pid, patient);
                    }
                } else if (PV1.class.isInstance(structure)) {
                    PV1 pv1 = (PV1) structure;
                    if (pv1 != null) {
                        spvp.parsePV1(pv1, patient);
                    }
                }
            }
            if (patient != null) {
                result.add(patient);
            }
        } catch (HL7Exception e) {
            logger.error(e);
        }
        return result;
    }
}
