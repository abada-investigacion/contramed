/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abada.trazability.exception;

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

import com.abada.trazability.entity.Dose;
import com.abada.trazability.entity.Patient;
import com.abada.trazability.entity.enums.TypeIncidence;
import com.abada.trazability.entity.temp.TreatmentActionMode;
import java.util.Date;

/**
 *
 * @author katsu
 */
public class NoCorrectThreatmentException extends WebContramedException{
    private Patient patient;
    private Date startTime;
    private Date endTime;
    private Dose dose;
    private TreatmentActionMode mode;

    public NoCorrectThreatmentException(Dose dose,Patient patient,Date startTime,Date endTime,TreatmentActionMode mode){
        super("Medicamento "+dose.getCatalogomedicamentosCODIGO().getNombre()+" no pautada.");
        this.patient=patient;
        this.startTime=startTime;
        this.endTime=endTime;
        this.dose=dose;
        this.mode=mode;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Patient getPatient() {
        return patient;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Dose getDose() {
        return dose;
    }

    @Override
    public TypeIncidence getType() {
        return TypeIncidence.NO_CORRECT_THREATMENT;
    }

    @Override
    public String getFullMessage() {
        return "Medicamento "+dose.getCatalogomedicamentosCODIGO().getNombre()+" no pautada de "+startTime+" a "+endTime;
    }

    @Override
    public String getMessage() {
        if (TreatmentActionMode.ADMINISTRATION_NURSING.equals(this.mode)){
            return dose.getCatalogomedicamentosCODIGO().getNombre();
        }else{
            return super.getMessage();
        }
    }

}
