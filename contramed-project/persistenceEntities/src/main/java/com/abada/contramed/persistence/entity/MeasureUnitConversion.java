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
import java.math.BigDecimal;
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
 *
 * @author katsu
 */
@Entity
@Table(name = "MEASURE_UNIT_CONVERSION")
@NamedQueries({
    @NamedQuery(name = "MeasureUnitConversion.findAll", query = "SELECT m FROM MeasureUnitConversion m"),
    @NamedQuery(name = "MeasureUnitConversion.findById", query = "SELECT m FROM MeasureUnitConversion m WHERE m.id = :id"),
    @NamedQuery(name = "MeasureUnitConversion.findByMultiplier", query = "SELECT m FROM MeasureUnitConversion m WHERE m.multiplier = :multiplier")})
public class MeasureUnitConversion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;
    @Basic(optional = false)
    @Column(name = "MULTIPLIER", nullable = false, precision = 19, scale = 9)
    private BigDecimal multiplier;
    @JoinColumn(name = "MU_FROM", referencedColumnName = "IDMEASURE_UNIT", nullable = false)
    @ManyToOne(optional = false)
    private MeasureUnit mu_from;
    @JoinColumn(name = "MU_TO", referencedColumnName = "IDMEASURE_UNIT", nullable = false)
    @ManyToOne(optional = false)
    private MeasureUnit mu_to;

    public MeasureUnitConversion() {
    }

    public MeasureUnitConversion(Long id) {
        this.id = id;
    }

    public MeasureUnitConversion(Long id, BigDecimal multiplier) {
        this.id = id;
        this.multiplier = multiplier;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(BigDecimal multiplier) {
        this.multiplier = multiplier;
    }

    public MeasureUnit getMu_from() {
        return mu_from;
    }

    public void setMu_from(MeasureUnit mu_from) {
        this.mu_from = mu_from;
    }

    public MeasureUnit getMu_to() {
        return mu_to;
    }

    public void setMu_to(MeasureUnit mu_to) {
        this.mu_to = mu_to;
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
        if (!(object instanceof MeasureUnitConversion)) {
            return false;
        }
        MeasureUnitConversion other = (MeasureUnitConversion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.MeasureUnitConversion[id=" + id + "]";
    }

}
