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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Datos de la identificacion de un paciente
 * @author david
 */
@Entity
@Table(name = "patient_id")
@NamedQueries({
    @NamedQuery(name = "PatientId.findAll", query = "SELECT p FROM PatientId p"),
    @NamedQuery(name = "PatientId.findById", query = "SELECT p FROM PatientId p WHERE p.id = :id"),
    @NamedQuery(name = "PatientId.findByValue", query = "SELECT p FROM PatientId p WHERE p.value = :value")})
public class PatientId implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;
    /**
     * Valor de la identificacion
     */
    @Column(name = "value", length = 45, nullable=false)
    private String value;
    /**
     * tipo de identificacion
     */
    @JoinColumn(name = "type", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Tablez029 type;
    /**
     * Paciente al que identifica
     */
    @JsonExclude
    @JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Patient patientId;

    public PatientId() {
    }

    public PatientId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Tablez029 getType() {
        return type;
    }

    public void setType(Tablez029 type) {
        this.type = type;
    }

    public Patient getPatientId() {
        return patientId;
    }

    public void setPatientId(Patient patientId) {
        this.patientId = patientId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PatientId)) {
            return false;
        }
        PatientId other = (PatientId) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.PatientId[id=" + id + "]";
    }

}
