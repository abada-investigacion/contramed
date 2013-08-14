
package com.abada.contramed.datamatrix.decode;

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
import com.abada.contramed.datamatrix.exceptions.ExceptionCodeDecode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
     * En total tendremos 97 caracteres, teniendo en cuenta que se va a expresar el GTIN con 13 caracteres
     */
public class CompruebaDecode {
   

 /**
 * Comprobamos que la longitud de los campos de longitud variable se encuentra dentro
 * del rango permitido.
 * @param subcadena
 * @param maximo
 * @return
 */
  public  int comprobarLongitud(String subcadena,int maximo){
        int longitud = 0;
        for (int i = 0; i < subcadena.length(); i++) {
            longitud++;
            if (subcadena.charAt(i) == ('<') || (subcadena.charAt(i) == '.') || (i == maximo)) {
                if (subcadena.charAt(i) == ('<')) {
                    longitud = longitud + 3;
                    return longitud;
                } else {
                    if (i==maximo) {
                        longitud = longitud + 4;
                        return longitud;
                    } else {
                        longitud++;
                        return longitud;
                    }

                }

            
            }

        }
        return longitud;
    }
   /**
    * Dependiendo de los digitos decimales que se hayan introducido a la hora de expresar el
    * peso o el volumen, colocaremos el punto decimal en una posicion u otra.
    * @param a
    * @param caso
    * @return
    */
  public  String escribeExpoPeso(String a, DatosExponentePeso caso){
        String expo="";
        switch(caso){
            case kg:
                 expo=a.substring(4,10)+caso;
                break;
            case dag:
                  expo=a.substring(4,9)+"."+ a.substring(9,10);
                 
                  break;
            case hg:
                 expo=a.substring(4,8)+"."+ a.substring(8,10);
                 break;
            case g:
                expo=a.substring(4,7)+"."+ a.substring(7,10);
                
            case dg:
                expo=a.substring(4,6)+"."+ a.substring(6,10);
                
                break;
            case cg:
                expo=a.substring(4,5)+"."+ a.substring(5,10);
                
                break;
            case mg:
                expo="0."+a.substring(4,10);
             
                break;
            default:
                expo=a.substring(4,10)+caso;
        }
       return expo;

    }
  public  String escribeExpoVolumen(String a, DatosExponenteVolumen caso){
       String expo="";
        switch(caso){
            case l:
               expo=a.substring(4,10);

               break;
            case dl:
                expo=a.substring(4,9)+"."+a.substring(9);

                break;
            case cl:
                expo=a.substring(4,8)+"."+a.substring(8,10);
                break;
            case ml:
                expo=a.substring(4,7)+"."+a.substring(7,10);
                break;
           
            default:
                expo=a.substring(4,10)+caso.l;
        }
       return expo;

    }
/**
 * Quita los parentesis introducidos para que el GS1 sea legible por el ojo humano
 * @param a
 * @return
 */
  public  String quitaParentesis(String a) {
        String entrada ="";
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i)!='(')  {
                if(a.charAt(i)!=')'){
                    entrada += a.charAt(i);
            }
            }
        }
        return entrada;
    }
/**
 * Dependiendo de los valores iniciales del string a decodificar nos encontramos ante
 * diversos casos de decodificacion
 * @param a
 * @return
 */
  public  DatosCodeDecode recogecaso(String a){
     DatosCodeDecode caso=DatosCodeDecode.EAN13;
        if (a.substring(0, 2).equals("01")) {
            return caso.EAN13;
        }
        if (a.substring(0, 2).equals("10")) {
            return caso.BATCH;
        }
        if (a.substring(0, 2).equals("17")) {
          return caso.EXPIRATEDATE;
        }
        if (a.substring(0, 2).equals("21")) {
            return caso.SERIALNUMBER;
        }
        if (a.substring(0, 3).equals("310")) {
            return caso.WEIGHT;
        }
        if (a.substring(0, 3).equals("315")) {
            return caso.DIMENSION;
        }
       return caso;
    }
/**
 * Igual que en el caso anterior, dependiendo de la forma de expresar el valor del peso
 * y la dimension estamos ante varios casos.
 * @param a
 * @return
 */
  public  DatosExponentePeso recogeExpoPeso(String a){
 //Kg, Dg, Hg, gr, dg, cg, mg, mlg, ng
       DatosExponentePeso expo=DatosExponentePeso.kg;

        if (a.charAt(3) == '0') {
           return expo.kg;
        }
        if (a.charAt(3) == '1') {
            return expo.dag;
        }
        if (a.charAt(3) == '2') {
          return expo.hg;
        }
        if (a.charAt(3) == '3') {
             return expo.g;
        }
        if (a.charAt(3) == '4') {
            return expo.dg;
        }
        if (a.charAt(3) == '5') {
             return expo.cg;
        }
        if (a.charAt(3) == '6') {
             return expo.mg;
        }
       
       return expo;
  }
  public  DatosExponenteVolumen recogeExpoVolumen(String a){

       DatosExponenteVolumen expov=DatosExponenteVolumen.l;
  
        if (a.charAt(3) == '0') {
             return expov.l;
        }
        if (a.charAt(3) == '1') {
            return expov.dl;
        }
        if (a.charAt(3) == '2') {
             return expov.cl;
        }
        if (a.charAt(3) == '3') {
             return expov.ml;
        }
        
        return expov;
  }
  /**
   * Pasamos de String a Calendar para decodificar
   * @param fecha
   * @return
   * @throws ExceptionCodeDecode
   */
  public Calendar fechaCalendar(String fecha) throws ExceptionCodeDecode{

  
      Calendar cal = Calendar.getInstance();
      SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
      Date date;
      String a="Mistake in the expirate date ";
        try {
            date = sdf.parse(fecha);
        } catch (ParseException ex) {
            throw new ExceptionCodeDecode(a);
        }
     cal.setTime(date);
      return cal;
  }
    
    
}
