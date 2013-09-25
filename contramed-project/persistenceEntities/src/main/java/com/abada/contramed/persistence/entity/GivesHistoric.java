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

import com.abada.contramed.persistence.entity.enums.TypeGetPatient;
import com.abada.contramed.persistence.entity.enums.TypeGivesHistoric;
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
 * Datos de la Administracion de una medicacion
 * @author david
 */
@Entity
@Table(name = "gives_historic")
@NamedQueries({
    @NamedQuery(name = "GivesHistoric.findAll", query = "SELECT g FROM GivesHistoric g"),
    @NamedQuery(name = "GivesHistoric.findByIdgivesHistoric", query = "SELECT g FROM GivesHistoric g WHERE g.idgivesHistoric = :idgivesHistoric"),
    @NamedQuery(name = "GivesHistoric.findByEventDate", query = "SELECT g FROM GivesHistoric g WHERE g.eventDate = :eventDate"),
    @NamedQuery(name = "GivesHistoric.findByObservation", query = "SELECT g FROM GivesHistoric g WHERE g.observation = :observation"),
    @NamedQuery(name = "GivesHistoric.findByType", query = "SELECT g FROM GivesHistoric g WHERE g.type = :type")})
public class GivesHistoric implements Serializable, Historic {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "idgives_historic", nullable = false)
    private Long idgivesHistoric;
    /**
     * Hora a la que se produjo la administracion
     */
    @Basic(optional = false)
    @Column(name = "event_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventDate;
    /**
     * Fecha a la que debia administrar la medicacion, hora de la toma
     */
    @Column(name = "order_timing_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderTimingDate;
    /**
     * Observaciones recogidas por parte del personal
     */
    @Column(name = "observation", length = 1024)
    private String observation;
    /**
     * Tipo de administracion
     */
    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 10)
    private TypeGivesHistoric type;
    /**
     * Dosis administrada
     */
    @JoinColumn(name = "dose_iddose", referencedColumnName = "iddose")
    @ManyToOne()
    private Dose doseIddose;
    /**
     * Datos de la toma que se ha administrado
     */
    @JoinColumn(name = "order_timing_idorder_timing", referencedColumnName = "idorder_timing")
    @ManyToOne()
    private OrderTiming orderTimingIdorderTiming;
    /**
     * Personal que administro la medicacion
     */
    @JoinColumn(name = "staff_idstaff", referencedColumnName = "idstaff", nullable = false)
    @ManyToOne(optional = false)
    private Staff staffIdstaff;
    /**
     * Tipo de administracion
     */
    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Column(name = "read_patient_type", nullable = false, length = 15)
    private TypeGetPatient typeGetPatient;
    /**
     * Paciente al que administro la medicacion
     */
    @JoinColumn(name = "patient", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Patient patient;
    
    
    public GivesHistoric() {
    }

    public Date getOrderTimingDate() {
        return orderTimingDate;
    }

    public void setOrderTimingDate(Date orderTimingDate) {
        this.orderTimingDate = orderTimingDate;
    }

    public GivesHistoric(Long idgivesHistoric) {
        this.idgivesHistoric = idgivesHistoric;
    }

    public GivesHistoric(Long idgivesHistoric, Date eventDate, TypeGivesHistoric type) {
        this.idgivesHistoric = idgivesHistoric;
        this.eventDate = eventDate;
        this.type = type;
    }

    public Long getIdgivesHistoric() {
        return idgivesHistoric;
    }

    public void setIdgivesHistoric(Long idgivesHistoric) {
        this.idgivesHistoric = idgivesHistoric;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public TypeGivesHistoric getType() {
        return type;
    }

    public void setType(TypeGivesHistoric type) {
        this.type = type;
    }

    public Dose getDoseIddose() {
        return doseIddose;
    }

    public void setDoseIddose(Dose doseIddose) {
        this.doseIddose = doseIddose;
    }

    public OrderTiming getOrderTimingIdorderTiming() {
        return orderTimingIdorderTiming;
    }

    public void setOrderTimingIdorderTiming(OrderTiming orderTimingIdorderTiming) {
        this.orderTimingIdorderTiming = orderTimingIdorderTiming;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Staff getStaffIdstaff() {
        return staffIdstaff;
    }

    public void setStaffIdstaff(Staff staffIdstaff) {
        this.staffIdstaff = staffIdstaff;
    }

    public TypeGetPatient getTypeGetPatient() {
        return typeGetPatient;
    }

    public void setTypeGetPatient(TypeGetPatient typeGetPatient) {
        this.typeGetPatient = typeGetPatient;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idgivesHistoric != null ? idgivesHistoric.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GivesHistoric)) {
            return false;
        }
        GivesHistoric other = (GivesHistoric) object;
        if ((this.idgivesHistoric == null && other.idgivesHistoric != null) || (this.idgivesHistoric != null && !this.idgivesHistoric.equals(other.idgivesHistoric))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.GivesHistoric[idgivesHistoric=" + idgivesHistoric + "]";
    }
}
