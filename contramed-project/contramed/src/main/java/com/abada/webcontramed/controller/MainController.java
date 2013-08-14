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
import com.abada.contramed.persistence.entity.enums.TypeRole;
import com.abada.springframework.web.servlet.handler.interceptor.annotation.Role;
import com.abada.springframework.web.servlet.view.JsonView;
import com.abada.web.exjs.ExtjsStore;
import com.abada.webcontramed.entities.PatientInfo;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 *
 * @author katsu
 */
@Controller
@Role({"SYSTEM_ADMINISTRATOR",
    "ADMINISTRATIVE",
    "NURSE_PLANT",
    "NURSE_PLANT_SUPERVISOR",
    "NURSE_PHARMACY",
    "PHARMACIST"})
public class MainController extends CommonControllerConstants{
   
    @RequestMapping("/main.htm")
    public ModelAndView main(HttpServletRequest request) {
        TypeRole role;
        if ((role = this.getStaff(request).getRole())!=null){
            switch(role){
                case NURSE_PHARMACY:
                case PHARMACIST:
                    return new ModelAndView(new RedirectView("preparebox.htm"));
                case NURSE_PLANT:
                case NURSE_PLANT_SUPERVISOR:
                    return new ModelAndView(new RedirectView("nursing.htm"));
                case ADMINISTRATIVE:
                case SYSTEM_ADMINISTRATOR:
            }
        }
        return new ModelAndView("pages/main");
    }

    
    /***
     * Devuelve la informacion del paciente
     * @param bed
     * @param request
     * @return
     */
    @RequestMapping("/getPatientInfo.htm")
    public ModelAndView getPatient(String bed, HttpServletRequest request) {
        Patient pat = this.getPatient(request);
        if (pat != null) {
            ExtjsStore result = new ExtjsStore();
            List<PatientInfo> resultData = new ArrayList<PatientInfo>();
            resultData.add(PatientInfo.getPatientInfo(pat));
            result.setData(resultData);
            return new ModelAndView(new JsonView(result));
        }
        return null;
    }
}
