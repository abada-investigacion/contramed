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

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Relaciona la especialidad con el principio activo del que se compone
 * @author katsu
 */
@Entity
@Table(name = "PRINCIPIO_HAS_ESPECIALIDAD")
@NamedQueries({
    @NamedQuery(name = "PrincipioHasEspecialidad.findAll", query = "SELECT p FROM PrincipioHasEspecialidad p"),
    @NamedQuery(name = "PrincipioHasEspecialidad.findById", query = "SELECT p FROM PrincipioHasEspecialidad p WHERE p.id = :id"),
    @NamedQuery(name = "PrincipioHasEspecialidad.findByComposicion", query = "SELECT p FROM PrincipioHasEspecialidad p WHERE p.composicion = :composicion"),
    @NamedQuery(name = "PrincipioHasEspecialidad.findByIdUnidad", query = "SELECT p FROM PrincipioHasEspecialidad p WHERE p.idUnidad = :idUnidad")})
public class PrincipioHasEspecialidad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    /**
     *
     */
    @Column(name = "COMPOSICION")
    private String composicion;
    @Column(name = "ID_UNIDAD")
    private String idUnidad;
    /**
     * Especialidad
     */
    @JoinColumn(name = "CODIGO_ESPEC", referencedColumnName = "CODIGO")
    @ManyToOne
    private CatalogoMedicamentos codigoEspec;
    /**
     * Principio activo
     */
    @JoinColumn(name = "CODIGO_PRINCIPIO", referencedColumnName = "CODIGO")
    @ManyToOne
    private PrincipioActivo codigoPrincipio;

    public PrincipioHasEspecialidad() {
    }

    public PrincipioHasEspecialidad(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComposicion() {
        return composicion;
    }

    public void setComposicion(String composicion) {
        this.composicion = composicion;
    }

    public String getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(String idUnidad) {
        this.idUnidad = idUnidad;
    }

    public CatalogoMedicamentos getCodigoEspec() {
        return codigoEspec;
    }

    public void setCodigoEspec(CatalogoMedicamentos codigoEspec) {
        this.codigoEspec = codigoEspec;
    }

    public PrincipioActivo getCodigoPrincipio() {
        return codigoPrincipio;
    }

    public void setCodigoPrincipio(PrincipioActivo codigoPrincipio) {
        this.codigoPrincipio = codigoPrincipio;
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
        if (!(object instanceof PrincipioHasEspecialidad)) {
            return false;
        }
        PrincipioHasEspecialidad other = (PrincipioHasEspecialidad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.PrincipioHasEspecialidad[id=" + id + "]";
    }

}
