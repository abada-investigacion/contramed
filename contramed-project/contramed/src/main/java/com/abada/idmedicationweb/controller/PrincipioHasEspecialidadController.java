/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.idmedicationweb.controller;

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

import com.abada.contramed.persistence.entity.PrincipioHasEspecialidad;
import com.abada.contramed.persistence.dao.principioHasEspecialidad.PrincipioHasEspecialidadDao;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest_Extjs_v3;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.factory.GridRequest_Extjs_v3Factory;
import com.abada.springframework.web.servlet.handler.interceptor.annotation.Role;
import com.abada.springframework.web.servlet.view.JsonView;
import com.abada.web.exjs.ExtjsStore;
import com.abada.web.exjs.Success;
import com.abada.webcontramed.exception.PrimaryKeyException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author mmartin
 */
@Controller
@Role({"SYSTEM_ADMINISTRATOR",
    "NURSE_PHARMACY",
    "PHARMACIST"})
public class PrincipioHasEspecialidadController {

    /**
     * Instancia de la interfaz principioHasEspecialidadDao
     */
    private PrincipioHasEspecialidadDao principioHasEspecialidadDao;

    /**
     * Constructor
     * @param principioHasEspecialidadDao
     */
    @Resource(name = "principioHasEspecialidadDao")
    public void setPrincipioHasEspecialidadDao(PrincipioHasEspecialidadDao principioHasEspecialidadDao) {
        this.principioHasEspecialidadDao = principioHasEspecialidadDao;
    }

    /**
     * Pantalla principal
     * @return
     */
    @RequestMapping("/principioHasEspecialidad.htm")
    public ModelAndView principioHasEspecialidad() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("js", new String[]{"commonPrincipioHasEspecialidad.js", "commonMedicamentos.js", "commonPrincipioActivo.js", "principioHasEspecialidad.js"});
        return new ModelAndView("pages/main", model);
    }

    /**
     * Grid que muestra los datos de {@link PrincipioHasEspecialidad} <br/>
     * @param filter
     * @param dir
     * @param sort
     * @param limit
     * @param start
     * @return
     */
    @RequestMapping("/gridprincipiohasespecialidad.htm")
    public ModelAndView gridPrincipioHasEspecialidad(String filter, String dir, String sort, Integer limit, Integer start) throws UnsupportedEncodingException {

        GridRequest_Extjs_v3 request = GridRequest_Extjs_v3Factory.parse(sort, dir, start, limit, filter);
        ExtjsStore result = new ExtjsStore();
        result.setTotal(principioHasEspecialidadDao.loadSizeAll(request).intValue());
        List<PrincipioHasEspecialidad> aux = principioHasEspecialidadDao.loadAll(request);
        result.setData(aux);


        return new ModelAndView(new JsonView(result));
    }

    /**
     * Inserta en {@link PrincipioHasEspecialidad} a partir de los datos recibidos <br/>
     * @param codigoespec
     * @param codigoprincipio
     * @param id
     * @param composicion
     * @param id_unidad
     * @return
     */
    @RequestMapping("/insertPrincipioHasEspecialidad.htm")
    public ModelAndView insertPrincipioHasESpecialidad(String codigoespec, String codigoprincipio, Long id, String composicion, String id_unidad) {

        Success result = new Success(false);

        if (id != null && !codigoespec.equals("") && !codigoprincipio.equals("")) {
            try {
                principioHasEspecialidadDao.insertPrincipioHasEspecialidad(codigoespec, codigoprincipio, id, composicion, id_unidad);
                result.setSuccess(Boolean.TRUE);
            } catch (PrimaryKeyException ex) {
                 result.setErrors(new com.abada.web.exjs.Error(ex.getMessage()));
            } catch (Exception e) {
                result.setErrors(new com.abada.web.exjs.Error("Inserción fallida"));
            }
        }
        return new ModelAndView(new JsonView(result));

    }

    /**
     * Modifica en {@link PrincipioHasEspecialidad} a partir de los datos recibidos <br/>
     * @param oldid
     * @param oldcodigoespec
     * @param codigoespec
     * @param codigoprincipio
     * @param id
     * @param composicion
     * @param id_unidad
     * @return
     */
    @RequestMapping("/updatePrincipioHasEspecialidad.htm")
    public ModelAndView updatePrincipioHasESpecialidad(Long oldid, String oldcodigoespec, String codigoespec, String codigoprincipio, Long id, String composicion, String id_unidad) {

        Success result = new Success(false);

        if (id != null && !codigoespec.equals("") && !codigoprincipio.equals("")) {
            try {
                principioHasEspecialidadDao.updatePrincipioHasEspecialidad(oldid, oldcodigoespec, codigoespec, codigoprincipio, id, composicion, id_unidad);
                result.setSuccess(Boolean.TRUE);
            } catch (PrimaryKeyException ex) {
                 result.setErrors(new com.abada.web.exjs.Error(ex.getMessage()));
            } catch (Exception e) {
                result.setErrors(new com.abada.web.exjs.Error("Modificación fallida"));
            }
        }
        return new ModelAndView(new JsonView(result));
    }

    /**
     * Borra en {@link PrincipioHasEspecialidad} a partir del identificador recibido <br/>
     * @param id
     * @return
     */
    @RequestMapping("/deletePrincipioHasEspecialidad.htm")
    public ModelAndView deletePrincipioHasEspecialidad(Long id) {
        Success result = new Success(false);
        try {
            principioHasEspecialidadDao.delete(id);
            result.setSuccess(Boolean.TRUE);
        } catch (Exception ex) {
            result.setErrors(new com.abada.web.exjs.Error("Borrado fallido."));

        }

        return new ModelAndView(new JsonView(result));
    }
}
