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
 * Tipo de codigos de control de los tratamientos
 * @author david
 */
@Entity
@Table(name = "table0119", uniqueConstraints = {@UniqueConstraint(columnNames = {"code"})})
@NamedQueries({
    @NamedQuery(name = "Table0119.findAll", query = "SELECT t FROM Table0119 t"),
    @NamedQuery(name = "Table0119.findById", query = "SELECT t FROM Table0119 t WHERE t.id = :id"),
    @NamedQuery(name = "Table0119.findByCode", query = "SELECT t FROM Table0119 t WHERE t.code = :code"),
    @NamedQuery(name = "Table0119.findByDetails", query = "SELECT t FROM Table0119 t WHERE t.details = :details")})
public class Table0119 implements Serializable {

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
    @Column(name = "code", nullable = false, length = 3)
    private String code;
    /**
     * Nombre descriptivo
     */
    @Column(name = "details", length = 50)
    private String details;
    /**
     * Listado de ordenes que tienen este mismo código de control
     */
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "control", fetch = FetchType.LAZY)
    private List<Order1> order1List;

    public Table0119() {
    }

    public Table0119(Integer id) {
        this.id = id;
    }

    public Table0119(Integer id, String code) {
        this.id = id;
        this.code = code;
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

    public List<Order1> getOrder1List() {
        return order1List;
    }

    public void setOrder1List(List<Order1> order1List) {
        this.order1List = order1List;
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
        if (!(object instanceof Table0119)) {
            return false;
        }
        Table0119 other = (Table0119) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.Table0119[id=" + id + "]";
    }

    public void addOrder1(Order1 order1) {
        if (this.order1List == null) {
            this.setOrder1List(new ArrayList<Order1>());
        }
        this.order1List.add(order1);
        if (order1.getControl() != this) {
            order1.setControl(this);
        }
    }
}
