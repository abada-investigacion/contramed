/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.trazability.dao.givehistoric;

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

import com.abada.trazability.entity.GivesHistoric;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest;
import java.util.List;

/**
 *
 * @author katsu
 */
public interface GiveHistoricDao {

    /**
     * Devuelve el numero de resultados que coinciden en el hisrtorial de Administración
     * con los filtros que se pasan
     * @param filters filtros para la JPQL
     * @return
     */
    public Long loadGiveHistoricSizeAll(GridRequest filters);

    /**
     * Devuelve los resultados paginados que coinciden en el hisrtorial de Administración
     * con los filtros que se pasan
     * @param filters filtros para la JPQL
     * @return
     */
    public List<GivesHistoric> loadGiveHistoricAll(GridRequest filters);

    public long loadGiveHistoricAllPrecisaCount(GridRequest filters);
    public List<GivesHistoric> loadGiveHistoricAllPrecisa(GridRequest filters);
}
