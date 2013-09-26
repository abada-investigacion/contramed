/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.trazability.dao.staff;

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

import com.abada.trazability.entity.Staff;
import com.abada.trazability.entity.enums.TypeRole;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest;
import java.util.List;

/**
 *
 * @author maria
 */
public interface StaffDao {

    /**
     * obtenemos el numero de Staff existentes que estan activos
     * @param filters
     * @return
     */
    public Long loadSizeAll(GridRequest filtersm,String option);

    /**
     *obtenemos la lista de todos los Staff que estan dados de alta actualmente
     * (historic=0)
     * @return list <Staff>
     */
    public List<Staff> loadAll(GridRequest filters,String option);

    /**
     * Lista de todo el personal de la aplicación
     * @return
     */
    public List<Staff> listStaff();

    /**
     * Inserta un nuevo personal en la base de datos,sabiendo que esta dado de
     * alta en el LDAP
     * @param username
     * @param tag
     * @param type
     * @param role
     * @param name
     * @param surname1
     * @param surname2
     */
    public void insertStaff(String username, String tag, TypeRole role, String name, String surname1, String surname2) throws Exception;

    /**
     * Modifica la información del personal, exceptuando su nombre de usuario
     * que debe ser el mismo del LDAP
     * @param idstaff
     * @param username
     * @param tag
     * @param type
     * @param role
     * @param name
     * @param surname1
     * @param surname2
     */
    public void updateStaff(Long idstaff, String username, String tag, TypeRole role, String name, String surname1, String surname2) throws Exception;

    /**
     * El personal no se borrara, se pondra su campo historic a false
     * @param idstaff
     */
    public void delete(Long idstaff);

    /**
     *obtenemos la lista de Staff a partir de tag
     * @param tag
     * @return list <Staff>
     */
    public List<Staff> findByTag(String tag);
    /**
     *obtenemos el personal con ese username
     * @param tag
     * @return list <Staff>
     */
    public List<Staff> findByUsername(String username);



    public Staff check(String adminuser,String password,String username) throws Exception;
}
