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

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Datos de una dosis
 * @author david
 */
@Entity
@Table(name = "dose")
@NamedQueries({
    @NamedQuery(name = "Dose.findAll", query = "SELECT d FROM Dose d"),
    @NamedQuery(name = "Dose.findByIddose", query = "SELECT d FROM Dose d WHERE d.iddose = :iddose"),
    @NamedQuery(name = "Dose.findByBatch", query = "SELECT d FROM Dose d WHERE d.batch = :batch"),
    @NamedQuery(name = "Dose.findByExpirationDate", query = "SELECT d FROM Dose d WHERE d.expirationDate = :expirationDate")})
public class Dose implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "iddose", nullable = false)
    private Long iddose;
    /**
     * Lote al que pertenece una dosis
     */
    @Basic(optional = false)
    @Column(name = "batch", nullable = false, length = 20)
    private String batch;
    /**
     * Fecha de caducidad
     */
    @Basic(optional = false)
    @Column(name = "expiration_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;
    /**
     * Cantidad administrada
     */
    @Basic(optional = false)
    @Column(name = "give_amount", nullable = false, precision = 10, scale = 3)
    private BigDecimal giveAmount;
    /**
     * Especialidad a la que pertenece la dosis
     */
    @JoinColumn(name = "catalogo_medicamentos_CODIGO", referencedColumnName = "CODIGO", nullable = false)
    @ManyToOne(optional = false)
    private CatalogoMedicamentos catalogomedicamentosCODIGO;
    /**
     * Unidad de medida de la cantidad a administrar
     */
    @JoinColumn(name = "measure_unit_idmeasure_unit", referencedColumnName = "idmeasure_unit", nullable = false)
    @ManyToOne(optional = false)
    private MeasureUnit measureUnitIdmeasureUnit;
    /**
     * Listado de datos historicos que tienen que ver con esta dosis
     */
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doseIddose", fetch = FetchType.LAZY)
    private List<GivesHistoric> givesHistoricList;
    /**
     * Listado de datos historicos que tienen que ver con esta dosis
     */
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doseIddose", fetch = FetchType.LAZY)
    private List<PrepareHistoric> prepareHistoricList;
    /**
     * Listado de incidencias historicas que tienen que ver con esta dosis
     */
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dose", fetch = FetchType.LAZY)
    private List<PrepareIncidence> prepareIncidenceList;
    /**
     * Listado de incidencias historicas que tienen que ver con esta dosis
     */
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dose", fetch = FetchType.LAZY)
    private List<GivesIncidence> givesIncidenceList;

    public Dose() {
    }

    public Dose(Long iddose) {
        this.iddose = iddose;
    }

    public Dose(Long iddose, String batch, Date expirationDate, BigDecimal giveAmount) {
        this.iddose = iddose;
        this.batch = batch;
        this.expirationDate = expirationDate;
        this.giveAmount = giveAmount;
    }

    public List<GivesIncidence> getGivesIncidenceList() {
        return givesIncidenceList;
    }

    public void setGivesIncidenceList(List<GivesIncidence> givesIncidenceList) {
        this.givesIncidenceList = givesIncidenceList;
    }

    public List<PrepareIncidence> getPrepareIncidenceList() {
        return prepareIncidenceList;
    }

    public void setPrepareIncidenceList(List<PrepareIncidence> prepareIncidenceList) {
        this.prepareIncidenceList = prepareIncidenceList;
    }

    public Long getIddose() {
        return iddose;
    }

    public void setIddose(Long iddose) {
        this.iddose = iddose;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public BigDecimal getGiveAmount() {
        return giveAmount;
    }

    public void setGiveAmount(BigDecimal giveAmount) {
        this.giveAmount = giveAmount;
    }

    public CatalogoMedicamentos getCatalogomedicamentosCODIGO() {
        return catalogomedicamentosCODIGO;
    }

    public void setCatalogomedicamentosCODIGO(CatalogoMedicamentos catalogomedicamentosCODIGO) {
        this.catalogomedicamentosCODIGO = catalogomedicamentosCODIGO;
    }

    public MeasureUnit getMeasureUnitIdmeasureUnit() {
        return measureUnitIdmeasureUnit;
    }

    public void setMeasureUnitIdmeasureUnit(MeasureUnit measureUnitIdmeasureUnit) {
        this.measureUnitIdmeasureUnit = measureUnitIdmeasureUnit;
    }

    public List<GivesHistoric> getGivesHistoricList() {
        return givesHistoricList;
    }

    public void setGivesHistoricList(List<GivesHistoric> givesHistoricList) {
        this.givesHistoricList = givesHistoricList;
    }

    public List<PrepareHistoric> getPrepareHistoricList() {
        return prepareHistoricList;
    }

    public void setPrepareHistoricList(List<PrepareHistoric> prepareHistoricList) {
        this.prepareHistoricList = prepareHistoricList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddose != null ? iddose.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dose)) {
            return false;
        }
        Dose other = (Dose) object;
        if ((this.iddose == null && other.iddose != null) || (this.iddose != null && !this.iddose.equals(other.iddose))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.Dose[iddose=" + iddose + "]";
    }

    public void addGivesHistoric(GivesHistoric giveshistoric) {
        if (this.givesHistoricList == null) {
            this.setGivesHistoricList(new ArrayList<GivesHistoric>());
        }
        this.givesHistoricList.add(giveshistoric);
        if (giveshistoric.getDoseIddose() != this) {
            giveshistoric.setDoseIddose(this);
        }
    }

    public void addPrepareHistoric(PrepareHistoric preparehistoric) {
        if (this.prepareHistoricList == null) {
            this.setPrepareHistoricList(new ArrayList<PrepareHistoric>());
        }
        this.prepareHistoricList.add(preparehistoric);
        if (preparehistoric.getDoseIddose() != this) {
            preparehistoric.setDoseIddose(this);
        }
    }

    public void addGivesIncidence(GivesIncidence givesIncidence) {
        if (this.givesIncidenceList == null) {
            this.setGivesIncidenceList(new ArrayList<GivesIncidence>());
        }
        this.givesIncidenceList.add(givesIncidence);
        if (givesIncidence.getDose() != this) {
            givesIncidence.setDose(this);
        }
    }

    public void addPrepareIncidence(PrepareIncidence prepareIncidence) {
        if (this.prepareIncidenceList == null) {
            this.setPrepareIncidenceList(new ArrayList<PrepareIncidence>());
        }
        this.prepareIncidenceList.add(prepareIncidence);
        if (prepareIncidence.getDose() != this) {
            prepareIncidence.setDose(this);
        }
    }
}
