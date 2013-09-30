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

/**
 * tabla que relaciona el EAN13 y el fabricante con la especialidad del medicamento
 * @author david
 */
@Entity
@Table(name = "medication_additional_inf")
@NamedQueries({
    @NamedQuery(name = "MedicationAdditionalInformation.findAll", query = "SELECT m FROM MedicationAdditionalInformation m"),
    @NamedQuery(name = "MedicationAdditionalInformation.findByIdmedicationAditionalInformation", query = "SELECT m FROM MedicationAdditionalInformation m WHERE m.idmedicationAditionalInformation = :idmedicationAditionalInformation"),
    @NamedQuery(name = "MedicationAdditionalInformation.findByEan", query = "SELECT m FROM MedicationAdditionalInformation m WHERE m.ean = :ean"),
    @NamedQuery(name = "MedicationAdditionalInformation.findByManufacturer", query = "SELECT m FROM MedicationAdditionalInformation m WHERE m.manufacturer = :manufacturer")})
@Deprecated
public class MedicationAdditionalInformation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "idmedication_aditional_inf", nullable = false)
    private Long idmedicationAditionalInformation;
    /**
     * valor del EAN13
     */
    @Column(name = "ean", length = 13)
    private String ean;
    /**
     * Fabricante
     */
    @Column(name = "manufacturer", length = 45)
    private String manufacturer;
    /**
     * Especialidad con la que esta relacionada
     */
    @JoinColumn(name = "catalogo_medicamentos_CODIGO", referencedColumnName = "CODIGO", nullable = false)
    @ManyToOne(optional = false)
    private CatalogoMedicamentos catalogomedicamentosCODIGO;

    public MedicationAdditionalInformation() {
    }

    public MedicationAdditionalInformation(Long idmedicationAditionalInformation) {
        this.idmedicationAditionalInformation = idmedicationAditionalInformation;
    }

    public Long getIdmedicationAditionalInformation() {
        return idmedicationAditionalInformation;
    }

    public void setIdmedicationAditionalInformation(Long idmedicationAditionalInformation) {
        this.idmedicationAditionalInformation = idmedicationAditionalInformation;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public CatalogoMedicamentos getCatalogomedicamentosCODIGO() {
        return catalogomedicamentosCODIGO;
    }

    public void setCatalogomedicamentosCODIGO(CatalogoMedicamentos catalogomedicamentosCODIGO) {
        this.catalogomedicamentosCODIGO = catalogomedicamentosCODIGO;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmedicationAditionalInformation != null ? idmedicationAditionalInformation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MedicationAdditionalInformation)) {
            return false;
        }
        MedicationAdditionalInformation other = (MedicationAdditionalInformation) object;
        if ((this.idmedicationAditionalInformation == null && other.idmedicationAditionalInformation != null) || (this.idmedicationAditionalInformation != null && !this.idmedicationAditionalInformation.equals(other.idmedicationAditionalInformation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.MedicationAdditionalInformation[idmedicationAditionalInformation=" + idmedicationAditionalInformation + "]";
    }

}
