/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.figerprintdb4o;

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

import com.abada.commons.fingerprint.Verificator;
import com.abada.figerprintdb4o.data.User;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author katsu
 */
public class Db4oUserDatabase implements UserDatabase {

    protected static final String FILE = System.getProperty("user.home") + File.separatorChar + "figerprint.db";
    private ObjectContainer db;

    public Db4oUserDatabase() {
    }

    public void addUser(User user) {
        open();
        try {
            Query q = db.query();
            q.constrain(User.class);
            q.descend("username").constrain(user.getUsername());
            ObjectSet result = q.execute();
            if (result != null && result.size() == 1) {
                User aux = ((User) result.get(0));
                aux.setAll(user);
                db.store(aux);
            } else {
                db.store(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public User getUser(String username) {
        open();
        try {
            Query q = db.query();
            q.constrain(User.class);
            q.descend("username").constrain(username);
            ObjectSet result = q.execute();
            if (result != null && result.size() == 1) {
                return (User) result.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return null;
    }

    public List<User> getAllUser() {
        List<User> result=new ArrayList<User>();
        open();
        try {
            ObjectSet os=db.query(User.class);
            for (Object o:os){
                result.add((User)o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result;
    }

    public User getUserByFingerPrint(byte [] finger,Verificator verificator){
        List<User> users=this.getAllUser();
        for (User user:users){
            for (byte [] f:user.getTemplates().values()){
                if (verificator.verification(f, finger))
                    return user;
            }
        }
        return null;
    }

    private synchronized void open() {
        db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), FILE);
    }

    private synchronized void close() {
        if (db != null) {
            db.close();
            db = null;
        }
    }
}
