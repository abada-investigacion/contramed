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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Patrones de fecuencia
 * @author katsu
 */
@Entity
@Table(name = "FRECUENCIA_PREDEF")
@NamedQueries({
    @NamedQuery(name = "FrecuenciaPredef.findAll", query = "SELECT f FROM FrecuenciaPredef f"),
    @NamedQuery(name = "FrecuenciaPredef.findByIdTarea", query = "SELECT f FROM FrecuenciaPredef f WHERE f.idTarea = :idTarea"),
    @NamedQuery(name = "FrecuenciaPredef.findByTipoFrecuencia", query = "SELECT f FROM FrecuenciaPredef f WHERE f.tipoFrecuencia = :tipoFrecuencia"),
    @NamedQuery(name = "FrecuenciaPredef.findByNombre", query = "SELECT f FROM FrecuenciaPredef f WHERE f.nombre = :nombre")})
public class FrecuenciaPredef implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_TAREA")
    private BigDecimal idTarea;
    @Column(name = "TIPO_FRECUENCIA")
    private String tipoFrecuencia;
    @Column(name = "NOMBRE")
    private String nombre;
    @JsonIgnore
    @OneToMany(mappedBy = "frecuencia")
    private List<CatalogoMedicamentos> catalogoMedicamentosList;

    public FrecuenciaPredef() {
    }

    public FrecuenciaPredef(BigDecimal idTarea) {
        this.idTarea = idTarea;
    }

    public BigDecimal getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(BigDecimal idTarea) {
        this.idTarea = idTarea;
    }

    public String getTipoFrecuencia() {
        return tipoFrecuencia;
    }

    public void setTipoFrecuencia(String tipoFrecuencia) {
        this.tipoFrecuencia = tipoFrecuencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<CatalogoMedicamentos> getCatalogoMedicamentosList() {
        return catalogoMedicamentosList;
    }

    public void setCatalogoMedicamentosList(List<CatalogoMedicamentos> catalogoMedicamentosList) {
        this.catalogoMedicamentosList = catalogoMedicamentosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTarea != null ? idTarea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FrecuenciaPredef)) {
            return false;
        }
        FrecuenciaPredef other = (FrecuenciaPredef) object;
        if ((this.idTarea == null && other.idTarea != null) || (this.idTarea != null && !this.idTarea.equals(other.idTarea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.FrecuenciaPredef[idTarea=" + idTarea + "]";
    }

    public void addCatalogoMedicamentos(CatalogoMedicamentos catalogomedicamentos) {
        if (this.catalogoMedicamentosList == null) {
            this.setCatalogoMedicamentosList(new ArrayList<CatalogoMedicamentos>());
        }
        this.catalogoMedicamentosList.add(catalogomedicamentos);
        if (catalogomedicamentos.getFrecuencia() != this) {
            catalogomedicamentos.setFrecuencia(this);
        }
    }
}
