/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abada.trazability.entity.temp;

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

import com.abada.trazability.entity.MeasureUnit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author katsu
 */
public class ThreatmentInfo {
    private Long idOrderTiming;
    //private Date startTime;
    private String codigoNacionalMedicamento;
    private String nombreMedicamento;
    private BigDecimal giveAmount;
    private Long idMeasureUnit;
    private String measureUnit;
    private boolean alergy;
    private String administrationType;
    private String idPrincipioActivo;
    private String principioActivo;
    private Date giveTime;
    private List<String> instructions;
    private List<String> observations;
    private ThreatmentInfoStatus threatmentInfoStatus;
    private boolean ifNecesary;
    private PrescriptionType prescriptionType;
    private String especialidadPActivo;
    @JsonIgnore
    private MeasureUnit measureUnitObject;
    @JsonIgnore
    private String codeVia;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ThreatmentInfo other = (ThreatmentInfo) obj;
        if ((this.codigoNacionalMedicamento == null) ? (other.codigoNacionalMedicamento != null) : !this.codigoNacionalMedicamento.equals(other.codigoNacionalMedicamento)) {
            return false;
        }
        if (this.giveAmount != other.giveAmount && (this.giveAmount == null || !this.giveAmount.equals(other.giveAmount))) {
            return false;
        }
        if (this.idMeasureUnit != other.idMeasureUnit && (this.idMeasureUnit == null || !this.idMeasureUnit.equals(other.idMeasureUnit))) {
            return false;
        }
        if ((this.administrationType == null) ? (other.administrationType != null) : !this.administrationType.equals(other.administrationType)) {
            return false;
        }
        if ((this.idPrincipioActivo == null) ? (other.idPrincipioActivo != null) : !this.idPrincipioActivo.equals(other.idPrincipioActivo)) {
            return false;
        }
        if ((this.codeVia == null) ? (other.codeVia != null) : !this.codeVia.equals(other.codeVia)) {
            return false;
        }
        return true;
    }

    public String getCodeVia() {
        return codeVia;
    }

    public void setCodeVia(String codeVia) {
        this.codeVia = codeVia;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + (this.codigoNacionalMedicamento != null ? this.codigoNacionalMedicamento.hashCode() : 0);
        hash = 43 * hash + (this.giveAmount != null ? this.giveAmount.hashCode() : 0);
        hash = 43 * hash + (this.idMeasureUnit != null ? this.idMeasureUnit.hashCode() : 0);
        hash = 43 * hash + (this.administrationType != null ? this.administrationType.hashCode() : 0);
        hash = 43 * hash + (this.idPrincipioActivo != null ? this.idPrincipioActivo.hashCode() : 0);
        return hash;
    }

    public MeasureUnit getMeasureUnitObject() {
        return measureUnitObject;
    }

    public void setMeasureUnitObject(MeasureUnit measureUnitObject) {
        this.measureUnitObject = measureUnitObject;
    }

    public String getEspecialidadPActivo() {
        return especialidadPActivo;
    }

    public void setEspecialidadPActivo(String especialidadPActivo) {
        this.especialidadPActivo = especialidadPActivo;
    }

    public boolean isIfNecesary() {
        return ifNecesary;
    }

    public void setIfNecesary(boolean ifNecesary) {
        this.ifNecesary = ifNecesary;
    }

    public ThreatmentInfoStatus getThreatmentInfoStatus() {
        return threatmentInfoStatus;
    }

    public void setThreatmentInfoStatus(ThreatmentInfoStatus threatmentInfoStatus) {
        this.threatmentInfoStatus = threatmentInfoStatus;
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    public Date getGiveTime() {
        return giveTime;
    }

    public void setGiveTime(Date giveTime) {
        this.giveTime = giveTime;
    }

    public String getIdPrincipioActivo() {
        return idPrincipioActivo;
    }

    public void setIdPrincipioActivo(String idPrincipioActivo) {
        this.idPrincipioActivo = idPrincipioActivo;
    }

    public String getPrincipioActivo() {
        return principioActivo;
    }

    public void setPrincipioActivo(String principioActivo) {
        this.principioActivo = principioActivo;
    }

    public String getAdministrationType() {
        return administrationType;
    }

    public void setAdministrationType(String administrationType) {
        this.administrationType = administrationType;
    }

    public boolean isAlergy() {
        return alergy;
    }

    public void setAlergy(boolean alergy) {
        this.alergy = alergy;
    }

    public String getCodigoNacionalMedicamento() {
        return codigoNacionalMedicamento;
    }

    public void setCodigoNacionalMedicamento(String codigoNacionalMedicamento) {
        this.codigoNacionalMedicamento = codigoNacionalMedicamento;
    }

    public BigDecimal getGiveAmount() {
        return giveAmount;
    }

    public void setGiveAmount(BigDecimal giveAmount) {
        this.giveAmount = giveAmount;
    }

    public Long getIdMeasureUnit() {
        return idMeasureUnit;
    }

    public void setIdMeasureUnit(Long idMeasureUnit) {
        this.idMeasureUnit = idMeasureUnit;
    }

    public Long getIdOrderTiming() {
        return idOrderTiming;
    }

    public void setIdOrderTiming(Long idOrderTiming) {
        this.idOrderTiming = idOrderTiming;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public List<String> getObservations() {
        return observations;
    }

    public void setObservations(List<String> observations) {
        this.observations = observations;
    }

    public PrescriptionType getPrescriptionType() {
        return prescriptionType;
    }

    public void setPrescriptionType(PrescriptionType prescriptionType) {
        this.prescriptionType = prescriptionType;
    }

    /*public Date getStartTime() {
    return startTime;
    }

    public void setStartTime(Date startTime) {
    this.startTime = startTime;
    }*/


}
