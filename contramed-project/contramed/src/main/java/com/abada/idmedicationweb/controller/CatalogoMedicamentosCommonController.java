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

import com.abada.contramed.persistence.dao.catalogoMedicamentos.CatalogoMedicamentosDao;
import com.abada.contramed.persistence.entity.CatalogoMedicamentos;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest_Extjs_v3;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.factory.GridRequest_Extjs_v3Factory;
import com.abada.springframework.web.servlet.handler.interceptor.annotation.Role;
import com.abada.springframework.web.servlet.view.JsonView;
import com.abada.web.exjs.ExtjsStore;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author mmartin
 *
 * Controlador de la entidad CatalogoMedicamentos. Realiza llamadas al DAO correspodiente <br/>
 * para recibir datos, insertarlos o modificarlos en la Base de Datos
 */
@Controller
@Role({"SYSTEM_ADMINISTRATOR",
    "NURSE_PHARMACY",
    "NURSE_PLANT_SUPERVISOR",
    "PHARMACIST"})
public class CatalogoMedicamentosCommonController {

    /**
     * Instancia de la interfaz DoseDao
     */
    private CatalogoMedicamentosDao catalogoMedicamentosDao;

    /**
     * Constructor
     * @param catalogoMedicamentosDao
     */
    @Resource(name = "catalogoMedicamentosDao")
    public void setCatalogoMedicamentosDao(CatalogoMedicamentosDao catalogoMedicamentosDao) {
        this.catalogoMedicamentosDao = catalogoMedicamentosDao;
    }

    /**
     * Grid que muestra los datos de {@link CatalogoMedicamentos} <br/>
     * @param filter Filtro para la JPQL
     * @param dir Direccion de la ordenacion de los resultados
     * @param sort Campo por el que se ordena
     * @param limit Maxima cantidad de resultado en la busqueda, util para paginacion
     * @param start Posicion de comienzo de los resultados de la busqueda, util para paginacion
     * @return
     */
    @RequestMapping("/gridcatmedicamentos.htm")
    public ModelAndView gridCatMedicamentos(String filter, String dir, String sort, Integer limit, Integer start) throws UnsupportedEncodingException {

        GridRequest_Extjs_v3 request = GridRequest_Extjs_v3Factory.parse(sort, dir, start, limit, filter);
        ExtjsStore result = new ExtjsStore();
        result.setTotal(catalogoMedicamentosDao.loadSizeAll(request).intValue());
        List<CatalogoMedicamentos> aux = catalogoMedicamentosDao.loadAll(request);
        result.setData(aux);

        return new ModelAndView(new JsonView(result));
    }

}
