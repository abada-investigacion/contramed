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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Medicacion de la que se compone un tratamiento
 * @author david
 */
@Entity
@Table(name = "order_medication")
@NamedQueries({
    @NamedQuery(name = "OrderMedication.findAll", query = "SELECT o FROM OrderMedication o"),
    @NamedQuery(name = "OrderMedication.findByOrderIdorder", query = "SELECT o FROM OrderMedication o WHERE o.orderIdorder = :orderIdorder"),
    @NamedQuery(name = "OrderMedication.findByGiveAmount", query = "SELECT o FROM OrderMedication o WHERE o.giveAmount = :giveAmount"),    
    @NamedQuery(name = "OrderMedication.findByAlergy", query = "SELECT o FROM OrderMedication o WHERE o.alergy = :alergy")})
public class OrderMedication implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "order_idorder", nullable = false)
    private Long orderIdorder;
    /**
     * Cantidad a administrar
     */
    @Basic(optional = false)
    @Column(name = "give_amount", nullable = false, precision = 10, scale = 3)
    private BigDecimal giveAmount;
    /**
     * Determina si el paciente es alergico al medicamento
     */
    @Basic(optional = false)
    @Column(name = "alergy", nullable = false)
    private boolean alergy;
    /**
     * Especialidad del medicamento que se debe administrar
     */
    @JoinColumn(name = "catalogo_medicamentos_CODIGO", referencedColumnName = "CODIGO")
    @ManyToOne()
    private CatalogoMedicamentos catalogomedicamentosCODIGO;

     /**
     * principio activo
     */
    @JoinColumn(name = "principio_activo_codigo", referencedColumnName = "CODIGO")
    @ManyToOne()
    private PrincipioActivo principioActivo;

    /**
     * Unidad de administracion
     */
    @JoinColumn(name = "measure_unit_idmeasure_unit", referencedColumnName = "idmeasure_unit", nullable = false)
    @ManyToOne(optional = false)
    private MeasureUnit measureUnitIdmeasureUnit;
    /**
     * Tratamiento con el que esta relacionado
     */
    @JsonExclude
    @JoinColumn(name = "order_idorder", referencedColumnName = "idorder", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Order1 order1;
    /**
     * via de administracion
     */
    @JoinColumn(name = "administration_type", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Table0162 administrationType;
    /**
     * Listado de observaciones
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderMedicationOrderIdorder", fetch = FetchType.LAZY)
    private List<OrderMedicationObservation> orderMedicationObservationList;
    /**
     * Listado de instrucciones
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderMedicationOrderIdorder", fetch = FetchType.LAZY)
    private List<OrderMedicationInstruction> orderMedicationInstructionList;

    public OrderMedication() {
    }

    public OrderMedication(Long orderIdorder) {
        this.orderIdorder = orderIdorder;
    }

    public OrderMedication(Long orderIdorder, BigDecimal giveAmount, boolean alergy) {
        this.orderIdorder = orderIdorder;
        this.giveAmount = giveAmount;
        this.alergy = alergy;
    }

    public Long getOrderIdorder() {
        return orderIdorder;
    }

    public void setOrderIdorder(Long orderIdorder) {
        this.orderIdorder = orderIdorder;
    }

    public BigDecimal getGiveAmount() {
        return giveAmount;
    }

    public void setGiveAmount(BigDecimal giveAmount) {
        this.giveAmount = giveAmount;
    }

    public boolean getAlergy() {
        return alergy;
    }

    public void setAlergy(boolean alergy) {
        this.alergy = alergy;
    }
      public PrincipioActivo getPrincipioActivo() {
        return principioActivo;
    }

    public void setPrincipioActivo(PrincipioActivo principioActivo) {
        this.principioActivo = principioActivo;
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

    public Order1 getOrder1() {
        return order1;
    }

    public void setOrder1(Order1 order1) {
        this.order1 = order1;
    }

    public Table0162 getAdministrationType() {
        return administrationType;
    }

    public void setAdministrationType(Table0162 administrationType) {
        this.administrationType = administrationType;
    }

    public List<OrderMedicationObservation> getOrderMedicationObservationList() {
        return orderMedicationObservationList;
    }

    public void setOrderMedicationObservationList(List<OrderMedicationObservation> orderMedicationObservationList) {
        this.orderMedicationObservationList = orderMedicationObservationList;
    }

    public List<OrderMedicationInstruction> getOrderMedicationInstructionList() {
        return orderMedicationInstructionList;
    }

    public void setOrderMedicationInstructionList(List<OrderMedicationInstruction> orderMedicationInstructionList) {
        this.orderMedicationInstructionList = orderMedicationInstructionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderIdorder != null ? orderIdorder.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderMedication)) {
            return false;
        }
        OrderMedication other = (OrderMedication) object;
        if ((this.orderIdorder == null && other.orderIdorder != null) || (this.orderIdorder != null && !this.orderIdorder.equals(other.orderIdorder))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.OrderMedication[orderIdorder=" + orderIdorder + "]";
    }

    public void addOrderMedicationObservation(OrderMedicationObservation ordermedicationobservation) {
        if (this.orderMedicationObservationList == null) {
            this.setOrderMedicationObservationList(new ArrayList<OrderMedicationObservation>());
        }
        this.orderMedicationObservationList.add(ordermedicationobservation);
        if (ordermedicationobservation.getOrderMedicationOrderIdorder() != this) {
            ordermedicationobservation.setOrderMedicationOrderIdorder(this);
        }
    }

    public void addOrderMedicationInstruction(OrderMedicationInstruction ordermedicationinstruction) {
        if (this.orderMedicationInstructionList == null) {
            this.setOrderMedicationInstructionList(new ArrayList<OrderMedicationInstruction>());
        }
        this.orderMedicationInstructionList.add(ordermedicationinstruction);
        if (ordermedicationinstruction.getOrderMedicationOrderIdorder() != this) {
            ordermedicationinstruction.setOrderMedicationOrderIdorder(this);
        }
    }
}
