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
 * Instrucciones sobre la Medicacion de la que se compone un tratamiento
 * @author david
 */
@Entity
@Table(name = "order_medication_instruction")
@NamedQueries({
    @NamedQuery(name = "OrderMedicationInstruction.findAll", query = "SELECT o FROM OrderMedicationInstruction o"),
    @NamedQuery(name = "OrderMedicationInstruction.findByIdorderMedicationInstruction", query = "SELECT o FROM OrderMedicationInstruction o WHERE o.idorderMedicationInstruction = :idorderMedicationInstruction"),
    @NamedQuery(name = "OrderMedicationInstruction.findByInstruction", query = "SELECT o FROM OrderMedicationInstruction o WHERE o.instruction = :instruction")})
public class OrderMedicationInstruction implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "idorder_medication_instruction", nullable = false)
    private Long idorderMedicationInstruction;
    /**
     * Intrucciones
     */
    @Basic(optional = false)
    @Column(name = "instruction", nullable = false, length = 1024)
    private String instruction;
    /**
     * Medicacion sobre la que se a hecho la observacion
     */
    @JsonIgnore
    @JoinColumn(name = "order_medication_order_idorder", referencedColumnName = "order_idorder", nullable = false)
    @ManyToOne(optional = false)
    private OrderMedication orderMedicationOrderIdorder;

    public OrderMedicationInstruction() {
    }

    public OrderMedicationInstruction(Long idorderMedicationInstruction) {
        this.idorderMedicationInstruction = idorderMedicationInstruction;
    }

    public OrderMedicationInstruction(Long idorderMedicationInstruction, String instruction) {
        this.idorderMedicationInstruction = idorderMedicationInstruction;
        this.instruction = instruction;
    }

    public Long getIdorderMedicationInstruction() {
        return idorderMedicationInstruction;
    }

    public void setIdorderMedicationInstruction(Long idorderMedicationInstruction) {
        this.idorderMedicationInstruction = idorderMedicationInstruction;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public OrderMedication getOrderMedicationOrderIdorder() {
        return orderMedicationOrderIdorder;
    }

    public void setOrderMedicationOrderIdorder(OrderMedication orderMedicationOrderIdorder) {
        this.orderMedicationOrderIdorder = orderMedicationOrderIdorder;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idorderMedicationInstruction != null ? idorderMedicationInstruction.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderMedicationInstruction)) {
            return false;
        }
        OrderMedicationInstruction other = (OrderMedicationInstruction) object;
        if ((this.idorderMedicationInstruction == null && other.idorderMedicationInstruction != null) || (this.idorderMedicationInstruction != null && !this.idorderMedicationInstruction.equals(other.idorderMedicationInstruction))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.OrderMedicationInstruction[idorderMedicationInstruction=" + idorderMedicationInstruction + "]";
    }

}
