/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.contramedadministrationweb.entities;

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

import com.abada.contramed.persistence.entity.PatientId;

/**
 * Clase con la información necesaria para buscar un paciente a partir de un identificador
 * y asignarle un tag
 * @author maria
 */
public class PatientTagInfo {

    private Long patientid;
    private String name;
    private String surname1;
    private String surname2;
    private String details;
    private String tag;
    private Long idtablez029;

    public Long getPatientid() {
        return patientid;
    }

    public void setPatientid(Long patientid) {
        this.patientid = patientid;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Long getIdtablez029() {
        return idtablez029;
    }

    public void setIdtablez029(Long idtablez029) {
        this.idtablez029 = idtablez029;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname1() {
        return surname1;
    }

    public void setSurname1(String surname1) {
        this.surname1 = surname1;
    }

    public String getSurname2() {
        return surname2;
    }

    public void setSurname2(String surname2) {
        this.surname2 = surname2;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
    /**
     * Devuelve la informacion necesaria para el grid {@link patientTag} <br/>
     * que muestra la informacion de un paciente y su tag.
     * @param patient
     * @return
     */

    public static PatientTagInfo getPatientInfo(PatientId patient) {
        PatientTagInfo result = new PatientTagInfo();
        result.setPatientid(patient.getPatientId().getId());
        result.setDetails(patient.getType().getDetails()+"  "+patient.getValue());
        result.setName(patient.getPatientId().getName());
        result.setSurname1(patient.getPatientId().getSurname1());
        result.setSurname2(patient.getPatientId().getSurname2());
        if (patient.getPatientId().getPatientTag() != null) {
            result.setTag(patient.getPatientId().getPatientTag().getTag());
        } else {
            result.setTag(" ");
        }
        return result;
    }
}
