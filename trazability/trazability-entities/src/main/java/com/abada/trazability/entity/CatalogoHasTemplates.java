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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Relaciona las especialidades con las plantillas de las etiquetas de las dosis
 * @author katsu
 */
@Entity
@Table(name = "CATALOGO_HAS_TEMPLATES")
@NamedQueries({
    @NamedQuery(name = "CatalogoHasTemplates.findAll", query = "SELECT c FROM CatalogoHasTemplates c"),
    @NamedQuery(name = "CatalogoHasTemplates.findByCatalogoCodigo", query = "SELECT c FROM CatalogoHasTemplates c WHERE c.catalogoCodigo = :catalogoCodigo")})
public class CatalogoHasTemplates implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CATALOGO_CODIGO")
    private String catalogoCodigo;
    /**
     * Especialidad
     */
    @JoinColumn(name = "CATALOGO_CODIGO", referencedColumnName = "CODIGO", nullable = false, insertable = true, updatable = true)
    @OneToOne(optional = false)
    private CatalogoMedicamentos catalogoMedicamentos;
    /**
     * Plantilla
     */
    @JoinColumn(name = "TEMPLATES_ID", referencedColumnName = "IDTEMPLATES_MEDICATION", nullable = false, insertable = true, updatable = true)
    @ManyToOne(optional = false)
    private TemplatesMedication templatesId;

    public CatalogoHasTemplates() {
    }

    public CatalogoHasTemplates(String catalogoCodigo) {
        this.catalogoCodigo = catalogoCodigo;
    }

    public String getCatalogoCodigo() {
        return catalogoCodigo;
    }

    public void setCatalogoCodigo(String catalogoCodigo) {
        this.catalogoCodigo = catalogoCodigo;
    }

    public CatalogoMedicamentos getCatalogoMedicamentos() {
        return catalogoMedicamentos;
    }

    public void setCatalogoMedicamentos(CatalogoMedicamentos catalogoMedicamentos1) {
        this.catalogoMedicamentos = catalogoMedicamentos1;
    }

    public TemplatesMedication getTemplatesId() {
        return templatesId;
    }

    public void setTemplatesId(TemplatesMedication templatesId) {
        this.templatesId = templatesId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (catalogoCodigo != null ? catalogoCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CatalogoHasTemplates)) {
            return false;
        }
        CatalogoHasTemplates other = (CatalogoHasTemplates) object;
        if ((this.catalogoCodigo == null && other.catalogoCodigo != null) || (this.catalogoCodigo != null && !this.catalogoCodigo.equals(other.catalogoCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.CatalogoHasTemplates[catalogoCodigo=" + catalogoCodigo + "]";
    }
}
