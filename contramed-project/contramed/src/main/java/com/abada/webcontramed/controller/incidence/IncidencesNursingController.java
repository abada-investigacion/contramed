/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.webcontramed.controller.incidence;

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

import com.abada.springframework.web.servlet.handler.interceptor.annotation.Role;
import com.abada.webcontramed.controller.CommonControllerConstants;
import java.util.HashMap;
import java.util.Map;
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
public class IncidencesNursingController extends CommonControllerConstants {

    /**
     * Devuelve la pantalla principal
     * @return
     */
    @RequestMapping("/givesIncidences.htm")
    public ModelAndView givesIncidences() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("js", new String[]{"common-statics.js", "give-incidences.js"});
        return new ModelAndView("pages/main", model);
    }

    /**
     * Devuelve la pantalla principal
     * @return
     */
    @RequestMapping("/givesIncidencesByStaff.htm")
    public ModelAndView givesIncidencesByStaff() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("js", new String[]{"common-statics.js", "give-incidences-personal.js"});
        return new ModelAndView("pages/main", model);
    }
}
