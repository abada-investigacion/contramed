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

import com.abada.contramed.persistence.dao.observation.ObservationDao;
import com.abada.contramed.persistence.dao.patient.PatientDao;
import com.abada.contramed.persistence.entity.Patient;
import com.abada.contramed.persistence.entity.enums.TypeGetPatient;
import com.abada.springframework.web.servlet.handler.interceptor.annotation.Role;
import com.abada.springframework.web.servlet.view.JsonView;
import com.abada.web.exjs.Success;
import freemarker.core.ParseException;
import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author katsu
 */
@Controller
@Role({"SYSTEM_ADMINISTRATOR",
    "NURSE_PLANT",
    "NURSE_PLANT_SUPERVISOR",
    "NURSE_PHARMACY",
    "PHARMACIST"})
public class PatientValidationController extends CommonControllerConstants {
    @Resource(name = "patientDao")
    private PatientDao patientDao;
    @Resource(name = "observationDao")
    private ObservationDao observationDao;

    /**
     * Comprueba si una cama es correcta y si hay paciente asignado a la misma, y deja en la
     * sesion el paciente del que esta comprobando el tratamiento
     * @param bed
     * @return
     */
    @RequestMapping("/patienttagvalidation.htm")
    public ModelAndView searchPatientByTag(String tag, HttpServletRequest request) {
        Patient pat = patientDao.getPatientByBedTag(tag);
        if (pat != null) {
            this.setPatient(request, pat);
            this.setPatientReadMethod(request, TypeGetPatient.RFID_BOX);
        } else {
            pat = patientDao.getPatientByTag(tag);
            if (pat != null) {
                this.setPatient(request, pat);
                this.setPatientReadMethod(request, TypeGetPatient.RFID_PATIENT);
            } else {
                this.setPatient(request, null);
                this.setPatientReadMethod(request, null);
            }
        }
        Success result = new Success(pat != null);
        return new ModelAndView(new JsonView(result));
    }

    @RequestMapping("/patientidbedvalidation.htm")
    public ModelAndView setPatientByIDBed(Long id, HttpServletRequest request) {
        Patient pat = patientDao.getPatientByBedId(id);
        if (pat != null) {
            this.setPatient(request, pat);
            this.setPatientReadMethod(request, TypeGetPatient.BED_MAP);
        } else {
            this.setPatient(request, null);
            this.setPatientReadMethod(request, null);
        }
        Success result = new Success(pat != null);
        return new ModelAndView(new JsonView(result));
    }

    /*@RequestMapping("/patienttagbedvalidation.htm")
    public ModelAndView setPatientByTagBed(String tag, HttpServletRequest request) {
    Patient pat = patientDao.getPatientByBedTag(tag);
    if (pat != null) {
    this.setPatient(request, pat);
    } else {
    this.setPatient(request, null);
    }
    Success result = new Success(pat != null);
    return new ModelAndView(new JsonView(result));
    }*/
    @RequestMapping("/saveObservation.htm")
    public ModelAndView saveObservation(String observation,String privtext, HttpServletRequest request) throws ParseException {
        Success result = new Success(true);
        if (observation != null && !observation.isEmpty()) {
            try {
                this.observationDao.insert(new Date(),observation+System.getProperty("line.separator")+privtext, this.getPatient(request).getId(), this.getStaff(request).getIdstaff());
            } catch (Exception e) {
                result.setErrors(new com.abada.web.exjs.Error(e.getMessage()));
            }
        } else {
            result.setErrors(new com.abada.web.exjs.Error("Observaci&oncute;n vacia"));
        }
        return new ModelAndView(new JsonView(result));
    }
}
