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
 * Datos de una toma, esta puede servir para varios dias
 * @author david
 */
@Entity
@Table(name = "order_timing")
@NamedQueries({
    @NamedQuery(name = "OrderTiming.findAll", query = "SELECT o FROM OrderTiming o"),
    @NamedQuery(name = "OrderTiming.findByIdorderTiming", query = "SELECT o FROM OrderTiming o WHERE o.idorderTiming = :idorderTiming"),
    @NamedQuery(name = "OrderTiming.findByRepetitionPattern", query = "SELECT o FROM OrderTiming o WHERE o.repetitionPattern = :repetitionPattern"),
    @NamedQuery(name = "OrderTiming.findByTime", query = "SELECT o FROM OrderTiming o WHERE o.time = :time"),
    @NamedQuery(name = "OrderTiming.findByDurationTime", query = "SELECT o FROM OrderTiming o WHERE o.durationTime = :durationTime"),
    @NamedQuery(name = "OrderTiming.findByStartDate", query = "SELECT o FROM OrderTiming o WHERE o.startDate = :startDate"),
    @NamedQuery(name = "OrderTiming.findByEndDate", query = "SELECT o FROM OrderTiming o WHERE o.endDate = :endDate"),
    @NamedQuery(name = "OrderTiming.findByIfNecesary", query = "SELECT o FROM OrderTiming o WHERE o.ifNecesary = :ifNecesary"),
    @NamedQuery(name = "OrderTiming.findByInstructions", query = "SELECT o FROM OrderTiming o WHERE o.instructions = :instructions"),
    @NamedQuery(name = "OrderTiming.findByHistoric", query = "SELECT o FROM OrderTiming o WHERE o.historic = :historic"),
    @NamedQuery(name = "OrderTiming.findByGiveAmount", query = "SELECT o FROM OrderTiming o WHERE o.giveAmount = :giveAmount")})
public class OrderTiming implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "idorder_timing", nullable = false)
    private Long idorderTiming;
    /**
     * Patron de repeticion
     */
    @Column(name = "repetition_pattern", length = 45)
    private String repetitionPattern;
    /**
     * Hora a la que se debe administrar
     */
    @Basic(optional = false)
    @Column(name = "time", nullable = false)
    @Temporal(TemporalType.TIME)
    private Date time;
    /**
     * Dias que va a durar esta toma desde la fecha de startTime
     */
    @Column(name = "duration_time")
    private Integer durationTime;
    /**
     * Fecha de comienzo de esta toma
     */
    @Basic(optional = false)
    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    /**
     * Determinina la fecha de fin de la toma
     */
    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    /**
     * Determina si que el medicamento solo se debe administrar en caso de ser necesario
     */
    @Basic(optional = false)
    @Column(name = "if_necesary", nullable = false)
    private boolean ifNecesary;
    /**
     * Intrucciones escritas por el medico
     */
    @Column(name = "instructions", length = 1024)
    private String instructions;
    /**
     * Flag para determinar la finalizacion de la toma
     */
    @Basic(optional = false)
    @Column(name = "historic", nullable = false)
    private boolean historic;
    /**
     * Cantidad a administrar
     */
    @Deprecated
    @Column(name = "give_amount", precision = 10, scale = 3)
    private BigDecimal giveAmount;
    /**
     * Historial de veces que se ha administrado esta toma
     */
    @JsonIgnore
    @OneToMany(mappedBy = "orderTimingIdorderTiming", fetch = FetchType.LAZY)
    private List<GivesHistoric> givesHistoricList;
    /**
     * Tratamiento al que pertenece esta toma
     */
    @JoinColumn(name = "order_idorder", referencedColumnName = "idorder", nullable = false)
    @ManyToOne(optional = false)
    private Order1 orderIdorder;
    /**
     * Unidad de medida de la cantidad a administrar
     */
    @Deprecated
    @JoinColumn(name = "measure_unit_idmeasure_unit", referencedColumnName = "idmeasure_unit")
    @ManyToOne()
    private MeasureUnit measureUnitIdmeasureUnit;
    /**
     * Incidencias a la hora de administrar que se han producido sobre esta toma
     */
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderTimingIdorderTiming", fetch = FetchType.LAZY)
    private List<PrepareIncidence> prepareIncidenceList;
    /**
     * Incidencias a la hora de preparar que se han producido sobre esta toma
     */
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderTimingIdorderTiming", fetch = FetchType.LAZY)
    private List<GivesIncidence> givesIncidenceList;
    /**
     * Historial de veces que se ha preparado esta toma
     */
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderTimingIdorderTiming", fetch = FetchType.LAZY)
    private List<PrepareHistoric> prepareHistoricList;

    public OrderTiming() {
    }

    public OrderTiming(Long idorderTiming) {
        this.idorderTiming = idorderTiming;
    }

    public OrderTiming(Long idorderTiming, Date time, Date startDate, boolean ifNecesary, boolean historic) {
        this.idorderTiming = idorderTiming;
        this.time = time;
        this.startDate = startDate;
        this.ifNecesary = ifNecesary;
        this.historic = historic;
    }

    public Long getIdorderTiming() {
        return idorderTiming;
    }

    public void setIdorderTiming(Long idorderTiming) {
        this.idorderTiming = idorderTiming;
    }

    public String getRepetitionPattern() {
        return repetitionPattern;
    }

    public void setRepetitionPattern(String repetitionPattern) {
        this.repetitionPattern = repetitionPattern;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(Integer durationTime) {
        this.durationTime = durationTime;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean getIfNecesary() {
        return ifNecesary;
    }

    public void setIfNecesary(boolean ifNecesary) {
        this.ifNecesary = ifNecesary;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public boolean getHistoric() {
        return historic;
    }

    public void setHistoric(boolean historic) {
        this.historic = historic;
    }

    public BigDecimal getGiveAmount() {
        return giveAmount;
    }

    public void setGiveAmount(BigDecimal giveAmount) {
        this.giveAmount = giveAmount;
    }

    public List<GivesHistoric> getGivesHistoricList() {
        return givesHistoricList;
    }

    public void setGivesHistoricList(List<GivesHistoric> givesHistoricList) {
        this.givesHistoricList = givesHistoricList;
    }

    public Order1 getOrderIdorder() {
        return orderIdorder;
    }

    public void setOrderIdorder(Order1 orderIdorder) {
        this.orderIdorder = orderIdorder;
    }

    public MeasureUnit getMeasureUnitIdmeasureUnit() {
        return measureUnitIdmeasureUnit;
    }

    public void setMeasureUnitIdmeasureUnit(MeasureUnit measureUnitIdmeasureUnit) {
        this.measureUnitIdmeasureUnit = measureUnitIdmeasureUnit;
    }

    public List<PrepareIncidence> getPrepareIncidenceList() {
        return prepareIncidenceList;
    }

    public void setPrepareIncidenceList(List<PrepareIncidence> prepareIncidenceList) {
        this.prepareIncidenceList = prepareIncidenceList;
    }

    public List<GivesIncidence> getGivesIncidenceList() {
        return givesIncidenceList;
    }

    public void setGivesIncidenceList(List<GivesIncidence> givesIncidenceList) {
        this.givesIncidenceList = givesIncidenceList;
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
        hash += (idorderTiming != null ? idorderTiming.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderTiming)) {
            return false;
        }
        OrderTiming other = (OrderTiming) object;
        if ((this.idorderTiming == null && other.idorderTiming != null) || (this.idorderTiming != null && !this.idorderTiming.equals(other.idorderTiming))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.OrderTiming[idorderTiming=" + idorderTiming + "]";
    }

    public void addGivesHistoric(GivesHistoric giveshistoric) {
        if (this.givesHistoricList == null) {
            this.setGivesHistoricList(new ArrayList<GivesHistoric>());
        }
        this.givesHistoricList.add(giveshistoric);
        if (giveshistoric.getOrderTimingIdorderTiming() != this) {
            giveshistoric.setOrderTimingIdorderTiming(this);
        }
    }

    public void addPrepareIncidence(PrepareIncidence prepareincidence) {
        if (this.prepareIncidenceList == null) {
            this.setPrepareIncidenceList(new ArrayList<PrepareIncidence>());
        }
        this.prepareIncidenceList.add(prepareincidence);
        if (prepareincidence.getOrderTimingIdorderTiming() != this) {
            prepareincidence.setOrderTimingIdorderTiming(this);
        }
    }

    public void addGivesIncidence(GivesIncidence givesincidence) {
        if (this.givesIncidenceList == null) {
            this.setGivesIncidenceList(new ArrayList<GivesIncidence>());
        }
        this.givesIncidenceList.add(givesincidence);
        if (givesincidence.getOrderTimingIdorderTiming() != this) {
            givesincidence.setOrderTimingIdorderTiming(this);
        }
    }

    public void addPrepareHistoric(PrepareHistoric preparehistoric) {
        if (this.prepareHistoricList == null) {
            this.setPrepareHistoricList(new ArrayList<PrepareHistoric>());
        }
        this.prepareHistoricList.add(preparehistoric);
        if (preparehistoric.getOrderTimingIdorderTiming() != this) {
            preparehistoric.setOrderTimingIdorderTiming(this);
        }
    }

    @Override
    public OrderTiming clone(){
        OrderTiming result=new OrderTiming();
        result.setDurationTime(this.getDurationTime());
        result.setEndDate(this.getEndDate());
        result.setGiveAmount(this.getGiveAmount());
        result.setHistoric(this.getHistoric());
        result.setIdorderTiming(this.getIdorderTiming());
        result.setIfNecesary(this.getIfNecesary());
        result.setInstructions(this.getInstructions());
        result.setMeasureUnitIdmeasureUnit(this.getMeasureUnitIdmeasureUnit());
        result.setOrderIdorder(this.getOrderIdorder());
        result.setRepetitionPattern(this.getRepetitionPattern());
        result.setStartDate(this.getStartDate());
        result.setTime(this.getTime());
        return result;
    }
}
