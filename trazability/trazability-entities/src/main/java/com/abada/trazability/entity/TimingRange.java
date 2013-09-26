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

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Representa los horarios de las tomas
 * @author david
 */
@Entity
@Table(name = "timing_range")
@NamedQueries({
    @NamedQuery(name = "TimingRange.findAll", query = "SELECT t FROM TimingRange t"),
    @NamedQuery(name = "TimingRange.findByIdtimingRange", query = "SELECT t FROM TimingRange t WHERE t.idtimingRange = :idtimingRange"),
    @NamedQuery(name = "TimingRange.findByStartTime", query = "SELECT t FROM TimingRange t WHERE t.startTime = :startTime"),
    @NamedQuery(name = "TimingRange.findByEndTime", query = "SELECT t FROM TimingRange t WHERE t.endTime = :endTime"),
    @NamedQuery(name = "TimingRange.findByName", query = "SELECT t FROM TimingRange t WHERE t.name = :name")})
public class TimingRange implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "idtiming_range", nullable = false)
    private Long idtimingRange;
    /**
     * Hora de comienzo
     */
    @Basic(optional = false)
    @Column(name = "start_time", nullable = false)
    @Temporal(TemporalType.TIME)
    private Date startTime;
    /**
     * Hora de fin
     */
    @Basic(optional = false)
    @Column(name = "end_time", nullable = false)
    @Temporal(TemporalType.TIME)
    private Date endTime;
    /**
     * Nombre de la toma
     */
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 45)
    private String name;

    public TimingRange() {
    }

    public TimingRange(Long idtimingRange) {
        this.idtimingRange = idtimingRange;
    }

    public TimingRange(Long idtimingRange, Date startTime, Date endTime, String name) {
        this.idtimingRange = idtimingRange;
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
    }

    public Long getIdtimingRange() {
        return idtimingRange;
    }

    public void setIdtimingRange(Long idtimingRange) {
        this.idtimingRange = idtimingRange;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtimingRange != null ? idtimingRange.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TimingRange)) {
            return false;
        }
        TimingRange other = (TimingRange) object;
        if ((this.idtimingRange == null && other.idtimingRange != null) || (this.idtimingRange != null && !this.idtimingRange.equals(other.idtimingRange))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.TimingRange[idtimingRange=" + idtimingRange + "]";
    }

}
