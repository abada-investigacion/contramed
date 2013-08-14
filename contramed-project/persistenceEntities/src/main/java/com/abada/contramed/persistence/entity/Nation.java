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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Datos de un pais
 * @author david
 */
@Entity
@Table(name = "nation")
@NamedQueries({
    @NamedQuery(name = "Nation.findAll", query = "SELECT n FROM Nation n"),
    @NamedQuery(name = "Nation.findByIdnation", query = "SELECT n FROM Nation n WHERE n.idnation = :idnation"),
    @NamedQuery(name = "Nation.findByName", query = "SELECT n FROM Nation n WHERE n.name = :name")})
public class Nation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "idnation", nullable = false)
    private Integer idnation;
    /**
     * Nombre
     */
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 45)
    private String name;
    /**
     * Comunidades que forman el pais
     */
    @JsonExclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nationIdnation", fetch = FetchType.LAZY)
    private List<Region> regionList;

    public Nation() {
    }

    public Nation(Integer idnation) {
        this.idnation = idnation;
    }

    public Nation(Integer idnation, String name) {
        this.idnation = idnation;
        this.name = name;
    }

    public Integer getIdnation() {
        return idnation;
    }

    public void setIdnation(Integer idnation) {
        this.idnation = idnation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Region> getRegionList() {
        return regionList;
    }

    public void setRegionList(List<Region> regionList) {
        this.regionList = regionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idnation != null ? idnation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Nation)) {
            return false;
        }
        Nation other = (Nation) object;
        if ((this.idnation == null && other.idnation != null) || (this.idnation != null && !this.idnation.equals(other.idnation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.Nation[idnation=" + idnation + "]";
    }

    public void addRegion(Region region) {
        if (this.regionList == null) {
            this.setRegionList(new ArrayList<Region>());
        }
        this.regionList.add(region);
        if (region.getNationIdnation() != this) {
            region.setNationIdnation(this);
        }
    }
}
