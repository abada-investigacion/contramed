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
import com.abada.contramed.datamatrix.exceptions.ExceptionCodeDecode;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * El formato para la codificacion de los GS1 sera el siguiente:
 * como caracter inicial aparecera el FNC1() que vendra codificado como ]d2
 * A continuacion se colocaran los IA:identificadores de aplicación, teniendo en cuenta
 * que aquellos cuya longitud es variable vendra finalizado como el caracter separador<GS>
 *en el caso que no sea un campo final.
 * Un ejemplo de GS1 codificado sera: ]d201371827097123410342LX<GS>171009092112345678<GS>3153123456."
 *
 *
 */
public class Codificar implements Code {

    private static final Log logger = LogFactory.getLog(Codificar.class);
    public static final String START_TAG = "]d2";

    /**
     * Recibe como entrada un Map con los datos a codificar para obtener el String
     * con el que se va a generar el Datamatrix
     *
     * @return salida: Salida del String usado para generar el Datamatrix con legibilidad
     * para la maquina y no humana; es decir, no aparece con parentesis
     */
    public String codifica(Map<DatosCodeDecode, Object> entrada) {
        CompruebaCode cc = new CompruebaCode();
        String salida = START_TAG; //Salida con legibilidad
         /*
         *Creo el iterador para recorrer el Map
         */
        //Mientras queden claves por leer vamos a comprobar el contenido del map
        //Recogemos todas las key

        Iterator itKeys = entrada.keySet().iterator();

        while (itKeys.hasNext()) {

            DatosCodeDecode llave = (DatosCodeDecode) itKeys.next(); //Siguiente key
            Object value = (Object) entrada.get(llave); //Siguiente valor
            boolean fin = false;
            if (itKeys.hasNext()) {
                fin = true;
            } else {
                fin = false;
            }
            try {

                salida = salida + cc.estado(llave, value, itKeys, entrada, fin);
            } catch (ExceptionCodeDecode ex) {
                logger.error(ex);
            }
        }
        logger.debug("Salida: "+salida);
        return salida + ".";
    }
}
