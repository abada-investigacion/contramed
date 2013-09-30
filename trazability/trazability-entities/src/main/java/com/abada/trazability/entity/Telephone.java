/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abada.trazability.entity;

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

import com.abada.trazability.entity.enums.TypeTelephone;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Telefono del paciente
 * @author david
 */
@Entity
@Table(name = "telephone")
@NamedQueries({
    @NamedQuery(name = "Telephone.findAll", query = "SELECT t FROM Telephone t"),
    @NamedQuery(name = "Telephone.findByIdtelephone", query = "SELECT t FROM Telephone t WHERE t.idtelephone = :idtelephone"),
    @NamedQuery(name = "Telephone.findByPhone", query = "SELECT t FROM Telephone t WHERE t.phone = :phone"),
    @NamedQuery(name = "Telephone.findByType", query = "SELECT t FROM Telephone t WHERE t.type = :type")})
public class Telephone implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "idtelephone", nullable = false)
    private Long idtelephone;
    /**
     * Telefono
     */
    @Column(name = "phone", length = 14, nullable=false)
    private String phone;
    /**
     * Tipo de telefono
     */
    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 7)
    private TypeTelephone type;
    /**
     * Paciente al que pertenece el telefono
     */
    @JsonIgnore
    @JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Patient patientId;

    public Telephone() {
    }

    public Telephone(Long idtelephone) {
        this.idtelephone = idtelephone;
    }

    public Telephone(Long idtelephone, TypeTelephone type) {
        this.idtelephone = idtelephone;
        this.type = type;
    }

    public Long getIdtelephone() {
        return idtelephone;
    }

    public void setIdtelephone(Long idtelephone) {
        this.idtelephone = idtelephone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public TypeTelephone getType() {
        return type;
    }

    public void setType(TypeTelephone type) {
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
        hash += (idtelephone != null ? idtelephone.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Telephone)) {
            return false;
        }
        Telephone other = (Telephone) object;
        if ((this.idtelephone == null && other.idtelephone != null) || (this.idtelephone != null && !this.idtelephone.equals(other.idtelephone))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.Telephone[idtelephone=" + idtelephone + "]";
    }

}
