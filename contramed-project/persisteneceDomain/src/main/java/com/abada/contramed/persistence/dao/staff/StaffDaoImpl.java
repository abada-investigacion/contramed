/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.contramed.persistence.dao.staff;

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

import com.abada.authentication.ldap.Authentication;
import com.abada.authentication.ldap.exception.LDAPException;
import com.abada.contramed.persistence.entity.Staff;
import com.abada.contramed.persistence.entity.enums.TypeRole;
import com.abada.contramed.persistence.entity.enums.TypeStaff;
import com.abada.springframework.orm.jpa.support.JpaDaoSupport;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest_Extjs_v3;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author maria
 */
@Repository("StaffDao")
public class StaffDaoImpl extends JpaDaoSupport implements StaffDao {

    private Authentication authentication;

    @Resource(name = "authenticationContramed")
    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }
   

    @Resource(name = "entityManager")
    public void setEntityManagerFactory2(EntityManagerFactory entityManagerFactory) {
        setEntityManagerFactory(entityManagerFactory);
    }

    /**
     * obtenemos el numero de Staff existentes que estan activos
     * @param filters
     * @return
     */
    @Transactional(readOnly = true)
    public Long loadSizeAll(GridRequest_Extjs_v3 filters,String option) {
        List<Long> result = new ArrayList<Long>();
        if(option.equals("2")){
                result=this.find("select count(*) from Staff st where st.historic=0" + filters.getQL("st", false), filters.getParamsValues());
         }else{
            if(option.equals("3")){
                 result=this.find("select count(*) from Staff st where st.historic=1" + filters.getQL("st", false), filters.getParamsValues());
            }  else{
                if(option.equals("1")){
                     result=this.find("select count(*) from Staff st " + filters.getQL("st", true), filters.getParamsValues());
                }
            }
        }
        return result.get(0);
    }

    /**
     *obtenemos la lista de todos los Staff que estan dados de alta actualmente
     * (historic=0)
     * @return list <Staff>
     */
    @Transactional(readOnly = true)
    public List<Staff> loadAll(GridRequest_Extjs_v3 filters, String option) {
        List<Staff> staff =new ArrayList<Staff>();
        if(option.equals("2")){
            staff = this.find("from Staff st where st.historic=0" + filters.getQL("st", false), filters.getParamsValues(), filters.getStart(), filters.getLimit());
        }else{
            if(option.equals("3")){
                staff = this.find("from Staff st where st.historic=1" + filters.getQL("st", false), filters.getParamsValues(), filters.getStart(), filters.getLimit());
            }else{
                if(option.equals("1")){
                    staff = this.find("from Staff st " + filters.getQL("st", false), filters.getParamsValues(), filters.getStart(), filters.getLimit());
                }
            }
        }
        return staff;
    }

    /**
     * Lista de todo el personal de la aplicación
     * @return
     */
    @Transactional(readOnly = true)
    public List<Staff> listStaff() {
        List<Staff> result = (List<Staff>) this.getJpaTemplate().findByNamedQuery("Staff.findAll");
        return result;
    }

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
    @Transactional
    public void insertStaff(String username, String tag, TypeRole role, String name, String surname1, String surname2) throws Exception{
        //Miramos si la persona que vamos a dar de alta
        // ya ha estado dada de alta en la aplicación anteriormente
        Staff s = new Staff();
        List<Staff> personal=this.findByUsername(username);
        //Si esa persona no esta en la base de datos
        if(personal.isEmpty()){
        //Comprobar que no existe ningun tag igual ni ese usuario ya esta en la base de datos
             List tags = this.findByTag(tag);
              if (tags.isEmpty()) {
                //No hay algun tag con ese valor             
                s.setUsername(username);
                s.setTag(tag);
                s.setRole(role);
                s.setName(name);
                s.setType(TypeStaff.NURSING);
                s.setSurname1(surname1);
                s.setSurname2(surname2);
                s.setHistoric(false);
                this.getJpaTemplate().persist(s);
            } else {//El tag ya habia sido asignado a otra persona.
                throw new Exception("Tag asignado a otro Empleado.");
            }
        }else{// La persona ha estado dada de alta con anterioridad en la base de datos, actualizacion

            this.updateStaff(personal.get(0).getIdstaff(), username, tag, role, name, surname1, surname2);
        }
    }

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
    @Transactional
    public void updateStaff(Long idstaff, String username, String tag, TypeRole role, String name, String surname1, String surname2) throws Exception {

        Staff stf = (Staff) this.getJpaTemplate().find(Staff.class, new Long(idstaff));

        if (!tag.equals(stf.getTag())) {
            //compruebo si es de alguien.
            List tags = this.findByTag(tag);
            if (!tags.isEmpty()) {
                throw new Exception("Tag asignado a otro Empleado.");
            }
        }

        if (stf.getIdstaff().compareTo(idstaff) == 0) {
            stf.setIdstaff(idstaff);
            stf.setUsername(username);
            stf.setTag(tag);
            stf.setType(TypeStaff.NURSING);
            stf.setRole(role);
            stf.setHistoric(false);
            stf.setName(name);
            stf.setSurname1(surname1);
            stf.setSurname2(surname2);
            //this.getJpaTemplate();
        }
    }

    /**
     * El personal no se borrara, se pondra su campo historic a false
     * @param idstaff
     */
    @Transactional
    @Override
    public void delete(Long idstaff) {
        Staff s = this.getJpaTemplate().find(Staff.class, idstaff);
        if (s != null) {
            s.setHistoric(true);
            this.getJpaTemplate().persist(s);
        }

    }

    /**
     *obtenemos la lista de Staff a partir de tag
     * @param tag
     * @return list <Staff>
     */
    @Transactional(readOnly = true)
    @Override
    public List<Staff> findByTag(String tag) {
        Map<String, String> parametros = new HashMap<String, String>();
        parametros.put("tag", tag);
        return this.getJpaTemplate().findByNamedQueryAndNamedParams("Staff.findByTag", parametros);
    }

    /**
     *obtenemos el personal con ese username
     * @param tag
     * @return list <Staff>
     */
    @Transactional(readOnly = true)
    @Override
    public List<Staff> findByUsername(String username) {
        Map<String, String> parametros = new HashMap<String, String>();
        parametros.put("username", username);
        return this.getJpaTemplate().findByNamedQueryAndNamedParams("Staff.findByUsername", parametros);
    }

    public Staff check(String adminuser,String password,String username) throws Exception{
        
        Staff sldap = new Staff();
        Staff sl = new Staff();
        //Busco en la base de datos si esta dado de alta ya en la base de datos
        List<Staff> usern = this.findByUsername(username);
        //Traigo la información del personal del LDAP.
        Map userValues = null;
        try {
            userValues = authentication.getUserInformation(adminuser, password, username);        
        } catch (LDAPException ex) {
            throw ex;
        }
        //Obtengo los datos de la persona
        if (userValues != null) {
            if (userValues.containsKey("sn")) {
                String[] aux = (String[]) userValues.get("sn");
                for (String a : aux) {
                    sldap.setSurname1(a);
                }
            }
            if (userValues.containsKey("givenName")) {
                String[] aux = (String[]) userValues.get("givenName");
                for (String a : aux) {
                    sldap.setName(a);
                }
            }
            //Creamos los datos con el personal que nos traemos de la base de datos
            sldap.setUsername(username);
            sldap.setHistoric(true);
            sldap.setRole(TypeRole.PHARMACIST);
            sldap.setTag("");

            //Miramos si la persona esta en la base de datos
            if (usern.isEmpty()) {
                //No esta dada de alta en la base de datos, devolemos los datos del LDAP
                return sldap;
            } else {
                //El personal ya ha sido dado de alta anteriormente en la base de datos
                if (usern.get(0).isHistoric()) {
                    sl.setName(usern.get(0).getName());
                    sl.setSurname1(usern.get(0).getSurname1());
                    sl.setSurname2(usern.get(0).getSurname2());
                    sl.setUsername(username);
                    sl.setRole(usern.get(0).getRole());
                    sl.setTag(usern.get(0).getTag());
                    return sl;
                } else {
                    throw new Exception("Personal ya existente en la Aplicación");
                }
            }
            
        }
        if (sldap != null){
            return sldap;
        } else {
            return sl;
        }
    }


}
