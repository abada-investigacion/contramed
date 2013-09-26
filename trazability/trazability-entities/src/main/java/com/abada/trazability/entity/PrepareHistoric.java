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

import com.abada.trazability.entity.enums.TypePrepareHistoric;
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
 * Datos de la preparacion de una medicacion
 * @author david
 */
@Entity
@Table(name = "prepare_historic")
@NamedQueries({
    @NamedQuery(name = "PrepareHistoric.findAll", query = "SELECT p FROM PrepareHistoric p"),
    @NamedQuery(name = "PrepareHistoric.findByIdprepareHistoric", query = "SELECT p FROM PrepareHistoric p WHERE p.idprepareHistoric = :idprepareHistoric"),
    @NamedQuery(name = "PrepareHistoric.findByEventDate", query = "SELECT p FROM PrepareHistoric p WHERE p.eventDate = :eventDate"),
    @NamedQuery(name = "PrepareHistoric.findByObservation", query = "SELECT p FROM PrepareHistoric p WHERE p.observation = :observation"),
    @NamedQuery(name = "PrepareHistoric.findByType", query = "SELECT p FROM PrepareHistoric p WHERE p.type = :type"),
    @NamedQuery(name = "PrepareHistoric.findByEquivalent", query = "SELECT p FROM PrepareHistoric p WHERE p.equivalent = :equivalent")})
public class PrepareHistoric implements Serializable,Historic {
       private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "idprepare_historic", nullable = false)
    private Long idprepareHistoric;
    /**
     * Hora a la que se produjo la preparacion
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
     * Tipo de accion en la preparacion
     */
    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 6)
    private TypePrepareHistoric type;
    /**
     * Si la medicacion preparada era equivalente a la preparada
     */
    @Basic(optional = false)
    @Column(name = "equivalent", nullable = false)
    private boolean equivalent;
    /**
     * Dosis preparada
     */
    @JoinColumn(name = "dose_iddose", referencedColumnName = "iddose")
    @ManyToOne()
    private Dose doseIddose;
    /**
     * Datos de la toma para la que se ha preparado
     */
    @JoinColumn(name = "order_timing_idorder_timing", referencedColumnName = "idorder_timing", nullable = false)
    @ManyToOne(optional = false)
    private OrderTiming orderTimingIdorderTiming;
    /**
     * Personal que ha preparado la dosis
     */
    @JoinColumn(name = "staff_idstaff", referencedColumnName = "idstaff", nullable = false)
    @ManyToOne(optional = false)
    private Staff staffIdstaff;

    public PrepareHistoric() {
    }

    public Date getOrderTimingDate() {
        return orderTimingDate;
    }

    public void setOrderTimingDate(Date orderTimingDate) {
        this.orderTimingDate = orderTimingDate;
    }

    public PrepareHistoric(Long idprepareHistoric) {
        this.idprepareHistoric = idprepareHistoric;
    }

    public PrepareHistoric(Long idprepareHistoric, Date eventDate, TypePrepareHistoric type, boolean equivalent) {
        this.idprepareHistoric = idprepareHistoric;
        this.eventDate = eventDate;
        this.type = type;
        this.equivalent = equivalent;
    }

    public Long getIdprepareHistoric() {
        return idprepareHistoric;
    }

    public void setIdprepareHistoric(Long idprepareHistoric) {
        this.idprepareHistoric = idprepareHistoric;
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
    public TypePrepareHistoric getType() {
        return type;
    }

    public void setType(TypePrepareHistoric type) {
        this.type = type;
    }
    

    public boolean getEquivalent() {
        return equivalent;
    }

    public void setEquivalent(boolean equivalent) {
        this.equivalent = equivalent;
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

    public Staff getStaffIdstaff() {
        return staffIdstaff;
    }

    public void setStaffIdstaff(Staff staffIdstaff) {
        this.staffIdstaff = staffIdstaff;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idprepareHistoric != null ? idprepareHistoric.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrepareHistoric)) {
            return false;
        }
        PrepareHistoric other = (PrepareHistoric) object;
        if ((this.idprepareHistoric == null && other.idprepareHistoric != null) || (this.idprepareHistoric != null && !this.idprepareHistoric.equals(other.idprepareHistoric))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.PrepareHistoric[idprepareHistoric=" + idprepareHistoric + "]";
    }

}
