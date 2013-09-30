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

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Tipos de acciones en los mensajes OMP y ORP
 * @author david
 */
@Deprecated
@Entity
@Table(name = "table0038", uniqueConstraints = {@UniqueConstraint(columnNames = {"code"})})
@NamedQueries({
    @NamedQuery(name = "Table0038.findAll", query = "SELECT t FROM Table0038 t"),
    @NamedQuery(name = "Table0038.findById", query = "SELECT t FROM Table0038 t WHERE t.id = :id"),
    @NamedQuery(name = "Table0038.findByCode", query = "SELECT t FROM Table0038 t WHERE t.code = :code"),
    @NamedQuery(name = "Table0038.findByDetails", query = "SELECT t FROM Table0038 t WHERE t.details = :details")})
public class Table0038 implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    /**
     * Codigo
     */
    @Basic(optional = false)
    @Column(name = "code", nullable = false, length = 3)
    private String code;
    /**
     * Nombre descriptivo
     */
    @Column(name = "details", length = 50)
    private String details;

    public Table0038() {
    }

    public Table0038(Integer id) {
        this.id = id;
    }

    public Table0038(Integer id, String code) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Table0038)) {
            return false;
        }
        Table0038 other = (Table0038) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.Table0038[id=" + id + "]";
    }

}
