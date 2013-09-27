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

import com.lowagie.text.PageSize;
import static com.abada.trazability.jasper.commons.Utils.pixelsToMeasureUnit;
import com.abada.trazability.jasper.commons.units.MeasureUnit;

/**
 *
 * @author aroldan
 */
public class Paper {

    //Number of blisters
    private int nBlisters;

    //Landscape (invert dimensions) or portrait
    private boolean landscape;

    //Paper dimensions
    private double width;
    private double height;

    //Paper margins
    private double topMargin;
    private double bottomMargin;
    private double leftMargin;
    private double rightMargin;

    //Gap between blisters
    private double blisterXGap;
    private double blisterYGap;

    //Measure unit used
    private MeasureUnit unit;

    //Several blisters per page
    private boolean severalBlistersPerPage;

    /**
     * Creates a default Paper with 4 blisters. Defaults to DINA4 and
     * all margins and gaps = 0
     */
    public Paper() {
        nBlisters = 4;
        landscape = false;
        width = pixelsToMeasureUnit((int)PageSize.A4.getWidth(),MeasureUnit.CM);
        height = pixelsToMeasureUnit((int)PageSize.A4.getHeight(),MeasureUnit.CM);
        topMargin = 0.0;
        bottomMargin = 0.0;
        leftMargin = 0.0;
        rightMargin = 0.0;

        blisterXGap = 0.0;
        blisterYGap = 0.0;

        unit = MeasureUnit.CM;
        severalBlistersPerPage=true;
    }

    public Paper(int nBlisters, boolean landscape, double width, double height, double topMargin, double bottomMargin, double leftMargin, double rightMargin, double blisterXGap, double blisterYGap, MeasureUnit unit) {
        this.nBlisters = nBlisters;
        this.landscape = landscape;
        this.width = width;
        this.height = height;
        this.topMargin = topMargin;
        this.bottomMargin = bottomMargin;
        this.leftMargin = leftMargin;
        this.rightMargin = rightMargin;
        this.blisterXGap = blisterXGap;
        this.blisterYGap = blisterYGap;
        this.unit = unit;
    }

    public int getnBlisters() {
        return nBlisters;
    }

    public void setnBlisters(int nBlisters) {
        if(nBlisters<1) nBlisters = 1;
        this.nBlisters = nBlisters;
    }

    public boolean isLandscape() {
        return landscape;
    }

    public void setLandscape(boolean landscape) {
        this.landscape = landscape;
    }
    
    public double getLeftMargin() {
        return leftMargin;
    }

    public void setLeftMargin(double leftMargin) {
        if(leftMargin<0.0) leftMargin = 0.0;
        this.leftMargin = leftMargin;
    }

    public double getRightMargin() {
        return rightMargin;
    }

    public void setRightMargin(double rightMargin) {
        if(rightMargin<0.0) rightMargin = 0.0;
        this.rightMargin = rightMargin;
    }

    public double getBlisterXGap() {
        return blisterXGap;
    }

    public void setBlisterXGap(double blisterXGap) {
        if(blisterXGap<0.0) blisterXGap = 0.0;
        this.blisterXGap = blisterXGap;
    }

    public double getBlisterYGap() {
        return blisterYGap;
    }

    public void setBlisterYGap(double blisterYGap) {
        if(blisterYGap<0.0) blisterYGap = 0.0;
        this.blisterYGap = blisterYGap;
    }

    public double getBottomMargin() {
        return bottomMargin;
    }

    public void setBottomMargin(double bottomMargin) {
        if(bottomMargin<0.0) bottomMargin = 0.0;
        this.bottomMargin = bottomMargin;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        if(height<0.0) height = 0.0;
        this.height = height;
    }

    public double getTopMargin() {
        return topMargin;
    }

    public void setTopMargin(double topMargin) {
        if(topMargin<0.0) topMargin = 0.0;
        this.topMargin = topMargin;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        if(width<0.0) width = 0.0;
        this.width = width;
    }

    public MeasureUnit getUnit() {
        return unit;
    }

    public void setUnit(MeasureUnit unit) {
        this.unit = unit;
    }

    public boolean isSeveralBlistersPerPage() {
        return severalBlistersPerPage;
    }

    public void setSeveralBlistersPerPage(boolean severalBlistersPerPage) {
        this.severalBlistersPerPage = severalBlistersPerPage;
    }
}
