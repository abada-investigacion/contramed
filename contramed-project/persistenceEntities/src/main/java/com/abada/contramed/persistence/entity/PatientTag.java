/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abada.contramed.persistence.entity;

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

import com.abada.gson.exclusionstrategy.JsonExclude;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Relaciona los tag Rfid con los pacientes
 * @author david
 */
@Entity
@Table(name = "patient_tag")
@NamedQueries({
    @NamedQuery(name = "PatientTag.findAll", query = "SELECT p FROM PatientTag p"),
    @NamedQuery(name = "PatientTag.findByPatientId", query = "SELECT p FROM PatientTag p WHERE p.patientId = :patientId"),
    @NamedQuery(name = "PatientTag.findByTag", query = "SELECT p FROM PatientTag p WHERE p.tag = :tag")})
public class PatientTag implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "patient_id", nullable = false)
    private Long patientId;
    /**
     * Identificador Hexadecimal del tag Rfid
     */
    @Basic(optional = false)
    @Column(name = "tag", nullable = false, length = 45)
    private String tag;
    /**
     * Paciente al que pertenece el tag
     */
    @JsonExclude
    @JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Patient patient;

    public PatientTag() {
    }

    public PatientTag(Long patientId) {
        this.patientId = patientId;
    }

    public PatientTag(Long patientId, String tag) {
        this.patientId = patientId;
        this.tag = tag;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (patientId != null ? patientId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PatientTag)) {
            return false;
        }
        PatientTag other = (PatientTag) object;
        if ((this.patientId == null && other.patientId != null) || (this.patientId != null && !this.patientId.equals(other.patientId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.PatientTag[patientId=" + patientId + "]";
    }

}
