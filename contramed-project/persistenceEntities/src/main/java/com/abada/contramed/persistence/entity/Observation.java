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


import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author maria
 */
@Entity
@Table(name = "observation")
@NamedQueries({
    @NamedQuery(name = "Observation.findAll", query = "SELECT o FROM Observation o"),
    @NamedQuery(name = "Observation.findByStaffId", query = "SELECT o FROM Observation o WHERE o.staffId = :staffId"),
    @NamedQuery(name = "Observation.findByEventTime", query = "SELECT o FROM Observation o WHERE o.eventTime = :eventTime"),
    @NamedQuery(name = "Observation.findByObservation", query = "SELECT o FROM Observation o WHERE o.observation = :observation"),
    @NamedQuery(name = "Observation.findByPatientId", query = "SELECT o FROM Observation o WHERE o.patientId = :patientId")})


public class Observation implements Serializable {
   private static final long serialVersionUID = 1L;
   /**
    * Id
    */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id_observation", nullable = false)
    private Long idobservation;
     /**
     * Hora en la que se introduce la observacion
     */

    //@Basic(optional = false)
    @Column(name = "event_Time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventTime;
   
    /**
     * Observacion
     */
    @Column(name = "observation", length = 1024, nullable=false)
    private String observation;

    /**
     * Paciente sobre el que se genera la observacion
     */
    @JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Patient patientId;

    /**
     * Personal que incluye la observacion
     */
    @JoinColumn(name = "staff_id", referencedColumnName = "idstaff", nullable = false)
    @ManyToOne(optional = false)
    private Staff staffId;

    public Observation(){

    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public Long getIdobservation() {
        return idobservation;
    }

    public void setIdobservation(Long idobservation) {
        this.idobservation = idobservation;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Patient getPatientId() {
        return patientId;
    }

    public void setPatientId(Patient patientId) {
        this.patientId = patientId;
    }

    public Staff getStaffId() {
        return staffId;
    }

    public void setStaffId(Staff staffId) {
        this.staffId = staffId;
    }

  
   
    @Override
    public String toString() {
         return "com.abada.contramed.persistence.entity.Observation[idobservation=" + idobservation + "]";
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idobservation != null ? idobservation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Staff)) {
            return false;
        }
        Observation other = (Observation) object;
        if ((this.idobservation == null && other.idobservation != null) || (this.idobservation != null && !this.idobservation.equals(other.idobservation))) {
            return false;
        }
        return true;
    }

    

}
