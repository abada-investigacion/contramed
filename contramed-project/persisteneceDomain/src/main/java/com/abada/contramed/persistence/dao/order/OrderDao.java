/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.contramed.persistence.dao.order;

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

import ca.uhn.hl7v2.HL7Exception;
import com.abada.contramed.persistence.entity.Order1;
import com.abada.contramed.persistence.entity.Patient;
import java.util.List;

/**
 *
 * @author david
 *
 * Dao de la entidad Order, datos de tramientos
 */
public interface OrderDao {

    /**
     * Analizamos segun venga la order de control que vamos hacer con la order
     * @param order
     * @param patient
     * @throws HL7Exception
     */
    public void controlOrder(Order1 order, Patient patient) throws HL7Exception;

    /**
     * cancelamos un tratamiento poniendolo a historico
     * @param order
     * @throws HL7Exception
     */
    public void orderCancel(Order1 order) throws HL7Exception;

    /**
     *obtenemos la lista de todos los tratamientos
     * @return list {@link Order1}s
     */
    public List<Order1> findAll();

     /**
     *obtenemos la lista de un tratamiento a partir de su orderId y grupo
     * @param orderId
     * @param groupId
     * @return list {@link Order1}
     */
    public List<Order1> findByOrderId(String orderId,String groupId);

}

