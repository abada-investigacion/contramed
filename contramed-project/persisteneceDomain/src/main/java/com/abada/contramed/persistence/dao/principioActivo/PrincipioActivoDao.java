/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.contramed.persistence.dao.principioActivo;

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

import com.abada.contramed.persistence.entity.PrincipioActivo;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest_Extjs_v3;
import java.util.List;

/**
 *
 * @author mmartin
 */
public interface PrincipioActivoDao {

    /**
     * Obtiene el nombre de {@link PrincipioActivo} a partir de su codigo <br/>
     * @param filters Filtros para la JPQL
     * @return List<PrincipioActivo>
     */
    public String loadByCodigo(String codigo);

    /**
     * Obtiene la lista de {@link PrincipioActivo} a partir de codigo <br/>
     * @param codigo
     * @return List<{@link PrincipioActivo}>
     */
    public List<PrincipioActivo> findByCodigo(String codigo);

    /**
     * Obtiene la lista de {@link PrincipioActivo} que cumplen el filtro <br/>
     * @param filters
     * @return List<{@link PrincipioActivo}>
     */
    public List<PrincipioActivo> loadAll(GridRequest_Extjs_v3 filters);

    /**
     * Obtiene el tamaño de {@link PrincipioActivo}
     * @param filters
     * @return
     */
    public Long loadSizeAll(GridRequest_Extjs_v3 filters);

    /**
     * Inserta en {@link PrincipioActivo} a partir de los datos recibidos <br/>
     * @param codigo
     * @param nombre
     * @param anulado
     * @param codigo_ca
     * @param composicion
     * @param denominacion
     * @param formula
     * @param peso
     * @param dosis
     * @param dosis_superficie
     * @param dosis_peso
     * @param u_dosis
     * @param u_dosis_superficie
     * @param u_dosis_peso
     * @param via
     * @param frecuencia
     * @param aditivo
     * @throws Exception
     */
    public void insertPrincipioActivo(String codigo, String nombre, String anulado, String codigo_ca, String composicion,
            String denominacion, String formula, String peso, String dosis, String dosis_superficie, String dosis_peso, String u_dosis,
            String u_dosis_superficie, String u_dosis_peso, Long via, Long frecuencia, String aditivo) throws Exception;

    /**
     * Modifica {@link PrincipioActivo} a partir de los datos recibidos <br/>
     * @param codigo
     * @param oldcodigo
     * @param nombre
     * @param anulado
     * @param codigo_ca
     * @param composicion
     * @param denominacion
     * @param formula
     * @param peso
     * @param dosis
     * @param dosis_superficie
     * @param dosis_peso
     * @param u_dosis
     * @param u_dosis_superficie
     * @param u_dosis_peso
     * @param via
     * @param frecuencia
     * @param aditivo
     * @throws Exception
     */
    public void updatePrincipioActivo(final String codigo, final String oldcodigo, String nombre, String anulado, String codigo_ca, String composicion,
            String denominacion, String formula, String peso, String dosis, String dosis_superficie, String dosis_peso, String u_dosis,
            String u_dosis_superficie, String u_dosis_peso, Long via, Long frecuencia, String aditivo) throws Exception;

    /**
     * Borra un {@link PrincipioActivo} a partir del identificador recibido <br/>
     * @param id
     */
    public void delete(String id);
}
