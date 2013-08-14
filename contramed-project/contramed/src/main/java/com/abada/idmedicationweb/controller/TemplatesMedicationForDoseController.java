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
import com.abada.contramed.persistence.entity.TemplatesMedication;
import com.abada.springframework.web.servlet.handler.interceptor.annotation.Role;
import com.abada.springframework.web.servlet.view.JsonView;
import com.abada.web.exjs.ExtjsStore;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author mmartin
 *
 * Controlador de la entidad TemplatesMedication. Realiza llamadas al DAO correspodiente <br/>
 * para recibir datos, insertarlos o modificarlos en la Base de Datos
 */
@Controller
@Role({"SYSTEM_ADMINISTRATOR",
    "NURSE_PLANT_SUPERVISOR",
    "NURSE_PHARMACY",
    "PHARMACIST"})
public class TemplatesMedicationForDoseController {

    /**
     * Instancia de la interfaz CatalogoHasTemplatesDao
     */
    private CatalogoHasTemplatesDao catalogoHasTemplatesDao;

    /**
     * Constructor
     * @param catalogoHasTemplatesDao
     */
    @Resource(name = "catalogoHasTemplatesDao")
    public void setCatalogoHasTemplatesDao(CatalogoHasTemplatesDao catalogoHasTemplatesDao) {
        this.catalogoHasTemplatesDao = catalogoHasTemplatesDao;
    }

    /**
     * Devuelve la plantilla perteneciente a una especialidad
     * @param codigo Codigo de la especialidad
     * @return
     * @throws IOException
     */
    @RequestMapping("/getTemplate.htm")
    public ModelAndView getTemplate(String codigo) throws IOException {

        List<TemplatesMedication> tm = this.catalogoHasTemplatesDao.getTemplateFromEspecialidad(codigo);
        ExtjsStore result = new ExtjsStore();

        result.setData(tm);

        return new ModelAndView(new JsonView(result));

    }
}
