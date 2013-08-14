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

import com.abada.contramed.persistence.dao.principioActivo.PrincipioActivoDao;
import com.abada.contramed.persistence.entity.PrincipioActivo;
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
public class PrincipioActivoController {

    /**
     * Instancia de la interfaz principioActivoDao
     */
    private PrincipioActivoDao principioActivoDao;

    /**
     * Constructor
     * @param principioActivoDao
     */
    @Resource(name = "principioActivoDao")
    public void setPrincipioActivoDao(PrincipioActivoDao principioActivoDao) {
        this.principioActivoDao = principioActivoDao;
    }

    /**
     * Pantalla principal
     * @return
     */
    @RequestMapping("/principioActivo.htm")
    public ModelAndView principioActivo() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("js", new String[]{"commonPrincipioActivo.js", "principioActivo.js"});
        return new ModelAndView("pages/main", model);
    }

    /**
     * Grid que muestra los datos de {@link PrincipioActivo} <br/>
     * @param filter
     * @param dir
     * @param sort
     * @param limit
     * @param start
     * @return
     */
    @RequestMapping("/gridprincipioactivo.htm")
    public ModelAndView gridPrincipioActivo(String filter, String dir, String sort, Integer limit, Integer start) throws UnsupportedEncodingException {

        GridRequest_Extjs_v3 request = GridRequest_Extjs_v3Factory.parse(sort, dir, start, limit, filter);
        ExtjsStore result = new ExtjsStore();
        result.setTotal(principioActivoDao.loadSizeAll(request).intValue());
        List<PrincipioActivo> aux = principioActivoDao.loadAll(request);
        result.setData(aux);


        return new ModelAndView(new JsonView(result));
    }

    /**
     * Inserta en {@link PrincipioActivo} a partir de los datos recibidos <br/>
     * @param codigo
     * @param nombre
     * @param anulado
     * @param codigo_ca
     * @param composicion
     * @param denominacion
     * @param formula
     * @param peso
     * @param dosis
     * @param dosis_superficie
     * @param dosis_peso
     * @param u_dosis
     * @param u_dosis_superficie
     * @param u_dosis_peso
     * @param via
     * @param frecuencia
     * @param aditivo
     * @return
     */
    @RequestMapping("/insertPrincipioActivo.htm")
    public ModelAndView insertPrincipioActivo(String codigo, String nombre, String anulado, String codigo_ca, String composicion,
            String denominacion, String formula, String peso, String dosis, String dosis_superficie, String dosis_peso, String u_dosis,
            String u_dosis_superficie, String u_dosis_peso, Long via, Long frecuencia, String aditivo) throws Exception {

        Success result = new Success(false);
        try {
            if (codigo != null) {

                principioActivoDao.insertPrincipioActivo(codigo, nombre, anulado, codigo_ca, composicion,
                        denominacion, formula, peso, dosis, dosis_superficie, dosis_peso, u_dosis, u_dosis_superficie,
                        u_dosis_peso, via, frecuencia, aditivo);
                result.setSuccess(Boolean.TRUE);
            }
        } catch (PrimaryKeyException ex) {
            result.setErrors(new com.abada.web.exjs.Error(ex.getMessage()));
        } catch (Exception ex) {
            result.setErrors(new com.abada.web.exjs.Error("Inserción fallida."));
        }

        return new ModelAndView(new JsonView(result));

    }

    /**
     * Modifica en {@link PrincipioActivo} a partir de los datos recibidos <br/>
     * @param codigo
     * @param oldcodigo
     * @param nombre
     * @param anulado
     * @param codigo_ca
     * @param composicion
     * @param denominacion
     * @param formula
     * @param peso
     * @param dosis
     * @param dosis_superficie
     * @param dosis_peso
     * @param u_dosis
     * @param u_dosis_superficie
     * @param u_dosis_peso
     * @param via
     * @param frecuencia
     * @param aditivo
     * @return
     */
    @RequestMapping("/updatePrincipioActivo.htm")
    public ModelAndView updatePrincipioActivo(String codigo, String oldcodigo, String nombre, String anulado, String codigo_ca, String composicion,
            String denominacion, String formula, String peso, String dosis, String dosis_superficie, String dosis_peso, String u_dosis,
            String u_dosis_superficie, String u_dosis_peso, Long via, Long frecuencia, String aditivo) {

        Success result = new Success(false);
        try {
            if (codigo != null) {

                principioActivoDao.updatePrincipioActivo(codigo, oldcodigo, nombre, anulado, codigo_ca, composicion,
                        denominacion, formula, peso, dosis, dosis_superficie, dosis_peso, u_dosis, u_dosis_superficie,
                        u_dosis_peso, via, frecuencia, aditivo);
                result.setSuccess(Boolean.TRUE);
            }

        } catch (PrimaryKeyException ex) {
            result.setErrors(new com.abada.web.exjs.Error(ex.getMessage()));
        } catch (Exception ex) {
            result.setErrors(new com.abada.web.exjs.Error("Modificación fallida."));
        }

        return new ModelAndView(new JsonView(result));

    }

    /**
     * Borra en {@link PrincipioActivo} a partir del identificador recibido <br/>
     * @param id
     * @return
     */
    @RequestMapping("/deletePrincipioActivo.htm")
    public ModelAndView deletePrincipioActivo(String id) {
        Success result = new Success(false);
        try {
            principioActivoDao.delete(id);
            result.setSuccess(Boolean.TRUE);
        } catch (Exception ex) {
            result.setErrors(new com.abada.web.exjs.Error("Borrado fallido. Compruebe que el Principio Activo no este asociado a una Especialidad."));
        }

        return new ModelAndView(new JsonView(result));
    }
}
