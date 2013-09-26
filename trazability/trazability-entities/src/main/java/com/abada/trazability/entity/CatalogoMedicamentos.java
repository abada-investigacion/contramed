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
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Datos de una especialidad
 * @author david
 */
@Entity
@Table(name = "catalogo_medicamentos")
@NamedQueries({
    @NamedQuery(name = "CatalogoMedicamentos.findAll", query = "SELECT c FROM CatalogoMedicamentos c"),
    @NamedQuery(name = "CatalogoMedicamentos.findByCodigo", query = "SELECT c FROM CatalogoMedicamentos c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "CatalogoMedicamentos.findByAnulado", query = "SELECT c FROM CatalogoMedicamentos c WHERE c.anulado = :anulado"),
    @NamedQuery(name = "CatalogoMedicamentos.findByBioequivalente", query = "SELECT c FROM CatalogoMedicamentos c WHERE c.bioequivalente = :bioequivalente"),
    @NamedQuery(name = "CatalogoMedicamentos.findByCaducidad", query = "SELECT c FROM CatalogoMedicamentos c WHERE c.caducidad = :caducidad"),
    @NamedQuery(name = "CatalogoMedicamentos.findByCaracteristicas", query = "SELECT c FROM CatalogoMedicamentos c WHERE c.caracteristicas = :caracteristicas"),
    @NamedQuery(name = "CatalogoMedicamentos.findByCodGrupo", query = "SELECT c FROM CatalogoMedicamentos c WHERE c.codGrupo = :codGrupo"),
    @NamedQuery(name = "CatalogoMedicamentos.findByCodLab", query = "SELECT c FROM CatalogoMedicamentos c WHERE c.codLab = :codLab"),
    @NamedQuery(name = "CatalogoMedicamentos.findByConservacion", query = "SELECT c FROM CatalogoMedicamentos c WHERE c.conservacion = :conservacion"),
    @NamedQuery(name = "CatalogoMedicamentos.findByDispensacion", query = "SELECT c FROM CatalogoMedicamentos c WHERE c.dispensacion = :dispensacion"),
    @NamedQuery(name = "CatalogoMedicamentos.findByEspecialControl", query = "SELECT c FROM CatalogoMedicamentos c WHERE c.especialControl = :especialControl"),
    @NamedQuery(name = "CatalogoMedicamentos.findByFechaAlta", query = "SELECT c FROM CatalogoMedicamentos c WHERE c.fechaAlta = :fechaAlta"),
    @NamedQuery(name = "CatalogoMedicamentos.findByFechaBaja", query = "SELECT c FROM CatalogoMedicamentos c WHERE c.fechaBaja = :fechaBaja"),
    @NamedQuery(name = "CatalogoMedicamentos.findByFicha", query = "SELECT c FROM CatalogoMedicamentos c WHERE c.ficha = :ficha"),
    @NamedQuery(name = "CatalogoMedicamentos.findByFormaFarma", query = "SELECT c FROM CatalogoMedicamentos c WHERE c.formaFarma = :formaFarma"),
    @NamedQuery(name = "CatalogoMedicamentos.findByGenerico", query = "SELECT c FROM CatalogoMedicamentos c WHERE c.generico = :generico"),
    @NamedQuery(name = "CatalogoMedicamentos.findByLargaDuracion", query = "SELECT c FROM CatalogoMedicamentos c WHERE c.largaDuracion = :largaDuracion"),
    @NamedQuery(name = "CatalogoMedicamentos.findByNombre", query = "SELECT c FROM CatalogoMedicamentos c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "CatalogoMedicamentos.findByNombreLab", query = "SELECT c FROM CatalogoMedicamentos c WHERE c.nombreLab = :nombreLab"),
    @NamedQuery(name = "CatalogoMedicamentos.findByPrecioRef", query = "SELECT c FROM CatalogoMedicamentos c WHERE c.precioRef = :precioRef"),
    @NamedQuery(name = "CatalogoMedicamentos.findByPvp", query = "SELECT c FROM CatalogoMedicamentos c WHERE c.pvp = :pvp"),
    @NamedQuery(name = "CatalogoMedicamentos.findByRegimen", query = "SELECT c FROM CatalogoMedicamentos c WHERE c.regimen = :regimen"),
    @NamedQuery(name = "CatalogoMedicamentos.findByDosis", query = "SELECT c FROM CatalogoMedicamentos c WHERE c.dosis = :dosis"),
    @NamedQuery(name = "CatalogoMedicamentos.findByDosisSuperficie", query = "SELECT c FROM CatalogoMedicamentos c WHERE c.dosisSuperficie = :dosisSuperficie"),
    @NamedQuery(name = "CatalogoMedicamentos.findByDosisPeso", query = "SELECT c FROM CatalogoMedicamentos c WHERE c.dosisPeso = :dosisPeso"),
    @NamedQuery(name = "CatalogoMedicamentos.findByUDosis", query = "SELECT c FROM CatalogoMedicamentos c WHERE c.uDosis = :uDosis"),
    @NamedQuery(name = "CatalogoMedicamentos.findByUDosisSuperficie", query = "SELECT c FROM CatalogoMedicamentos c WHERE c.uDosisSuperficie = :uDosisSuperficie"),
    @NamedQuery(name = "CatalogoMedicamentos.findByUDosisPeso", query = "SELECT c FROM CatalogoMedicamentos c WHERE c.uDosisPeso = :uDosisPeso"),
    @NamedQuery(name = "CatalogoMedicamentos.findByDiluyente", query = "SELECT c FROM CatalogoMedicamentos c WHERE c.diluyente = :diluyente"),
    @NamedQuery(name = "CatalogoMedicamentos.findByAditivo", query = "SELECT c FROM CatalogoMedicamentos c WHERE c.aditivo = :aditivo"),
    @NamedQuery(name = "CatalogoMedicamentos.findByDosisDiluyente", query = "SELECT c FROM CatalogoMedicamentos c WHERE c.dosisDiluyente = :dosisDiluyente")})
public class CatalogoMedicamentos implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Codigo nacional de la especialidad
     */
    @Id
    @Basic(optional = false)
    @Column(name = "CODIGO", nullable = false, length = 50, insertable = true, updatable = true)
    private String codigo;
    @Column(name = "ANULADO", length = 1)
    private String anulado;
    @Column(name = "BIOEQUIVALENTE", length = 1)
    private String bioequivalente;
    @Column(name = "CADUCIDAD")
    @Temporal(TemporalType.DATE)
    private Date caducidad;
    @Column(name = "CARACTERISTICAS", length = 10)
    private String caracteristicas;
    @Column(name = "COD_GRUPO", length = 10)
    private String codGrupo;
    @Column(name = "COD_LAB", length = 255)
    private String codLab;
    @Column(name = "CONSERVACION", length = 10)
    private String conservacion;
    @Column(name = "DISPENSACION", length = 10)
    private String dispensacion;
    @Column(name = "ESPECIAL_CONTROL", length = 1)
    private String especialControl;
    @Column(name = "FECHA_ALTA")
    @Temporal(TemporalType.DATE)
    private Date fechaAlta;
    @Column(name = "FECHA_BAJA")
    @Temporal(TemporalType.DATE)
    private Date fechaBaja;
    @Column(name = "FICHA")
    private BigInteger ficha;
    @Column(name = "FORMA_FARMA", length = 1000)
    private String formaFarma;
    @Column(name = "GENERICO", length = 1)
    private String generico;
    @Column(name = "LARGA_DURACION", length = 1)
    private String largaDuracion;
    /**
     * Nombre de la especialidad
     */
    @Column(name = "NOMBRE", length = 1000)
    private String nombre;
    @Column(name = "NOMBRE_LAB", length = 1000)
    private String nombreLab;
    @Column(name = "PRECIO_REF", length = 50)
    private String precioRef;
    @Column(name = "PVP", length = 50)
    private String pvp;
    @Column(name = "REGIMEN", length = 10)
    private String regimen;
    @Column(name = "DOSIS", precision = 10, scale = 3)
    private BigDecimal dosis;
    @Column(name = "DOSIS_SUPERFICIE", precision = 10, scale = 3)
    private BigDecimal dosisSuperficie;
    @Column(name = "DOSIS_PESO", precision = 10, scale = 3)
    private BigDecimal dosisPeso;
    @Column(name = "U_DOSIS")
    private Long uDosis;
    @Column(name = "U_DOSIS_SUPERFICIE")
    private Long uDosisSuperficie;
    @Column(name = "U_DOSIS_PESO")
    private Long uDosisPeso;
    @Basic(optional = false)
    @Column(name = "DILUYENTE", nullable = false, length = 1)
    private String diluyente;
    @Basic(optional = false)
    @Column(name = "ADITIVO", nullable = false, length = 1)
    private String aditivo;
    @Column(name = "DOSIS_DILUYENTE", precision = 10, scale = 3)
    private BigDecimal dosisDiluyente;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "catalogomedicamentosCODIGO", fetch = FetchType.LAZY)
    private List<Dose> doseList;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "catalogoMedicamentos")
    private CatalogoHasTemplates catalogoHasTemplates;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "catalogomedicamentosCODIGO", fetch = FetchType.LAZY)
    private List<OrderMedication> orderMedicationList;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "catalogomedicamentosCODIGO", fetch = FetchType.LAZY)
    private List<MedicationAdditionalInformation> medicationAdditionalInformationList;
    @JsonIgnore
    @OneToMany(mappedBy = "codigoEspec")
    private List<PrincipioHasEspecialidad> principioHasEspecialidadList;
    @JoinColumn(name = "FRECUENCIA", referencedColumnName = "ID_TAREA",nullable=true)
    @ManyToOne
    private FrecuenciaPredef frecuencia;
    @JoinColumn(name = "VIA", referencedColumnName = "ID",nullable=true)
    @ManyToOne
    private Table0162 via;

    public CatalogoMedicamentos() {
    }

    public CatalogoMedicamentos(String codigo) {
        this.codigo = codigo;
    }

    public CatalogoMedicamentos(String codigo, String diluyente, String aditivo) {
        this.codigo = codigo;
        this.diluyente = diluyente;
        this.aditivo = aditivo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getAnulado() {
        return anulado;
    }

    public void setAnulado(String anulado) {
        this.anulado = anulado;
    }

    public String getBioequivalente() {
        return bioequivalente;
    }

    public void setBioequivalente(String bioequivalente) {
        this.bioequivalente = bioequivalente;
    }

    public Date getCaducidad() {
        return caducidad;
    }

    public void setCaducidad(Date caducidad) {
        this.caducidad = caducidad;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public String getCodGrupo() {
        return codGrupo;
    }

    public void setCodGrupo(String codGrupo) {
        this.codGrupo = codGrupo;
    }

    public String getCodLab() {
        return codLab;
    }

    public void setCodLab(String codLab) {
        this.codLab = codLab;
    }

    public String getConservacion() {
        return conservacion;
    }

    public void setConservacion(String conservacion) {
        this.conservacion = conservacion;
    }

    public String getDispensacion() {
        return dispensacion;
    }

    public void setDispensacion(String dispensacion) {
        this.dispensacion = dispensacion;
    }

    public String getEspecialControl() {
        return especialControl;
    }

    public void setEspecialControl(String especialControl) {
        this.especialControl = especialControl;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public BigInteger getFicha() {
        return ficha;
    }

    public void setFicha(BigInteger ficha) {
        this.ficha = ficha;
    }

    public String getFormaFarma() {
        return formaFarma;
    }

    public void setFormaFarma(String formaFarma) {
        this.formaFarma = formaFarma;
    }

    public String getGenerico() {
        return generico;
    }

    public void setGenerico(String generico) {
        this.generico = generico;
    }

    public String getLargaDuracion() {
        return largaDuracion;
    }

    public void setLargaDuracion(String largaDuracion) {
        this.largaDuracion = largaDuracion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreLab() {
        return nombreLab;
    }

    public void setNombreLab(String nombreLab) {
        this.nombreLab = nombreLab;
    }

    public String getPrecioRef() {
        return precioRef;
    }

    public void setPrecioRef(String precioRef) {
        this.precioRef = precioRef;
    }

    public String getPvp() {
        return pvp;
    }

    public void setPvp(String pvp) {
        this.pvp = pvp;
    }

    public String getRegimen() {
        return regimen;
    }

    public void setRegimen(String regimen) {
        this.regimen = regimen;
    }

    public BigDecimal getDosis() {
        return dosis;
    }

    public void setDosis(BigDecimal dosis) {
        this.dosis = dosis;
    }

    public BigDecimal getDosisSuperficie() {
        return dosisSuperficie;
    }

    public void setDosisSuperficie(BigDecimal dosisSuperficie) {
        this.dosisSuperficie = dosisSuperficie;
    }

    public BigDecimal getDosisPeso() {
        return dosisPeso;
    }

    public void setDosisPeso(BigDecimal dosisPeso) {
        this.dosisPeso = dosisPeso;
    }

    public Long getUDosis() {
        return uDosis;
    }

    public void setUDosis(Long uDosis) {
        this.uDosis = uDosis;
    }

    public CatalogoHasTemplates getCatalogoHasTemplates() {
        return catalogoHasTemplates;
    }

    public void setCatalogoHasTemplates(CatalogoHasTemplates catalogoHasTemplates) {
        this.catalogoHasTemplates = catalogoHasTemplates;
    }

    public Long getUDosisSuperficie() {
        return uDosisSuperficie;
    }

    public void setUDosisSuperficie(Long uDosisSuperficie) {
        this.uDosisSuperficie = uDosisSuperficie;
    }

    public Long getUDosisPeso() {
        return uDosisPeso;
    }

    public void setUDosisPeso(Long uDosisPeso) {
        this.uDosisPeso = uDosisPeso;
    }

    public FrecuenciaPredef getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(FrecuenciaPredef frecuencia) {
        this.frecuencia = frecuencia;
    }

    public List<PrincipioHasEspecialidad> getPrincipioHasEspecialidadList() {
        return principioHasEspecialidadList;
    }

    public void setPrincipioHasEspecialidadList(List<PrincipioHasEspecialidad> principioHasEspecialidadList) {
        this.principioHasEspecialidadList = principioHasEspecialidadList;
    }

    public Table0162 getVia() {
        return via;
    }

    public void setVia(Table0162 via) {
        this.via = via;
    }

    public String getDiluyente() {
        return diluyente;
    }

    public void setDiluyente(String diluyente) {
        this.diluyente = diluyente;
    }

    public String getAditivo() {
        return aditivo;
    }

    public void setAditivo(String aditivo) {
        this.aditivo = aditivo;
    }

    public BigDecimal getDosisDiluyente() {
        return dosisDiluyente;
    }

    public void setDosisDiluyente(BigDecimal dosisDiluyente) {
        this.dosisDiluyente = dosisDiluyente;
    }

    public List<Dose> getDoseList() {
        return doseList;
    }

    public void setDoseList(List<Dose> doseList) {
        this.doseList = doseList;
    }

    public List<OrderMedication> getOrderMedicationList() {
        return orderMedicationList;
    }

    public void setOrderMedicationList(List<OrderMedication> orderMedicationList) {
        this.orderMedicationList = orderMedicationList;
    }

    public List<MedicationAdditionalInformation> getMedicationAdditionalInformationList() {
        return medicationAdditionalInformationList;
    }

    public void setMedicationAdditionalInformationList(List<MedicationAdditionalInformation> medicationAdditionalInformationList) {
        this.medicationAdditionalInformationList = medicationAdditionalInformationList;
    }

    public Long getuDosis() {
        return uDosis;
    }

    public void setuDosis(Long uDosis) {
        this.uDosis = uDosis;
    }

    public Long getuDosisPeso() {
        return uDosisPeso;
    }

    public void setuDosisPeso(Long uDosisPeso) {
        this.uDosisPeso = uDosisPeso;
    }

    public Long getuDosisSuperficie() {
        return uDosisSuperficie;
    }

    public void setuDosisSuperficie(Long uDosisSuperficie) {
        this.uDosisSuperficie = uDosisSuperficie;
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
        if (!(object instanceof CatalogoMedicamentos)) {
            return false;
        }
        CatalogoMedicamentos other = (CatalogoMedicamentos) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.CatalogoMedicamentos[codigo=" + codigo + "]";
    }

    public void addDose(Dose dose) {
        if (this.doseList == null) {
            this.setDoseList(new ArrayList<Dose>());
        }
        this.doseList.add(dose);
        if (dose.getCatalogomedicamentosCODIGO() != this) {
            dose.setCatalogomedicamentosCODIGO(this);
        }
    }

    public void addOrderMedication(OrderMedication ordermedication) {
        if (this.orderMedicationList == null) {
            this.setOrderMedicationList(new ArrayList<OrderMedication>());
        }
        this.orderMedicationList.add(ordermedication);
        if (ordermedication.getCatalogomedicamentosCODIGO() != this) {
            ordermedication.setCatalogomedicamentosCODIGO(this);
        }
    }

    public void addMedicationAdditionalInformation(MedicationAdditionalInformation medicationadditionalinformation) {
        if (this.medicationAdditionalInformationList == null) {
            this.setMedicationAdditionalInformationList(new ArrayList<MedicationAdditionalInformation>());
        }
        this.medicationAdditionalInformationList.add(medicationadditionalinformation);
        if (medicationadditionalinformation.getCatalogomedicamentosCODIGO() != this) {
            medicationadditionalinformation.setCatalogomedicamentosCODIGO(this);
        }
    }

    public void addPrincipioHasEspecialidad(PrincipioHasEspecialidad principiohasespecialidad) {
        if (this.principioHasEspecialidadList == null) {
            this.setPrincipioHasEspecialidadList(new ArrayList<PrincipioHasEspecialidad>());
        }
        this.principioHasEspecialidadList.add(principiohasespecialidad);
        if (principiohasespecialidad.getCodigoEspec() != this) {
            principiohasespecialidad.setCodigoEspec(this);
        }
    }
}
