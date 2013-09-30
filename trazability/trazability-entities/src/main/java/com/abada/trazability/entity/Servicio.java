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
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Datos del un servicio
 * @author david
 */
@Entity
@Table(name = "servicio")
@NamedQueries({
    @NamedQuery(name = "Servicio.findAll", query = "SELECT s FROM Servicio s"),
    @NamedQuery(name = "Servicio.findByEmpResponsable", query = "SELECT s FROM Servicio s WHERE s.empResponsable = :empResponsable"),
    @NamedQuery(name = "Servicio.findByEstadoServicio", query = "SELECT s FROM Servicio s WHERE s.estadoServicio = :estadoServicio"),
    @NamedQuery(name = "Servicio.findByFechaCreacion", query = "SELECT s FROM Servicio s WHERE s.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Servicio.findByIdServicio", query = "SELECT s FROM Servicio s WHERE s.servicioPK.idServicio = :idServicio"),
    @NamedQuery(name = "Servicio.findByLocalizador", query = "SELECT s FROM Servicio s WHERE s.servicioPK.localizador = :localizador"),
    @NamedQuery(name = "Servicio.findByNs", query = "SELECT s FROM Servicio s WHERE s.ns = :ns"),
    @NamedQuery(name = "Servicio.findByRuta", query = "SELECT s FROM Servicio s WHERE s.ruta = :ruta"),
    @NamedQuery(name = "Servicio.findByTieneServPadre", query = "SELECT s FROM Servicio s WHERE s.tieneServPadre = :tieneServPadre"),
    @NamedQuery(name = "Servicio.findByTipoServicio", query = "SELECT s FROM Servicio s WHERE s.tipoServicio = :tipoServicio"),
    @NamedQuery(name = "Servicio.findByDescripcion", query = "SELECT s FROM Servicio s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "Servicio.findByFechaHabServicio", query = "SELECT s FROM Servicio s WHERE s.fechaHabServicio = :fechaHabServicio"),
    @NamedQuery(name = "Servicio.findByMotivoDesh", query = "SELECT s FROM Servicio s WHERE s.motivoDesh = :motivoDesh"),
    @NamedQuery(name = "Servicio.findByCodExterno", query = "SELECT s FROM Servicio s WHERE s.codExterno = :codExterno"),
    @NamedQuery(name = "Servicio.findByIdEntidad", query = "SELECT s FROM Servicio s WHERE s.idEntidad = :idEntidad"),
    @NamedQuery(name = "Servicio.findByIdHupEspe", query = "SELECT s FROM Servicio s WHERE s.idHupEspe = :idHupEspe")})
public class Servicio implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ServicioPK servicioPK;
    @Basic(optional = false)
    @Column(name = "EMP_RESPONSABLE", nullable = false, length = 50)
    private String empResponsable;
    @Basic(optional = false)
    @Column(name = "ESTADO_SERVICIO", nullable = false, length = 20)
    private String estadoServicio;
    @Basic(optional = false)
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    @Basic(optional = false)
    @Column(name = "NS", nullable = false, length = 50)
    private String ns;
    @Basic(optional = false)
    @Column(name = "RUTA", nullable = false, length = 1024)
    private String ruta;
    @Basic(optional = false)
    @Column(name = "TIENE_SERV_PADRE", nullable = false)
    private char tieneServPadre;
    @Basic(optional = false)
    @Column(name = "TIPO_SERVICIO", nullable = false, length = 10)
    private String tipoServicio;
    @Column(name = "DESCRIPCION", length = 150)
    private String descripcion;
    @Column(name = "FECHA_HAB_SERVICIO")
    @Temporal(TemporalType.DATE)
    private Date fechaHabServicio;
    @Column(name = "MOTIVO_DESH")
    private Long motivoDesh;
    @Column(name = "COD_EXTERNO", length = 50)
    private String codExterno;
    @Column(name = "ID_ENTIDAD", length = 10)
    private String idEntidad;
    @Column(name = "ID_HUP_ESPE")
    private BigInteger idHupEspe;

    public Servicio() {
    }

    public Servicio(ServicioPK servicioPK) {
        this.servicioPK = servicioPK;
    }

    public Servicio(ServicioPK servicioPK, String empResponsable, String estadoServicio, Date fechaCreacion, String ns, String ruta, char tieneServPadre, String tipoServicio) {
        this.servicioPK = servicioPK;
        this.empResponsable = empResponsable;
        this.estadoServicio = estadoServicio;
        this.fechaCreacion = fechaCreacion;
        this.ns = ns;
        this.ruta = ruta;
        this.tieneServPadre = tieneServPadre;
        this.tipoServicio = tipoServicio;
    }

    public Servicio(long idServicio, String localizador) {
        this.servicioPK = new ServicioPK(idServicio, localizador);
    }

    public ServicioPK getServicioPK() {
        return servicioPK;
    }

    public void setServicioPK(ServicioPK servicioPK) {
        this.servicioPK = servicioPK;
    }

    public String getEmpResponsable() {
        return empResponsable;
    }

    public void setEmpResponsable(String empResponsable) {
        this.empResponsable = empResponsable;
    }

    public String getEstadoServicio() {
        return estadoServicio;
    }

    public void setEstadoServicio(String estadoServicio) {
        this.estadoServicio = estadoServicio;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getNs() {
        return ns;
    }

    public void setNs(String ns) {
        this.ns = ns;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public char getTieneServPadre() {
        return tieneServPadre;
    }

    public void setTieneServPadre(char tieneServPadre) {
        this.tieneServPadre = tieneServPadre;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaHabServicio() {
        return fechaHabServicio;
    }

    public void setFechaHabServicio(Date fechaHabServicio) {
        this.fechaHabServicio = fechaHabServicio;
    }

    public Long getMotivoDesh() {
        return motivoDesh;
    }

    public void setMotivoDesh(Long motivoDesh) {
        this.motivoDesh = motivoDesh;
    }

    public String getCodExterno() {
        return codExterno;
    }

    public void setCodExterno(String codExterno) {
        this.codExterno = codExterno;
    }

    public String getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(String idEntidad) {
        this.idEntidad = idEntidad;
    }

    public BigInteger getIdHupEspe() {
        return idHupEspe;
    }

    public void setIdHupEspe(BigInteger idHupEspe) {
        this.idHupEspe = idHupEspe;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (servicioPK != null ? servicioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Servicio)) {
            return false;
        }
        Servicio other = (Servicio) object;
        if ((this.servicioPK == null && other.servicioPK != null) || (this.servicioPK != null && !this.servicioPK.equals(other.servicioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.Servicio[servicioPK=" + servicioPK + "]";
    }

}
