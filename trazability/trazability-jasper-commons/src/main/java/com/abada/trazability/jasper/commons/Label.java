/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.trazability.jasper.commons;

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

/**
 * Bean that contains data to fill a label
 * @author aroldan
 */
public class Label {

    public Label() {
    }

    public Label(String medicamento, String pactivo, String dimpresion, String lote, String fcaducidad, String datamatrix) {
        this.medicamento = medicamento;
        this.pactivo = pactivo;
        this.dimpresion = dimpresion;
        this.lote = lote;
        this.fcaducidad = fcaducidad;
        this.datamatrix = datamatrix;
    }
    private String medicamento;
    private String pactivo;
    private String dimpresion;
    private String lote;
    private String fcaducidad;
    private String datamatrix;

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public String getDimpresion() {
        return dimpresion;
    }

    public void setDimpresion(String dimpresion) {
        this.dimpresion = dimpresion;
    }

    public String getFcaducidad() {
        return fcaducidad;
    }

    public void setFcaducidad(String fcaducidad) {
        this.fcaducidad = fcaducidad;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getPactivo() {
        return pactivo;
    }

    public void setPactivo(String pactivo) {
        this.pactivo = pactivo;
    }

    public String getDatamatrix() {
        return datamatrix;
    }

    public void setDatamatrix(String datamatrix) {
        this.datamatrix = datamatrix;
    }
}
