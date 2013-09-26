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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Datos del tag que esta asociado a una cama/recurso
 * @author david
 */
@Entity
@Table(name = "recurso_tag", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"TAG"})})
@NamedQueries({
    @NamedQuery(name = "RecursoTag.findAll", query = "SELECT r FROM RecursoTag r"),
    @NamedQuery(name = "RecursoTag.findByTag", query = "SELECT r FROM RecursoTag r WHERE r.tag = :tag"),
    @NamedQuery(name = "RecursoTag.findById", query = "SELECT r FROM RecursoTag r WHERE r.id = :id")})
public class RecursoTag implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false, precision = 19, scale = 0)
    private Long id;
    /**
     * Tag de la cama/recurso
     */
    @Basic(optional = false)
    @Column(name = "TAG", nullable = false, length = 45)
    private String tag;
    /**
     * Recurso/cama al que pertenece
     */
    @JoinColumn(name = "recurso_ID_RECURSO", referencedColumnName = "ID_RECURSO", nullable = false)
    @ManyToOne(optional = false)
    private Recurso recurso;

    public RecursoTag() {
    }

    public RecursoTag(Long id) {
        this.id = id;
    }

    public RecursoTag(Long id, String tag) {
        this.id = id;
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recurso getRecurso() {
        return recurso;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
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
        if (!(object instanceof RecursoTag)) {
            return false;
        }
        RecursoTag other = (RecursoTag) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.RecursoTag[id=" + id + "]";
    }
}
