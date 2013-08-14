/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.contramed.persistence.dao.timingrange;

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

import com.abada.contramed.persistence.entity.TimingRange;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest_Extjs_v3;
import java.util.Date;
import java.util.List;

/**
 *
 * @author maria
 *
 * Dao de la entidad TimingRange , trabajamos con los datos de las diferentes franjas horarias
 */
public interface TimingRangeDao {

    /**
     *obtenemos la lista de todos los TimingRange
     * @return List {@link TimingRange}
     */
    public List<TimingRange> listTimingRange();

    /**
     * insertamos timing Range
     * @param startTime
     * @param endTime
     * @param name
     */
    public void insertTimingRange(Date startTime, Date endTime, String name);

    /**
     * Modificamos timing Range
     * @param start_time 
     * @param end_time
     * @param name
     * @param idtimingRange
     */
    public void updateTimingRange(Date start_time, Date end_time, String name, Long idtimingRange);

    /**
     * borramos un TimingRange dado su id
     * @param timingRange
     */
    public void removeTimingRange(Long timingRange);

    /**
     *obtenemos la lista de todos los TimingRange selecionado por el filters
     * @param filters
     * @return List {@link TimingRange}
     */
    public List<TimingRange> loadAll(GridRequest_Extjs_v3 filters);

    /**
     *obtenemos una cuenta de todos los timingRange selecionados por el filters
     * @param filters
     * @return List {@link TimingRange}
     */
    public Long loadSizeAll(GridRequest_Extjs_v3 filters);

    /**
     *obtenemos la lista de TimingRange a partir de idtimingRange
     * @param idtimingRange
     * @return List {@link TimingRange}
     */
    public List<TimingRange> findByIdtimingRange(Long idtimingRange);
}
