/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abada.contramed.persistence.entity;

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

import java.util.Date;

/**
 *
 * @author katsu
 */
public interface Historic {

    Dose getDoseIddose();

    Date getEventDate();

    String getObservation();

    Date getOrderTimingDate();

    OrderTiming getOrderTimingIdorderTiming();

    Staff getStaffIdstaff();

    void setDoseIddose(Dose doseIddose);

    void setEventDate(Date eventDate);

    void setObservation(String observation);

    void setOrderTimingDate(Date orderTimingDate);

    void setOrderTimingIdorderTiming(OrderTiming orderTimingIdorderTiming);

    void setStaffIdstaff(Staff staffIdstaff);

}
