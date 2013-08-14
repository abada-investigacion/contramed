/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.contramedadministrationweb.controller;

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
import com.abada.contramed.persistence.dao.staff.StaffDao;
import com.abada.contramed.persistence.entity.Staff;
import com.abada.contramed.persistence.entity.enums.TypeRole;
import com.abada.contramed.persistence.entity.enums.TypeStaff;
import com.abada.contramedadministrationweb.entities.StaffLdap;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest_Extjs_v3;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.factory.GridRequest_Extjs_v3Factory;
import com.abada.springframework.web.servlet.handler.interceptor.annotation.Role;
import com.abada.springframework.web.servlet.view.JsonView;
import com.abada.utils.enumerates.Utils;
import com.abada.web.exjs.ExtjsStore;
import com.abada.web.exjs.Success;
import com.abada.webcontramed.controller.CommonControllerConstants;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Realiza las comprobaciones de la existencia del personal en el LDAP , al igual que
 * la inserción,modificación y borrado del tag asociado a dicho personal
 * @author maria
 */
@Controller
@Role({"SYSTEM_ADMINISTRATOR",
    "ADMINISTRATIVE"})
public class StaffController extends CommonControllerConstants {
    private StaffDao staffDao;

    @Resource(name = "StaffDao")
    public void setStaffDao(StaffDao staffDao) {
        this.staffDao = staffDao;
    }
    
   private Authentication authentication;

    @Resource(name = "authenticationContramed")
    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }


    /**
     * Pantalla inicial de la gestión de Staff
     * @return
     */
    @RequestMapping("/staff.htm")
    public ModelAndView staff() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("js", new String[]{"common-administration.js", "staff.js"});
        return new ModelAndView("pages/main", model);
    }
   /**
    * Grid con los datos del personal que usa la aplicación
    * Grid que muestra los datos de {@link staff} <br/>
    * @return
    */
    @RequestMapping("/gridstaff.htm")
    public ModelAndView gridStaff(String filter, String dir, String sort, Integer limit, Integer start,String filterStaff) throws UnsupportedEncodingException {

        GridRequest_Extjs_v3 request = GridRequest_Extjs_v3Factory.parse(sort, dir, start, limit, filter);
        ExtjsStore store = new ExtjsStore();
        List<Staff> st= staffDao.loadAll(request,filterStaff);
        store.setTotal(staffDao.loadSizeAll(request, filterStaff).intValue());
        store.setData(st);

        return new ModelAndView(new JsonView(store));
    }
   /**
    *  Inserta en la base de datos un nuevo personal de farmacia o enfermeria
    * @param username
    * @param tag
    * @param type
    * @param role
    * @param name
    * @param surname1
    * @param surname2
    * @param request
    * @return
    */
     @RequestMapping("/insertstaff.htm")
    public ModelAndView insertStaff(String username, String tag, String role,String name,String surname1,String surname2,HttpServletRequest request) {
        Success result = new Success(false);
        TypeRole roleAux = (TypeRole) Utils.get(role, TypeRole.class);
        if (role != null && name != null && surname1 != null) {
            try {
                staffDao.insertStaff(username, tag, roleAux, name, surname1, surname2);
                result.setSuccess(Boolean.TRUE);
            } catch (Exception ex) {
                result.setErrors(new com.abada.web.exjs.Error(ex.getMessage()));
            }
        }
        return new ModelAndView(new JsonView(result));

    }

    /**
     * Modificacion de los datos del Personal
     * @param idstaff
     * @param username
     * @param tag
     * @param type
     * @param role
     * @param name
     * @param surname1
     * @param surname2
     * @return
     */

    @RequestMapping("/updateStaff.htm")
    public ModelAndView updateStaff(String idstaff,String username,String tag, String role,String name,String surname1,String surname2) {

        Success result = new Success(false);
        if (role != null && role != null && name != null && surname1!=null) {
            Long id = Long.parseLong(idstaff);
            TypeRole roleAux = (TypeRole) Utils.get(role, TypeRole.class);
            try{
                staffDao.updateStaff(id, username, tag, roleAux, name, surname1, surname2);
                result.setSuccess(Boolean.TRUE);
            }catch(Exception ex){
               result.setErrors(new com.abada.web.exjs.Error(ex.getMessage()));
            }
        }
        return new ModelAndView(new JsonView(result));
    }
    /**
     * Se pone el campo historico a false, para mantener el personal en la base
     * de datos y poder guardar la información relativa a su trabajoen un
     * historico
     * @param id: identifiacdor del personal
     * @return
     */

    @RequestMapping("/removeStaff.htm")
    public ModelAndView removeStaff(String id) {
        Success result = new Success(false);
        try{
            staffDao.delete(Long.parseLong(id));
            result.setSuccess(Boolean.TRUE);
        }catch(Exception ex){
               result.setErrors(new com.abada.web.exjs.Error(ex.getMessage()));
        }
        return new ModelAndView(new JsonView(result));
    }
    
    /**
     * Valida la existencia del personal con ese usuario y ese password en el
     * LDAP.
     * @param user:nombre de usuario en el LDAP
     * @param password: password del usuario en el LDAP
     * @param request
     * @return
     */
    @RequestMapping("/uservalidation.htm")
    public ModelAndView checkuserLDAP(String adminuser,String password,String user,HttpServletRequest request){

        Success result = new Success(false);
        StaffLdap sldap = new StaffLdap();
        Staff s =new Staff();
        try{
            s=staffDao.check(adminuser,password,user);
            if (s!=null){
                result.setSuccess(Boolean.TRUE);
                sldap.setName(s.getName());
                sldap.setSurname1(s.getSurname1());
                sldap.setSurname2(s.getSurname2());
                sldap.setRole(s.getRole().toString());
                sldap.setTag(s.getTag());
                sldap.setUsername(user);
                this.setStaffLdap(request, sldap);
            }else{
                result.setSuccess(Boolean.FALSE);
                 this.setStaffLdap(request, null);
            }
        }catch(Exception ex){
            result.setErrors(new com.abada.web.exjs.Error(ex.getMessage()));
        }


        return new ModelAndView(new JsonView(result));
    }

    /**
     * Cargamos los datos del usuario del LDAP.
     * @param request
     * @return
     */
    @RequestMapping("/loadDataLdap.htm")
    public ModelAndView loadData(HttpServletRequest request) {
        ExtjsStore result = new ExtjsStore();
        List<StaffLdap> sl=new ArrayList<StaffLdap>();
        sl.add(this.getStaffLdap(request));
        result.setData(sl);
        return new ModelAndView(new JsonView(result));
    }
  

}
