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
 * Observaciones sobre la Medicacion de la que se compone un tratamiento
 * @author david
 */
@Entity
@Table(name = "order_medication_observation")
@NamedQueries({
    @NamedQuery(name = "OrderMedicationObservation.findAll", query = "SELECT o FROM OrderMedicationObservation o"),
    @NamedQuery(name = "OrderMedicationObservation.findByIdorderMedicationObservation", query = "SELECT o FROM OrderMedicationObservation o WHERE o.idorderMedicationObservation = :idorderMedicationObservation"),
    @NamedQuery(name = "OrderMedicationObservation.findByObservation", query = "SELECT o FROM OrderMedicationObservation o WHERE o.observation = :observation")})
public class OrderMedicationObservation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "idorder_medication_observation", nullable = false)
    private Long idorderMedicationObservation;
    /**
     * Observaciones sobre la medicacion
     */
    @Basic(optional = false)
    @Column(name = "observation", nullable = false, length = 1024)
    private String observation;
    /**
     * Medicacion sobre la que se a hecho la observacion
     */
    @JsonExclude
    @JoinColumn(name = "order_medication_order_idorder", referencedColumnName = "order_idorder", nullable = false)
    @ManyToOne(optional = false)
    private OrderMedication orderMedicationOrderIdorder;

    public OrderMedicationObservation() {
    }

    public OrderMedicationObservation(Long idorderMedicationObservation) {
        this.idorderMedicationObservation = idorderMedicationObservation;
    }

    public OrderMedicationObservation(Long idorderMedicationObservation, String observation) {
        this.idorderMedicationObservation = idorderMedicationObservation;
        this.observation = observation;
    }

    public Long getIdorderMedicationObservation() {
        return idorderMedicationObservation;
    }

    public void setIdorderMedicationObservation(Long idorderMedicationObservation) {
        this.idorderMedicationObservation = idorderMedicationObservation;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
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
        hash += (idorderMedicationObservation != null ? idorderMedicationObservation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderMedicationObservation)) {
            return false;
        }
        OrderMedicationObservation other = (OrderMedicationObservation) object;
        if ((this.idorderMedicationObservation == null && other.idorderMedicationObservation != null) || (this.idorderMedicationObservation != null && !this.idorderMedicationObservation.equals(other.idorderMedicationObservation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.OrderMedicationObservation[idorderMedicationObservation=" + idorderMedicationObservation + "]";
    }

}
