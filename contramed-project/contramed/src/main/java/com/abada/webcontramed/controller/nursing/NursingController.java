/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.webcontramed.controller.nursing;

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

import com.abada.contramed.persistence.dao.givethreatment.GiveThreatmentDao;
import com.abada.contramed.persistence.dao.incidence.IncidenceDao;
import com.abada.contramed.persistence.dao.timingrange.TimingRangeDao;
import com.abada.contramed.persistence.dao.treatment.ThreatmentDao;
import com.abada.contramed.persistence.entity.Patient;
import com.abada.contramed.persistence.entity.TimingRange;
import com.abada.contramed.persistence.entity.enums.TypeGivesHistoric;
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
    "NURSE_PLANT",
    "NURSE_PLANT_SUPERVISOR"})
public class NursingController extends CommonControllerConstants {

    @Resource(name = "threatmentDao")
    private ThreatmentDao threatmentDao;
    @Resource(name = "managerThreatmentDao")
    private GiveThreatmentDao giveThreatmentDao;
    @Resource(name = "TimingRangeDao")
    private TimingRangeDao timingRangeDao;
    @Resource(name = "incidenceDao")
    private IncidenceDao incidenceDao;
    private SimpleDateFormat format;
    //private SimpleDateFormat format2;
    private SimpleDateFormat format3;

    public NursingController() {
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //format2= new SimpleDateFormat("HH:mm");
        format3 = new SimpleDateFormat("dd/MM/yyyy");
    }

    /**
     * Devuelve la pantalla principal
     * @return
     */
    @RequestMapping("/nursing.htm")
    public ModelAndView templatesMedication() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("js", new String[]{"common.js", "commonBedMap.js", "giveMedication.js"});
        return new ModelAndView("pages/main", model);
    }

    private TimingRange getTimingRange(String value) {
        Long id;
        try {
            id = Long.parseLong(value);
        } catch (Exception e) {
            id = null;
        }
        if (id != null) {
            List<TimingRange> list = this.timingRangeDao.findByIdtimingRange(id);
            if (list != null && list.size() == 1) {
                return list.get(0);
            }
        } else {
            String[] aux = value.trim().split("-");
            if (aux.length == 2) {
                SimpleDateFormat f = new SimpleDateFormat("HH:mm");
                try {
                    TimingRange result = new TimingRange();
                    result.setStartTime(f.parse(aux[0]));
                    result.setEndTime(f.parse(aux[1]));
                    return result;
                } catch (ParseException e) {
                }
            }
        }
        return null;
    }

    @RequestMapping("/getthreatmentNursing.htm")
    public ModelAndView getThreatment(HttpServletRequest request, String timingrange, String date) throws ParseException, WebContramedException {
        Patient pat = this.getPatient(request);
        if (pat != null) {
            TimingRange timingRange;
            if ((timingRange = getTimingRange(timingrange)) != null) {
                Date startTime = timingRange.getStartTime();
                Date endTime = timingRange.getEndTime();
                Date startDate = format3.parse(date);

                startDate.setHours(startTime.getHours());
                startDate.setMinutes(startTime.getMinutes());
                startDate.setSeconds(startTime.getSeconds());

                Date endDate;
                if (timingRange.getEndTime().getTime() - timingRange.getStartTime().getTime() >= 0) {
                    endDate = new Date(startDate.getTime());
                } else {
                    endDate = new Date(startDate.getTime());
                    endDate.setDate(endDate.getDate() + 1);
                }
                endDate.setHours(endTime.getHours());
                endDate.setMinutes(endTime.getMinutes());
                endDate.setSeconds(endTime.getSeconds());

                this.setStartDate(request, startDate);
                this.setEndDate(request, endDate);

                List<ThreatmentInfo> data = this.threatmentDao.getActiveThreatmentByNursing(pat.getId(), this.getStartDate(request), this.getEndDate(request));
                ExtjsStore result = new ExtjsStore();
                result.setData(data);
                return new ModelAndView(new JsonView(result));
            }
        }
        return null;
    }

    @RequestMapping("/give.htm")
    public ModelAndView give(String tagDose, TypeGivesHistoric type, String observation, HttpServletRequest request) {
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
                        this.giveThreatmentDao.giveDose(idDose, pat, this.getStartDate(request), this.getEndDate(request), this.getStaff(request), type, this.getPatientReadMethod(request), observation);
                        result.setSuccess(Boolean.TRUE);
                    } catch (WebContramedException e) {
                        result.setErrors(new com.abada.web.exjs.Error(e.getMessage(), e.getType().toString()));
                    } catch (Exception e) {
                        result.setErrors(new com.abada.web.exjs.Error("Error desconocido " + e + " " + e.getMessage()));
                    }
                } else {
                    result.setErrors(new com.abada.web.exjs.Error("Error en la extracci&oacute;n de la informaci&oacute;n del DataMatrix"));
                }
            } catch (Exception e) {
                result.setErrors(new com.abada.web.exjs.Error("Error en la decodificaci&oacute;n del DataMatrix " + e.getMessage()));
            }
        }
        return new ModelAndView(new JsonView(result));
    }

    @RequestMapping("/removeDose.htm")
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
                        this.giveThreatmentDao.removeDoseNursing(idDose, pat, this.getStartDate(request), this.getEndDate(request), this.getStaff(request), this.getPatientReadMethod(request), observation);
                        result.setSuccess(Boolean.TRUE);
                    } catch (WebContramedException e) {
                        result.setErrors(new com.abada.web.exjs.Error(e.getMessage(), e.getType().toString()));
                    } catch (Exception e) {
                        result.setErrors(new com.abada.web.exjs.Error("Error desconocido " + e + " " + e.getMessage()));
                    }
                } else {
                    result.setErrors(new com.abada.web.exjs.Error("Error en la extracci&oacute;n de la informaci&oacute;n del DataMatrix"));
                }
            } catch (Exception e) {
                result.setErrors(new com.abada.web.exjs.Error("Error en la decodificaci&oacute;n del DataMatrix " + e.getMessage()));
            }
        }
        return new ModelAndView(new JsonView(result));
    }

    @RequestMapping("/giveAll.htm")
    public ModelAndView giveAll(HttpServletRequest request) {
        Success result = new Success(false);
        Patient pat = this.getPatient(request);
        if (pat == null) {
            result.setErrors(new com.abada.web.exjs.Error("No ha seleccionado un paciente"));
        } else {
            try {
                this.giveThreatmentDao.giveAll(pat, this.getStartDate(request), this.getEndDate(request), this.getStaff(request), this.getPatientReadMethod(request));
                result.setSuccess(Boolean.TRUE);
            } catch (WebContramedException e) {
                result.setErrors(new com.abada.web.exjs.Error(e.getMessage(), e.getType().toString()));
            }
        }
        return new ModelAndView(new JsonView(result));
    }

    @RequestMapping("/giveHistoric.htm")
    public ModelAndView getHistoric(String search, HttpServletRequest request) throws ParseException {
        Map<String, String> searchFields = SearchField.parse(search);
        Long idOrderTiming = Long.parseLong(searchFields.get("idOrderTiming"));
        Date giveDate = format.parse(searchFields.get("giveTime"));

        ExtjsStore result = new ExtjsStore();
        List<ThreatmentInfoHistoric> resultData = this.threatmentDao.getGiveHistoric(idOrderTiming, giveDate);
        result.setData(resultData);
        return new ModelAndView(new JsonView(result));
    }

    @RequestMapping("/notgiven.htm")
    public ModelAndView notGiven(String tagDose, HttpServletRequest request) {
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
                        incidenceDao.generateMedicationNotGivenException(this.getStaff(request), idDose, pat);
                        result.setSuccess(Boolean.TRUE);
                    } catch (Exception e) {
                        result.setErrors(new com.abada.web.exjs.Error("Error desconocido " + e + " " + e.getMessage()));
                    }
                } else {
                    result.setErrors(new com.abada.web.exjs.Error("Error en la extracci&oacute;n de la informaci&oacute;n del DataMatrix"));
                }
            } catch (Exception e) {
                result.setErrors(new com.abada.web.exjs.Error("Error en la decodificaci&oacute;n del DataMatrix " + e.getMessage()));
            }
        }
        return new ModelAndView(new JsonView(result));
    }
}
