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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Datos del principio activo
 * @author katsu
 */
@Entity
@Table(name = "PRINCIPIO_ACTIVO")
@NamedQueries({
    @NamedQuery(name = "PrincipioActivo.findAll", query = "SELECT p FROM PrincipioActivo p"),
    @NamedQuery(name = "PrincipioActivo.findByCodigo", query = "SELECT p FROM PrincipioActivo p WHERE p.codigo = :codigo"),
    @NamedQuery(name = "PrincipioActivo.findByNombre", query = "SELECT p FROM PrincipioActivo p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "PrincipioActivo.findByAnulado", query = "SELECT p FROM PrincipioActivo p WHERE p.anulado = :anulado"),
    @NamedQuery(name = "PrincipioActivo.findByCodigoCa", query = "SELECT p FROM PrincipioActivo p WHERE p.codigoCa = :codigoCa"),
    @NamedQuery(name = "PrincipioActivo.findByComposicion", query = "SELECT p FROM PrincipioActivo p WHERE p.composicion = :composicion"),
    @NamedQuery(name = "PrincipioActivo.findByDenominacion", query = "SELECT p FROM PrincipioActivo p WHERE p.denominacion = :denominacion"),
    @NamedQuery(name = "PrincipioActivo.findByFormula", query = "SELECT p FROM PrincipioActivo p WHERE p.formula = :formula"),
    @NamedQuery(name = "PrincipioActivo.findByPeso", query = "SELECT p FROM PrincipioActivo p WHERE p.peso = :peso"),
    @NamedQuery(name = "PrincipioActivo.findByDosis", query = "SELECT p FROM PrincipioActivo p WHERE p.dosis = :dosis"),
    @NamedQuery(name = "PrincipioActivo.findByDosisSuperficie", query = "SELECT p FROM PrincipioActivo p WHERE p.dosisSuperficie = :dosisSuperficie"),
    @NamedQuery(name = "PrincipioActivo.findByDosisPeso", query = "SELECT p FROM PrincipioActivo p WHERE p.dosisPeso = :dosisPeso"),
    @NamedQuery(name = "PrincipioActivo.findByUDosis", query = "SELECT p FROM PrincipioActivo p WHERE p.uDosis = :uDosis"),
    @NamedQuery(name = "PrincipioActivo.findByUDosisSuperficie", query = "SELECT p FROM PrincipioActivo p WHERE p.uDosisSuperficie = :uDosisSuperficie"),
    @NamedQuery(name = "PrincipioActivo.findByUDosisPeso", query = "SELECT p FROM PrincipioActivo p WHERE p.uDosisPeso = :uDosisPeso"),
    @NamedQuery(name = "PrincipioActivo.findByVia", query = "SELECT p FROM PrincipioActivo p WHERE p.via = :via"),
    @NamedQuery(name = "PrincipioActivo.findByFrecuencia", query = "SELECT p FROM PrincipioActivo p WHERE p.frecuencia = :frecuencia"),
    @NamedQuery(name = "PrincipioActivo.findByAditivo", query = "SELECT p FROM PrincipioActivo p WHERE p.aditivo = :aditivo")})
public class PrincipioActivo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CODIGO")
    private String codigo;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "ANULADO")
    private String anulado;
    @Column(name = "CODIGO_CA")
    private String codigoCa;
    @Column(name = "COMPOSICION")
    private String composicion;
    @Column(name = "DENOMINACION")
    private String denominacion;
    @Column(name = "FORMULA")
    private String formula;
    @Column(name = "PESO")
    private String peso;
    @Column(name = "DOSIS")
    private String dosis;
    @Column(name = "DOSIS_SUPERFICIE")
    private String dosisSuperficie;
    @Column(name = "DOSIS_PESO")
    private String dosisPeso;
    @Column(name = "U_DOSIS")
    private String uDosis;
    @Column(name = "U_DOSIS_SUPERFICIE")
    private String uDosisSuperficie;
    @Column(name = "U_DOSIS_PESO")
    private String uDosisPeso;
    @Column(name = "VIA")
    private Long via;
    @Column(name = "FRECUENCIA")
    private Long frecuencia;
    @Column(name = "ADITIVO")
    private String aditivo;
    @JsonExclude
    @OneToMany(mappedBy = "codigoPrincipio")
    private List<PrincipioHasEspecialidad> principioHasEspecialidadList;
    @JsonExclude
    @OneToMany(mappedBy = "principioActivo")
    private List<OrderMedication> orderMedicationList;

    public PrincipioActivo() {
    }

    public PrincipioActivo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAnulado() {
        return anulado;
    }

    public void setAnulado(String anulado) {
        this.anulado = anulado;
    }

    public String getCodigoCa() {
        return codigoCa;
    }

    public void setCodigoCa(String codigoCa) {
        this.codigoCa = codigoCa;
    }

    public String getComposicion() {
        return composicion;
    }

    public void setComposicion(String composicion) {
        this.composicion = composicion;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getDosisSuperficie() {
        return dosisSuperficie;
    }

    public void setDosisSuperficie(String dosisSuperficie) {
        this.dosisSuperficie = dosisSuperficie;
    }

    public String getDosisPeso() {
        return dosisPeso;
    }

    public void setDosisPeso(String dosisPeso) {
        this.dosisPeso = dosisPeso;
    }

    public String getUDosis() {
        return uDosis;
    }

    public void setUDosis(String uDosis) {
        this.uDosis = uDosis;
    }

    public String getUDosisSuperficie() {
        return uDosisSuperficie;
    }

    public void setUDosisSuperficie(String uDosisSuperficie) {
        this.uDosisSuperficie = uDosisSuperficie;
    }

    public String getUDosisPeso() {
        return uDosisPeso;
    }

    public void setUDosisPeso(String uDosisPeso) {
        this.uDosisPeso = uDosisPeso;
    }

    public Long getVia() {
        return via;
    }

    public void setVia(Long via) {
        this.via = via;
    }

    public Long getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Long frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getAditivo() {
        return aditivo;
    }

    public void setAditivo(String aditivo) {
        this.aditivo = aditivo;
    }

    public List<PrincipioHasEspecialidad> getPrincipioHasEspecialidadList() {
        return principioHasEspecialidadList;
    }

    public void setPrincipioHasEspecialidadList(List<PrincipioHasEspecialidad> principioHasEspecialidadList) {
        this.principioHasEspecialidadList = principioHasEspecialidadList;
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
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrincipioActivo)) {
            return false;
        }
        PrincipioActivo other = (PrincipioActivo) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.PrincipioActivo[codigo=" + codigo + "]";
    }

    public void addPrincipioHasEspecialidad(PrincipioHasEspecialidad principiohasespecialidad) {
        if (this.principioHasEspecialidadList == null) {
            this.setPrincipioHasEspecialidadList(new ArrayList<PrincipioHasEspecialidad>());
        }
        this.principioHasEspecialidadList.add(principiohasespecialidad);
        if (principiohasespecialidad.getCodigoPrincipio() != this) {
            principiohasespecialidad.setCodigoPrincipio(this);
        }

    }

    public void addPrincipioActivo(OrderMedication orderMedication) {
        if (this.orderMedicationList == null) {
            this.setOrderMedicationList(new ArrayList<OrderMedication>());
        }
        this.orderMedicationList.add(orderMedication);
        if (orderMedication.getPrincipioActivo() != this) {
            orderMedication.setPrincipioActivo(this);
        }
    }
}
