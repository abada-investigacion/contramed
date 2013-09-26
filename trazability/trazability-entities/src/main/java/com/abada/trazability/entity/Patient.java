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
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
 * Datos de un paciente
 * @author david
 */
@Entity
@Table(name = "patient")
@NamedQueries({
    @NamedQuery(name = "Patient.findAll", query = "SELECT p FROM Patient p"),
    @NamedQuery(name = "Patient.findById", query = "SELECT p FROM Patient p WHERE p.id = :id"),
    @NamedQuery(name = "Patient.findByName", query = "SELECT p FROM Patient p WHERE p.name = :name"),
    @NamedQuery(name = "Patient.findBySurname1", query = "SELECT p FROM Patient p WHERE p.surname1 = :surname1"),
    @NamedQuery(name = "Patient.findBySurname2", query = "SELECT p FROM Patient p WHERE p.surname2 = :surname2"),
    @NamedQuery(name = "Patient.findByBirthday", query = "SELECT p FROM Patient p WHERE p.birthday = :birthday"),
    @NamedQuery(name = "Patient.findByExitus", query = "SELECT p FROM Patient p WHERE p.exitus = :exitus"),
    @NamedQuery(name = "Patient.findByExitusDate", query = "SELECT p FROM Patient p WHERE p.exitusDate = :exitusDate"),
    @NamedQuery(name = "Patient.findByWaitingExitus", query = "SELECT p FROM Patient p WHERE p.waitingExitus = :waitingExitus")})
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;
    /**
     * Nombre de un paciente
     */
    @Column(name = "name", length = 45)
    private String name;
    /**
     * Primer apellido
     */
    @Column(name = "surname1", length = 45)
    private String surname1;
    /**
     * Segundo apellido
     */
    @Column(name = "surname2", length = 45)
    private String surname2;
    /**
     * Fecha de nacimiento
     */
    @Column(name = "birthday")
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthday;
    /**
     * Estado de exitus, ¿esta muerto?
     */
    @Column(name = "exitus")
    private Boolean exitus;
    /**
     * Fecha de la muerte, si procede
     */
    @Column(name = "exitus_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date exitusDate;
    /**
     * Esta en estado terminal?
     */
    @Column(name = "waiting_exitus")
    private Boolean waitingExitus;
    /**
     * tag rfid del paciente
     */
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "patient")
    private PatientTag patientTag;
    /**
     * Sexo
     */
    @JoinColumn(name = "genre", referencedColumnName = "id")
    @ManyToOne()
    private Table0001 genre;
    /**
     * Estado civil
     */
    @JoinColumn(name = "marital_status", referencedColumnName = "id")
    @ManyToOne()
    private Table0002 maritalStatus;
    /**
     * Cama en la que esta
     */
    @JoinColumn(name = "bed", referencedColumnName = "ID_RECURSO")
    @ManyToOne()
    private Recurso bed;
    /**
     * Ciudad de nacimiento
     */
    @JoinColumn(name = "birth_city", referencedColumnName = "idcity")
    @ManyToOne()
    private City birthCity;
    /**
     * Tratamientos que tiene o ha tenido
     */
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patientId", fetch = FetchType.LAZY)
    private List<Order1> order1List;
    /**
     * Direcciones de contacto
     */
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patientId", fetch = FetchType.LAZY)
    private List<Address> addressList;
    /**
     * Todos los identificadores del paciente
     */
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patientId", fetch = FetchType.LAZY)
    private List<PatientId> patientIdList;
    /**
     * Telefonos de contacto
     */
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patientId", fetch = FetchType.LAZY)
    private List<Telephone> telephoneList;
    /**
     * Incidencias que se han producido con sobre este paciente a la hora de la preparacion de medicacion
     */
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patient", fetch = FetchType.LAZY)
    private List<PrepareIncidence> prepareIncidenceList;
    /**
     * Incidencias que se han producido con sobre este paciente a la hora de la aministracion de medicacion
     */
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patient", fetch = FetchType.LAZY)
    private List<GivesIncidence> givesIncidenceList;
    /**
     * Listado de Administraciones de medicacion
     */
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patient", fetch = FetchType.LAZY)
    private List<GivesHistoric> givesHistoricList;
    
    /**
     * Listado de las observaciones del paciente
     */
   @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patientId", fetch = FetchType.LAZY)
    private List<Observation> observationList;



    public List<GivesIncidence> getGivesIncidenceList() {
        return givesIncidenceList;
    }

    public void setGivesIncidenceList(List<GivesIncidence> givesIncidenceList) {
        this.givesIncidenceList = givesIncidenceList;
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

    public Patient() {
    }

    public Patient(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Boolean getExitus() {
        return exitus;
    }

    public void setExitus(Boolean exitus) {
        this.exitus = exitus;
    }

    public Date getExitusDate() {
        return exitusDate;
    }

    public void setExitusDate(Date exitusDate) {
        this.exitusDate = exitusDate;
    }

    public Boolean getWaitingExitus() {
        return waitingExitus;
    }

    public void setWaitingExitus(Boolean waitingExitus) {
        this.waitingExitus = waitingExitus;
    }

    public PatientTag getPatientTag() {
        return patientTag;
    }

    public void setPatientTag(PatientTag patientTag) {
        this.patientTag = patientTag;
    }

    public Table0001 getGenre() {
        return genre;
    }

    public void setGenre(Table0001 genre) {
        this.genre = genre;
    }

    public Table0002 getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Table0002 maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Recurso getBed() {
        return bed;
    }

    public void setBed(Recurso bed) {
        this.bed = bed;
    }

    public City getBirthCity() {
        return birthCity;
    }

    public void setBirthCity(City birthCity) {
        this.birthCity = birthCity;
    }

    public List<Order1> getOrder1List() {
        return order1List;
    }

    public void setOrder1List(List<Order1> order1List) {
        this.order1List = order1List;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public List<PatientId> getPatientIdList() {
        return patientIdList;
    }

    public void setPatientIdList(List<PatientId> patientIdList) {
        this.patientIdList = patientIdList;
    }

    public List<Telephone> getTelephoneList() {
        return telephoneList;
    }

    public void setTelephoneList(List<Telephone> telephoneList) {
        this.telephoneList = telephoneList;
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
        if (!(object instanceof Patient)) {
            return false;
        }
        Patient other = (Patient) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.Patient[id=" + id + "]";
    }

    public void addOrder1(Order1 order1) {
        if (this.order1List == null) {
            this.setOrder1List(new ArrayList<Order1>());
        }
        this.order1List.add(order1);
        if (order1.getPatientId() != this) {
            order1.setPatientId(this);
        }
    }

    public void addAddress(Address address) {
        if (this.addressList == null) {
            this.setAddressList(new ArrayList<Address>());
        }
        this.addressList.add(address);
        if (address.getPatientId() != this) {
            address.setPatientId(this);
        }
    }

    public void addPatientId(PatientId patientid) {
        if (this.patientIdList == null) {
            this.setPatientIdList(new ArrayList<PatientId>());
        }
        this.patientIdList.add(patientid);
        if (patientid.getPatientId() != this) {
            patientid.setPatientId(this);
        }
    }

    public void addTelephone(Telephone telephone) {
        if (this.telephoneList == null) {
            this.setTelephoneList(new ArrayList<Telephone>());
        }
        this.telephoneList.add(telephone);
        if (telephone.getPatientId() != this) {
            telephone.setPatientId(this);
        }
    }

    public void addGivesIncidence(GivesIncidence givesIncidence) {
        if (this.givesIncidenceList == null) {
            this.setGivesIncidenceList(new ArrayList<GivesIncidence>());
        }
        this.givesIncidenceList.add(givesIncidence);
        if (givesIncidence.getPatient() != this) {
            givesIncidence.setPatient(this);
        }
    }

    public void addPrepareIncidence(PrepareIncidence prepareIncidence) {
        if (this.prepareIncidenceList == null) {
            this.setPrepareIncidenceList(new ArrayList<PrepareIncidence>());
        }
        this.prepareIncidenceList.add(prepareIncidence);
        if (prepareIncidence.getPatient() != this) {
            prepareIncidence.setPatient(this);
        }
    }

    public void addGiveHistoric(GivesHistoric giveHistoric) {
        if (this.givesHistoricList == null) {
            this.setGivesHistoricList(new ArrayList<GivesHistoric>());
        }
        this.givesHistoricList.add(giveHistoric);
        if (giveHistoric.getPatient() != this) {
            giveHistoric.setPatient(this);
        }
    }

   public void addObservation(Observation observation) {
        if (this.observationList == null) {
            this.setObservationList(new ArrayList<Observation>());
        }
        this.observationList.add(observation);
        if (observation.getPatientId() != this) {
            observation.setPatientId(this);
        }
    }


}
