/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abada.webcontramed.controller.observation;

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
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest_Extjs_v3;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.factory.GridRequest_Extjs_v3Factory;
import com.abada.springframework.web.servlet.handler.interceptor.annotation.Role;
import com.abada.springframework.web.servlet.view.JsonView;
import com.abada.web.exjs.ExtjsStore;
import com.abada.web.exjs.Success;
import com.abada.webcontramed.controller.CommonControllerConstants;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author maria
 */
@Controller
@Role({"SYSTEM_ADMINISTRATOR",
    "NURSE_PLANT",
    "NURSE_PLANT_SUPERVISOR"})
    
public class ObservationController extends CommonControllerConstants {
     private ObservationDao observationDao;

    @Resource(name = "observationDao")
    public void setObservationDao(ObservationDao observationDao) {
        this.observationDao = observationDao;
    }
    /**
     * Pantalla inicial de la gestión de Staff
     * @return
     */
    @RequestMapping("/observation.htm")
    public ModelAndView observation() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("js", new String[]{"common-observation.js", "observation.js"});
        return new ModelAndView("pages/main", model);
    }


    @RequestMapping("/observationwithStaff.htm")
    public ModelAndView observationWithStaff() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("js", new String[]{"common-observation.js", "observation-personal.js"});
        return new ModelAndView("pages/main", model);
    }


    @RequestMapping("/observationGrid.htm")
    public ModelAndView observationGrid(String filter, String dir, String sort, Integer limit, Integer start) throws UnsupportedEncodingException {

        GridRequest_Extjs_v3 request = GridRequest_Extjs_v3Factory.parse(sort, dir, start, limit, filter);
        ExtjsStore store = new ExtjsStore();
        store.setTotal(observationDao.loadSizeAll(request).intValue());
        store.setData(observationDao.loadAll(request));

        return new ModelAndView(new JsonView(store));
    }


    @RequestMapping("/observationwithStaffGrid.htm")
    public ModelAndView observationGridStaff(String filter, String dir, String sort, Integer limit, Integer start) throws UnsupportedEncodingException {

        GridRequest_Extjs_v3 request = GridRequest_Extjs_v3Factory.parse(sort, dir, start, limit, filter);
        ExtjsStore store = new ExtjsStore();
        store.setTotal(observationDao.loadSizeAll(request).intValue());
        store.setData(observationDao.loadAll(request));

        return new ModelAndView(new JsonView(store));
    }

    @RequestMapping("/insertobservation.htm")
    public ModelAndView insertObservation(Date eventTime, String observation, Long patient, Long staff,HttpServletRequest request) {
        Success result = new Success(false);  
        //Los campos obligatorios
        if(eventTime !=null && observation !=null && patient!=null && staff !=null){
            try {
                observationDao.insert(eventTime,observation,patient,staff);
                result.setSuccess(Boolean.TRUE);
            } catch (Exception ex) {
                result.setErrors(new com.abada.web.exjs.Error(ex.getMessage()));
            }
        }else{
            result.setErrors(new com.abada.web.exjs.Error("Todos los campos son obligatorios "));
        }

        return new ModelAndView(new JsonView(result));

    }

   

   

   
}
