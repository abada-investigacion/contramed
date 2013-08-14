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
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Datos de las plantas
 * @author david
 */
@Entity
@Table(name = "plantas")
@NamedQueries({
    @NamedQuery(name = "Plantas.findAll", query = "SELECT p FROM Plantas p"),
    @NamedQuery(name = "Plantas.findByIdUOFisica", query = "SELECT p FROM Plantas p WHERE p.idUOFisica = :idUOFisica"),
    @NamedQuery(name = "Plantas.findByLocalizador", query = "SELECT p FROM Plantas p WHERE p.plantasPK.localizador = :localizador"),
    @NamedQuery(name = "Plantas.findByPou", query = "SELECT p FROM Plantas p WHERE p.plantasPK.pou = :pou"),
    @NamedQuery(name = "Plantas.findByRuta", query = "SELECT p FROM Plantas p WHERE p.ruta = :ruta"),
    @NamedQuery(name = "Plantas.findByDescripcionUOF", query = "SELECT p FROM Plantas p WHERE p.descripcionUOF = :descripcionUOF"),
    @NamedQuery(name = "Plantas.findByDireccionUOF", query = "SELECT p FROM Plantas p WHERE p.direccionUOF = :direccionUOF"),
    @NamedQuery(name = "Plantas.findByEdadPermitida", query = "SELECT p FROM Plantas p WHERE p.edadPermitida = :edadPermitida"),
    @NamedQuery(name = "Plantas.findByEliminado", query = "SELECT p FROM Plantas p WHERE p.eliminado = :eliminado"),
    @NamedQuery(name = "Plantas.findBySexoPermitido", query = "SELECT p FROM Plantas p WHERE p.sexoPermitido = :sexoPermitido"),
    @NamedQuery(name = "Plantas.findByTelefonoUOF", query = "SELECT p FROM Plantas p WHERE p.telefonoUOF = :telefonoUOF"),
    @NamedQuery(name = "Plantas.findByTieneReglaAis", query = "SELECT p FROM Plantas p WHERE p.tieneReglaAis = :tieneReglaAis"),
    @NamedQuery(name = "Plantas.findByTipoReglaEdad", query = "SELECT p FROM Plantas p WHERE p.tipoReglaEdad = :tipoReglaEdad"),
    @NamedQuery(name = "Plantas.findByTipoReglaSexo", query = "SELECT p FROM Plantas p WHERE p.tipoReglaSexo = :tipoReglaSexo"),
    @NamedQuery(name = "Plantas.findByTipoUOF", query = "SELECT p FROM Plantas p WHERE p.tipoUOF = :tipoUOF"),
    @NamedQuery(name = "Plantas.findByIdDistrito", query = "SELECT p FROM Plantas p WHERE p.idDistrito = :idDistrito"),
    @NamedQuery(name = "Plantas.findByIdEntidad", query = "SELECT p FROM Plantas p WHERE p.idEntidad = :idEntidad"),
    @NamedQuery(name = "Plantas.findByTipoHosp", query = "SELECT p FROM Plantas p WHERE p.tipoHosp = :tipoHosp")})
public class Plantas implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlantasPK plantasPK;
    @Basic(optional = false)
    @Column(name = "ID_U_O_FISICA", nullable = false)
    private long idUOFisica;
    @Basic(optional = false)
    @Column(name = "RUTA", nullable = false, length = 1024)
    private String ruta;
    @Column(name = "DESCRIPCION_U_O_F", length = 150)
    private String descripcionUOF;
    @Column(name = "DIRECCION_U_O_F", length = 150)
    private String direccionUOF;
    @Column(name = "EDAD_PERMITIDA")
    private BigInteger edadPermitida;
    @Column(name = "ELIMINADO",length=1)
    private String eliminado;
    @Column(name = "SEXO_PERMITIDO", length = 3)
    private String sexoPermitido;
    @Column(name = "TELEFONO_U_O_F", length = 30)
    private String telefonoUOF;
    @Column(name = "TIENE_REGLA_AIS",length=1)
    private String tieneReglaAis;
    @Column(name = "TIPO_REGLA_EDAD", length = 3)
    private String tipoReglaEdad;
    @Column(name = "TIPO_REGLA_SEXO", length = 3)
    private String tipoReglaSexo;
    @Column(name = "TIPO_U_O_F", length = 30)
    private String tipoUOF;
    @Column(name = "ID_DISTRITO")
    private Short idDistrito;
    @Column(name = "ID_ENTIDAD", length = 10)
    private String idEntidad;
    @Column(name = "TIPO_HOSP", length = 1)
    private String tipoHosp;

    public Plantas() {
    }

    public Plantas(PlantasPK plantasPK) {
        this.plantasPK = plantasPK;
    }

    public Plantas(PlantasPK plantasPK, long idUOFisica, String ruta) {
        this.plantasPK = plantasPK;
        this.idUOFisica = idUOFisica;
        this.ruta = ruta;
    }

    public Plantas(String localizador, String pou) {
        this.plantasPK = new PlantasPK(localizador, pou);
    }

    public PlantasPK getPlantasPK() {
        return plantasPK;
    }

    public void setPlantasPK(PlantasPK plantasPK) {
        this.plantasPK = plantasPK;
    }

    public long getIdUOFisica() {
        return idUOFisica;
    }

    public void setIdUOFisica(long idUOFisica) {
        this.idUOFisica = idUOFisica;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getDescripcionUOF() {
        return descripcionUOF;
    }

    public void setDescripcionUOF(String descripcionUOF) {
        this.descripcionUOF = descripcionUOF;
    }

    public String getDireccionUOF() {
        return direccionUOF;
    }

    public void setDireccionUOF(String direccionUOF) {
        this.direccionUOF = direccionUOF;
    }

    public BigInteger getEdadPermitida() {
        return edadPermitida;
    }

    public void setEdadPermitida(BigInteger edadPermitida) {
        this.edadPermitida = edadPermitida;
    }

    public String getEliminado() {
        return eliminado;
    }

    public void setEliminado(String eliminado) {
        this.eliminado = eliminado;
    }

    public String getSexoPermitido() {
        return sexoPermitido;
    }

    public void setSexoPermitido(String sexoPermitido) {
        this.sexoPermitido = sexoPermitido;
    }

    public String getTelefonoUOF() {
        return telefonoUOF;
    }

    public void setTelefonoUOF(String telefonoUOF) {
        this.telefonoUOF = telefonoUOF;
    }

    public String getTieneReglaAis() {
        return tieneReglaAis;
    }

    public void setTieneReglaAis(String tieneReglaAis) {
        this.tieneReglaAis = tieneReglaAis;
    }

    public String getTipoReglaEdad() {
        return tipoReglaEdad;
    }

    public void setTipoReglaEdad(String tipoReglaEdad) {
        this.tipoReglaEdad = tipoReglaEdad;
    }

    public String getTipoReglaSexo() {
        return tipoReglaSexo;
    }

    public void setTipoReglaSexo(String tipoReglaSexo) {
        this.tipoReglaSexo = tipoReglaSexo;
    }

    public String getTipoUOF() {
        return tipoUOF;
    }

    public void setTipoUOF(String tipoUOF) {
        this.tipoUOF = tipoUOF;
    }

    public Short getIdDistrito() {
        return idDistrito;
    }

    public void setIdDistrito(Short idDistrito) {
        this.idDistrito = idDistrito;
    }

    public String getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(String idEntidad) {
        this.idEntidad = idEntidad;
    }

    public String getTipoHosp() {
        return tipoHosp;
    }

    public void setTipoHosp(String tipoHosp) {
        this.tipoHosp = tipoHosp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (plantasPK != null ? plantasPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Plantas)) {
            return false;
        }
        Plantas other = (Plantas) object;
        if ((this.plantasPK == null && other.plantasPK != null) || (this.plantasPK != null && !this.plantasPK.equals(other.plantasPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.Plantas[plantasPK=" + plantasPK + "]";
    }

}
