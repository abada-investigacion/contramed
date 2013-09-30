/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.trazability.jasper.print;

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

import com.abada.trazability.jasper.commons.Label;
import com.abada.trazability.jasper.utils.Utils;
import com.abada.trazability.jasper.commons.units.MeasureUnit;
import com.abada.trazability.jasper.utils.Paper;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author mmartin
 */
public class GenLabelImpl implements GenLabel {

    private SimpleDateFormat dateFormat1;
    private SimpleDateFormat dateFormat2;
    private Utils utils;

    /**
     * Constructor vacio
     */
    public GenLabelImpl() {
        dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat2 = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        utils = new Utils();
    }

    /**
     * Genera las etiquetas de una dosis
     * @param name Nombre de la persona que genera las etiquetas
     * @param iddose Identificador de la dosis
     * @param datamatrix Datamatrix
     * @param date_exp Fecha de caducidad de la dosis
     * @param batch Lote al que pertenece la dosis
     * @param especialidad Nombre de la especialidad
     * @param pactivo Principio activo de la especialidad
     * @param reportPath Ruta del template
     * @param stylePath Ruta del style
     * @param blisters Numero de blisters
     * @param pagesize Formato de la hoja
     * @param msuperior Margen superior en cm
     * @param minferior Margen inferior en cm
     * @param mderecho Margen derecho en cm
     * @param mizquierdo Margen izquierdo en cm
     * @return
     */
    public OutputStream genLabel(String name, Long iddose, String datamatrix, String sExpDate1, String batch, String especialidad, String pactivo, String reportPath, String stylePath, Integer blisters, String pagesize, Integer msuperior, Integer minferior, Integer mderecho, Integer mizquierdo, boolean apaisado, double xGap, double yGap, String pbatch, String pexpiration_date, boolean SeveralBlisterPerPage) throws Exception {
       
        Calendar c2 = Calendar.getInstance();

        String sExpDate2 = dateFormat2.format(c2.getTime());

        Label labelData = new Label(especialidad, pactivo, name + " " + sExpDate2, pbatch+" "+batch, pexpiration_date+" "+sExpDate1, datamatrix);
        float width;
        float height;

        Paper paper = new Paper();
        paper.setnBlisters(blisters);
        paper.setBlisterXGap(xGap);
        paper.setBlisterYGap(yGap);
        paper.setTopMargin(msuperior);
        paper.setBottomMargin(minferior);
        paper.setRightMargin(mderecho);
        paper.setLeftMargin(mizquierdo);
        paper.setUnit(MeasureUnit.PIX);
        paper.setLandscape(apaisado);


        /*if (pagesize.equals("11x17")) {
            width = (float) com.abada.jasperCommons.Utils.measureUnitToPixels(11.3, MeasureUnit.CM);
            height = (float) com.abada.jasperCommons.Utils.measureUnitToPixels(16.8, MeasureUnit.CM);
        } else if (pagesize.equals("10x16")) {
            width = (float) com.abada.jasperCommons.Utils.measureUnitToPixels(9.9, MeasureUnit.CM);
            height = (float) com.abada.jasperCommons.Utils.measureUnitToPixels(15.9, MeasureUnit.CM);
        } else {
            Rectangle a = PageSize.getRectangle(pagesize);
            width = a.getWidth();
            height = a.getHeight();
        }*/

        if (pagesize == null) throw new Exception("PageSize is null.");
        String [] aux=pagesize.split("x");
        if (aux.length!=2) throw new Exception("Error Format in pagesize.");

        width = Integer.parseInt(aux[0]);
        height = Integer.parseInt(aux[1]);

        paper.setWidth(width);
        paper.setHeight(height);

        return (OutputStream) utils.exportReportToPdfStream(reportPath, stylePath, 300, labelData, paper);
    }
}
