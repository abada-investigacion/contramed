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

import com.abada.contramed.persistence.entity.enums.TypeRole;
import com.abada.contramed.persistence.entity.enums.TypeStaff;
import com.abada.gson.exclusionstrategy.JsonExclude;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
 * Datos del personal hospitalario
 * @author david
 */
@Entity
@Table(name = "staff", uniqueConstraints = {@UniqueConstraint(columnNames = {"tag"})})
@NamedQueries({
    @NamedQuery(name = "Staff.findAll", query = "SELECT s FROM Staff s"),
    @NamedQuery(name = "Staff.findByIdstaff", query = "SELECT s FROM Staff s WHERE s.idstaff = :idstaff"),
    @NamedQuery(name = "Staff.findByUsername", query = "SELECT s FROM Staff s WHERE s.username = :username"),
    @NamedQuery(name = "Staff.findByTag", query = "SELECT s FROM Staff s WHERE s.tag = :tag"),
    @NamedQuery(name = "Staff.findByType", query = "SELECT s FROM Staff s WHERE s.type = :type"),
    @NamedQuery(name = "Staff.findByRole", query = "SELECT s FROM Staff s WHERE s.role = :role")})
public class Staff implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "idstaff", nullable = false)
    private Long idstaff;
    /**
     * Nombre de usuario, coincide con el del LDAP
     */
    @Basic(optional = false)
    @Column(name = "username", nullable = false, length = 45)
    private String username;
    /**
     * Identificador del Tag RFID asignado a el
     */
    @Basic(optional = false)
    @Column(name = "tag", nullable = false, length = 45)
    private String tag;
    /**
     * Tipo de personal
     */
    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 9)
    private TypeStaff type;
    /**
     * Role que tiene respecto de la aplicacion
     */
    @Basic(optional = false)
    @Column(name = "role", nullable = false, length = 45)
    @Enumerated(EnumType.STRING)
    private TypeRole role;
    /**
     * Nombre
     */
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    /**
     * Primer apellido
     */
    @Basic(optional = false)
    @Column(name = "surname1", nullable = false, length = 50)
    private String surname1;
    /**
     * Segundo apellido
     */
    @Basic(optional = false)
    @Column(name = "surname2", nullable = false, length = 50)
    private String surname2;
    /**
     * Flag usado para saber si el personal a causado baja
     */
    @Basic(optional = false)
    @Column(name = "historic", nullable = false)
    private boolean historic;
    /**
     * Listado de Administraciones de medicacion
     */
    @JsonExclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "staffIdstaff", fetch = FetchType.LAZY)
    private List<GivesHistoric> givesHistoricList;
    /**
     * Listado de preparacion de medicacion
     */
    @JsonExclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "staffIdstaff", fetch = FetchType.LAZY)
    private List<PrepareIncidence> prepareIncidenceList;
    /**
     * Listado de incidencias de Administraciones de medicacion
     */
    @JsonExclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "staffIdstaff", fetch = FetchType.LAZY)
    private List<GivesIncidence> givesIncidenceList;
    /**
     * Listado de incidencias de preparacion de medicacion
     */
    @JsonExclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "staffIdstaff", fetch = FetchType.LAZY)
    private List<PrepareHistoric> prepareHistoricList;

     /**
     * Listado de las observaciones generadas por el personal
     */
   @JsonExclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "staffId", fetch = FetchType.LAZY)
    private List<Observation> observationList;

    public Staff() {
    }

    public Staff(Long idstaff) {
        this.idstaff = idstaff;
    }

    public boolean isHistoric() {
        return historic;
    }

    public void setHistoric(boolean historic) {
        this.historic = historic;
    }

    public Staff(Long idstaff, String username, String tag, TypeStaff type, TypeRole role) {
        this.idstaff = idstaff;
        this.username = username;
        this.tag = tag;
        this.type = type;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname1() {
        return surname1;
    }

    public void setSurname1(String surname1) {
        this.surname1 = surname1;
    }

    public String getSurname2() {
        return surname2;
    }

    public void setSurname2(String surname2) {
        this.surname2 = surname2;
    }

    public Long getIdstaff() {
        return idstaff;
    }

    public void setIdstaff(Long idstaff) {
        this.idstaff = idstaff;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public TypeStaff getType() {
        return type;
    }

    public void setType(TypeStaff type) {
        this.type = type;
    }

    public TypeRole getRole() {
        return role;
    }

    public void setRole(TypeRole role) {
        this.role = role;
    }

    public List<GivesHistoric> getGivesHistoricList() {
        return givesHistoricList;
    }

    public void setGivesHistoricList(List<GivesHistoric> givesHistoricList) {
        this.givesHistoricList = givesHistoricList;
    }

    public List<Observation> getObservationList() {
        return observationList;
    }

    public void setObservationList(List<Observation> observationList) {
        this.observationList = observationList;
    }


    public List<PrepareIncidence> getPrepareIncidenceList() {
        return prepareIncidenceList;
    }

    public void setPrepareIncidenceList(List<PrepareIncidence> prepareIncidenceList) {
        this.prepareIncidenceList = prepareIncidenceList;
    }

    public List<GivesIncidence> getGivesIncidenceList() {
        return givesIncidenceList;
    }

    public void setGivesIncidenceList(List<GivesIncidence> givesIncidenceList) {
        this.givesIncidenceList = givesIncidenceList;
    }

    public List<PrepareHistoric> getPrepareHistoricList() {
        return prepareHistoricList;
    }

    public void setPrepareHistoricList(List<PrepareHistoric> prepareHistoricList) {
        this.prepareHistoricList = prepareHistoricList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idstaff != null ? idstaff.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Staff)) {
            return false;
        }
        Staff other = (Staff) object;
        if ((this.idstaff == null && other.idstaff != null) || (this.idstaff != null && !this.idstaff.equals(other.idstaff))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.Staff[idstaff=" + idstaff + "]";
    }

    public void addGivesHistoric(GivesHistoric giveshistoric) {
        if (this.givesHistoricList == null) {
            this.setGivesHistoricList(new ArrayList<GivesHistoric>());
        }
        this.givesHistoricList.add(giveshistoric);
        if (giveshistoric.getStaffIdstaff() != this) {
            giveshistoric.setStaffIdstaff(this);
        }
    }

    public void addPrepareIncidence(PrepareIncidence prepareincidence) {
        if (this.prepareIncidenceList == null) {
            this.setPrepareIncidenceList(new ArrayList<PrepareIncidence>());
        }
        this.prepareIncidenceList.add(prepareincidence);
        if (prepareincidence.getStaffIdstaff() != this) {
            prepareincidence.setStaffIdstaff(this);
        }
    }

    public void addGivesIncidence(GivesIncidence givesincidence) {
        if (this.givesIncidenceList == null) {
            this.setGivesIncidenceList(new ArrayList<GivesIncidence>());
        }
        this.givesIncidenceList.add(givesincidence);
        if (givesincidence.getStaffIdstaff() != this) {
            givesincidence.setStaffIdstaff(this);
        }
    }

    public void addPrepareHistoric(PrepareHistoric preparehistoric) {
        if (this.prepareHistoricList == null) {
            this.setPrepareHistoricList(new ArrayList<PrepareHistoric>());
        }
        this.prepareHistoricList.add(preparehistoric);
        if (preparehistoric.getStaffIdstaff() != this) {
            preparehistoric.setStaffIdstaff(this);
        }
    }


   public void addObservation(Observation observation) {
        if (this.observationList == null) {
            this.setObservationList(new ArrayList<Observation>());
        }
        this.observationList.add(observation);
        if (observation.getStaffId() != this) {
            observation.setStaffId(this);
        }
    }
}
