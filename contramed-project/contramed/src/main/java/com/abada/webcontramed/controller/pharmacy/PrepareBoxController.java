/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.webcontramed.controller.pharmacy;

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

import com.abada.contramed.persistence.dao.preparethreatment.PrepareThreatmentDao;
import com.abada.contramed.persistence.dao.treatment.ThreatmentDao;
import com.abada.contramed.persistence.entity.Patient;
import com.abada.springframework.web.servlet.handler.interceptor.annotation.Role;
import com.abada.springframework.web.servlet.view.JsonView;
import com.abada.web.exjs.ExtjsStore;
import com.abada.web.exjs.SearchField;
import com.abada.web.exjs.Success;
import com.abada.webcontramed.controller.CommonControllerConstants;
import com.abada.webcontramed.controller.util.Utils;
import com.abada.webcontramed.entities.ThreatmentInfo;
import com.abada.webcontramed.entities.ThreatmentInfoHistoric;
import com.abada.webcontramed.exception.WebContramedException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    "NURSE_PHARMACY",
    "PHARMACIST"})
public class PrepareBoxController extends CommonControllerConstants {

    /*@Resource(name = "patientDao")
    private PatientDao patientDao;*/
    @Resource(name = "threatmentDao")
    private ThreatmentDao threatmentDao;
    @Resource(name = "managerThreatmentDao")
    private PrepareThreatmentDao prepareThreatmentDao;
    private SimpleDateFormat format;
    private SimpleDateFormat format2;

    public PrepareBoxController() {
        format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * Devuelve la pantalla principal
     * @return
     */
    @RequestMapping("/preparebox.htm")
    public ModelAndView templatesMedication() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("js", new String[]{"common.js","commonBedMap.js", "preparebox.js"});
        return new ModelAndView("pages/main", model);
    }

    /***
     * Devuelve la informaction de un tratamiento para un periodo de fechas
     * @param request
     * @param startDate
     * @param endDate
     * @return
     * @throws ParseException
     * @throws WebContramedException
     */
    @RequestMapping("/getthreatment.htm")
    public ModelAndView getThreatment(HttpServletRequest request, String startDate, String endDate) throws ParseException, WebContramedException {
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Patient pat = this.getPatient(request);
        if (pat != null) {
            this.setStartDate(request, format.parse(startDate));
            //El ultimo día lo pongo a la última hora del día
            Date endAux = format.parse(endDate);
            /*endAux.setHours(23);
            endAux.setMinutes(59);
            endAux.setSeconds(59);*/
            this.setEndDate(request, endAux);
            List<ThreatmentInfo> data = this.threatmentDao.getActiveThreatmentByPharmacy(pat.getId(), this.getStartDate(request), this.getEndDate(request));
            ExtjsStore result = new ExtjsStore();
            result.setData(data);
            return new ModelAndView(new JsonView(result));
        }
        return null;
    }

    @RequestMapping("/prepare.htm")
    public ModelAndView prepare(String tagDose, String observation, HttpServletRequest request) {
        Success result = new Success(false);
        Patient pat = this.getPatient(request);
        if (pat == null) {
            result.setErrors(new com.abada.web.exjs.Error("No ha seleccionado un paciente"));
        } else {
            Long idDose = null;
            try {
                idDose = Utils.decodeIdDose(tagDose);
                if (idDose != null) {
                    try {
                        this.prepareThreatmentDao.prepareDose(idDose, pat, this.getStartDate(request), this.getEndDate(request), this.getStaff(request), observation);
                        result.setSuccess(Boolean.TRUE);
                    } catch (WebContramedException e) {
                        result.setErrors(new com.abada.web.exjs.Error((e.getMessage())));
                    } catch (Exception e){
                        result.setErrors(new com.abada.web.exjs.Error("Error desconocido "+e+" "+e.getMessage()));
                    }
                } else {
                    result.setErrors(new com.abada.web.exjs.Error("Error en la extracci&oacute;n de la informacti&oacute;n del DataMatrix"));
                }
            } catch (Exception e) {
                result.setErrors(new com.abada.web.exjs.Error("Error en la decodificaci&oacute;n del DataMatrix " + e.getMessage()));
            }
        }
        return new ModelAndView(new JsonView(result));
    }

    @RequestMapping("/prepareAll.htm")
    public ModelAndView prepareAll(HttpServletRequest request) {
        Success result = new Success(false);
        Patient pat = this.getPatient(request);
        if (pat == null) {
            result.setErrors(new com.abada.web.exjs.Error("No ha seleccionado un paciente"));
        } else {
            try {
                this.prepareThreatmentDao.prepareAll(pat, this.getStartDate(request), this.getEndDate(request), this.getStaff(request));
                result.setSuccess(Boolean.TRUE);
            } catch (WebContramedException e) {
                result.setErrors(new com.abada.web.exjs.Error(e.getMessage(), e.getType().toString()));
            }
        }
        return new ModelAndView(new JsonView(result));
    }

    @RequestMapping("/remove.htm")
    public ModelAndView remove(String tagDose, String observation, HttpServletRequest request) {
        Success result = new Success(false);
        Patient pat = this.getPatient(request);
        if (pat == null) {
            result.setErrors(new com.abada.web.exjs.Error("No ha seleccionado un paciente"));
        } else {
            Long idDose = null;
            try {
                idDose = Utils.decodeIdDose(tagDose);
                if (idDose != null) {
                    try {
                        this.prepareThreatmentDao.removeDosePharmacy(idDose, pat, this.getStartDate(request), this.getEndDate(request), this.getStaff(request), observation);
                        result.setSuccess(Boolean.TRUE);
                    } catch (WebContramedException e) {
                        result.setErrors(new com.abada.web.exjs.Error((e.getMessage())));
                    }catch (Exception e){
                        result.setErrors(new com.abada.web.exjs.Error("Error desconocido "+e+" "+e.getMessage()));
                    }
                } else {
                    result.setErrors(new com.abada.web.exjs.Error("Error en la decodificaci&oacute;n del DataMatrix"));
                }
            } catch (Exception e) {
                result.setErrors(new com.abada.web.exjs.Error("Error en la decodificaci&oacute;n del DataMatrix " + e.getMessage()));
            }
        }
        return new ModelAndView(new JsonView(result));
    }

    @RequestMapping("/prepareHistoric.htm")
    public ModelAndView getHistoric(String search, HttpServletRequest request) throws ParseException {
        Map<String, String> searchFields = SearchField.parse(search);
        Long idOrderTiming = Long.parseLong(searchFields.get("idOrderTiming"));
        Date giveDate = format2.parse(searchFields.get("giveTime"));

        ExtjsStore result = new ExtjsStore();
        List<ThreatmentInfoHistoric> resultData = this.threatmentDao.getPrepareHistoric(idOrderTiming, giveDate);
        result.setData(resultData);
        return new ModelAndView(new JsonView(result));
    }
}
