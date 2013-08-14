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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Usada para la clave primaria de servicio, que esta formada por 2 variables
 * @author david
 */
@Embeddable
public class ServicioPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_SERVICIO", nullable = false)
    private long idServicio;
    @Basic(optional = false)
    @Column(name = "LOCALIZADOR", nullable = false, length = 255)
    private String localizador;

    public ServicioPK() {
    }

    public ServicioPK(long idServicio, String localizador) {
        this.idServicio = idServicio;
        this.localizador = localizador;
    }

    public long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(long idServicio) {
        this.idServicio = idServicio;
    }

    public String getLocalizador() {
        return localizador;
    }

    public void setLocalizador(String localizador) {
        this.localizador = localizador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idServicio;
        hash += (localizador != null ? localizador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ServicioPK)) {
            return false;
        }
        ServicioPK other = (ServicioPK) object;
        if (this.idServicio != other.idServicio) {
            return false;
        }
        if ((this.localizador == null && other.localizador != null) || (this.localizador != null && !this.localizador.equals(other.localizador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.ServicioPK[idServicio=" + idServicio + ", localizador=" + localizador + "]";
    }

}
