package com.abada.contramed.persistence.dao.catalogoMedicamentos;

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

import com.abada.contramed.persistence.entity.CatalogoMedicamentos;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest_Extjs_v3;
import java.util.List;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 *
 * @author mmartin
 *
 * Implementa todos los metodos de {@link CatalogoMedicamentosDaoImpl}
 */
public interface CatalogoMedicamentosDao {

    /**
     * Obtiene la lista de {@link CatalogoMedicamentos} a partir de codigo <br/>
     * @param codigo
     * @return List<CatalogoMedicamentos>
     */
    public List<CatalogoMedicamentos> findByCodigo(String codigo);

    /**
     * Obtiene el tamaño de {@link CatalogoMedicamentos} a partir del filtro <br/>
     * @param filters Filtros para la JPQL
     * @return Long
     */
    public Long loadSizeAll(GridRequest_Extjs_v3 filters);

    /**
     * Obtiene la lista de {@link CatalogoMedicamentos} que cumplen el filtro <br/>
     * @param filters Filtros para la JPQL
     * @return List<{@link CatalogoMedicamentos}>
     */
    public List<CatalogoMedicamentos> loadAll(GridRequest_Extjs_v3 filters);

    /**
     * Inserta un {@link CatalagoMedicamentos} a partir de los datos recibidos <br/>
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
     */
    public void insertCatalogoMedicamentos(String codigo, String nombre, String anulado, String bioequivalente, Date caducidad,
            String caracteristicas, String codGrupo, String codLab, String conservacion, String dispensacion, String especialControl, Date fechaAlta,
            Date fechaBaja, BigInteger ficha, String formaFarma, String generico, String largaDuracion, String nombreLab, String precioRef,
            String pvp, String regimen, BigDecimal dosis, BigDecimal dosisSuperficie, BigDecimal dosisPeso, Long uDosis, Long uDosisSuperficie,
            Long uDosisPeso, Integer via, BigDecimal frecuencia, String diluyente, String aditivo, BigDecimal dosisDiluyente)throws Exception;

    /**
     * Modifica {@link CatalogoMedicamentos} a partir de los datos recibidos <br/>
     * @param newcodigo Nuevo codigo nacional que se asignara a la especialidad
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
     */
    public void updateCatalogoMedicamentos(final String newcodigo, final String codigo, String nombre, String anulado, String bioequivalente, Date caducidad,
            String caracteristicas, String codGrupo, String codLab, String conservacion, String dispensacion, String especialControl, Date fechaAlta,
            Date fechaBaja, BigInteger ficha, String formaFarma, String generico, String largaDuracion, String nombreLab, String precioRef,
            String pvp, String regimen, BigDecimal dosis, BigDecimal dosisSuperficie, BigDecimal dosisPeso, Long uDosis, Long uDosisSuperficie,
            Long uDosisPeso, Integer via, BigDecimal frecuencia, String diluyente, String aditivo, BigDecimal dosisDiluyente)throws Exception;
}

