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

import com.abada.trazability.entity.enums.TypeIncidence;

/**
 *
 * @author katsu
 */
public class NoExistDoseException extends WebContramedException{
    private Long idDose;

    public NoExistDoseException(Long idDose){
        super("Dosis "+idDose+" no existe.");
        this.idDose=idDose;
    }

    public Long getIdDose() {
        return idDose;
    }

    @Override
    public TypeIncidence getType() {
        return TypeIncidence.NO_EXIST_DOSE;
    }

    @Override
    public String getFullMessage() {
        return "Dosis "+idDose+" no existe.";
    }
}
