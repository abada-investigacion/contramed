/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.webcontramed.controller;

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

import com.abada.contramed.persistence.entity.Patient;
import com.abada.contramed.persistence.entity.PatientId;
import com.abada.contramed.persistence.entity.Staff;
import com.abada.contramed.persistence.entity.enums.TypeGetPatient;
import com.abada.contramedadministrationweb.entities.StaffLdap;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author katsu
 */
public abstract class CommonControllerConstants {
    private static final String PATIENT = "patient";
    private static final String PATIENT_ID = "patientid";
    private static final String PATIENT_READ_METHOD = "patientReadMethod";
    private static final String USER = "user";
    private static final String ROLE="role";
    //private static final String ROLE_MENU="roleMenu";
    private static final String START_DATE="startDate";
    private static final String END_DATE="endDate";
    private static final String USERLDAP="userldap";

    protected PatientId getPatientId(HttpServletRequest request) {
        return (PatientId) request.getSession().getAttribute(PATIENT_ID);
    }

    protected void setPatientId(HttpServletRequest request,PatientId obj) {
        request.getSession().setAttribute(PATIENT_ID,obj);
    }

    protected Patient getPatient(HttpServletRequest request) {
        return (Patient) request.getSession().getAttribute(PATIENT);
    }

    protected void setPatient(HttpServletRequest request,Patient patient) {
        request.getSession().setAttribute(PATIENT,patient);
    }

    protected TypeGetPatient getPatientReadMethod(HttpServletRequest request) {
        return (TypeGetPatient) request.getSession().getAttribute(PATIENT_READ_METHOD);
    }

    protected void setPatientReadMethod(HttpServletRequest request,TypeGetPatient typeGetPatient) {
        request.getSession().setAttribute(PATIENT_READ_METHOD,typeGetPatient);
    }

    protected Staff getStaff(HttpServletRequest request) {
        return (Staff) request.getSession().getAttribute(USER);
    }

    protected void setStaff(HttpServletRequest request,Staff obj) {
        request.getSession().setAttribute(USER,obj);
    }

    protected String getRole(HttpServletRequest request) {
        return (String) request.getSession().getAttribute(ROLE);
    }

    protected void setRole(HttpServletRequest request,String obj) {
        request.getSession().setAttribute(ROLE,obj);
    }

    /*protected String getRoleMenu(HttpServletRequest request) {
        return (String) request.getSession().getAttribute(ROLE_MENU);
    }

    protected void setRoleMenu(HttpServletRequest request,String obj) {
        request.getSession().setAttribute(ROLE_MENU,obj);
    }*/

    protected Date getStartDate(HttpServletRequest request) {
        return (Date) request.getSession().getAttribute(START_DATE);
    }

    protected void setStartDate(HttpServletRequest request,Date obj) {
        request.getSession().setAttribute(START_DATE,obj);
    }

    protected Date getEndDate(HttpServletRequest request) {
        return (Date) request.getSession().getAttribute(END_DATE);
    }

    protected void setEndDate(HttpServletRequest request,Date obj) {
        request.getSession().setAttribute(END_DATE,obj);
    }
     protected StaffLdap getStaffLdap(HttpServletRequest request) {
        return (StaffLdap) request.getSession().getAttribute(USERLDAP);
    }

    protected void setStaffLdap(HttpServletRequest request,StaffLdap obj) {
        request.getSession().setAttribute(USERLDAP,obj);
    }

    protected void createSession(HttpServletRequest request){
        request.getSession(true);
    }

    protected void invalidateSession(HttpServletRequest request){
        request.getSession().invalidate();
    }


}
