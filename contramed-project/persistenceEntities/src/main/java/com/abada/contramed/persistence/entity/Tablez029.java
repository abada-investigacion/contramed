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
import javax.persistence.UniqueConstraint;

/**
 * Tipo de identificadores:
 * <ul>
 * <li>DNI</li>
 * <li>Pasaporte</li>
 * <li>Tarjeta residencial</li>
 * <li>Numero de Seguridad socila</li>
 * <li>CIP SNS</li>
 * <li>CIP autonomico</li>
 * <li>Numero de historia clinica</li>
 * <li>CIP</li>
 * </ul>
 * @author david
 */
@Entity
@Table(name = "tablez029", uniqueConstraints = {@UniqueConstraint(columnNames = {"code"})})
@NamedQueries({
    @NamedQuery(name = "Tablez029.findAll", query = "SELECT t FROM Tablez029 t"),
    @NamedQuery(name = "Tablez029.findById", query = "SELECT t FROM Tablez029 t WHERE t.id = :id"),
    @NamedQuery(name = "Tablez029.findByCode", query = "SELECT t FROM Tablez029 t WHERE t.code = :code"),
    @NamedQuery(name = "Tablez029.findByDetails", query = "SELECT t FROM Tablez029 t WHERE t.details = :details")})
public class Tablez029 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    /**
     * Codigo abreviado
     */
    @Basic(optional = false)
    @Column(name = "code", nullable = false, length = 5)
    private String code;
    /**
     * Nombre completo
     */
    @Column(name = "details", length = 50)
    private String details;
    /**
     * Listado de identificaciones de este tipo
     */
    @JsonExclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "type", fetch = FetchType.LAZY)
    private List<PatientId> patientIdList;

    public Tablez029() {
    }

    public Tablez029(Integer id) {
        this.id = id;
    }

    public Tablez029(Integer id, String code) {
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

    public List<PatientId> getPatientIdList() {
        return patientIdList;
    }

    public void setPatientIdList(List<PatientId> patientIdList) {
        this.patientIdList = patientIdList;
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
        if (!(object instanceof Tablez029)) {
            return false;
        }
        Tablez029 other = (Tablez029) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.Tablez029[id=" + id + "]";
    }

    public void addPatientId(PatientId patientid) {
        if (this.patientIdList == null) {
            this.setPatientIdList(new ArrayList<PatientId>());
        }
        this.patientIdList.add(patientid);
        if (patientid.getType() != this) {
            patientid.setType(this);
        }
    }
}
