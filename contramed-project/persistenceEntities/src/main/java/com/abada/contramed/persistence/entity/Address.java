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
 * Direccion
 * @author david
 */
@Entity
@Table(name = "address")
@NamedQueries({
    @NamedQuery(name = "Address.findAll", query = "SELECT a FROM Address a"),
    @NamedQuery(name = "Address.findByIdaddress", query = "SELECT a FROM Address a WHERE a.idaddress = :idaddress"),
    @NamedQuery(name = "Address.findByDirection", query = "SELECT a FROM Address a WHERE a.direction = :direction"),
    @NamedQuery(name = "Address.findByNumber", query = "SELECT a FROM Address a WHERE a.number = :number"),
    @NamedQuery(name = "Address.findByDirectionNumber", query = "SELECT a FROM Address a WHERE a.direction=:direction and a.number = :number")})
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "idaddress", nullable = false)
    private Long idaddress;
    /**
     * Calle
     */
    @Column(name = "direction", length = 45)
    private String direction;
    /**
     * Numero, piso, escalera y demas
     */
    @Column(name = "number_address", length = 45)
    private String number;
    /**
     * Codigo postal
     */
    @Column(name = "postal_code",  length = 45)
    private String postalCode;
    /**
     * Paciente al que pertenece esta direccion
     */
    @JsonExclude
    @JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Patient patientId;
    /**
     * Ciudad a la que pertenece esta direccion
     */
    @JoinColumn(name = "city_idcity", referencedColumnName = "idcity", nullable = false)
    @ManyToOne(optional = false)
    private City cityIdcity;

    public Address() {
    }

    public Address(Long idaddress) {
        this.idaddress = idaddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Long getIdaddress() {
        return idaddress;
    }

    public void setIdaddress(Long idaddress) {
        this.idaddress = idaddress;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Patient getPatientId() {
        return patientId;
    }

    public void setPatientId(Patient patientId) {
        this.patientId = patientId;
    }

    public City getCityIdcity() {
        return cityIdcity;
    }

    public void setCityIdcity(City cityIdcity) {
        this.cityIdcity = cityIdcity;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idaddress != null ? idaddress.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Address)) {
            return false;
        }
        Address other = (Address) object;
        if ((this.idaddress == null && other.idaddress != null) || (this.idaddress != null && !this.idaddress.equals(other.idaddress))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.Address[idaddress=" + idaddress + "]";
    }

}
