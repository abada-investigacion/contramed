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

import com.abada.contramed.persistence.dao.catalogoHasTemplates.CatalogoHasTemplatesDao;
import com.abada.contramed.persistence.entity.CatalogoHasTemplates;
import com.abada.contramed.persistence.entity.CatalogoMedicamentos;
import com.abada.contramed.persistence.entity.TemplatesMedication;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest_Extjs_v3;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.factory.GridRequest_Extjs_v3Factory;
import java.io.UnsupportedEncodingException;
import org.springframework.stereotype.Controller;
import com.abada.springframework.web.servlet.handler.interceptor.annotation.Role;
import com.abada.springframework.web.servlet.view.JsonView;
import com.abada.web.exjs.ExtjsStore;
import com.abada.web.exjs.Success;
import com.abada.webcontramed.exception.PrimaryKeyException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author mmartin
 *
 * Controlador de la entidad CatalogoHasTemplates. Realiza llamadas al DAO correspodiente <br/>
 * para recibir datos, insertarlos o modificarlos en la Base de Datos
 */
@Controller
@Role({"SYSTEM_ADMINISTRATOR",
    "NURSE_PHARMACY",
    "PHARMACIST"})
public class CatalogoHasTemplatesController {

    /**
     * Instancia de la interfaz CatalogoHasTemplatesDao
     */
    private CatalogoHasTemplatesDao catalogoHasTemplatesDao;

    /**
     *Constructor
     * @param catalogoHasTemplatesDao
     */
    @Resource(name = "catalogoHasTemplatesDao")
    public void setCatalogoHasTemplatesDao(CatalogoHasTemplatesDao catalogoHasTemplatesDao) {
        this.catalogoHasTemplatesDao = catalogoHasTemplatesDao;
    }

    /**
     * Pantalla principal
     * @return
     */
    @RequestMapping("/catalogoHasTemplates.htm")
    public ModelAndView templatesMedication() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("js", new String[]{"commonTemplates.js", "commonMedicamentos.js", "commonCatalogoHasTemplates.js", "catalogoHasTemplates.js"});
        return new ModelAndView("pages/main", model);
    }

    /**
     * Grid que muestra los datos de {@link CatalogoHasTemplates} <br/>
     * @param filter Filtro para la JPQL
     * @param dir Direccion de la ordenacion de los resultados
     * @param sort Campo por el que se ordena
     * @param limit Maxima cantidad de resultado en la busqueda, util para paginacion
     * @param start Posicion de comienzo de los resultados de la busqueda, util para paginacion
     * @return
     */
    @RequestMapping("/gridcatalogohastemplates.htm")
    public ModelAndView gridCatalogoHasTemplates(String filter, String dir, String sort, Integer limit, Integer start) throws UnsupportedEncodingException {

        GridRequest_Extjs_v3 request = GridRequest_Extjs_v3Factory.parse(sort, dir, start, limit, filter);
        ExtjsStore result = new ExtjsStore();
        result.setTotal(catalogoHasTemplatesDao.loadSizeAll(request).intValue());
        List<CatalogoHasTemplates> aux = catalogoHasTemplatesDao.loadAll(request);
        result.setData(aux);


        return new ModelAndView(new JsonView(result));
    }

    /**
     * Inserta en {@link CatalogoHasTemplates} a partir de los datos recibidos <br/>
     * @param codigo Identificador de {@link CatalogoMedicamentos}
     * @param idtemplate Identificador de {@link TemplatesMedication}
     * @return
     */
    @RequestMapping("/insertcatalogohastemplates.htm")
    public ModelAndView insertCatalogoHasTemplates(String codigo, Long idtemplate) {

        Success result = new Success(false);

        if (idtemplate != null && !codigo.equals("")) {
            try {
                catalogoHasTemplatesDao.insertCatalogoHasTemplates(codigo, idtemplate);
                result.setSuccess(Boolean.TRUE);
            } catch (PrimaryKeyException ex) {
                result.setErrors(new com.abada.web.exjs.Error(ex.getMessage()));
            } catch (Exception ex) {
                result.setErrors(new com.abada.web.exjs.Error("Inserción fallida."));
            }
        }
        return new ModelAndView(new JsonView(result));

    }

    /**
     * Modifica en {@link CatalogoHasTemplates} a partir de los datos recibidos <br/>
     * @param oldcodigo Identificador de {@link CatalogoMedicamentos} antes de la modificacion
     * @param codigo Identificador de {@link CatalogoMedicamentos}
     * @param idtemplate Identificador de {@link TemplatesMedication}
     * @return
     */
    @RequestMapping("/updatecatalogohastemplates.htm")
    public ModelAndView updateCatalogoHasTemplates(String oldcodigo, String codigo, Long idtemplate) {

        Success result = new Success(false);

        if (idtemplate != null && !codigo.equals("")) {
            try {
                catalogoHasTemplatesDao.updateCatalogoHasTemplates(oldcodigo, codigo, idtemplate);
                result.setSuccess(Boolean.TRUE);
            } catch (PrimaryKeyException ex) {
                result.setErrors(new com.abada.web.exjs.Error(ex.getMessage()));
            } catch (Exception ex) {
                result.setErrors(new com.abada.web.exjs.Error("Modificación fallida."));
            }
        }

        return new ModelAndView(new JsonView(result));

    }
}
