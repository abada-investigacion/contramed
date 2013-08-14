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
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Unidad de medida
 * @author david
 */
@Entity
@Table(name = "measure_unit")
@NamedQueries({
    @NamedQuery(name = "MeasureUnit.findAll", query = "SELECT m FROM MeasureUnit m"),
    @NamedQuery(name = "MeasureUnit.findByIdmeasureUnit", query = "SELECT m FROM MeasureUnit m WHERE m.idmeasureUnit = :idmeasureUnit"),
    @NamedQuery(name = "MeasureUnit.findByFarmacology", query = "SELECT m FROM MeasureUnit m WHERE m.farmacology = :farmacology"),
    @NamedQuery(name = "MeasureUnit.findByDescription", query = "SELECT m FROM MeasureUnit m WHERE m.description = :description"),
    @NamedQuery(name = "MeasureUnit.findByName", query = "SELECT m FROM MeasureUnit m WHERE m.name = :name")})
public class MeasureUnit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "idmeasure_unit", nullable = false)
    private Integer idmeasureUnit;
    @Column(name = "farmacology", length = 1)
    private String farmacology;
    /**
     * Nombre descriptivo
     */
    @Column(name = "description", length = 200)
    private String description;
    /**
     * Codigo de la unidad mg
     */
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 20)
    private String name;
    /**
     * Dosis que usan esta unidad de medida
     */
    @JsonExclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "measureUnitIdmeasureUnit", fetch = FetchType.LAZY)
    private List<Dose> doseList;
    /**
     * tomas que usan esta unidad de medida
     */
    @JsonExclude
    @OneToMany(mappedBy = "measureUnitIdmeasureUnit", fetch = FetchType.LAZY)
    private List<OrderTiming> orderTimingList;
    /**
     * OrdermMedication que usan esta unidad de medida
     */
    @JsonExclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "measureUnitIdmeasureUnit", fetch = FetchType.LAZY)
    private List<OrderMedication> orderMedicationList;
    /**
     * Conversion a distintas unidades
     */
    @JsonExclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mu_to", fetch = FetchType.LAZY)
    private List<MeasureUnitConversion> conversionsTo;
    /**
     * Conversion de distintas unidades
     */
    @JsonExclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mu_from", fetch = FetchType.LAZY)
    private List<MeasureUnitConversion> conversionsFrom;

    public MeasureUnit() {
    }

    public MeasureUnit(Integer idmeasureUnit) {
        this.idmeasureUnit = idmeasureUnit;
    }

    public MeasureUnit(Integer idmeasureUnit, String name) {
        this.idmeasureUnit = idmeasureUnit;
        this.name = name;
    }

    public Integer getIdmeasureUnit() {
        return idmeasureUnit;
    }

    public void setIdmeasureUnit(Integer idmeasureUnit) {
        this.idmeasureUnit = idmeasureUnit;
    }

    public String getFarmacology() {
        return farmacology;
    }

    public void setFarmacology(String farmacology) {
        this.farmacology = farmacology;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Dose> getDoseList() {
        return doseList;
    }

    public void setDoseList(List<Dose> doseList) {
        this.doseList = doseList;
    }

    public List<OrderTiming> getOrderTimingList() {
        return orderTimingList;
    }

    public void setOrderTimingList(List<OrderTiming> orderTimingList) {
        this.orderTimingList = orderTimingList;
    }

    public List<OrderMedication> getOrderMedicationList() {
        return orderMedicationList;
    }

    public void setOrderMedicationList(List<OrderMedication> orderMedicationList) {
        this.orderMedicationList = orderMedicationList;
    }

    public List<MeasureUnitConversion> getConversionsFrom() {
        return conversionsFrom;
    }

    public void setConversionsFrom(List<MeasureUnitConversion> conversionsFrom) {
        this.conversionsFrom = conversionsFrom;
    }

    public List<MeasureUnitConversion> getConversionsTo() {
        return conversionsTo;
    }

    public void setConversionsTo(List<MeasureUnitConversion> conversionsTo) {
        this.conversionsTo = conversionsTo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmeasureUnit != null ? idmeasureUnit.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MeasureUnit)) {
            return false;
        }
        MeasureUnit other = (MeasureUnit) object;
        if ((this.idmeasureUnit == null && other.idmeasureUnit != null) || (this.idmeasureUnit != null && !this.idmeasureUnit.equals(other.idmeasureUnit))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.MeasureUnit[idmeasureUnit=" + idmeasureUnit + "]";
    }

    public void addDose(Dose dose) {
        if (this.doseList == null) {
            this.setDoseList(new ArrayList<Dose>());
        }
        this.doseList.add(dose);
        if (dose.getMeasureUnitIdmeasureUnit() != this) {
            dose.setMeasureUnitIdmeasureUnit(this);
        }
    }

    public void addOrderTiming(OrderTiming ordertiming) {
        if (this.orderTimingList == null) {
            this.setOrderTimingList(new ArrayList<OrderTiming>());
        }
        this.orderTimingList.add(ordertiming);
        if (ordertiming.getMeasureUnitIdmeasureUnit() != this) {
            ordertiming.setMeasureUnitIdmeasureUnit(this);
        }
    }

    public void addOrderMedication(OrderMedication ordermedication) {
        if (this.orderMedicationList == null) {
            this.setOrderMedicationList(new ArrayList<OrderMedication>());
        }
        this.orderMedicationList.add(ordermedication);
        if (ordermedication.getMeasureUnitIdmeasureUnit() != this) {
            ordermedication.setMeasureUnitIdmeasureUnit(this);
        }
    }
    public void addConversionTo(MeasureUnitConversion measureUnitConversion) {
        if (this.conversionsTo == null) {
            this.setConversionsTo(new ArrayList<MeasureUnitConversion>());
        }
        this.conversionsTo.add(measureUnitConversion);
        if (measureUnitConversion.getMu_to() != this) {
            measureUnitConversion.setMu_to(this);
        }
    }
    public void addConversionFrom(MeasureUnitConversion measureUnitConversion) {
        if (this.conversionsFrom == null) {
            this.setConversionsFrom(new ArrayList<MeasureUnitConversion>());
        }
        this.conversionsFrom.add(measureUnitConversion);
        if (measureUnitConversion.getMu_from() != this) {
            measureUnitConversion.setMu_from(this);
        }
    }


}
