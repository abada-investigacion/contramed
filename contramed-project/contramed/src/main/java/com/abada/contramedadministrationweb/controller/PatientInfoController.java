/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.contramedadministrationweb.controller;

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

import com.abada.contramed.persistence.dao.patient.PatientDao;
import com.abada.contramed.persistence.dao.patientId.PatientIdDao;
import com.abada.contramed.persistence.dao.patientTag.PatientTagDao;
import com.abada.contramed.persistence.entity.Patient;
import com.abada.contramed.persistence.entity.PatientId;
import com.abada.contramedadministrationweb.entities.PatientTagInfo;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest_Extjs_v3;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.factory.GridRequest_Extjs_v3Factory;
import com.abada.springframework.web.servlet.handler.interceptor.annotation.Role;
import com.abada.springframework.web.servlet.view.JsonView;
import com.abada.web.exjs.ExtjsStore;
import com.abada.web.exjs.Success;
import com.abada.webcontramed.controller.CommonControllerConstants;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Realiza las comprobaciones de la existencia de los pacientes, al igual que
 * la inserción,modificación y borrado del tag asociado a un paciente
 * @author maria
 */
@Controller
@Role({"SYSTEM_ADMINISTRATOR",
    "ADMINISTRATIVE"})
public class PatientInfoController extends CommonControllerConstants {

    private PatientIdDao patientIdDao;
    private PatientTagDao patientTagDao;
    private PatientDao patientDao;

    @Resource(name = "patientDao")
    public void setPatientDao(PatientDao patientDao) {
        this.patientDao = patientDao;
    }

    @Resource(name = "patientTagDao")
    public void setPatientTagDao(PatientTagDao patientTagDao) {
        this.patientTagDao = patientTagDao;
    }

    @Resource(name = "patientIdDao")
    public void setPatientIdDao(PatientIdDao patientIdDao) {
        this.patientIdDao = patientIdDao;
    }

    /**
     * Pantalla principal
     * @return
     */
    @RequestMapping("/patientTag.htm")
    public ModelAndView patientTag() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("js", new String[]{"common-administration.js", "patientTag.js"});
        return new ModelAndView("pages/main", model);
    }

    /**
     * Comprobamos que el identificador de paciente es correcto por identificador y valor
     * @param identif: identificador del paciente, sea DNI,Pasaporte,Número de historia clínica,etc
     * @param valor: valor de ese identificador,por ejemplo, DNI:123898876P
     * @param request
     * @return
     */
    @RequestMapping("/codevalidation.htm")
    public ModelAndView isCorrectCodeIdent(String identif, String valor, HttpServletRequest request) {
        // Lista de los pacientes con el identificador indicado y el tipo de identificacion
        List<PatientId> p = patientIdDao.findByValue(valor);
        Success result = new Success(false);
        if (p != null && p.size() > 0) {
            for (int i = 0; i < p.size(); i++) {
                int det = p.get(i).getType().getId();
                int numEntero = Integer.parseInt(identif);
                //True si esta muerto 
                if (det == numEntero && p.get(i).getPatientId().getExitus() == false) {
                    this.setPatientId(request, p.get(i));
                    result.setSuccess(Boolean.TRUE);
                }
            }
        } else {
            this.setPatientId(request, null);
            result.setSuccess(Boolean.FALSE);
            result.setErrors(new com.abada.web.exjs.Error("El paciente no existe"));
        }

        return new ModelAndView(new JsonView(result));
    }

    /**
     * Compruebo que existe ese paciente al ser seleccionado en el grid, para mostrar su informacion
     * @param identif: identificador del paciente, sea DNI,Pasaporte,Número de historia clínica,etc
     * @param valor: valor de ese identificador,por ejemplo, DNI:123898876P
     * @param request
     * @return
     */
    @RequestMapping("/codevalidationid.htm")
    public ModelAndView isCorrectCodeId(String id, HttpServletRequest request) {
        // Lista de los pacientes seleccionados en el grid
        Long patient = Long.parseLong(id);
        List<PatientId> p = patientIdDao.findByPatientId(patient);
        //Pueden aparecer varios patientId, para el mismo id de PatientId
        //solo guardaremos la informacion del primero que aparezca
        if (p != null && p.size() > 0) {
            this.setPatientId(request, p.get(0));
        }
        Success result = new Success(p != null);

        return new ModelAndView(new JsonView(result));
    }

    /**
     * Cargo todos los pacientes que estan en cama
     * Grid que muestra los datos de {@link patientTag} <br/>
     * @param request
     * @param value
     * @return
     */
    @RequestMapping("/gridinicial.htm")
    public ModelAndView gridPatientInfoInicial(String filter, String dir, String sort, Integer limit, Integer start) throws UnsupportedEncodingException {

        GridRequest_Extjs_v3 request = GridRequest_Extjs_v3Factory.parse(sort, dir, start, limit, filter);
        ExtjsStore store = new ExtjsStore();
        List<Patient> pat = patientDao.loadAll(request);
        store.setTotal(patientDao.loadSizeAll(request).intValue()); 
        store.setData(pat);

        return new ModelAndView(new JsonView(store));

    }

    /**
     * Cargo la informacion del paciente buscada por identificador y valor o por nombre y apellidos
     * Grid que muestra los datos de {@link patientTag} <br/>
     * @param request
     * @param request
     * @param patientid: identificador del paciente
     * @return
     */
    @RequestMapping("/gridpatientinfo.htm")
    public ModelAndView gridPatientInfo(HttpServletRequest request) {

        //Recogo el paciente
        PatientId p = this.getPatientId(request);
        //Busco al paciente para obtener los datos de la BBDD
        ExtjsStore result = new ExtjsStore();
        List<PatientId> pat = new ArrayList<PatientId>();
        List<PatientTagInfo> resultData = new ArrayList<PatientTagInfo>();
        if (p != null) {
            pat = patientIdDao.findById(p.getId());
            if (pat != null && pat.size() > 0) {
                resultData.add(PatientTagInfo.getPatientInfo(pat.get(0)));
                result.setData(resultData);
                result.setTotal(resultData.size());
            }
        }
        return new ModelAndView(new JsonView(result));
    }

    /**
     * Inserto un nuevo Tag asociado al paciente
     * @param request
     * @param tag
     * @return
     */
    @RequestMapping("/insertpatienttag.htm")
    public ModelAndView insertPatientTag(HttpServletRequest request, String tag) {
        //Obtengo la informacion del paciente
        PatientId pt = this.getPatientId(request);
        Long idenpati = pt.getPatientId().getId();
        Patient pat = pt.getPatientId();
        Success result = new Success(false);
        try {
            patientTagDao.insertPatientTag(idenpati, pat, tag);
            result.setSuccess(Boolean.TRUE);
        } catch (Exception ex) {
            result.setErrors(new com.abada.web.exjs.Error(ex.getMessage()));
        }
        return new ModelAndView(new JsonView(result));

    }
  

    /**
     * Borrado del tag del paciente, el paciente continua en la base de datos, pero no su Tag
     * @param request
     * @param tag
     * @return
     */
    @RequestMapping("/removepatienttag.htm")
    public ModelAndView removePatientTag(HttpServletRequest request) {

        PatientId pt = this.getPatientId(request);
        Success result = new Success(false);
        Long patientid = pt.getPatientId().getId();
        if (patientid != null) {
            try{
             patientTagDao.removePatientTag(patientid);
             result.setSuccess(Boolean.TRUE);
            }catch(Exception ex){
                result.setErrors(new com.abada.web.exjs.Error(ex.getMessage()));
            }
        }else{
             result.setErrors(new com.abada.web.exjs.Error("No se puede eliminar el paciente "));
        }
       
        return new ModelAndView(new JsonView(result));
    }
}
