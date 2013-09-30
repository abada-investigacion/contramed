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

/**
 * Plantillas de etiquetas. Las platillas estan formadas por 2 ficheros, uno de
 * estilos y el otro con el formato de la plantilla.
 * @author david
 */
@Entity
@Table(name = "templates_medication")
@NamedQueries({
    @NamedQuery(name = "TemplatesMedication.findAll", query = "SELECT t FROM TemplatesMedication t"),
    @NamedQuery(name = "TemplatesMedication.findByIdtemplatesMedication", query = "SELECT t FROM TemplatesMedication t WHERE t.idtemplatesMedication = :idtemplatesMedication"),
    @NamedQuery(name = "TemplatesMedication.findByTemplate", query = "SELECT t FROM TemplatesMedication t WHERE t.template = :template"),
    @NamedQuery(name = "TemplatesMedication.findByPathTemplate", query = "SELECT t FROM TemplatesMedication t WHERE t.pathTemplate = :pathTemplate")})
public class TemplatesMedication implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "IDTEMPLATES_MEDICATION")
    private Long idtemplatesMedication;
    /**
     * Nombre de la plantilla
     */
    @Basic(optional = false)
    @Column(name = "TEMPLATE")
    private String template;
    /**
     * UUID bajo el que esta almacenado el fichero de plantilla
     */
    @Basic(optional = false)
    @Column(name = "PATH_TEMPLATE")
    private String pathTemplate;
    /**
     * UUID bajo el que esta almacenado el fichero de estilos
     */
    @Basic(optional = false)
    @Column(name = "PATH_STYLE")
    private String pathStyle;
    /**
     * Listado de Especialidades que usan esta plantilla
     */
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "templatesId",fetch=FetchType.LAZY)
    private List<CatalogoHasTemplates> catalogoHasTemplatesList;

    public TemplatesMedication() {
    }

    public TemplatesMedication(Long idtemplatesMedication) {
        this.idtemplatesMedication = idtemplatesMedication;
    }

    public TemplatesMedication(Long idtemplatesMedication, String template) {
        this.idtemplatesMedication = idtemplatesMedication;
        this.template = template;        
    }

    public String getPathStyle() {
        return pathStyle;
    }

    public void setPathStyle(String pathStyle) {
        this.pathStyle = pathStyle;
    }

    public String getPathTemplate() {
        return pathTemplate;
    }

    public void setPathTemplate(String pathTemplate) {
        this.pathTemplate = pathTemplate;
    }

    public Long getIdtemplatesMedication() {
        return idtemplatesMedication;
    }

    public void setIdtemplatesMedication(Long idtemplatesMedication) {
        this.idtemplatesMedication = idtemplatesMedication;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public List<CatalogoHasTemplates> getCatalogoHasTemplatesList() {
        return catalogoHasTemplatesList;
    }

    public void setCatalogoHasTemplatesList(List<CatalogoHasTemplates> catalogoHasTemplatesList) {
        this.catalogoHasTemplatesList = catalogoHasTemplatesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtemplatesMedication != null ? idtemplatesMedication.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TemplatesMedication)) {
            return false;
        }
        TemplatesMedication other = (TemplatesMedication) object;
        if ((this.idtemplatesMedication == null && other.idtemplatesMedication != null) || (this.idtemplatesMedication != null && !this.idtemplatesMedication.equals(other.idtemplatesMedication))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.TemplatesMedication[idtemplatesMedication=" + idtemplatesMedication + "]";
    }

    public void addCatalogoHasTemplates(CatalogoHasTemplates catalogoHasTemplates) {
        if (this.catalogoHasTemplatesList == null) {
            this.setCatalogoHasTemplatesList(new ArrayList<CatalogoHasTemplates>());
        }
        this.catalogoHasTemplatesList.add(catalogoHasTemplates);
        if (catalogoHasTemplates.getTemplatesId() != this) {
            catalogoHasTemplates.setTemplatesId(this);
        }
    }


}
