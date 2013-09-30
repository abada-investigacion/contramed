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

import com.abada.trazability.entity.enums.TypeIncidence;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Datos de una incidencia a la hora de preparar una medicacion
 * @author david
 */
@Entity
@Table(name = "prepare_incidence")
@NamedQueries({
    @NamedQuery(name = "PrepareIncidence.findAll", query = "SELECT p FROM PrepareIncidence p"),
    @NamedQuery(name = "PrepareIncidence.findByIdprepareIncidence", query = "SELECT p FROM PrepareIncidence p WHERE p.idprepareIncidence = :idprepareIncidence"),
    @NamedQuery(name = "PrepareIncidence.findByIncidence", query = "SELECT p FROM PrepareIncidence p WHERE p.incidence = :incidence"),
    @NamedQuery(name = "PrepareIncidence.findByEventDate", query = "SELECT p FROM PrepareIncidence p WHERE p.eventDate = :eventDate")})
public class PrepareIncidence implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "idprepare_incidence", nullable = false)
    private Long idprepareIncidence;
    /**
     * Texto de la incidencia
     */
    @Basic(optional = false)
    @Column(name = "incidence", nullable = false, length = 1024)
    private String incidence;
    /**
     * Hora a la que se produjo la incidencia
     */
    @Basic(optional = false)
    @Column(name = "event_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventDate;
    /**
     * Datos de la toma para la que se esta preparando la medicacion. lo normal
     * es que sea null porque no se suele saber con los datos de los que dispone para cual se estaba preparando
     */
    @JsonIgnore
    @JoinColumn(name = "order_timing_idorder_timing", referencedColumnName = "idorder_timing",nullable=true)
    @ManyToOne//(optional = false)
    private OrderTiming orderTimingIdorderTiming;
    /**
     * Personal que produce la incidencia
     */
    @JoinColumn(name = "staff_idstaff", referencedColumnName = "idstaff", nullable = false)
    @ManyToOne(optional = false)
    private Staff staffIdstaff;
    /**
     * Cama en la que estaba el paciente que iba a recibir la medicacion
     */
    @JoinColumn(name = "recurso_idrecurso", referencedColumnName = "ID_RECURSO", nullable = false)
    @ManyToOne(optional = false)
    private Recurso bed;
    /**
     * Dosis que se estaba preparando
     */
    @JoinColumn(name = "dose_iddose", referencedColumnName = "iddose", nullable = true)
    @ManyToOne//(optional = false)
    private Dose dose;
    /**
     * Tipo de incidencia
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false,length = 30)
    private TypeIncidence typeIncidence;
    /**
     * Paciente que iba a recibir la medicacion
     */
    @JoinColumn(name = "patient_patientid", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Patient patient;
    
    public TypeIncidence getTypeIncidence() {
        return typeIncidence;
    }

    public Dose getDose() {
        return dose;
    }

    public void setDose(Dose dose) {
        this.dose = dose;
    }

    public void setTypeIncidence(TypeIncidence typeIncidence) {
        this.typeIncidence = typeIncidence;
    }
    
    public Recurso getBed() {
        return bed;
    }

    public void setBed(Recurso bed) {
        this.bed = bed;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public PrepareIncidence() {
    }

    public PrepareIncidence(Long idprepareIncidence) {
        this.idprepareIncidence = idprepareIncidence;
    }

    public PrepareIncidence(Long idprepareIncidence, String incidence, Date eventDate) {
        this.idprepareIncidence = idprepareIncidence;
        this.incidence = incidence;
        this.eventDate = eventDate;
    }

    public Long getIdprepareIncidence() {
        return idprepareIncidence;
    }

    public void setIdprepareIncidence(Long idprepareIncidence) {
        this.idprepareIncidence = idprepareIncidence;
    }

    public String getIncidence() {
        return incidence;
    }

    public void setIncidence(String incidence) {
        this.incidence = incidence;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public OrderTiming getOrderTimingIdorderTiming() {
        return orderTimingIdorderTiming;
    }

    public void setOrderTimingIdorderTiming(OrderTiming orderTimingIdorderTiming) {
        this.orderTimingIdorderTiming = orderTimingIdorderTiming;
    }

    public Staff getStaffIdstaff() {
        return staffIdstaff;
    }

    public void setStaffIdstaff(Staff staffIdstaff) {
        this.staffIdstaff = staffIdstaff;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idprepareIncidence != null ? idprepareIncidence.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrepareIncidence)) {
            return false;
        }
        PrepareIncidence other = (PrepareIncidence) object;
        if ((this.idprepareIncidence == null && other.idprepareIncidence != null) || (this.idprepareIncidence != null && !this.idprepareIncidence.equals(other.idprepareIncidence))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.PrepareIncidence[idprepareIncidence=" + idprepareIncidence + "]";
    }

}
