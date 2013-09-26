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
 * Tratamiento
 * @author david
 */
@Entity
@Table(name = "order1")
@NamedQueries({
    @NamedQuery(name = "Order1.findAll", query = "SELECT o FROM Order1 o"),
    @NamedQuery(name = "Order1.findByIdorder", query = "SELECT o FROM Order1 o WHERE o.idorder = :idorder"),
    @NamedQuery(name = "Order1.findByOrderId", query = "SELECT o FROM Order1 o WHERE o.orderId = :orderId"),
    //@NamedQuery(name = "Order1.findByOrderHospitalId", query = "SELECT o FROM Order1 o WHERE o.orderHospitalId = :orderHospitalId"),
    @NamedQuery(name = "Order1.findByOrderStatus", query = "SELECT o FROM Order1 o WHERE o.orderStatus = :orderStatus"),
    @NamedQuery(name = "Order1.findByPriority", query = "SELECT o FROM Order1 o WHERE o.priority = :priority"),
    @NamedQuery(name = "Order1.findByOrderFather", query = "SELECT o FROM Order1 o WHERE o.orderFather = :orderFather"),
    @NamedQuery(name = "Order1.findByEventDate", query = "SELECT o FROM Order1 o WHERE o.eventDate = :eventDate"),
    @NamedQuery(name = "Order1.findByCreationEventUser", query = "SELECT o FROM Order1 o WHERE o.creationEventUser = :creationEventUser"),
    @NamedQuery(name = "Order1.findByValidationEventUser", query = "SELECT o FROM Order1 o WHERE o.validationEventUser = :validationEventUser"),
    @NamedQuery(name = "Order1.findByEventDoctor", query = "SELECT o FROM Order1 o WHERE o.eventDoctor = :eventDoctor"),
    @NamedQuery(name = "Order1.findByCreationSystem", query = "SELECT o FROM Order1 o WHERE o.creationSystem = :creationSystem"),
    @NamedQuery(name = "Order1.findByStartDate", query = "SELECT o FROM Order1 o WHERE o.startDate = :startDate"),
    @NamedQuery(name = "Order1.findByReason", query = "SELECT o FROM Order1 o WHERE o.reason = :reason"),
    @NamedQuery(name = "Order1.findByHistoric", query = "SELECT o FROM Order1 o WHERE o.historic = :historic")})
public class Order1 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "idorder", nullable = false, insertable = true, updatable = true)
    private Long idorder;
    /**
     * Identificador de la orden, unico en todo el sistema mientras sea valido(incluido el HIS)
     */
    @Column(name = "order_id", length = 45)
    private String orderId;
    /**
     * Grupo en la que se encuentra un tratamiento
     */
    @Column(name = "order_group_id", length = 45)
    private String orderGroupId;
    /**
     * Estado de la orden de tratamiento
     */
    @Column(name = "order_status", length = 45)
    private String orderStatus;
    /**
     * Prioridad de la orden del tratamiento
     */
    @Column(name = "priority", length = 45)
    private String priority;
    /**
     * Id de la orden que provoco esta otra orden
     */
    @Column(name = "order_father", length = 45)
    private String orderFather;
    /**
     * Fecha en la que se creo al orden
     */
    @Column(name = "event_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventDate;
    /**
     * Persona que creo la orden
     */
    @Column(name = "creation_event_user", length = 45)
    private String creationEventUser;
    /**
     * Persona que valido la orden
     */
    @Column(name = "validation_event_user", length = 45)
    private String validationEventUser;
    /**
     * Doctor que creo la orden
     */
    @Column(name = "event_doctor", length = 45)
    private String eventDoctor;
    /**
     * Sistema con el que se creo la orden
     */
    @Column(name = "creation_system", length = 45)
    private String creationSystem;
    /**
     * Fecha en la que debe comenzar el tratamiento
     */
    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    /**
     * Razon para la creacion o eliminacion del tratamiento
     */
    @Column(name = "reason", length = 45)
    private String reason;
    /**
     * Flag para determinar que la orden ha finalizado
     */
    @Basic(optional = false)
    @Column(name = "historic", nullable = false)
    private boolean historic;
    /**
     * Codigo de control
     */
    @JsonIgnore
    @JoinColumn(name = "control", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Table0119 control;
    /**
     * Paciente al que se preescribe el tratamiento
     */
    @JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Patient patientId;
    /**
     * Telefonos de contacto del medico
     */
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderIdorder", fetch = FetchType.LAZY)
    private List<TelephoneDoctor> telephoneDoctorList;
    /**
     * Tomas de las que se compone el tratamiento
     */
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderIdorder", fetch = FetchType.LAZY)
    private List<OrderTiming> orderTimingList;
    /**
     * Medicacion de la que esta formado el tratamiento
     */
    @JsonIgnore
    @OneToOne(mappedBy = "order1")
    private OrderMedication orderMedication;

    public Order1() {
    }

    public Order1(Long idorder) {
        this.idorder = idorder;
    }

    public Order1(Long idorder, boolean historic) {
        this.idorder = idorder;
        this.historic = historic;
    }

    public Long getIdorder() {
        return idorder;
    }

    public void setIdorder(Long idorder) {
        this.idorder = idorder;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderGroupId() {
        return orderGroupId;
    }

    public void setOrderGroupId(String orderGroupId) {
        this.orderGroupId = orderGroupId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getOrderFather() {
        return orderFather;
    }

    public void setOrderFather(String orderFather) {
        this.orderFather = orderFather;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getCreationEventUser() {
        return creationEventUser;
    }

    public void setCreationEventUser(String creationEventUser) {
        this.creationEventUser = creationEventUser;
    }

    public String getValidationEventUser() {
        return validationEventUser;
    }

    public void setValidationEventUser(String validationEventUser) {
        this.validationEventUser = validationEventUser;
    }

    public String getEventDoctor() {
        return eventDoctor;
    }

    public void setEventDoctor(String eventDoctor) {
        this.eventDoctor = eventDoctor;
    }

    public String getCreationSystem() {
        return creationSystem;
    }

    public void setCreationSystem(String creationSystem) {
        this.creationSystem = creationSystem;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean getHistoric() {
        return historic;
    }

    public void setHistoric(boolean historic) {
        this.historic = historic;
    }

    public Table0119 getControl() {
        return control;
    }

    public void setControl(Table0119 control) {
        this.control = control;
    }

    public Patient getPatientId() {
        return patientId;
    }

    public void setPatientId(Patient patientId) {
        this.patientId = patientId;
    }

    public List<TelephoneDoctor> getTelephoneDoctorList() {
        return telephoneDoctorList;
    }

    public void setTelephoneDoctorList(List<TelephoneDoctor> telephoneDoctorList) {
        this.telephoneDoctorList = telephoneDoctorList;
    }

    public List<OrderTiming> getOrderTimingList() {
        return orderTimingList;
    }

    public void setOrderTimingList(List<OrderTiming> orderTimingList) {
        this.orderTimingList = orderTimingList;
    }

    public OrderMedication getOrderMedication() {
        return orderMedication;
    }

    public void setOrderMedication(OrderMedication orderMedication) {
        this.orderMedication = orderMedication;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idorder != null ? idorder.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Order1)) {
            return false;
        }
        Order1 other = (Order1) object;
        if ((this.idorder == null && other.idorder != null) || (this.idorder != null && !this.idorder.equals(other.idorder))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.abada.contramed.persistence.entity.Order1[idorder=" + idorder + "]";
    }

    public void addTelephoneDoctor(TelephoneDoctor telephonedoctor) {
        if (this.telephoneDoctorList == null) {
            this.setTelephoneDoctorList(new ArrayList<TelephoneDoctor>());
        }
        this.telephoneDoctorList.add(telephonedoctor);
        if (telephonedoctor.getOrderIdorder() != this) {
            telephonedoctor.setOrderIdorder(this);
        }
    }

    public void addOrderTiming(OrderTiming ordertiming) {
        if (this.orderTimingList == null) {
            this.setOrderTimingList(new ArrayList<OrderTiming>());
        }
        this.orderTimingList.add(ordertiming);
        if (ordertiming.getOrderIdorder() != this) {
            ordertiming.setOrderIdorder(this);
        }
    }
}
