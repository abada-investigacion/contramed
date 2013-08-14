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
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Datos de la cama/recurso
 * @author david
 */
@Entity
@Table(name = "recurso")
@NamedQueries({
    @NamedQuery(name = "Recurso.findAll", query = "SELECT r FROM Recurso r order by r.nr"),
    @NamedQuery(name = "Recurso.findByEstadoRecurso", query = "SELECT r FROM Recurso r WHERE r.estadoRecurso = :estadoRecurso"),
    @NamedQuery(name = "Recurso.findByFechaCreacion", query = "SELECT r FROM Recurso r WHERE r.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Recurso.findByIdRecurso", query = "SELECT r FROM Recurso r WHERE r.idRecurso = :idRecurso"),
    @NamedQuery(name = "Recurso.findByLocalizador", query = "SELECT r FROM Recurso r WHERE r.localizador = :localizador"),
    @NamedQuery(name = "Recurso.findByNr", query = "SELECT r FROM Recurso r WHERE r.nr = :nr"),
    @NamedQuery(name = "Recurso.findByRuta", query = "SELECT r FROM Recurso r WHERE r.ruta = :ruta"),
    @NamedQuery(name = "Recurso.findByTipoRecurso", query = "SELECT r FROM Recurso r WHERE r.tipoRecurso = :tipoRecurso"),
    @NamedQuery(name = "Recurso.findByDescripcion", query = "SELECT r FROM Recurso r WHERE r.descripcion = :descripcion"),
    @NamedQuery(name = "Recurso.findByEmpResponsable", query = "SELECT r FROM Recurso r WHERE r.empResponsable = :empResponsable"),
    @NamedQuery(name = "Recurso.findByFechaHabilitacion", query = "SELECT r FROM Recurso r WHERE r.fechaHabilitacion = :fechaHabilitacion"),
    @NamedQuery(name = "Recurso.findByMotivoDesh", query = "SELECT r FROM Recurso r WHERE r.motivoDesh = :motivoDesh"),
    @NamedQuery(name = "Recurso.findByServiciosRecurso", query = "SELECT r FROM Recurso r WHERE r.serviciosRecurso = :serviciosRecurso"),
    @NamedQuery(name = "Recurso.findByServResponsable", query = "SELECT r FROM Recurso r WHERE r.servResponsable = :servResponsable"),
    @NamedQuery(name = "Recurso.findByUbicacionRecurso", query = "SELECT r FROM Recurso r WHERE r.ubicacionRecurso = :ubicacionRecurso"),
    @NamedQuery(name = "Recurso.findByObservaciones", query = "SELECT r FROM Recurso r WHERE r.observaciones = :observaciones"),
    @NamedQuery(name = "Recurso.findByClaseRecurso", query = "SELECT r FROM Recurso r WHERE r.claseRecurso = :claseRecurso"),
    @NamedQuery(name = "Recurso.findByTipoDesh", query = "SELECT r FROM Recurso r WHERE r.tipoDesh = :tipoDesh"),
    @NamedQuery(name = "Recurso.findByIdPadre", query = "SELECT r FROM Recurso r WHERE r.idPadre = :idPadre"),
    @NamedQuery(name = "Recurso.findByEliminado", query = "SELECT r FROM Recurso r WHERE r.eliminado = :eliminado"),
    @NamedQuery(name = "Recurso.findByIdEntidad", query = "SELECT r FROM Recurso r WHERE r.idEntidad = :idEntidad"),
    @NamedQuery(name = "Recurso.findByAdmisible", query = "SELECT r FROM Recurso r WHERE r.admisible = :admisible")})
public class Recurso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "ESTADO_RECURSO", nullable = false, length = 20)
    private String estadoRecurso;
    @Basic(optional = false)
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_RECURSO", nullable = false)
    private Long idRecurso;
    @Basic(optional = false)
    @Column(name = "LOCALIZADOR", nullable = false, length = 1024)
    private String localizador;
    @Basic(optional = false)
    @Column(name = "NR", nullable = false, length = 50)
    private String nr;
    @Basic(optional = false)
    @Column(name = "RUTA", nullable = false, length = 1024)
    private String ruta;
    @Basic(optional = false)
    @Column(name = "TIPO_RECURSO", nullable = false, length = 20)
    private String tipoRecurso;
    @Column(name = "DESCRIPCION", length = 150)
    private String descripcion;
    @Column(name = "EMP_RESPONSABLE", length = 50)
    private String empResponsable;
    @Column(name = "FECHA_HABILITACION")
    @Temporal(TemporalType.DATE)
    private Date fechaHabilitacion;
    @Column(name = "MOTIVO_DESH")
    private Long motivoDesh;
    @Column(name = "SERVICIOS_RECURSO_", length = 2000)
    private String serviciosRecurso;
    @Column(name = "SERV_RESPONSABLE", length = 1024)
    private String servResponsable;
    @Column(name = "UBICACION_RECURSO", length = 20)
    private String ubicacionRecurso;
    @Column(name = "OBSERVACIONES", length = 150)
    private String observaciones;
    @Basic(optional = false)
    @Column(name = "CLASE_RECURSO", nullable = false, length = 30)
    private String claseRecurso;
    @Column(name = "TIPO_DESH", length = 30)
    private String tipoDesh;
    @Column(name = "ID_PADRE")
    private Long idPadre;
    @Column(name = "ELIMINADO", length = 1)
    private String eliminado;
    @Column(name = "ID_ENTIDAD", length = 10)
    private String idEntidad;
    @Basic(optional = false)
    @Column(name = "ADMISIBLE", nullable = false, length = 1)
    private String admisible;
    @JsonExclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recurso", fetch = FetchType.LAZY)
    private List<RecursoTag> recursoTagList;
    @JsonExclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bed", fetch = FetchType.LAZY)
    private List<Patient> patientList;
    @JsonExclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bed", fetch = FetchType.LAZY)
    private List<PrepareIncidence> prepareIncidenceList;
    @JsonExclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bed", fetch = FetchType.LAZY)
    private List<GivesIncidence> givesIncidenceList;

    public Recurso() {
    }

    public Recurso(Long idRecurso) {
        this.idRecurso = idRecurso;
    }

    public List<GivesIncidence> getGivesIncidenceList() {
        return givesIncidenceList;
    }

    public void setGivesIncidenceList(List<GivesIncidence> givesIncidenceList) {
        this.givesIncidenceList = givesIncidenceList;
    }

    public List<PrepareIncidence> getPrepareIncidenceList() {
        return prepareIncidenceList;
    }

    public void setPrepareIncidenceList(List<PrepareIncidence> prepareIncidenceList) {
        this.prepareIncidenceList = prepareIncidenceList;
    }

    public Recurso(Long idRecurso, String estadoRecurso, Date fechaCreacion, String localizador, String nr, String ruta, String tipoRecurso, String claseRecurso, String admisible) {
        this.idRecurso = idRecurso;
        this.estadoRecurso = estadoRecurso;
        this.fechaCreacion = fechaCreacion;
        this.localizador = localizador;
        this.nr = nr;
        this.ruta = ruta;
        this.tipoRecurso = tipoRecurso;
        this.claseRecurso = claseRecurso;
        this.admisible = admisible;
    }

    public String getEstadoRecurso() {
        return estadoRecurso;
    }

    public void setEstadoRecurso(String estadoRecurso) {
        this.estadoRecurso = estadoRecurso;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Long getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(Long idRecurso) {
        this.idRecurso = idRecurso;
    }

    public String getLocalizador() {
        return localizador;
    }

    public void setLocalizador(String localizador) {
        this.localizador = localizador;
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getTipoRecurso() {
        return tipoRecurso;
    }

    public void setTipoRecurso(String tipoRecurso) {
        this.tipoRecurso = tipoRecurso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEmpResponsable() {
        return empResponsable;
    }

    public void setEmpResponsable(String empResponsable) {
        this.empResponsable = empResponsable;
    }

    public Date getFechaHabilitacion() {
        return fechaHabilitacion;
    }

    public void setFechaHabilitacion(Date fechaHabilitacion) {
        this.fechaHabilitacion = fechaHabilitacion;
    }

    public Long getMotivoDesh() {
        return motivoDesh;
    }

    public void setMotivoDesh(Long motivoDesh) {
        this.motivoDesh = motivoDesh;
    }

    public String getServiciosRecurso() {
        return serviciosRecurso;
    }

    public void setServiciosRecurso(String serviciosRecurso) {
        this.serviciosRecurso = serviciosRecurso;
    }

    public String getServResponsable() {
        return servResponsable;
    }

    public void setServResponsable(String servResponsable) {
        this.servResponsable = servResponsable;
    }

    public String getUbicacionRecurso() {
        return ubicacionRecurso;
    }

    public void setUbicacionRecurso(String ubicacionRecurso) {
        this.ubicacionRecurso = ubicacionRecurso;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getClaseRecurso() {
        return claseRecurso;
    }

    public void setClaseRecurso(String claseRecurso) {
        this.claseRecurso = claseRecurso;
    }

    public String getTipoDesh() {
        return tipoDesh;
    }

    public void setTipoDesh(String tipoDesh) {
        this.tipoDesh = tipoDesh;
    }

    public Long getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(Long idPadre) {
        this.idPadre = idPadre;
    }

    public String getEliminado() {
        return eliminado;
    }

    public void setEliminado(String eliminado) {
        this.eliminado = eliminado;
    }

    public String getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(String idEntidad) {
        this.idEntidad = idEntidad;
    }

    public String getAdmisible() {
        return admisible;
    }

    public void setAdmisible(String admisible) {
        this.admisible = admisible;
    }

    public List<RecursoTag> getRecursoTagList() {
        return recursoTagList;
    }

    public void setRecursoTagList(List<RecursoTag> recursoTagList) {
        this.recursoTagList = recursoTagList;
    }

    public List<Patient> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<Patient> patientList) {
        this.patientList = patientList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRecurso != null ? idRecurso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recurso)) {
            return false;
        }
        Recurso other = (Recurso) object;
        if ((this.idRecurso == null && other.idRecurso != null) || (this.idRecurso != null && !this.idRecurso.equals(other.idRecurso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.Recurso[idRecurso=" + idRecurso + "]";
    }

    public void addPatient(Patient patient) {
        if (this.patientList == null) {
            this.setPatientList(new ArrayList<Patient>());
        }
        this.patientList.add(patient);
        if (patient.getBed() != this) {
            patient.setBed(this);
        }
    }

    public void addGivesIncidence(GivesIncidence givesIncidence) {
        if (this.givesIncidenceList == null) {
            this.setGivesIncidenceList(new ArrayList<GivesIncidence>());
        }
        this.givesIncidenceList.add(givesIncidence);
        if (givesIncidence.getBed() != this) {
            givesIncidence.setBed(this);
        }
    }

    public void addPrepareIncidence(PrepareIncidence prepareIncidence) {
        if (this.prepareIncidenceList == null) {
            this.setPrepareIncidenceList(new ArrayList<PrepareIncidence>());
        }
        this.prepareIncidenceList.add(prepareIncidence);
        if (prepareIncidence.getBed() != this) {
            prepareIncidence.setBed(this);
        }
    }

    public void addRecursoTag(RecursoTag recursoTag) {
        if (this.recursoTagList == null) {
            this.setRecursoTagList(new ArrayList<RecursoTag>());
        }
        this.recursoTagList.add(recursoTag);
        if (recursoTag.getRecurso() != this) {
            recursoTag.setRecurso(this);
        }
    }
}
