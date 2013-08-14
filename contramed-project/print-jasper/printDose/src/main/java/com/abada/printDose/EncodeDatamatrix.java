/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.printDose;

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

import com.abada.contramed.datamatrix.code.Code;
import com.abada.contramed.datamatrix.code.Codificar;
import com.abada.contramed.datamatrix.dato.DatosCodeDecode;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author mmartin
 */

public class EncodeDatamatrix {

    public EncodeDatamatrix(){

    }

    /**
     * Metodo que genera el datamatrix para una dosis
     * @param date_exp Fecha de caducidad de la dosis
     * @param idDose Identificador de la dosis
     * @return
     */
    public String encodeDatamatrix(Date date_exp, Long idDose) {
        Map<DatosCodeDecode, Object> entrada = new HashMap<DatosCodeDecode, Object>();

        Calendar cal = Calendar.getInstance();
        /*cal.set(Calendar.YEAR, date_exp.getYear());
        cal.set(Calendar.MONTH, date_exp.getMonth() + 1);
        cal.set(Calendar.DATE, date_exp.getDate());*/
        cal.setTime(date_exp);

        //entrada.put(DatosCodeDecode.EXPIRATEDATE, cal);//Fecha de caducidad
        entrada.put(DatosCodeDecode.SERIALNUMBER, idDose);//Nº Serie

        Code c = new Codificar();
        String datamatrix = c.codifica(entrada);

        return datamatrix;

    }
}
