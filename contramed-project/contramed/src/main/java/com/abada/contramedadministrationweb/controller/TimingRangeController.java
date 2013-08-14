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

import com.abada.contramed.persistence.dao.timingrange.TimingRangeDao;
import com.abada.contramed.persistence.entity.TimingRange;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest_Extjs_v3;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.factory.GridRequest_Extjs_v3Factory;
import com.abada.springframework.web.servlet.handler.interceptor.annotation.Role;
import com.abada.springframework.web.servlet.view.JsonView;
import com.abada.web.exjs.ExtjsStore;
import com.abada.web.exjs.Success;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author maria
 */
@Controller
@Role({"SYSTEM_ADMINISTRATOR",
    "ADMINISTRATIVE"})
/**
 * inserción modificación borrado rango horario
 */
public class TimingRangeController {

    private TimingRangeDao timingRangeDao;

    /**
     *
     * @param timingRangeDao
     */
    @Resource(name = "TimingRangeDao")
    public void setTimingRangeDao(TimingRangeDao timingRangeDao) {
        this.timingRangeDao = timingRangeDao;
    }

    /**
     * pagina inicial timingRange
     * @return
     */
    @RequestMapping("/timingRange.htm")
    public ModelAndView timingRange() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("js", new String[]{"common-administration.js", "timingRange.js"});

        return new ModelAndView("pages/main", model);
    }

    /**
     * grid con los diferentes horario de Tomas
     *
     * Grid que muestra los datos de {@link timingRange} <br/>
     * @param filter
     * @param dir
     * @param sort
     * @param limit
     * @param start
     * @return
     */
    @RequestMapping("/gridTimingRange.htm")
    public ModelAndView gridTimingRange(String filter, String dir, String sort, Integer limit, Integer start) throws UnsupportedEncodingException {
        GridRequest_Extjs_v3 request = GridRequest_Extjs_v3Factory.parse(sort, dir, start, limit, filter);
        ExtjsStore result = new ExtjsStore();
        result.setTotal(timingRangeDao.loadSizeAll(request).intValue());
        List<TimingRange> aux = timingRangeDao.loadAll(request);
        result.setData(aux);
        return new ModelAndView(new JsonView(result));
    }

    /**
     * Devuelve true si la hora de inicio de la toma es menor que la hora de inicio
     * del fin de toma
     * @param horaEntrada
     * @param horaSalida
     * @return
     *
    public Double timeCorrecta(Date horaEntrada, Date horaSalida){
    Date fechaMayor;
    Date fechaMenor;
    Double hora = 0.00;
    if (horaEntrada.compareTo(horaSalida) > 0) {
    fechaMayor = horaEntrada;
    fechaMenor = horaSalida;
    } else {
    fechaMayor = horaSalida;
    fechaMenor = horaEntrada;
    }

    //los milisegundos
    double diferenciaMils = (fechaMayor.getTime() - fechaMenor.getTime());

    hora=((double)diferenciaMils/3600000);

    return hora;
    }*/
    /**
     * Nuevo horario de tomas
     * @param startTime
     * @param endTime
     * @param name
     * @return
     * @throws java.text.ParseException
     */
    @RequestMapping("/insertTimingRange.htm")
    public ModelAndView insertTimingRange(String startTime, String endTime, String name) throws java.text.ParseException {

        Success result = new Success(false);
        try {
            if (name != null) {
                SimpleDateFormat format = new SimpleDateFormat("HH:m");
                Date startDate = null;
                Date endDate = null;
                if (!startTime.equals("")) {
                    startDate = format.parse(startTime);
                }
                if (!endTime.equals("")) {
                    endDate = format.parse(endTime);
                }
                //if (startDate.before(endDate) == true) {
                    timingRangeDao.insertTimingRange(startDate, endDate, name);
                    result.setSuccess(Boolean.TRUE);

                /*} else {
                    result.setSuccess(Boolean.FALSE);
                    result.setErrors(new com.abada.web.exjs.Error("Hora de inicio posterior a la hora de fin"));
                }*/
            } else {
                result.setErrors(new com.abada.web.exjs.Error("nombre de toma no dado"));
            }
        } catch (Exception e) {
            result.setSuccess(Boolean.FALSE);
            result.setErrors(new com.abada.web.exjs.Error("Error a insertar la Toma"));
        }
        return new ModelAndView(new JsonView(result));

    }

    /**
     * modificar el rango horario
     * @param startTime
     * @param endTime
     * @param name
     * @param idtimingRange
     * @return
     * @throws java.text.ParseException
     */
    @RequestMapping("/updateTimingRange.htm")
    public ModelAndView updateTimingRange(String startTime, String endTime, String name, Long idtimingRange) throws java.text.ParseException {

        Success result = new Success(false);
        try {
            if (name != null) {
                SimpleDateFormat format = new SimpleDateFormat("HH:m");
                Date startDate = null;
                Date endDate = null;
                if (!startTime.equals("")) {
                    startDate = format.parse(startTime);
                }
                if (!endTime.equals("")) {
                    endDate = format.parse(endTime);
                }
                //if (startDate.before(endDate) == true) {
                    timingRangeDao.updateTimingRange(startDate, endDate, name, idtimingRange);
                    result.setSuccess(Boolean.TRUE);

                /*} else {
                    result.setErrors(new com.abada.web.exjs.Error("Hora de inicio posterior a la hora de fin"));
                }*/
            } else {
                result.setErrors(new com.abada.web.exjs.Error("nombre de toma no dado"));
            }
        } catch (Exception e) {
            result.setSuccess(Boolean.FALSE);
            result.setErrors(new com.abada.web.exjs.Error("Error a Modificar la Toma"));
        }
        return new ModelAndView(new JsonView(result));

    }

    /**
     * borrado de un timingRange
     * @param timingRange
     * @return
     */
    @RequestMapping("/removeTimingRange.htm")
    public ModelAndView removeTimingRange(String timingRange) {
        //Borra los objetos seleccionados, recibiendo un string de ids
        Success result = new Success(false);
        try {
            Long tmp = null;
            String cadena = timingRange.substring(1, timingRange.length() - 1);
            String[] ids;
            ids = cadena.split(",");
            for (int i = 0; i < ids.length; i++) {
                tmp = Long.parseLong(ids[i]);
                timingRangeDao.removeTimingRange(tmp);
            }
            result.setSuccess(Boolean.TRUE);
        } catch (Exception ex) {
            result.setSuccess(Boolean.FALSE);
            result.setErrors(new com.abada.web.exjs.Error("Error a Borrar la Toma"));
        }

        return new ModelAndView(new JsonView(result));

    }
}
