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
import com.abada.springframework.web.servlet.handler.interceptor.annotation.Role;
import com.abada.springframework.web.servlet.view.JsonView;
import com.abada.web.exjs.Success;
import com.abada.webcontramed.exception.PrimaryKeyException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
    "PHARMACIST"})
public class CatalogoMedicamentosController {

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
     * Pantalla principal
     * @return
     */
    @RequestMapping("/catalogoMedicamentos.htm")
    public ModelAndView catalogoMedicamentos() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("js", new String[]{"commonDose.js", "commonMedicamentos.js", "catalogoMedicamentos.js"});
        return new ModelAndView("pages/main", model);
    }

    /**
     * Inserta en {@link CatalogoMedicamentos} a partir de los datos recibidos <br/>
     * @param codigo Codigo nacional de la especialidad
     * @param nombre Nombre de la especialidad
     * @param anulado
     * @param bioequivalente
     * @param caducidad
     * @param caracteristicas
     * @param codGrupo
     * @param codLab
     * @param conservacion
     * @param dispensacion
     * @param especialControl
     * @param fechaAlta
     * @param fechaBaja
     * @param ficha
     * @param formaFarma
     * @param generico
     * @param largaDuracion
     * @param nombreLab
     * @param precioRef
     * @param pvp
     * @param regimen
     * @param dosis
     * @param dosisSuperficie
     * @param dosisPeso
     * @param uDosis
     * @param uDosisSuperficie
     * @param uDosisPeso
     * @param via
     * @param frecuencia
     * @param diluyente
     * @param aditivo
     * @param dosisDiluyente
     * @return
     */
    @RequestMapping("/insertCatMedicamentos.htm")
    public ModelAndView insertCatMedicamentos(String codigo, String nombre, String anulado, String bioequivalente, String caducidad,
            String caracteristicas, String codGrupo, String codLab, String conservacion, String dispensacion, String especialControl, String fechaAlta,
            String fechaBaja, BigInteger ficha, String formaFarma, String generico, String largaDuracion, String nombreLab, String precioRef,
            String pvp, String regimen, BigDecimal dosis, BigDecimal dosisSuperficie, BigDecimal dosisPeso, Long uDosis, Long uDosisSuperficie,
            Long uDosisPeso, Integer via, BigDecimal frecuencia, String diluyente, String aditivo, BigDecimal dosisDiluyente) {

        Success result = new Success(false);
        try {
            if (codigo != null) {

                nombre = nombre.replace("<", "(").replace(">", ")");

                SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy");
                Date fCaducidad = null;
                Date fAlta = null;
                Date fBaja = null;

                if (!caducidad.equals("")) {
                    fCaducidad = format.parse(caducidad);
                }
                if (!fechaAlta.equals("")) {
                    fAlta = format.parse(fechaAlta);
                }
                if (!fechaBaja.equals("")) {
                    fBaja = format.parse(fechaBaja);
                }
                catalogoMedicamentosDao.insertCatalogoMedicamentos(codigo, nombre, anulado, bioequivalente, fCaducidad, caracteristicas, codGrupo, codLab, conservacion, dispensacion, especialControl, fAlta, fBaja, ficha, formaFarma, generico, largaDuracion, nombreLab, precioRef, pvp, regimen, dosis, dosisSuperficie, dosisPeso, uDosis, uDosisSuperficie, uDosisPeso, via, frecuencia, diluyente, aditivo, dosisDiluyente);
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
     * Modifica en {@link CatalogoMedicamentos} a partir de los datos recibidos <br/>
     * @param codigo Codigo nacional de la especialidad antes de la modificación
     * @param newcodigo Codigo nacional de la especialidad despues de la modificacion
     * @param nombre Nombre de la especialidad
     * @param anulado
     * @param bioequivalente
     * @param caducidad
     * @param caracteristicas
     * @param codGrupo
     * @param codLab
     * @param conservacion
     * @param dispensacion
     * @param especialControl
     * @param fechaAlta
     * @param fechaBaja
     * @param ficha
     * @param formaFarma
     * @param generico
     * @param largaDuracion
     * @param nombreLab
     * @param precioRef
     * @param pvp
     * @param regimen
     * @param dosis
     * @param dosisSuperficie
     * @param dosisPeso
     * @param uDosis
     * @param uDosisSuperficie
     * @param uDosisPeso
     * @param via
     * @param frecuencia
     * @param diluyente
     * @param aditivo
     * @param dosisDiluyente
     * @return
     */
    @RequestMapping("/updateCatMedicamentos.htm")
    public ModelAndView updateCatMedicamentos(String codigo, String newcodigo, String nombre, String anulado, String bioequivalente, String caducidad,
            String caracteristicas, String codGrupo, String codLab, String conservacion, String dispensacion, String especialControl, String fechaAlta,
            String fechaBaja, BigInteger ficha, String formaFarma, String generico, String largaDuracion, String nombreLab, String precioRef,
            String pvp, String regimen, BigDecimal dosis, BigDecimal dosisSuperficie, BigDecimal dosisPeso, Long uDosis, Long uDosisSuperficie,
            Long uDosisPeso, Integer via, BigDecimal frecuencia, String diluyente, String aditivo, BigDecimal dosisDiluyente) {
        Success result = new Success(false);
        try {
            if (codigo != null) {

                nombre = nombre.replace("<", "(").replace(">", ")");

                SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy");
                Date fCaducidad = null;
                Date fAlta = null;
                Date fBaja = null;

                if (!caducidad.equals("")) {
                    fCaducidad = format.parse(caducidad);
                }

                if (!fechaAlta.equals("")) {
                    fAlta = format.parse(fechaAlta);
                }

                if (!fechaBaja.equals("")) {
                    fBaja = format.parse(fechaBaja);
                }
                catalogoMedicamentosDao.updateCatalogoMedicamentos(newcodigo, codigo, nombre, anulado, bioequivalente, fCaducidad, caracteristicas, codGrupo, codLab, conservacion, dispensacion, especialControl, fAlta, fBaja, ficha, formaFarma, generico, largaDuracion, nombreLab, precioRef, pvp, regimen, dosis, dosisSuperficie, dosisPeso, uDosis, uDosisSuperficie, uDosisPeso, via, frecuencia, diluyente, aditivo, dosisDiluyente);
                result.setSuccess(Boolean.TRUE);
            }

        } catch (PrimaryKeyException ex) {
            result.setErrors(new com.abada.web.exjs.Error(ex.getMessage()));
        } catch (Exception ex) {
            result.setErrors(new com.abada.web.exjs.Error("Modificación fallida."));
        }

        return new ModelAndView(new JsonView(result));

    }
}
