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
 * Datos de una incidencia a la hora de administrar una medicacion
 *
 * @author david
 */
@Entity
@Table(name = "gives_incidence")
@NamedQueries({
    @NamedQuery(name = "GivesIncidence.findAll", query = "SELECT g FROM GivesIncidence g"),
    @NamedQuery(name = "GivesIncidence.findByIdgivesIncidence", query = "SELECT g FROM GivesIncidence g WHERE g.idgivesIncidence = :idgivesIncidence"),
    @NamedQuery(name = "GivesIncidence.findByIncidence", query = "SELECT g FROM GivesIncidence g WHERE g.incidence = :incidence"),
    @NamedQuery(name = "GivesIncidence.findByEventDate", query = "SELECT g FROM GivesIncidence g WHERE g.eventDate = :eventDate")})
public class GivesIncidence implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "idgives_incidence", nullable = false)
    private Long idgivesIncidence;
    /**
     * Incidencia
     */
    @Basic(optional = false)
    @Column(name = "incidence", nullable = false, length = 1024)
    private String incidence;
    /**
     * Hora de la incidencia
     */
    @Basic(optional = false)
    @Column(name = "event_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventDate;
    /**
     * Datos de la toma para la que se esta administrando la medicacion. lo
     * normal es que sea null porque no se suele saber con los datos de los que
     * dispone para cual se estaba preparando
     */
    @JsonIgnore
    @JoinColumn(name = "order_timing_idorder_timing", referencedColumnName = "idorder_timing", nullable = true)
    @ManyToOne//(optional = false)
    private OrderTiming orderTimingIdorderTiming;
    /**
     * Personal que produce la incidencia
     */
    @JoinColumn(name = "staff_idstaff", referencedColumnName = "idstaff", nullable = false)
    @ManyToOne(optional = false)
    private Staff staffIdstaff;
    /**
     * recurso/cama en la que estaba el paciente
     */
    @JoinColumn(name = "recurso_idrecurso", referencedColumnName = "ID_RECURSO", nullable = false)
    @ManyToOne(optional = false)
    private Recurso bed;
    /**
     * Dosis sobre la que se produco la incidencia
     */
    @JoinColumn(name = "dose_iddose", referencedColumnName = "iddose", nullable = true)
    @ManyToOne//(optional = false)
    private Dose dose;
    /**
     * Paciente sobre el que se produjo la incidencia
     */
    @JoinColumn(name = "patient_patientid", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Patient patient;
    /**
     * Tipo de incidencia
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 30)
    private TypeIncidence typeIncidence;

    public GivesIncidence() {
    }

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

    public GivesIncidence(Long idgivesIncidence) {
        this.idgivesIncidence = idgivesIncidence;
    }

    public GivesIncidence(Long idgivesIncidence, String incidence, Date eventDate) {
        this.idgivesIncidence = idgivesIncidence;
        this.incidence = incidence;
        this.eventDate = eventDate;
    }

    public Long getIdgivesIncidence() {
        return idgivesIncidence;
    }

    public void setIdgivesIncidence(Long idgivesIncidence) {
        this.idgivesIncidence = idgivesIncidence;
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
        hash += (idgivesIncidence != null ? idgivesIncidence.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GivesIncidence)) {
            return false;
        }
        GivesIncidence other = (GivesIncidence) object;
        if ((this.idgivesIncidence == null && other.idgivesIncidence != null) || (this.idgivesIncidence != null && !this.idgivesIncidence.equals(other.idgivesIncidence))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.GivesIncidence[idgivesIncidence=" + idgivesIncidence + "]";
    }

}
