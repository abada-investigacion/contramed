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
import com.abada.contramed.persistence.dao.catalogoMedicamentos.CatalogoMedicamentosDao;
import com.abada.contramed.persistence.dao.dose.DoseDao;
import com.abada.contramed.persistence.dao.principioActivo.PrincipioActivoDao;
import com.abada.contramed.persistence.entity.CatalogoMedicamentos;
import com.abada.contramed.persistence.entity.Dose;
import com.abada.contramed.persistence.entity.Staff;
import com.abada.contramed.persistence.entity.TemplatesMedication;
import com.abada.jasperCommons.units.MeasureUnit;
import com.abada.printDose.*;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest_Extjs_v3;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.factory.GridRequest_Extjs_v3Factory;
import com.abada.springframework.web.servlet.handler.interceptor.annotation.Role;
import com.abada.springframework.web.servlet.view.JsonView;
import com.abada.springframework.web.servlet.view.OutputStreamView;
import com.abada.utils.file.FileManager;
import com.abada.web.exjs.ExtjsStore;
import com.abada.web.exjs.Success;
import com.abada.webcontramed.controller.CommonControllerConstants;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool.ObjectPool;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 *
 * @author mmartin
 *
 * Controlador de la entidad Dose. Realiza llamadas al DAO correspodiente <br/>
 * para recibir datos, insertarlos o modificarlos en la Base de Datos
 */
@Controller
@Role({"SYSTEM_ADMINISTRATOR",
    "NURSE_PLANT_SUPERVISOR",
    "NURSE_PHARMACY",
    "PHARMACIST"})
public class DoseController extends CommonControllerConstants {

    private SimpleDateFormat dateFormat1;
    private SimpleDateFormat dateFormat2;
    private SimpleDateFormat dateFormat3;

    /**
     * Constructor vacio
     */
    public DoseController() {
        dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat2 = new SimpleDateFormat("dd-MMMM-yyyy");
        dateFormat3 = new SimpleDateFormat("MM-yy");

    }
    /**
     * Constante que crea una instancia a un Log
     */
    private static final Log logger = LogFactory.getLog(DoseController.class);
    /**
     * Instancia de la interfaz CatalogoHasTemplatesDao
     */
    private CatalogoHasTemplatesDao catalogoHasTemplatesDao;
    /**
     * Instancia de la interfaz CatalogoMedicamentosDao
     */
    private CatalogoMedicamentosDao catalogoMedicamentosDao;
    /**
     * Instancia de la interfaz DoseDao
     */
    private DoseDao doseDao;
    /**
     * Instancia de la clase FileManager
     */
    private FileManager fileManager;
    /**
     * Instancia de la interfaz PrincipioActivoDao
     */
    private PrincipioActivoDao principioActivoDao;
    private ObjectPool poolGenLabel;

    @Resource(name = "poolGenLabel")
    public void setPoolGenLabel(ObjectPool poolGenLabel) {
        this.poolGenLabel = poolGenLabel;
    }

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
     * @param catalogoMedicamentosDao
     */
    @Resource(name = "catalogoMedicamentosDao")
    public void setCatalogoMedicamentosDao(CatalogoMedicamentosDao catalogoMedicamentosDao) {
        this.catalogoMedicamentosDao = catalogoMedicamentosDao;
    }

    /**
     * Constructor
     * @param doseDao
     */
    @Resource(name = "doseDao")
    public void setDoseDao(DoseDao doseDao) {
        this.doseDao = doseDao;
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
    @RequestMapping("/dose.htm")
    public ModelAndView dose() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("js", new String[]{"commonMedicamentos.js", "commonTemplates.js", "commonDose.js", "dose.js"});
        return new ModelAndView("pages/main", model);
    }

    /**
     * Grid que muestra los datos de {@link Dose} <br/>
     * @param filter Filtro para la JPQL
     * @param dir Direccion de la ordenacion de los resultados
     * @param sort Campo por el que se ordena
     * @param limit Maxima cantidad de resultado en la busqueda, util para paginacion
     * @param start Posicion de comienzo de los resultados de la busqueda, util para paginacion
     * @return
     */
    @RequestMapping("/griddose.htm")
    public ModelAndView gridDose(String filter, String dir, String sort, Integer limit, Integer start) throws UnsupportedEncodingException {

        GridRequest_Extjs_v3 request = GridRequest_Extjs_v3Factory.parse(sort, dir, start, limit, filter);
        ExtjsStore result = new ExtjsStore();
        result.setTotal(doseDao.loadSizeAll(request).intValue());
        List<Dose> aux = doseDao.loadAll(request);
        result.setData(aux);


        return new ModelAndView(new JsonView(result));
    }

    /**
     * Inserta en {@link Dose} a partir de los datos recibidos <br/>
     * @param codigo Especialidad a la que pertenece la dosis
     * @param batch Lote al que pertenece una dosis
     * @param expiration_date Fecha de caducidad
     * @param give_amount Cantidad administrada
     * @param idmeasure_unit Unidad de medida de la cantidad a administrar
     * @return
     */
    @RequestMapping("/insertdose.htm")
    public ModelAndView insertDose(String codigo, String batch, String expiration_date, BigDecimal give_amount, Integer idmeasure_unit) {


        Success result = new Success(false);

        if (codigo != null && batch != null && expiration_date != null && give_amount != null && idmeasure_unit != null) {

            Date date_exp = null;
            try {
                if (!expiration_date.equals("")) {
                    date_exp = dateFormat2.parse(expiration_date);
                }
            } catch (ParseException ex) {
                logger.error(ex, ex);
            }

            try {
                doseDao.insertDose(codigo, batch, date_exp, give_amount, idmeasure_unit);
                result.setSuccess(Boolean.TRUE);
            } catch (Exception e) {
                result.setErrors(new com.abada.web.exjs.Error(e.getMessage()));
            }

        }

        return new ModelAndView(new JsonView(result));

    }

    /**
     * Inserta e imprime en {@link Dose} a partir de los datos recibidos <br/>
     * @param codigo Especialidad a la que pertenece la dosis
     * @param batch Lote al que pertenece una dosis
     * @param expiration_date Fecha de caducidad
     * @param give_amount Cantidad administrada
     * @param idmeasure_unit Unidad de medida de la cantidad a administrar
     * @param blisters Numero de blisters que se van a imprimir
     * @return
     */
    @RequestMapping("/insertprintdose.htm")
    public ModelAndView insertPrintDose(HttpServletRequest request, String codigo, String batch, String expiration_date, BigDecimal give_amount, Integer idmeasure_unit, Integer blisters, String pbatch, String pexpiration_date) {


        Success result = new Success(false);
        TemplatesMedication template = null;
        String especialidad = "";
        ModelAndView mav = new ModelAndView();

        if (codigo != null && batch != null && expiration_date != null && give_amount != null && idmeasure_unit != null) {

            Date date_exp = null;
            String date_exp_p = "";
            try {
                if (!expiration_date.equals("")) {
                    date_exp = dateFormat2.parse(expiration_date);
                    date_exp_p = dateFormat1.format(date_exp);
                }
            } catch (ParseException ex) {
                logger.error(ex, ex);
            }

            try {
                Dose dose = doseDao.insertPrintDose(codigo, batch, date_exp, give_amount, idmeasure_unit);
                List<TemplatesMedication> tm = this.catalogoHasTemplatesDao.getTemplateFromEspecialidad(codigo);

                if (!tm.isEmpty()) {
                    for (TemplatesMedication t : tm) {
                        template = t;
                    }
                    List<CatalogoMedicamentos> cm = catalogoMedicamentosDao.findByCodigo(codigo);
                    if (!cm.isEmpty()) {
                        for (CatalogoMedicamentos c : cm) {
                            especialidad = c.getNombre();
                        }
                    }
                }

                if (template != null) {
                    try {
                        mav = this.printDose(request, "595x842", blisters, template.getPathTemplate(), template.getPathStyle(), false, 0, 0, pbatch, pexpiration_date, dose.getIddose(), batch, date_exp_p, codigo, especialidad, 0, 0, 0, 0);
                        return mav;
                    } catch (Exception e) {
                        logger.error(e, e);
                    }
                }
                result.setSuccess(Boolean.TRUE);
            } catch (Exception e) {
                result.setErrors(new com.abada.web.exjs.Error(e.getMessage()));
            }

        }
        if (result.getSuccess()) {
            return new ModelAndView(new JsonView(result));
        } else {
            return new ModelAndView(new JsonView(result));
        }
    }

    /**
     * Modifica en {@link Dose} a partir de los datos recibidos <br/>
     * @param iddose Identificador de la dosis
     * @param codigo Especialidad a la que pertenece la dosis
     * @param batch Lote al que pertenece una dosis
     * @param expiration_date Fecha de caducidad
     * @param give_amount Cantidad administrada
     * @param idmeasure_unit Unidad de medida de la cantidad a administrar
     * @return
     */
    @RequestMapping("/updatedose.htm")
    public ModelAndView updateDose(Long iddose, String codigo, String batch, String expiration_date, BigDecimal give_amount, Integer idmeasure_unit) {

        Success result = new Success(false);


        if (codigo != null && batch != null && expiration_date != null && give_amount != null && idmeasure_unit != null) {

            Date date_exp = null;
            try {

                date_exp = dateFormat2.parse(expiration_date);

            } catch (ParseException ex) {
                logger.error(ex, ex);
            }

            try {
                doseDao.updateDose(iddose, codigo, batch, date_exp, give_amount, idmeasure_unit);
                result.setSuccess(Boolean.TRUE);
            } catch (Exception e) {
                result.setErrors(new com.abada.web.exjs.Error(e.getMessage()));
            }
        }


        return new ModelAndView(new JsonView(result));

    }

    /**
     * Genera las etiquetas y permite su descarga a partir de los datos recibidos
     * @param request
     * @param pagesize
     * @param blisters
     * @param temp
     * @param style
     * @param apaisado
     * @param xGap
     * @param yGap
     * @param pbatch
     * @param pexpiration_date
     * @param iddose
     * @param batch
     * @param expiration_date
     * @param codigo
     * @param especialidad
     * @param msuperior
     * @param minferior
     * @param mderecho
     * @param mizquierdo
     * @return
     */
    @RequestMapping("/printdose.htm")
    public ModelAndView printDose(HttpServletRequest request, String pagesize, Integer blisters, String temp, String style, boolean apaisado, double xGap, double yGap, String pbatch, String pexpiration_date, Long iddose, String batch, String expiration_date, String codigo, String especialidad, Integer msuperior, Integer minferior, Integer mderecho, Integer mizquierdo) {
        String error = "Error desconocido.";
        File reportFile = this.fileManager.getFile(temp);
        File styleFile = this.fileManager.getFile(style);

        mderecho = com.abada.jasperCommons.Utils.measureUnitToPixels(mderecho, MeasureUnit.MM);
        mizquierdo = com.abada.jasperCommons.Utils.measureUnitToPixels(mizquierdo, MeasureUnit.MM);
        msuperior = com.abada.jasperCommons.Utils.measureUnitToPixels(msuperior, MeasureUnit.MM);
        minferior = com.abada.jasperCommons.Utils.measureUnitToPixels(minferior, MeasureUnit.MM);
        xGap = com.abada.jasperCommons.Utils.measureUnitToPixels(xGap, MeasureUnit.MM);
        yGap = com.abada.jasperCommons.Utils.measureUnitToPixels(yGap, MeasureUnit.MM);

        if (reportFile != null && styleFile != null) {
            String reportPath = reportFile.getAbsolutePath();
            String stylePath = styleFile.getAbsolutePath();

            String pactivo = this.principioActivoDao.loadByCodigo(codigo);

            Date date_exp = null;
            String date_exp_p = "";
            try {
                if (!expiration_date.equals("")) {
                    date_exp = dateFormat1.parse(expiration_date);
                    date_exp_p = dateFormat3.format(date_exp);
                }
            } catch (ParseException ex) {
                logger.error(ex, ex);
            }

            EncodeDatamatrix ed = new EncodeDatamatrix();
            String datamatrix = ed.encodeDatamatrix(date_exp, iddose);
            Staff staff = (Staff) request.getSession().getAttribute("user");

            GenLabel gl = null;
            OutputStream out = null;
            try {
                gl = (GenLabel) poolGenLabel.borrowObject();
                out = gl.genLabel(staff.getName(), iddose, datamatrix, date_exp_p, batch, especialidad, pactivo, reportPath, stylePath, blisters, pagesize, msuperior, minferior, mderecho, mizquierdo, apaisado, xGap, yGap, pbatch, pexpiration_date, false);
                ByteArrayOutputStream byteout = new ByteArrayOutputStream();
                byteout.writeTo(out);
                poolGenLabel.returnObject(gl);
                gl = null;
                return new ModelAndView(new OutputStreamView(byteout, iddose.toString() + ".pdf"));
            } catch (Exception e) {
                error = "Error durante la generación del PDF.";
                logger.error(e);
                if (gl != null) {
                    try {
                        poolGenLabel.returnObject(gl);
                        gl = null;
                    } catch (Exception e1) {
                    }
                }
            } catch (OutOfMemoryError e) {
                error = "Error generando PDF. Se acabo la memoria";
                logger.error(e);
                if (gl != null) {
                    try {
                        poolGenLabel.returnObject(gl);
                        gl = null;
                        System.gc();
                    } catch (Exception e1) {
                    }
                }
            }
        } else {
            error = "Fichero de la plantilla erroneos.";
        }
        return new ModelAndView(new RedirectView("dose.htm?error=" + error, true, true, false));
    }
}
