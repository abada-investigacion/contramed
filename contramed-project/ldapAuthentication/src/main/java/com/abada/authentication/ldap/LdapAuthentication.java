/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.authentication.ldap;

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

import com.abada.authentication.ldap.exception.LDAPException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.Attributes;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author david
 *
 *
 */
/*
 *Conexión y autentificación de usuarios con ldap
 */
public class LdapAuthentication implements Authentication {

    /**
     * es el DN que  identifica el nodo inicial de búsqueda dentro del árbol LDAP
     */
    private String basedn;
    /**
     * grupo del ldap
     */
    private String ou;
    /**
     * direccion donde se encuentra el servidor ldap
     */
    private String url;
    /**
     *
     */
    private String usernameField;
    
    /*
     * mostrado en consola información
     */
    private static final Log log = LogFactory.getLog(LdapAuthentication.class);

    public LdapAuthentication() {
    }

    public void setBasedn(String basedn) {
        this.basedn = basedn;
    }

    public void setOu(String ou) {
        this.ou = ou;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsernameField() {
        return usernameField;
    }

    public void setUsernameField(String usernameField) {
        this.usernameField = usernameField;
    }



    /**
     * conexion de usuarios con ldap y 
     * nos devuelve un hashMap con los atributos de ese usuario
     * en caso que no existe nos devuelve null
     * @param user
     * @param pass
     * @return HashMap
     */
    public Map authenticate(String user, String pass) {
        String userDN = getUsernameField()+"=" + user + "," + ou + "," + basedn;
        HashMap<String, String[]> atributos = null;
        try {
            atributos = (HashMap<String, String[]>) getconexionldap(user, pass, userDN, user);
        } catch (LDAPException ex) {
            Logger.getLogger(LdapAuthentication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return atributos;
    }

    /**
     * añadimos en un Hashmap los atributos del usuario
     * @param attrs
     * @return
     * @throws Exception
     */
    private HashMap formatAttributes(Attributes attrs) throws Exception {
        HashMap atributos = new HashMap();
        if (attrs == null) {
            log.error("This result has no attributes");
        } else {

            try {

                for (NamingEnumeration a = attrs.getAll();
                        a.hasMore();) {
                    Attribute attrib = (Attribute) a.next();
                    String nombreatributo = attrib.getID();
                    String atributo[] = new String[attrib.size()];
                    int i = 0;
                    for (NamingEnumeration e = attrib.getAll(); e.hasMore();) {
                        atributo[i++] = e.next().toString();
                    }
                    atributos.put(nombreatributo, atributo);
                }

            } catch (NamingException e) {
                log.error(Authentication.class.getName() + "error en Naming", e);
            }

        }
        return atributos;
    }

    /**
     * Obtenemos los datos de un usuario a partir de la conexion del administrador del ldap
     * @param adminUser
     * @param adminPassword
     * @param user
     * @return
     */
    public Map<String, String[]> getUserInformation(String adminUser, String adminPassword, String user) throws LDAPException {
        //String userDN = "cn=" + adminUser + "," + ou + "," + basedn;
        String userDN = getUsernameField()+"=" + adminUser + "," + ou + "," + basedn;
        HashMap<String, String[]> atributos = (HashMap<String, String[]>) getconexionldap(adminUser, adminPassword, userDN, user);
        return atributos;
    }

    /**
     * Conectamos con el ldap y obtenemos los atributos del user que queremos
     * @param userconexion
     * @param passwordconexion
     * @param userDN
     * @param user
     * @return
     */
    private Map<String, String[]> getconexionldap(String userconexion, String passwordconexion, String userDN, String user) throws LDAPException {
        Hashtable env = new Hashtable();
        if (userconexion.compareTo("") == 0 || passwordconexion.compareTo("") == 0) {
            return null;
        }
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, url + basedn);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, userDN);
        env.put(Context.SECURITY_CREDENTIALS, passwordconexion);
        HashMap atributos = null;
        try {
            DirContext ctx = new InitialDirContext(env);
            Attributes attrs = ctx.getAttributes(getUsernameField()+"=" + user + "," + ou);//obtenemos todos los atributos
            try {
                //obtenemos todos los atributos
                atributos = formatAttributes(attrs);
            } catch (Exception ex) {
                log.error(Authentication.class.getName() + " error en autentificacion : " + userconexion + " ", ex);
                throw new LDAPException("Error usuario no encontrado: " + userconexion);
            }
            ctx.close();

            log.debug("Authentication Success " + userconexion + " datos del usuario: " + user);


        } catch (AuthenticationException authEx) {
            log.error(Authentication.class.getName() + " error en autentificacion usuario: " + userconexion, authEx);
            throw new LDAPException("Error en autentificación usuario: " + userconexion);



        } catch (NamingException namEx) {
            log.error(Authentication.class.getName() + "error en Naming", namEx);
            throw new LDAPException("Error usuario no encontrado en Ldap: " + user);

        }

        return atributos;
    }
}
