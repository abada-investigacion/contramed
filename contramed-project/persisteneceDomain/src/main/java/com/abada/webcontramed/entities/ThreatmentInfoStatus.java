/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abada.webcontramed.entities;

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
 *
 * @author katsu
 */
public class ThreatmentInfoStatus {
    private TypeThreatmentInfoStatus status;
    private Double difference;
    private String text;

    public Double getDifference() {
        return difference;
    }

    public void setDifference(Double difference) {
        this.difference = difference;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TypeThreatmentInfoStatus getStatus() {
        return status;
    }

    public void setStatus(TypeThreatmentInfoStatus status) {
        this.status = status;
    }

    /*public boolean isEnded(){
        return TypeThreatmentInfoStatus.COMPLETED.equals(this.getStatus()) || TypeThreatmentInfoStatus.NOT_GIVEN_COMPLETED.equals(this.getStatus());
    }*/
}
