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
import com.abada.contramed.persistence.dao.templatesMedication.TemplatesMedicationDao;
import com.abada.contramed.persistence.entity.TemplatesMedication;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest_Extjs_v3;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.factory.GridRequest_Extjs_v3Factory;
import com.abada.springframework.web.servlet.handler.interceptor.annotation.Role;
import com.abada.springframework.web.servlet.view.JsonView;
import com.abada.utils.file.FileManager;
import com.abada.web.exjs.ExtjsStore;
import com.abada.web.exjs.Success;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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
    "NURSE_PHARMACY",
    "PHARMACIST"})
public class TemplatesMedicationController {

    /**
     * Instancia de la interfaz CatalogoHasTemplatesDao
     */
    private CatalogoHasTemplatesDao catalogoHasTemplatesDao;
    /**
     * Instancia de la interfaz TemplatesMadicationDAO
     */
    private TemplatesMedicationDao templatesMedicationDao;
    /**
     * Instancia la clase FileManager
     */
    private FileManager fileManager;

    /**
     * Constructor
     * @param catalogoHasTemplatesDao
     */
    @Resource(name = "catalogoHasTemplatesDao")
    public void setCatalogoHasTemplatesDao(CatalogoHasTemplatesDao catalogoHasTemplatesDao) {
        this.catalogoHasTemplatesDao = catalogoHasTemplatesDao;
    }

    /**
     * Constructor
     * @param templatesMedicationDao
     */
    @Resource(name = "templatesMedicationDao")
    public void setTemplatesMedicationDao(TemplatesMedicationDao templatesMedicationDao) {
        this.templatesMedicationDao = templatesMedicationDao;
    }

    /**
     * Constructor
     * @param fileManager
     */
    @Resource(name = "fileManager")
    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    /**
     * Pantalla principal
     * @return
     */
    @RequestMapping("/templatesMedication.htm")
    public ModelAndView templatesMedication() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("js", new String[]{"commonTemplates.js", "templatesMedication.js"});
        return new ModelAndView("pages/main", model);
    }

    /**
     * Grid que muestra los datos de {@link TemplatesMedication} <br/>
     * @param filter Filtro para la JPQL
     * @param dir Direccion de la ordenacion de los resultados
     * @param sort Campo por el que se ordena
     * @param limit Maxima cantidad de resultado en la busqueda, util para paginacion
     * @param start Posicion de comienzo de los resultados de la busqueda, util para paginacion
     * @return
     */
    @RequestMapping("/gridtempmedication.htm")
    public ModelAndView gridtempmedication(String filter, String dir, String sort, Integer limit, Integer start) throws UnsupportedEncodingException {

        GridRequest_Extjs_v3 request = GridRequest_Extjs_v3Factory.parse(sort, dir, start, limit, filter);
        ExtjsStore result = new ExtjsStore();
        
        result.setTotal(templatesMedicationDao.loadSizeAll(request).intValue());
        List<TemplatesMedication> aux = templatesMedicationDao.loadAll(request);
        result.setData(aux);

        return new ModelAndView(new JsonView(result));
    }

    /**
     * Inserta en {@link TemplatesMedication} a partir de los datos recibidos <br/>
     * @param template Nombre de la plantilla
     * @param file Fichero de plantilla
     * @param style Fichero de estilo
     * @return
     * @throws IOException
     */
    @RequestMapping("/insertTemplate.htm")
    public ModelAndView insertTemplate(String template, @RequestParam("file") MultipartFile file, @RequestParam("style") MultipartFile style) throws IOException {

        Success result = new Success(false);

        if (!template.equals("") && !file.getOriginalFilename().equals("") && !style.getOriginalFilename().equals("")) {
            byte[] data = (byte[]) file.getBytes();
            UUID uuid = this.fileManager.setFile(data);

            byte[] dataStyle = (byte[]) style.getBytes();
            UUID uuidStyle = this.fileManager.setFile(dataStyle);

            try {
                templatesMedicationDao.insertTemplate(template, uuid.toString(), uuidStyle.toString());
                result.setSuccess(Boolean.TRUE);
            } catch (Exception ex) {
                result.setErrors(new com.abada.web.exjs.Error(ex.getMessage()));
            }
        }

        return new ModelAndView(new JsonView(result));

    }

    /**
     * Modifica en {@link TemplatesMedication} a partir de los datos recibidos <br/>
     * @param idtemplatesmedication Identificador de {@link TemplatesMedication}
     * @param template Nombre de la plantilla
     * @param olduuid UUID bajo el que esta almacenado el fichero de plantilla antes de la modificacion
     * @param olduuidStyle UUID bajo el que esta almacenado el fichero de estilos antes de la modificacion
     * @param file Fichero de plantilla
     * @param style Fichero de estilo
     * @return
     * @throws IOException
     */
    @RequestMapping("/updateTemplate.htm")
    public ModelAndView updateTemplate(Long idtemplatesmedication, String template, String olduuid, String olduuidStyle, @RequestParam("file") MultipartFile file, @RequestParam("style") MultipartFile style) throws IOException {

        Success result = new Success(false);

        String uuid = null;
        String uuidStyle = null;

        if (!template.equals("")) {

            if (file.getOriginalFilename().equals("")) {

                uuid = olduuid;

            } else {

                this.fileManager.deleteFile(olduuid);
                byte[] data = (byte[]) file.getBytes();
                UUID uuidaux = this.fileManager.setFile(data);
                uuid = uuidaux.toString();
            }

            if (style.getOriginalFilename().equals("")) {

                uuidStyle = olduuidStyle;

            } else {

                this.fileManager.deleteFile(olduuidStyle);
                byte[] dataStyle = (byte[]) style.getBytes();
                UUID uuidauxStyle = this.fileManager.setFile(dataStyle);
                uuidStyle = uuidauxStyle.toString();
            }

            try {
                templatesMedicationDao.updateTemplatesMedication(idtemplatesmedication, template, uuid.toString(), uuidStyle.toString());
                result.setSuccess(Boolean.TRUE);
            } catch (Exception ex) {
                result.setErrors(new com.abada.web.exjs.Error(ex.getMessage()));
            }
        }

        return new ModelAndView(new JsonView(result));

    }
}
