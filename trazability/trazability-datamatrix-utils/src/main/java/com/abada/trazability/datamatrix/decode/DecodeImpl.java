package com.abada.trazability.datamatrix.decode;

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

import com.abada.trazability.datamatrix.code.Codificar;
import com.abada.trazability.datamatrix.dato.DatosCodeDecode;
import com.abada.trazability.datamatrix.dato.DatosExponentePeso;
import com.abada.trazability.datamatrix.dato.DatosExponenteVolumen;
import com.abada.trazability.datamatrix.dato.ObjectPeso;
import com.abada.trazability.datamatrix.dato.ObjectVolumen;
import com.abada.trazability.datamatrix.exceptions.ExceptionCodeDecode;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *Decodificar una cadena en formato GS1
 * @author maria
 */
public class DecodeImpl implements Decode {
    private static final Log logger=LogFactory.getLog(DecodeImpl.class);
    /**
     * Metodo para codificar en forma legible por el humano un string que representa
     * un formato GS1.
     * @param ent
     * @return map
     */
    public Map<DatosCodeDecode, Object> decode(String ent) {
        logger.debug("Entrada: "+ ent);
        CompruebaDecode cd = new CompruebaDecode();
        String sinparen = cd.quitaParentesis(ent);

        String a;
        if (sinparen.startsWith(Codificar.START_TAG)) {
            a = sinparen.substring(3, sinparen.length());
        } else {
            a = sinparen;
        }
        Map<DatosCodeDecode, Object> cadenadeco = new HashMap<DatosCodeDecode, Object>();
        /**
         * Comprobamos que el tamaño del String de entrada es correcto, sino algun
         * campo no estara bien codificado
         * El tamaño correcto se corresponde con :
         * GTIN n2+n14
         * BATCH n2+X..20
         * Expirate Date n2+n6 en formato YYMMDD
         * Serial Number n2+X..20
         * Wigeht n4+n6
         * Dimension n4+n6
         * En total tendremos 86 caracteres, teniendo en cuenta que se va a expresar el GTIN con 13 caracteres
         */
        if (a.length() > 86) {
            //new ExceptionCodeDecode("Cadena a decodificar incorrecta");
            String cad = "The size of the chain is superior to the allowed one";
            //FIXME falta lanzar la excepcion new ExceptionCodeDecode(cad);
        } else {
            String falta = "";
            while (a.length() != 0) {
                try {
                    if (a.substring(0, 1).equals(".")) {
                        return cadenadeco;
                    }
                    DatosCodeDecode dato = cd.recogecaso(a);
                    switch (dato) {
                        case EAN13:
                            //GTIN
                            falta = a.substring(16, a.length());
                            cadenadeco.put(dato.EAN13, a.substring(2, 16));
                            a = falta;
                            break;
                        case BATCH:
                            //BATCH
                            //La longitud Es variable a como maximo 20 caracteres
                            int maximalong = cd.comprobarLongitud(a.substring(2, a.length()), 20);
                            for (int i = 0; i < a.length(); i++) {
                                if (a.charAt(i) == ('<') || (a.charAt(i) == '.')) {
                                    if (a.charAt(i) == ('<')) {
                                        falta = a.substring(maximalong + 2, a.length());
                                        cadenadeco.put(dato.BATCH, a.substring(2, maximalong - 2));
                                        break;
                                    } else {
                                        falta = a.substring(maximalong, a.length());
                                        cadenadeco.put(dato.BATCH, a.substring(2, maximalong));
                                        break;
                                    }
                                }
                            }
                            a = falta;
                            break;
                        case EXPIRATEDATE:
                            //EXPIRATEDATE
                            falta = a.substring(8, a.length());
                            String fecha = a.substring(2, 8);
                            Calendar cal = cd.fechaCalendar(fecha);
                            cadenadeco.put(dato.EXPIRATEDATE, cal);
                            a = falta;
                            break;
                        case SERIALNUMBER:
                            //SerialNumber
                            //La longitud Es variable a como maximo 20 caracteres
                            int maxlong = cd.comprobarLongitud(a.substring(2, a.length()), 20);
                            for (int i = 0; i < a.length(); i++) {
                                if (a.charAt(i) == ('<') || (a.charAt(i) == '.')) {
                                    if (a.charAt(i) == ('<')) {
                                        falta = a.substring(maxlong + 2, a.length());
                                        cadenadeco.put(dato.SERIALNUMBER, a.substring(2, maxlong - 2));
                                        break;
                                    } else {
                                        falta = a.substring(maxlong, a.length());
                                        cadenadeco.put(dato.SERIALNUMBER, a.substring(2, maxlong));
                                        break;
                                    }
                                }
                            }
                            a = falta;
                            break;
                        case WEIGHT:
                            //Net weight, kilograms (Variable Measure Trade Item)
                            //Longitud fija de 6 caracteres, por eso al comprobar la cantidad de decimales
                            //que puede representar sera como maximo 5
                            ObjectPeso op = new ObjectPeso();
                            falta = falta = a.substring(10, a.length());
                            DatosExponentePeso caso = cd.recogeExpoPeso(a);
                            op.setExpo(caso);
                            String expop = cd.escribeExpoPeso(a, caso);
                            Double peso = Double.parseDouble(expop);
                            op.setValor(peso);
                            cadenadeco.put(dato.WEIGHT, op);
                            a = falta;
                            break;
                        case DIMENSION:
                            ObjectVolumen ov = new ObjectVolumen();
                            falta = falta = a.substring(10, a.length());
                            DatosExponenteVolumen volum = cd.recogeExpoVolumen(a);
                            ov.setExpo(volum);
                            String expov = cd.escribeExpoVolumen(a, volum);
                            Double volumen = Double.parseDouble(expov);
                            ov.setValor(volumen);
                            cadenadeco.put(dato.DIMENSION, ov);
                            a = falta;
                            break;
                        default:
                            break;
                    }
                } catch (ExceptionCodeDecode ex) {                    
                    logger.error(ex);
                }
            }
        }
        return cadenadeco;
    }
}


