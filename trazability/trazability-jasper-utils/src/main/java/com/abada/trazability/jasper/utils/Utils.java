/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.trazability.jasper.utils;

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
import static com.abada.trazability.jasper.commons.Utils.getLabelsPerBlister;
import static com.abada.trazability.jasper.commons.Utils.measureUnitToPixels;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 *
 * @author aroldan
 */
public class Utils {

    //Error margin admisible for a blister to fit in paper
    private final static int ERROR_MARGIN = 2;

    /**
     * Returns the internal report name. This name is the same that original
     * file name without the file extension.
     */
    public String getInnerReportName(String reportPath) {
        JasperDesign design;
        String name = null;
        try {
            design = JRXmlLoader.load(reportPath);
            name = design.getName();
        } catch (JRException ex) {
            ex.printStackTrace();
        }
        return name;
    }

    /**
     * Returns a OutputStream containing a PNG preview file with several blister in it.
     * @param reportPath Path to report
     * @param stylePath Path to style asociated to the report
     * @param DPI Dots Per Inch (Recomended values for preview quality are 50 or 100)
     * @param labelData Bean containing data to fullfill ONE label (several labels conforms ONE blister)
     * @param paper Bean containing information about the PNG previw (like dimensions, margins, number of blisters, etc)
     * @return the OutputStream with PDF data
     */
    public OutputStream exportReportToPreviewStream(String reportPath, String stylePath, int DPI, Label labelData, Paper paper) throws Exception {

        //load the initial report and style asociated
        JasperDesign design = JRXmlLoader.load(reportPath);

        //Modify inner style path in template from relative to absolute
        //Change template reference in report to new template file
        JRDesignExpression exp = (JRDesignExpression) design.getTemplates()[0].getSourceExpression();

        String absoluteStylePath = "\"" + stylePath.replace("\\", "\\\\") + "\"";
        /*Note that template path is written in absolute form (to allow
         * JVM can find and load it in
         * runtime). Single backslash must be escaped (dammed windows paths)
         */
        exp.setText(absoluteStylePath);
        exp = null;
        absoluteStylePath = null;

        //Compile the report
        JasperReport jasperReport = JasperCompileManager.compileReport(design);

        //Fill the report with label beans
        int nLabels = getLabelsPerBlister(design);
        design = null;

        Label[] list = new Label[nLabels];
        Arrays.fill(list, labelData);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, new JRBeanArrayDataSource(list));
        jasperReport = null;

        //Exports to Image (72ppp)
        BufferedImage image = (BufferedImage) JasperPrintManager.printPageToImage(jasperPrint, 0, 1.0f);

        //Creates the PNG preview
        if (paper.isLandscape()) {
            double newHeight = paper.getWidth();
            paper.setWidth(paper.getHeight());
            paper.setHeight(newHeight);
        }

        BufferedImage preview = new BufferedImage(measureUnitToPixels(paper.getWidth(), paper.getUnit()), measureUnitToPixels(paper.getHeight(), paper.getUnit()), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) preview.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, preview.getWidth(), preview.getHeight());
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        /*PNG change from 72dpi to DPI and zoom is reducted in concordance
         * so image can fits in pdf dimensions
         */

        int leftMargin = measureUnitToPixels(paper.getLeftMargin(), paper.getUnit());
        int rightMargin = measureUnitToPixels(paper.getRightMargin(), paper.getUnit());
        int topMargin = measureUnitToPixels(paper.getTopMargin(), paper.getUnit());
        int bottomMargin = measureUnitToPixels(paper.getBottomMargin(), paper.getUnit());
        int blisterXGap = measureUnitToPixels(paper.getBlisterXGap(), paper.getUnit());
        int blisterYGap = measureUnitToPixels(paper.getBlisterYGap(), paper.getUnit());
        int width = measureUnitToPixels(paper.getWidth(), paper.getUnit()) - rightMargin;
        int height = measureUnitToPixels(paper.getHeight(), paper.getUnit()) - topMargin;
        int x = leftMargin;
        int y = bottomMargin;

        boolean exit = false;
        int i = 0;
        //Construct the first pdf page (page to be repeated in document)
        for (i = 0; i < paper.getnBlisters() && !exit; i++) {
            g.drawImage(image, x, y, null);
            x += (image.getWidth() + blisterXGap);
            //If next image doesn't fit in width
            if ((x + image.getWidth()) > width) {
                //NewLine
                x = leftMargin;
                y += (image.getHeight() + blisterYGap);
                //New page
                if (y + image.getHeight() > height) {
                    exit = true;
                }
            }
        }

        //ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        FileOutputStream outputStream = new FileOutputStream("prueba.png");
        ImageIO.write(preview, "png", outputStream);

        return outputStream;
    }

    /**
     * Transform a report in a png image
     * @param reportPath
     * @param stylePath
     * @param labelData
     */
    public void report2BufferedImage(String reportPath, String stylePath, int DPI, Label labelData) throws Exception {
        //load the initial report and style asociated
        JasperDesign design = JRXmlLoader.load(reportPath);

        //Modify inner style path in template from relative to absolute
        //Change template reference in report to new template file
        JRDesignExpression exp = (JRDesignExpression) design.getTemplates()[0].getSourceExpression();

        String absoluteStylePath = "\"" + stylePath.replace("\\", "\\\\") + "\"";
        /*Note that template path is written in absolute form (to allow
         * JVM can find and load it in
         * runtime). Single backslash must be escaped (dammed windows paths)
         */
        exp.setText(absoluteStylePath);

        //Compile the report
        JasperReport jasperReport = JasperCompileManager.compileReport(design);

        //Fill the report with label beans
        int nLabels = getLabelsPerBlister(design);

        Label[] list = new Label[nLabels];
        Arrays.fill(list, labelData);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, new JRBeanArrayDataSource(list));

        //Exports to PNG image (72ppp)
        float zoomFactor = DPI / 72f;
        BufferedImage image = (BufferedImage) JasperPrintManager.printPageToImage(jasperPrint, 0, zoomFactor);

        BufferedImage result = new BufferedImage(600, 600, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) result.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(image, 0, 0, 300, 300, null);
        g.drawImage(image, 0, 300, 300, 300, null);
        g.drawImage(image, 300, 0, 300, 300, null);
        g.drawImage(image, 300, 300, 300, 300, null);
        ImageIO.write(result, "png", new File("prueba2.png"));



        /*ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);

        Image img = Image.getInstance(outputStream.toByteArray());
        img.setDpi(DPI, DPI);
        img.scalePercent(100.0f / zoomFactor);
        img.setCompressionLevel(9);
        return img;*/

    }

    /**
     * Returns a OutputStream containing a PDF file with several blister in it.
     * @param reportPath Path to report
     * @param stylePath Path to style asociated to the report
     * @param DPI Dots Per Inch (Recomended values for good image quality are 300 or 600)
     * @param labelData Bean containing data to fullfill ONE label (several labels conforms ONE blister)
     * @param paper Bean containing information about the PDF (like dimensions, margins, number of blisters, etc)
     * @return the OutputStream with PDF data
     */
    public OutputStream exportReportToPdfStream(String reportPath, String stylePath, int DPI, Label labelData, Paper paper) throws Exception {

        //load the initial report and style asociated
        JasperDesign design = JRXmlLoader.load(reportPath);

        int leftMargin = measureUnitToPixels(paper.getLeftMargin(), paper.getUnit());
        int rightMargin = measureUnitToPixels(paper.getRightMargin(), paper.getUnit());
        int topMargin = measureUnitToPixels(paper.getTopMargin(), paper.getUnit());
        int bottomMargin = measureUnitToPixels(paper.getBottomMargin(), paper.getUnit());
        int width = measureUnitToPixels(paper.getWidth(), paper.getUnit()) - rightMargin;
        int height = measureUnitToPixels(paper.getHeight(), paper.getUnit());

        int availableHeight = height - topMargin - bottomMargin;
        int availableWidth = width - leftMargin;
        int blisterHeight = design.getPageHeight();
        int blisterWidth = design.getPageWidth();

        //Check that paper margins are lower than paper dimensions
        if (availableHeight < 0 || availableWidth < 0) {
            throw new Exception("Paper margins must be lower than paper dimensions");
        } //Check that label fits inside paper (label dimensions < paper dimensions)
        else if ((blisterHeight - ERROR_MARGIN) > availableHeight || (blisterWidth - ERROR_MARGIN) > availableWidth) {
            throw new Exception("At least, one blister must fit in paper dimensions");
        } else {
            //Modify inner style path in template from relative to absolute
            //Change template reference in report to new template file
            JRDesignExpression exp = (JRDesignExpression) design.getTemplates()[0].getSourceExpression();

            String absoluteStylePath = "\"" + stylePath.replace("\\", "\\\\") + "\"";
            /*Note that template path is written in absolute form (to allow
             * JVM can find and load it in
             * runtime). Single backslash must be escaped (dammed windows paths)
             */
            exp.setText(absoluteStylePath);
            exp = null;
            absoluteStylePath = null;

            //Compile the report
            JasperReport jasperReport = JasperCompileManager.compileReport(design);

            //Fill the report with label beans
            int nLabels = getLabelsPerBlister(design);
            design = null;

            Label[] list = new Label[nLabels];
            Arrays.fill(list, labelData);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, new JRBeanArrayDataSource(list));
            jasperReport = null;

            //Exports to PNG image (72ppp)
            float zoomFactor = DPI / 72f;
            BufferedImage image = (BufferedImage) JasperPrintManager.printPageToImage(jasperPrint, 0, zoomFactor);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", outputStream);
            image = null;

            //Creates the pdf
            Rectangle dimensions = new Rectangle(measureUnitToPixels(paper.getWidth(), paper.getUnit()), measureUnitToPixels(paper.getHeight(), paper.getUnit()));
            if (paper.isLandscape()) {
                dimensions = dimensions.rotate();
                double newHeight = paper.getWidth();
                paper.setWidth(paper.getHeight());
                paper.setHeight(newHeight);
            }

            Document document = new Document(dimensions);

            /*PNG change from 72dpi to DPI and zoom is reducted in concordance
             * so image can fits in pdf dimensions
             */
            Image img = Image.getInstance(outputStream.toByteArray());
            outputStream.flush();
            outputStream.reset();
            img.setDpi(DPI, DPI);
            img.scalePercent(100.0f / zoomFactor);
            img.setCompressionLevel(9);

            int blisterXGap = measureUnitToPixels(paper.getBlisterXGap(), paper.getUnit());
            int blisterYGap = measureUnitToPixels(paper.getBlisterYGap(), paper.getUnit());
            int x = leftMargin;
            int y = height - (int) img.getScaledHeight() - topMargin;

            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            writer.setCompressionLevel(9);
            writer.setFullCompression();
            document.open();

            int i = 0;
            int nPages,imagesInLastPage;

            //If only one blister per page must be impressed...
            if (!paper.isSeveralBlistersPerPage()) {
                
                for (i = 0; i < paper.getnBlisters(); i++) {
                    img.setAbsolutePosition(x, y);
                    document.add(img);
                    document.newPage();
                }

                nPages = paper.getnBlisters();
                imagesInLastPage = 0;

            } //If several blisters per page must be impressed...
            else {
                boolean exit = false;
                //Construct the first pdf page (page to be repeated in document)
                for (i = 0; i < paper.getnBlisters() && !exit; i++) {
                    img.setAbsolutePosition(x, y);
                    document.add(img);
                    x += ((int) img.getScaledWidth() + blisterXGap);
                    //If next image doesn't fit in width
                    if ((x + (int) img.getScaledWidth()) > width) {
                        //NewLine
                        x = leftMargin;
                        y -= ((int) img.getScaledHeight() + blisterYGap);
                        //New page
                        if (y < bottomMargin) {
                            exit = true;
                        }
                    }
                }

                nPages = paper.getnBlisters() / i;
                imagesInLastPage = paper.getnBlisters() % i;

                //Construct the second pdf page (page to the last in document)
                if (imagesInLastPage > 0) {
                    y = height - (int) img.getScaledHeight() - topMargin;
                    document.newPage();
                    for (i = 0; i < imagesInLastPage; i++) {
                        img.setAbsolutePosition(x, y);
                        document.add(img);
                        x += ((int) img.getScaledWidth() + blisterXGap);
                        //If next image doesn't fit in width
                        if ((x + (int) img.getScaledWidth()) > width) {
                            //NewLine
                            x = leftMargin;
                            y -= ((int) img.getScaledHeight() + blisterYGap);
                        }
                    }
                }
            }
            document.close();
            writer.close();

            PdfReader reader = new PdfReader(outputStream.toByteArray());
            outputStream.flush();
            outputStream.reset();

            document = new Document(dimensions);
            //PdfCopy copy = new PdfCopy(document, new FileOutputStream("MyReport1.pdf"));

            PdfCopy copy = new PdfCopy(document, outputStream);
            copy.setCompressionLevel(9);
            copy.setFullCompression();
            document.open();




            //Duplicate first page n times (n=nBlisters/i)
            PdfImportedPage repeatedPage = copy.getImportedPage(reader, 1);
            for (int j = 0; j < nPages; j++) {
                copy.addPage(repeatedPage);
            }
            repeatedPage = null;
            //If several blister per page must be impressed and the
            //last page have images => insert second page at last one
            if (paper.isSeveralBlistersPerPage() && imagesInLastPage > 0) {
                copy.addPage(copy.getImportedPage(reader, 2));
            }

            copy.close();
            reader.close();
            document.close();

            return outputStream;
        }
    }
}
