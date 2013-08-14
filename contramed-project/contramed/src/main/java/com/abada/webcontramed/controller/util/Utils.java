/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abada.webcontramed.controller.util;

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

import com.abada.contramed.datamatrix.dato.DatosCodeDecode;
import com.abada.contramed.datamatrix.decode.Decode;
import com.abada.contramed.datamatrix.decode.DecodeImpl;
import java.util.Map;

/**
 *
 * @author katsu
 */
public class Utils {
    public static Long decodeIdDose(String datamatrixData){        
        Decode dec=new DecodeImpl();
        Map<DatosCodeDecode,Object> data=dec.decode(datamatrixData);
        if (data!=null){
            Object aux=data.get(DatosCodeDecode.SERIALNUMBER);
            if (aux!=null){
                try{
                    return Long.parseLong(aux.toString());
                }catch (NumberFormatException e){

                }
            }
        }
        return null;        
    }
}
