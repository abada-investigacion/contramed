/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.webcontramed.controller;

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

import com.abada.contramed.persistence.dao.frecuenciaPredef.FrecuenciaPredefDao;
import com.abada.contramed.persistence.dao.measureUnit.MeasureUnitDao;
import com.abada.contramed.persistence.dao.recurso.RecursoDao;
import com.abada.contramed.persistence.dao.table0162.Table0162Dao;
import com.abada.contramed.persistence.dao.tablez029.Tablez029Dao;
import com.abada.contramed.persistence.dao.templatesMedication.TemplatesMedicationDao;
import com.abada.contramed.persistence.dao.timingrange.TimingRangeDao;
import com.abada.contramed.persistence.entity.FrecuenciaPredef;
import com.abada.contramed.persistence.entity.MeasureUnit;
import com.abada.contramed.persistence.entity.Recurso;
import com.abada.contramed.persistence.entity.Table0162;
import com.abada.contramed.persistence.entity.Tablez029;
import com.abada.contramed.persistence.entity.TemplatesMedication;
import com.abada.contramed.persistence.entity.TimingRange;
import com.abada.contramed.persistence.entity.enums.TypeRole;
import com.abada.contramed.persistence.entity.enums.TypeStaff;
import com.abada.printDose.CustomPageSize;
import com.abada.printDose.CustomPageSizeImpl;
import com.abada.springframework.web.servlet.handler.interceptor.annotation.Role;
import com.abada.springframework.web.servlet.view.JsonView;
import com.abada.web.exjs.ComboBoxResponse;
import com.abada.web.exjs.ExtjsStore;
import com.abada.webcontramed.entities.BedDataView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author katsu
 */
@Controller
@Role({"SYSTEM_ADMINISTRATOR",
    "ADMINISTRATIVE",
    "NURSE_PLANT",
    "NURSE_PLANT_SUPERVISOR",
    "NURSE_PHARMACY",
    "PHARMACIST"})
public class ComboBoxController {

    private TimingRangeDao timingRangeDao;
    private Table0162Dao Table0162Dao;
    private MeasureUnitDao measureUnitDao;
    private FrecuenciaPredefDao FrecuenciaPredefDao;
    private Tablez029Dao tablez029Dao;
    private RecursoDao recursoDao;
    private TemplatesMedicationDao templatesMedicationDao;
    private MessageSource messagesource;

    @Resource(name = "messageSource")
    public void setMessagesource(MessageSource messagesource) {
        this.messagesource = messagesource;
    }

    @Resource(name = "tablez029Dao")
    public void setTimingRangeDao(Tablez029Dao tablez029Dao) {
        this.tablez029Dao = tablez029Dao;
    }

    @Resource(name = "recursoDao")
    public void setRecursoDao(RecursoDao recursoDao) {
        this.recursoDao = recursoDao;
    }

    @Resource(name = "measureUnitDao")
    public void setMeasureUnitDao(MeasureUnitDao measureUnitDao) {
        this.measureUnitDao = measureUnitDao;
    }

    @Resource(name = "table0162Dao")
    public void setTable0162Dao(Table0162Dao table0162Dao) {
        this.Table0162Dao = table0162Dao;
    }

    @Resource(name = "TimingRangeDao")
    public void setTimingRangeDao(TimingRangeDao timingRangeDao) {
        this.timingRangeDao = timingRangeDao;
    }

    @Resource(name = "frecuenciaPredefDao")
    public void setFrecuenciaPredefDao(FrecuenciaPredefDao frecuenciaPredefDao) {
        this.FrecuenciaPredefDao = frecuenciaPredefDao;
    }

    @Resource(name = "templatesMedicationDao")
    public void setTemplatesMedicationDao(TemplatesMedicationDao templatesMedicationDao) {
        this.templatesMedicationDao = templatesMedicationDao;
    }

    /**
     * Carga la lista de reports en un ExtjsStore <br/>
     * @return
     */
    @RequestMapping("/combotemplate.htm")
    public ModelAndView comboTemplate() {

        List<ComboBoxResponse> data = new ArrayList<ComboBoxResponse>();
        List<TemplatesMedication> tm = this.templatesMedicationDao.listAll();
        for (TemplatesMedication templatesMedication : tm) {
            if (!templatesMedication.getPathTemplate().toString().equals(" ")) {
                ComboBoxResponse aux = new ComboBoxResponse();
                aux.setId(templatesMedication.getPathTemplate().toString());
                aux.setValue(templatesMedication.getTemplate());
                data.add(aux);
            }
        }

        ExtjsStore result = new ExtjsStore();
        result.setData(data);

        return new ModelAndView(new JsonView(result));
    }

    /**
     * Carga la lista de estilos en un ExtjsStore <br/>
     * @returngetIdtemplatesMedication()
     */
    @RequestMapping("/combostyle.htm")
    public ModelAndView comboStyle() {

        List<ComboBoxResponse> data = new ArrayList<ComboBoxResponse>();
        List<TemplatesMedication> tm = this.templatesMedicationDao.listAll();
        for (TemplatesMedication templatesMedication : tm) {
            if (!templatesMedication.getPathStyle().toString().equals(" ")) {
                ComboBoxResponse aux = new ComboBoxResponse();
                aux.setId(templatesMedication.getPathStyle().toString());
                aux.setValue(templatesMedication.getTemplate());
                data.add(aux);
            }
        }

        ExtjsStore result = new ExtjsStore();
        result.setData(data);

        return new ModelAndView(new JsonView(result));
    }

    /**
     * Carga la lista de {@link FrecuenciaPredef} en un ExtjsStore <br/>
     * @return
     */
    @RequestMapping("/combofrecuencia.htm")
    public ModelAndView comboFrecuencia() {
        List<ComboBoxResponse> data = new ArrayList<ComboBoxResponse>();
        List<FrecuenciaPredef> fp = this.FrecuenciaPredefDao.listFrecuenciaPredef();
        for (FrecuenciaPredef frecuenciaPredef : fp) {
            ComboBoxResponse aux = new ComboBoxResponse();
            aux.setId(frecuenciaPredef.getIdTarea() + "");
            aux.setValue(frecuenciaPredef.getNombre() + " ");
            data.add(aux);
        }
        ExtjsStore result = new ExtjsStore();
        result.setData(data);

        return new ModelAndView(new JsonView(result));
    }

    /**
     * Devuelve el listado de timingsRanges.
     * @return
     */
    @RequestMapping("/timingranges.htm")
    public ModelAndView getTimingsRange() {
        List<ComboBoxResponse> data = new ArrayList<ComboBoxResponse>();
        List<TimingRange> list = this.timingRangeDao.listTimingRange();
        for (TimingRange tr : list) {
            ComboBoxResponse aux = new ComboBoxResponse();
            aux.setId(tr.getIdtimingRange().toString());
            aux.setValue(tr.getName() + " (" + tr.getStartTime() + "-" + tr.getEndTime() + ")");
            data.add(aux);
        }

        ExtjsStore result = new ExtjsStore();
        result.setData(data);
        return new ModelAndView(new JsonView(result));
    }

    /**
     * Carga la lista de {@link MeasureUnit} en un ExtjsStore
     * @return
     */
    @RequestMapping("/combounad.htm")
    public ModelAndView comboUnAd() {

        List<ComboBoxResponse> data = new ArrayList<ComboBoxResponse>();

        List<MeasureUnit> unad = this.measureUnitDao.findAll();
        for (MeasureUnit measureUnit : unad) {
            ComboBoxResponse aux = new ComboBoxResponse();
            aux.setId(measureUnit.getIdmeasureUnit() + "");
            aux.setValue(measureUnit.getName() + " -- " + measureUnit.getDescription());
            data.add(aux);

        }

        ExtjsStore result = new ExtjsStore();
        result.setData(data);

        return new ModelAndView(new JsonView(result));
    }

    /**
     * Carga la lista de {@link Table0162} (vias) en un ExtjsStore
     * @return
     */
    @RequestMapping("/combovia.htm")
    public ModelAndView comboVia() {

        List<ComboBoxResponse> data = new ArrayList<ComboBoxResponse>();

        List<Table0162> t = this.Table0162Dao.findAll();
        for (Table0162 table0162 : t) {
            ComboBoxResponse aux = new ComboBoxResponse();
            aux.setId(table0162.getId() + "");
            aux.setValue(table0162.getCode() + " -- " + table0162.getDetails());
            data.add(aux);
        }
        ExtjsStore result = new ExtjsStore();
        result.setData(data);

        return new ModelAndView(new JsonView(result));
    }

    /**
     * Carga la lista de {@link Tablez029} (details) en un ExtjsStore
     * @return
     */
    @RequestMapping("/combocode.htm")
    public ModelAndView comboCode() {
        List<ComboBoxResponse> data = new ArrayList<ComboBoxResponse>();
        List<Tablez029> details = this.tablez029Dao.findAll();
        for (Tablez029 tz : details) {
            ComboBoxResponse aux = new ComboBoxResponse();
            aux.setId(tz.getId().toString());
            aux.setValue(tz.getDetails());
            data.add(aux);
        }
        ExtjsStore result = new ExtjsStore();
        result.setData(data);
        return new ModelAndView(new JsonView(result));
    }

    @RequestMapping("/comboRecursoTag.htm")
    public ModelAndView comboRecursoTag() {
        List<ComboBoxResponse> data = new ArrayList<ComboBoxResponse>();
        List<Recurso> lrecurso = this.recursoDao.findAll();
        for (Recurso recurso : lrecurso) {
            ComboBoxResponse aux = new ComboBoxResponse();
            aux.setId(recurso.getIdRecurso().toString());
            aux.setValue(recurso.getNr());
            data.add(aux);
        }
        ExtjsStore result = new ExtjsStore();
        result.setData(data);
        return new ModelAndView(new JsonView(result));
    }

    @RequestMapping("/comboRecurso.htm")
    public ModelAndView comboRecurso(String path) {
        List<ComboBoxResponse> data = this.recursoDao.findRecusoByPath(path);
        ExtjsStore result = new ExtjsStore();
        result.setData(data);
        return new ModelAndView(new JsonView(result));
    }

    /*
     * Dinamico
    @RequestMapping("/comboRecursoLocalizador.htm")
    public ModelAndView comboRecursoLocalizador(String path) {
    List<ComboBoxResponse> data = this.recursoDao.findRecusoByLocalizador(path);
    ExtjsStore result = new ExtjsStore();
    result.setData(data);
    return new ModelAndView(new JsonView(result));
    }*/
    @RequestMapping("/comboRecursoLocalizador.htm")
    public ModelAndView comboRecursoLocalizador(String path) {
        List<ComboBoxResponse> data = new ArrayList<ComboBoxResponse>();
        ComboBoxResponse add = new ComboBoxResponse();
        add.setId("/HNP/ED. CRUZ/PLT PRIMERA/HOSPITALIZACION/1NE/");
        add.setValue("1NE");
        data.add(add);

        add = new ComboBoxResponse();
        add.setId("/HNP/ED. CRUZ/PLT PRIMERA/HOSPITALIZACION/1NO/");
        add.setValue("1NO");
        data.add(add);

        add = new ComboBoxResponse();
        add.setId("/HNP/ED. CRUZ/PLT PRIMERA/HOSPITALIZACION/1SE/");
        add.setValue("1SE");
        data.add(add);

        add = new ComboBoxResponse();
        add.setId("/HNP/ED. CRUZ/PLT PRIMERA/HOSPITALIZACION/1SO/");
        add.setValue("1SO");
        data.add(add);

        add = new ComboBoxResponse();
        add.setId("/HNP/ED. CRUZ/PLT SEGUNDA/HOSPITALIZACION/2NE/");
        add.setValue("2NE");
        data.add(add);

        add = new ComboBoxResponse();
        add.setId("/HNP/ED. CRUZ/PLT SEGUNDA/HOSPITALIZACION/2NO/");
        add.setValue("2NO");
        data.add(add);

        add = new ComboBoxResponse();
        add.setId("/HNP/ED. CRUZ/PLT SEGUNDA/HOSPITALIZACION/2SE/");
        add.setValue("2SE");
        data.add(add);

        add = new ComboBoxResponse();
        add.setId("/HNP/ED. CRUZ/PLT SEGUNDA/HOSPITALIZACION/2SO/");
        add.setValue("2SO");
        data.add(add);

        add = new ComboBoxResponse();
        add.setId("/");
        add.setValue("Todo el Hospital");
        data.add(add);

        ExtjsStore result = new ExtjsStore();
        result.setData(data);
        return new ModelAndView(new JsonView(result));
    }

    @RequestMapping("/bedMap.htm")
    public ModelAndView bedMap(String path) {
        List<BedDataView> data = this.recursoDao.findRecursoDataViewByLocalizador(path);
        ExtjsStore result = new ExtjsStore();
        result.setData(data);
        return new ModelAndView(new JsonView(result));
    }

    /**
     * Carga la lista de {@link TypeStaff} (role) en un ExtjsStore
     * @return
     */
    @RequestMapping("/comborole.htm")
    public ModelAndView comboRole() {

        List<ComboBoxResponse> data = new ArrayList<ComboBoxResponse>();

        for (TypeRole tr : TypeRole.values()) {
            ComboBoxResponse aux = new ComboBoxResponse();
            aux.setId(tr.toString());
            aux.setValue(messagesource.getMessage(tr.toString(), null, Locale.ENGLISH));
            data.add(aux);
        }
        ExtjsStore result = new ExtjsStore();
        result.setData(data);
        return new ModelAndView(new JsonView(result));
    }

    /**
     * Carga la lista de pagesizes en un ExtjsStore
     * @return
     */
    @RequestMapping("/comboPageSize.htm")
    public ModelAndView comboPageSize() {
        List<ComboBoxResponse> data = new ArrayList<ComboBoxResponse>();
        Map<String, String> map = new HashMap<String, String>();
        CustomPageSize cp = new CustomPageSizeImpl();

        map = cp.getSizePages();
        map = cp.sortByValue(map);

        for (String e : map.keySet()) {
            ComboBoxResponse aux = new ComboBoxResponse();
            aux.setId(e);
            aux.setValue(map.get(e));
            data.add(aux);
        }

        ExtjsStore result = new ExtjsStore();
        result.setData(data);
        return new ModelAndView(new JsonView(result));
    }

}
