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
import javax.persistence.UniqueConstraint;

/**
 * vias de administracion de medicación
 * @author david
 */
@Entity
@Table(name = "table0162", uniqueConstraints = {@UniqueConstraint(columnNames = {"code"})})
@NamedQueries({
    @NamedQuery(name = "Table0162.findAll", query = "SELECT t FROM Table0162 t"),
    @NamedQuery(name = "Table0162.findById", query = "SELECT t FROM Table0162 t WHERE t.id = :id"),
    @NamedQuery(name = "Table0162.findByCode", query = "SELECT t FROM Table0162 t WHERE t.code = :code"),
    @NamedQuery(name = "Table0162.findByDetails", query = "SELECT t FROM Table0162 t WHERE t.details = :details")})
public class Table0162 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    /**
     * Código
     */
    @Basic(optional = false)
    @Column(name = "code", nullable = false, length = 20)
    private String code;
    /**
     * Nombre descriptivo de la via
     */
    @Column(name = "details", length = 50)
    private String details;
    /**
     * Lista de tratamientos que incluyen esta vía de administración
     */
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "administrationType", fetch = FetchType.LAZY)
    private List<OrderMedication> orderMedicationList;
    /**
     * Lista de especialidades que tienen como via predeterminada esta via.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "via")
    private List<CatalogoMedicamentos> catalogoMedicamentosList;

    public Table0162() {
    }

    public Table0162(Integer id) {
        this.id = id;
    }

    public Table0162(Integer id, String code) {
        this.id = id;
        this.code = code;
    }

    public List<CatalogoMedicamentos> getCatalogoMedicamentosList() {
        return catalogoMedicamentosList;
    }

    public void setCatalogoMedicamentosList(List<CatalogoMedicamentos> catalogoMedicamentosList) {
        this.catalogoMedicamentosList = catalogoMedicamentosList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<OrderMedication> getOrderMedicationList() {
        return orderMedicationList;
    }

    public void setOrderMedicationList(List<OrderMedication> orderMedicationList) {
        this.orderMedicationList = orderMedicationList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Table0162)) {
            return false;
        }
        Table0162 other = (Table0162) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.Table0162[id=" + id + "]";
    }

    public void addOrderMedication(OrderMedication ordermedication) {
        if (this.orderMedicationList == null) {
            this.setOrderMedicationList(new ArrayList<OrderMedication>());
        }
        this.orderMedicationList.add(ordermedication);
        if (ordermedication.getAdministrationType() != this) {
            ordermedication.setAdministrationType(this);
        }
    }

    public void addCatalogoMedicamentos(CatalogoMedicamentos catalogomedicamentos) {
        if (this.catalogoMedicamentosList == null) {
            this.setCatalogoMedicamentosList(new ArrayList<CatalogoMedicamentos>());
        }
        this.catalogoMedicamentosList.add(catalogomedicamentos);
        if (catalogomedicamentos.getVia() != this) {
            catalogomedicamentos.setVia(this);
        }
    }
}
