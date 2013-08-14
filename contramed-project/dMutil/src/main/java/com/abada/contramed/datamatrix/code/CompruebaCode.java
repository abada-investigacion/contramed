/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import com.abada.contramed.datamatrix.dato.DatosExponentePeso;
import com.abada.contramed.datamatrix.dato.DatosExponenteVolumen;
import com.abada.contramed.datamatrix.dato.ObjectPeso;
import com.abada.contramed.datamatrix.dato.ObjectVolumen;
import com.abada.contramed.datamatrix.exceptions.ExceptionCodeDecode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;

/**
 *Clase para comprobar los datos recibidos para codificar
 * @author maria
 */

public class CompruebaCode {

    /**
     *Comprobamos que el tamaño de los campos son correctos
     * @param
     */
    public boolean tamGtin(Object gtin) throws ExceptionCodeDecode {
        //Comprobamos que el tamaño de los campos de tamaño fijo son correctos
        String a = "EAN-13 incorrect format.";
        if (gtin.toString().length()> 13) {
            throw new ExceptionCodeDecode(a);
        } else {
            return true;
        }
    }

    public String quitaDecimales(ObjectPeso valor) throws ExceptionCodeDecode {

        String peso="";
        //Comprobamos que el tamaño de los campos de tamaño fijo son correctos
        String cadena = Double.toString(valor.getValor());
        int i = cadena.indexOf('.');
        String parteEntera = cadena.substring(0, i);
        String parteFraccion = cadena.substring(i + 1);
        peso=parteEntera+parteFraccion;
        return peso;
    }
    public String quitaDecimalesVolumen(ObjectVolumen valor) throws ExceptionCodeDecode {

        String peso="";
        //Comprobamos que el tamaño de los campos de tamaño fijo son correctos
        String cadena = Double.toString(valor.getValor());
        int i = cadena.indexOf('.');
        String parteEntera = cadena.substring(0, i);
        String parteFraccion = cadena.substring(i + 1);
        peso=parteEntera+parteFraccion;
        return peso;
    }
    public boolean tamPeso(String peso) throws ExceptionCodeDecode {
        //Comprobamos que el tamaño de los campos de tamaño fijo son correctos
        String a = "Weight incorrect format.";
        if (peso.length() > 7) {
            //new ExceptionCodeDecode("Dimension incorrecta");
            throw new ExceptionCodeDecode(a);
        } else {
            return true;
        }
    }

    public boolean tamVolum(String volu) throws ExceptionCodeDecode {
        //Comprobamos que el tamaño de los campos de tamaño fijo son correctos
        String a = "Dimension incorrect format.";
        if (volu.length() > 7) {
            //new ExceptionCodeDecode("Dimension incorrecta");
            throw new ExceptionCodeDecode(a);
        } else {
            return true;
        }
    }

    public boolean tamLote(Object lote) throws ExceptionCodeDecode {

        //Comprobamos que el tamaño de los campos de tamaño fijo son correctos
        String a = "BATCH incorrect format.";
        if (lote.toString().length() > 20) {
            //new ExceptionCodeDecode("Numero de lote incorrecto");
            throw new ExceptionCodeDecode(a);
        }
        return true;
    }

    public boolean tamSerie(Object serie) throws ExceptionCodeDecode {

        //Comprobamos que el tamaño de los campos de tamaño fijo son correctos
        String a = "Serial Number incorrect format.";
        if (serie.toString().length() > 20) {
            //new ExceptionCodeDecode("Numero de Serie incorrecto");
            throw new ExceptionCodeDecode(a);
        }
        return true;
    }

     /**
     * Comprobamos tambien que el formato introducido es YYMMDD para el campo ExpirateDate
      * y los datos son correctos
     */
    public String compruebaFecha(Calendar cadu) throws ExceptionCodeDecode {
       SimpleDateFormat sdf=new SimpleDateFormat("yyMMdd");
        return sdf.format(cadu.getTime());
        /*
        String fechacorrecta="";
        String diacorrecto="";
        String mescorrecto="";

        int anyo=cadu.get(Calendar.YEAR);
        int mes=cadu.get(Calendar.MONTH);
        int dia=cadu.get(Calendar.DATE);
        if ((mes <= 12 && 0 < mes)) {
            if ((dia <= 31 && 1 <= dia)) {
                if (dia <= 9 && 1 <= dia) {
                    diacorrecto = "0" + Integer.toString(dia);
                } else {
                    diacorrecto = Integer.toString(dia);
                }
                if (mes <= 9 && 1 <= mes) {
                    mescorrecto = "0" + Integer.toString(mes);
                } else {
                    mescorrecto = Integer.toString(mes);
                }
                fechacorrecta = Integer.toString(anyo).substring(1, 3) + mescorrecto + diacorrecto;

            }
        } else {
            //new ExceptionCodeDecode("Fecha en formato incorrecto");
            String a = "Month or day out of range, month (1,12) and day (1,31),correct format YYMMDD";
            throw new ExceptionCodeDecode(a);

        }

        return fechacorrecta;*/
    }
    /**
     * Valor en el que se expresa el peso
     * @param valor
     * @return
     */
    public int devuelveExpoPeso(ObjectPeso valor){
        int salida=0;
        if(valor.getExpo() == DatosExponentePeso.kg){
          salida=0;
        }
         if(valor.getExpo() == DatosExponentePeso.dag){
          salida=1;
        }
         if(valor.getExpo() == DatosExponentePeso.hg){
          salida=2;
        }
         if(valor.getExpo() == DatosExponentePeso.g){
          salida=3;
        }
         if(valor.getExpo() == DatosExponentePeso.dg){
          salida=4;
        }
         if(valor.getExpo() == DatosExponentePeso.cg){
          salida=5;
        }
         if(valor.getExpo() == DatosExponentePeso.mg){
          salida=6;
        }
          return salida;
    }
    /**
     * Valor en el que se expresa el volumen
     * @param valor
     * @return
     */
    public int devuelveExpoVolumen(ObjectVolumen valor){
        int salida=0;
        
        if(valor.getExpo()  == DatosExponenteVolumen.l){
          salida=0;
        }
         if(valor.getExpo()  == DatosExponenteVolumen.dl){
          salida=1;
        }
         if(valor.getExpo()  == DatosExponenteVolumen.cl){
          salida=2;
        }
         if(valor.getExpo() == DatosExponenteVolumen.ml){
          salida=3;
        }
          return salida;
    }
    /**
     * Metodo que realiza la codificacion en formato GS1
     * @param dato
     * @param valor
     * @param i
     * @param entrada
     * @param fincadena
     * @return
     * @throws ExceptionCodeDecode
     */
    public String estado(DatosCodeDecode dato, Object valor, Iterator i, Map<DatosCodeDecode, Object> entrada, boolean fincadena) throws ExceptionCodeDecode {

        String separador = "<GS>";/*caracter separador*/
        String codificar = ""; /*Caracter inicial de la cadena*/

        switch (dato) {
            case EAN13://GTIN
                if (tamGtin(valor)) {
                    //Comprobamos que realmente son 13 caracteres correspondientes con
                    //el EAN en España
                    if (valor.toString().length() == 13) {
                        codificar = codificar + "01" + valor;//Formamos el String de salida
                    } else {
                        while (valor.toString().length()< 14) {
                            valor="0" + valor;

                        }
                        codificar = codificar + "01" + valor;
                    }
                }
                break;
            case BATCH://Separador al final si no es fin de map
                //Comprobamos que los caracteres son menos que 20
                //BATCH
                if (tamLote(valor)) {
                    if (fincadena == false) {
                        codificar = codificar + "10" + valor;
                    } else {
                        codificar = codificar + "10" + valor + separador;
                    }
                }
                break;
            case EXPIRATEDATE:
                //ExpirationDate    
                //Comprobar que es una fecha correcta y son 6 caracteres
                // valor.getExpirateDate().DATE;
                String cadu = compruebaFecha((Calendar) valor);
                codificar = codificar + "17" + cadu;
                break;
            //Serial Number
            case SERIALNUMBER: //Separador al final si no es fin de map
                if (tamSerie(valor)) {

                    if (fincadena == false) {
                        //se ha terminado la cadena no se pone el separador
                        codificar = codificar + "21" + valor;
                    } else {
                        codificar = codificar + "21" + valor + separador;
                    }
                }

                break;
            //Weight
            case WEIGHT:

                //Comprobamos si viene o no el campo
                int expo =devuelveExpoPeso((ObjectPeso) valor);
                String peso=quitaDecimales((ObjectPeso) valor);
                if (tamPeso(peso)) {
                    if (peso.length() == 6) {
                        codificar = codificar + "310" + expo + peso;//Formamos el String de salida
                    } else {
                        while (peso.length() < 6) {
                            peso = "0" + peso;

                        }
                        codificar = codificar + "310" + expo + peso;
                    }

                }
                break;

            case DIMENSION:

                int expovol =devuelveExpoVolumen((ObjectVolumen) valor);
                String vol=quitaDecimalesVolumen((ObjectVolumen) valor);
                if (tamPeso(vol)) {
                    if (vol.length() == 6) {
                        codificar = codificar + "315" + expovol + vol;//Formamos el String de salida
                    } else {
                        while (vol.length() < 6) {
                            vol = "0" + vol;

                        }
                        codificar = codificar + "315" + expovol + vol;
                    }

                }
                break;
            default:
                break;
        }
        return codificar;
    }
}
