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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Datos de una ciudad
 * @author david
 */
@Entity
@Table(name = "city")
@NamedQueries({
    @NamedQuery(name = "City.findAll", query = "SELECT c FROM City c"),
    @NamedQuery(name = "City.findByIdcity", query = "SELECT c FROM City c WHERE c.idcity = :idcity"),
    @NamedQuery(name = "City.findByName", query = "SELECT c FROM City c WHERE c.name = :name")})
public class City implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "idcity", nullable = false)
    private Integer idcity;
    /**
     * Nombre de la ciudad
     */
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 45)
    private String name;
    /**
     * Provincia la que pertenece la ciudad
     */
    @JoinColumn(name = "province_idprovince", referencedColumnName = "idprovince", nullable = false)
    @ManyToOne(optional = false)
    private Province provinceIdprovince;
    /**
     * Listado de pacientes que nacieron en esta ciudad
     */
    @JsonExclude
    @OneToMany(mappedBy = "birthCity", fetch = FetchType.LAZY)
    private List<Patient> patientList;
    /**
     * Listado de direcciones de pacientes en esta ciudad
     */
    @JsonExclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cityIdcity", fetch = FetchType.LAZY)
    private List<Address> addressList;

    public City() {
    }

    public City(Integer idcity) {
        this.idcity = idcity;
    }

    public City(Integer idcity, String name) {
        this.idcity = idcity;
        this.name = name;
    }

    public Integer getIdcity() {
        return idcity;
    }

    public void setIdcity(Integer idcity) {
        this.idcity = idcity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Province getProvinceIdprovince() {
        return provinceIdprovince;
    }

    public void setProvinceIdprovince(Province provinceIdprovince) {
        this.provinceIdprovince = provinceIdprovince;
    }

    public List<Patient> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<Patient> patientList) {
        this.patientList = patientList;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcity != null ? idcity.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof City)) {
            return false;
        }
        City other = (City) object;
        if ((this.idcity == null && other.idcity != null) || (this.idcity != null && !this.idcity.equals(other.idcity))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.City[idcity=" + idcity + "]";
    }

    public void addPatient(Patient patient) {
        if (this.patientList == null) {
            this.setPatientList(new ArrayList<Patient>());
        }
        this.patientList.add(patient);
        if (patient.getBirthCity() != this) {
            patient.setBirthCity(this);
        }
    }

    public void addAddress(Address address) {
        if (this.addressList == null) {
            this.setAddressList(new ArrayList<Address>());
        }
        this.addressList.add(address);
        if (address.getCityIdcity() != this) {
            address.setCityIdcity(this);
        }
    }
}
