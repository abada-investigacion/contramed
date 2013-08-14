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
 * Telefono de medico para un tratamiento en concreto
 * @author david
 */
@Entity
@Table(name = "telephone_doctor")
@NamedQueries({
    @NamedQuery(name = "TelephoneDoctor.findAll", query = "SELECT t FROM TelephoneDoctor t"),
    @NamedQuery(name = "TelephoneDoctor.findByIdtelephoneDoctor", query = "SELECT t FROM TelephoneDoctor t WHERE t.idtelephoneDoctor = :idtelephoneDoctor"),
    @NamedQuery(name = "TelephoneDoctor.findByTelephone", query = "SELECT t FROM TelephoneDoctor t WHERE t.telephone = :telephone")})
public class TelephoneDoctor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "idtelephone_doctor", nullable = false)
    private Long idtelephoneDoctor;
    /**
     * Telefono
     */
    @Basic(optional = false)
    @Column(name = "telephone", nullable = false, length = 14)
    private String telephone;
    /**
     * Tratamiento al que esta asociado
     */
    @JsonExclude
    @JoinColumn(name = "order_idorder", referencedColumnName = "idorder", nullable = false)
    @ManyToOne(optional = false)
    private Order1 orderIdorder;

    public TelephoneDoctor() {
    }

    public TelephoneDoctor(Long idtelephoneDoctor) {
        this.idtelephoneDoctor = idtelephoneDoctor;
    }

    public TelephoneDoctor(Long idtelephoneDoctor, String telephone) {
        this.idtelephoneDoctor = idtelephoneDoctor;
        this.telephone = telephone;
    }

    public Long getIdtelephoneDoctor() {
        return idtelephoneDoctor;
    }

    public void setIdtelephoneDoctor(Long idtelephoneDoctor) {
        this.idtelephoneDoctor = idtelephoneDoctor;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Order1 getOrderIdorder() {
        return orderIdorder;
    }

    public void setOrderIdorder(Order1 orderIdorder) {
        this.orderIdorder = orderIdorder;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtelephoneDoctor != null ? idtelephoneDoctor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TelephoneDoctor)) {
            return false;
        }
        TelephoneDoctor other = (TelephoneDoctor) object;
        if ((this.idtelephoneDoctor == null && other.idtelephoneDoctor != null) || (this.idtelephoneDoctor != null && !this.idtelephoneDoctor.equals(other.idtelephoneDoctor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.TelephoneDoctor[idtelephoneDoctor=" + idtelephoneDoctor + "]";
    }

}
