/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abada.idmedicationweb.domain.file;

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

import com.abada.utils.file.FileManager;
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;
import org.springframework.context.support.ApplicationObjectSupport;


/**
 *
 * @author katsu
 */
public class FileManagerLocalPath extends ApplicationObjectSupport implements FileManager{
    private File path;

    public void setPath(File path) {
        //TODO eliminar al final
        //path=new File(System.getProperty("user.home"));
        //this.logger.debug(path.getAbsolutePath());
        if (path.exists() && path.isDirectory())
            this.path = path;
    }

    public File getFile(String uuid){
        File result=new File(mountPath(uuid));
        if (result.exists())
            return result;
        return null;
    }

    private String mountPath(String uuid){
        return path.getPath()+path.separator+uuid;
    }

    public UUID setFile(byte [] data){
        UUID uuid=UUID.randomUUID();

        if (saveFile(mountPath(uuid.toString()),data))
            return uuid;
        return null;
    }

    private boolean saveFile(String path, byte [] data){
        try {
            File filecon = new File(path);
            if(!filecon.exists()) {
                filecon.createNewFile();

                FileOutputStream out=new FileOutputStream(filecon,true);
                out.write(data);
                out.close();
                return true;
            }
        } catch(Exception ioe) {
        }
        return false;
    }

    public boolean deleteFile(String uuid){
        try {
            String path=mountPath(uuid);
            File filecon = new File(path);
            if(filecon.exists()) {
                filecon.delete();

                return true;
            }
        } catch(Exception ioe) {
        }
        return false;
    }
}
