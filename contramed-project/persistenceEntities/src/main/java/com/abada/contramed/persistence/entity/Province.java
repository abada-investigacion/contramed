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
 * Datos de la provincia
 * @author david
 */
@Entity
@Table(name = "province")
@NamedQueries({
    @NamedQuery(name = "Province.findAll", query = "SELECT p FROM Province p"),
    @NamedQuery(name = "Province.findByIdprovince", query = "SELECT p FROM Province p WHERE p.idprovince = :idprovince"),
    @NamedQuery(name = "Province.findByName", query = "SELECT p FROM Province p WHERE p.name = :name")})
public class Province implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "idprovince", nullable = false)
    private Integer idprovince;
    /**
     * Nombre de la provincia
     */
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 45)
    private String name;
    /**
     * Listado de ciudades que tiene la provincia
     */
    @JsonExclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "provinceIdprovince", fetch = FetchType.LAZY)
    private List<City> cityList;
    /**
     * Comunidad autonoma a la que pertenece la provincia
     */
    @JoinColumn(name = "region_idregion", referencedColumnName = "idregion", nullable = false)
    @ManyToOne(optional = false)
    private Region regionIdregion;

    public Province() {
    }

    public Province(Integer idprovince) {
        this.idprovince = idprovince;
    }

    public Province(Integer idprovince, String name) {
        this.idprovince = idprovince;
        this.name = name;
    }

    public Integer getIdprovince() {
        return idprovince;
    }

    public void setIdprovince(Integer idprovince) {
        this.idprovince = idprovince;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }

    public Region getRegionIdregion() {
        return regionIdregion;
    }

    public void setRegionIdregion(Region regionIdregion) {
        this.regionIdregion = regionIdregion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idprovince != null ? idprovince.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Province)) {
            return false;
        }
        Province other = (Province) object;
        if ((this.idprovince == null && other.idprovince != null) || (this.idprovince != null && !this.idprovince.equals(other.idprovince))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.Province[idprovince=" + idprovince + "]";
    }

    public void addCity(City city) {
        if (this.cityList == null) {
            this.setCityList(new ArrayList<City>());
        }
        this.cityList.add(city);
        if (city.getProvinceIdprovince() != this) {
            city.setProvinceIdprovince(this);
        }
    }
}
