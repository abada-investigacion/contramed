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

import com.abada.contramed.persistence.dao.recursoTag.RecursoTagDao;
import com.abada.contramed.persistence.entity.Recurso;
import com.abada.contramed.persistence.entity.RecursoTag;
import com.abada.contramedadministrationweb.entities.BedTag;
import com.abada.contramedadministrationweb.entities.RecursoBed;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest_Extjs_v3;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.factory.GridRequest_Extjs_v3Factory;
import com.abada.springframework.web.servlet.handler.interceptor.annotation.Role;
import com.abada.springframework.web.servlet.view.JsonView;
import com.abada.web.exjs.ExtjsStore;
import com.abada.web.exjs.Success;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Realiza la asociación modificacion de ella y borrado de una cama con su tag;
 * una misma cama puede tener asociados 2 tag diferentes
 * @author david
 */
@Controller
@Role({"SYSTEM_ADMINISTRATOR",
    "ADMINISTRATIVE"})
public class RecursoTagController {

    private RecursoTagDao recursoTagDao;
    /**
     * constante para numero de tag asociados a la misma cama
     */
    private static final int MAX_NUM_TAGS = 2;

    /**
     *
     * @param recursoTagDao
     */
    @Resource(name = "recursoTagDao")
    public void setDoseDao(RecursoTagDao recursoTagDao) {
        this.recursoTagDao = recursoTagDao;
    }

    /**
     * Devuelve la pantalla principal
     * @return
     */
    @RequestMapping("/recursoTag.htm")
    public ModelAndView recursoTag() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("js", new String[]{"common-administration.js", "recursoTag.js"});
        return new ModelAndView("pages/main", model);
    }

    /**
     * Devuelve el grid de Recurso Tag
     * Grid que muestra los datos de {@link recursoTag} <br/>
     * @param filter
     * @param dir
     * @param sort
     * @param limit
     * @param start
     * @return
     */
    @RequestMapping("/gridRecursoTag.htm")
    public ModelAndView gridRecursoTag(String filter, String dir, String sort, Integer limit, Integer start) throws UnsupportedEncodingException {
        List<BedTag> lbedtag = new ArrayList();
        GridRequest_Extjs_v3 request = GridRequest_Extjs_v3Factory.parse(sort, dir, start, limit, filter);
        ExtjsStore result = new ExtjsStore();
        result.setTotal(recursoTagDao.loadSizeAll(request).intValue());
        List<RecursoTag> aux = recursoTagDao.loadAll(request);
        for (RecursoTag r : aux) {
            RecursoBed recursobed = new RecursoBed();
            recursobed.setNr(r.getRecurso().getNr());
            BedTag bedtag = new BedTag(r.getId(), r.getRecurso().getIdRecurso(), recursobed, r.getTag());
            lbedtag.add(bedtag);
        }
        result.setData(lbedtag);
        return new ModelAndView(new JsonView(result));
    }

    /**
     * insertamos un RecursoTag en nuestra base de datos comprobando que no haya más de 2 recursos ya asociados
     * a un tag y que el tag sea siempre unico
     * @param idrecurso
     * @param tag
     * @return
     */
    @RequestMapping("/insertRecursoTag.htm")
    public ModelAndView insertRecursoTag(String idrecurso, String tag) {
        Success result = new Success(false);
        Long numberrecursotag = recursoTagDao.findByRecursoCount(Long.parseLong(idrecurso));
        try {
            if (numberrecursotag < MAX_NUM_TAGS) {
                RecursoTag recursotag = recursoTagDao.findtagunique(tag);
                if (recursotag != null) {
                    result.setErrors(new com.abada.web.exjs.Error("el tag: " + tag + " ya existe para la cama " + recursotag.getRecurso().getNr()));
                } else {
                    result.setSuccess(recursoTagDao.save(tag, Long.parseLong(idrecurso)));
                }
            } else {
                result.setErrors(new com.abada.web.exjs.Error("ya existen " + MAX_NUM_TAGS + " tag para esa cama"));
            }
        } catch (Exception e) {
            result.setErrors(new com.abada.web.exjs.Error("Error no se puede insertar Recurso Tag"));
        }
        return new ModelAndView(new JsonView(result));
    }

    /**
     * modificarmos recursoTag de nuestra base de datos comprobando que el tag es unico y que no hay mas de 2 tag para la misma cama
     * @param idrecurso
     * @param tagorigen
     * @param idrecursoorigen
     * @param tag
     * @return
     */
    @RequestMapping("/updateRecursoTag.htm")
    public ModelAndView updateRecursoTag(String idrecurso, String tagorigen, String idrecursoorigen, String tag) {
        RecursoTag recursotag = null;
        Long numberrecursotag = null;
        boolean error = false;
        Success result = new Success(true);
        try {
            if ((!idrecurso.equals(idrecursoorigen)) || (!tag.equals(tagorigen))) {
                if (!idrecurso.equals(idrecursoorigen)) {
                    numberrecursotag = recursoTagDao.findByRecursoCount(Long.parseLong(idrecurso));
                }
                if (numberrecursotag != null) {
                    if (numberrecursotag >= MAX_NUM_TAGS) {
                        error = true;
                    }
                }
                if (!error) {
                    if (!tag.equals(tagorigen)) {
                        recursotag = recursoTagDao.findtagunique(tag);
                    }
                    if (recursotag != null) {
                        result.setSuccess(Boolean.FALSE);
                        result.setErrors(new com.abada.web.exjs.Error("el tag: " + tag + " ya existe para la cama " + recursotag.getRecurso().getNr()));
                    } else {
                        RecursoTag rt = new RecursoTag();
                        Recurso r = new Recurso();
                        r.setIdRecurso(Long.parseLong(idrecurso));
                        rt.setTag(tag);
                        r.addRecursoTag(rt);
                        Long idrecursoTagOrigen = Long.parseLong(idrecursoorigen);
                        recursoTagDao.update(rt, idrecursoTagOrigen, tagorigen);
                    }
                } else {
                    result.setSuccess(Boolean.FALSE);
                    result.setErrors(new com.abada.web.exjs.Error("ya existen " + MAX_NUM_TAGS + " tag para esa cama"));
                }
            }
        } catch (Exception e) {
            result.setSuccess(Boolean.FALSE);
            result.setErrors(new com.abada.web.exjs.Error("Error a modificar Recurso Tag"));
        }
        return new ModelAndView(new JsonView(result));
    }

    /**
     * borramos un RecursoTag de nuestra base de datos
     * @param id
     * @return
     */
    @RequestMapping("/deleteRecursoTag.htm")
    public ModelAndView deleteRecursoTag(String id) {
        Success result = new Success(false);
        try {
            recursoTagDao.delete(Long.parseLong(id));
            result.setSuccess(Boolean.TRUE);
        } catch (Exception ex) {
            result.setErrors(new com.abada.web.exjs.Error(ex.getMessage()));
        }
        return new ModelAndView(new JsonView(result));
    }
}
