/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.contramed.persistence.dao.dose;

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

import com.abada.contramed.persistence.entity.Dose;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest_Extjs_v3;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author mmartin
 *
 * Implementa todos los metodos de {@link DoseDaoImpl}
 */
public interface DoseDao {

    /**
     *Obtiene el tamaño de {@link Dose}
     * @param filters Filtros para la JPQL
     * @return Long
     */
    public Long loadSizeAll(GridRequest_Extjs_v3 filters);

    /**
     *Obtiene la lista de {@link Dose} que cumplen el filtro <br/>
     * @param filters Filtros para la JPQL
     * @return List<Dose>
     */
    public List<Dose> loadAll(GridRequest_Extjs_v3 filters);

    /**
     * Inserta en {@link Dose} a partir de los datos recibidos <br/>
     * @param codigo Codigo de dosis
     * @param batch  Lote de una dosis
     * @param expiration_date Fecha de caducidad de una dosis
     * @param give_amount Cantidad de administracion de una dosis
     * @param idmeasure_unit  Unidad de medida de una dosis
     */
    public void insertDose(String codigo, String batch, Date expiration_date, BigDecimal give_amount, Integer idmeasure_unit)throws Exception;

     /**
     * Inserta en {@link Dose} a partir de los datos recibidos <br/>
     * @param codigo Codigo de dosis
     * @param batch  Lote de una dosis
     * @param expiration_date Fecha de caducidad de una dosis
     * @param give_amount Cantidad de administracion de una dosis
     * @param idmeasure_unit  Unidad de medida de una dosis
     * @return {@link Dose}
     */
    public Dose insertPrintDose(String codigo, String batch, Date expiration_date, BigDecimal give_amount, Integer idmeasure_unit)throws Exception;

    /**
     *Modifica {@link Dose} a partir de los datos recibidos <br/>
     * @param iddose Identificador de dosis
     * @param codigo Codigo de dosis
     * @param batch  Lote de una dosis
     * @param expiration_date Fecha de caducidad de una dosis
     * @param give_amount Cantidad de administracion de una dosis
     * @param idmeasure_unit  Unidad de medida de una dosis
     */
    public void updateDose(Long iddose, String codigo, String batch, Date expiration_date, BigDecimal give_amount, Integer idmeasure_unit)throws Exception;
}
