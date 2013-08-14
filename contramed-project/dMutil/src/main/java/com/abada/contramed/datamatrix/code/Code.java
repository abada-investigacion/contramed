

package com.abada.contramed.datamatrix.code;

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
import java.util.Map;

   /**
    * Recibe como entrada un Map con los datos a codificar para obtener el String
    * con el que se va a generar el Datamatrix
    * @param salida: Salida del String usado para generar el Datamatrix
    */
public interface Code {
    public String codifica(Map<DatosCodeDecode,Object> entrada);

}
