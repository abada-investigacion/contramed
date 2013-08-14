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
 * Comunidad Autonoma
 * @author david
 */
@Entity
@Table(name = "region")
@NamedQueries({
    @NamedQuery(name = "Region.findAll", query = "SELECT r FROM Region r"),
    @NamedQuery(name = "Region.findByIdregion", query = "SELECT r FROM Region r WHERE r.idregion = :idregion"),
    @NamedQuery(name = "Region.findByName", query = "SELECT r FROM Region r WHERE r.name = :name")})
public class Region implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "idregion", nullable = false)
    private Integer idregion;
    /**
     * Nombre de la comunidad autonoma
     */
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 45)
    private String name;
    /**
     * Pais al que pertenece la comunidad autonoma
     */
    @JoinColumn(name = "nation_idnation", referencedColumnName = "idnation", nullable = false)
    @ManyToOne(optional = false)
    private Nation nationIdnation;
    /**
     * Listado de provincias que posee la comunidad autonoma
     */
    @JsonExclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "regionIdregion", fetch = FetchType.LAZY)
    private List<Province> provinceList;

    public Region() {
    }

    public Region(Integer idregion) {
        this.idregion = idregion;
    }

    public Region(Integer idregion, String name) {
        this.idregion = idregion;
        this.name = name;
    }

    public Integer getIdregion() {
        return idregion;
    }

    public void setIdregion(Integer idregion) {
        this.idregion = idregion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Nation getNationIdnation() {
        return nationIdnation;
    }

    public void setNationIdnation(Nation nationIdnation) {
        this.nationIdnation = nationIdnation;
    }

    public List<Province> getProvinceList() {
        return provinceList;
    }

    public void setProvinceList(List<Province> provinceList) {
        this.provinceList = provinceList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idregion != null ? idregion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Region)) {
            return false;
        }
        Region other = (Region) object;
        if ((this.idregion == null && other.idregion != null) || (this.idregion != null && !this.idregion.equals(other.idregion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.Region[idregion=" + idregion + "]";
    }

    public void addProvince(Province province) {
        if (this.provinceList == null) {
            this.setProvinceList(new ArrayList<Province>());
        }
        this.provinceList.add(province);
        if (province.getRegionIdregion() != this) {
            province.setRegionIdregion(this);
        }
    }
}
