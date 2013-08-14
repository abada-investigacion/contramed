/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abada.webcontramed.exception;

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

import com.abada.contramed.persistence.entity.Dose;
import com.abada.contramed.persistence.entity.enums.TypeIncidence;

/**
 *
 * @author katsu
 */
public class ExpiredDoseException extends WebContramedException{
    private Dose dose;
    public ExpiredDoseException(Dose dose){
        super("Dosis de "+dose.getCatalogomedicamentosCODIGO().getNombre() + " caducada.");
        this.dose=dose;
    }

    public Dose getDose() {
        return dose;
    }

    @Override
    public TypeIncidence getType() {
        return TypeIncidence.EXPIRED_DOSE;
    }

    @Override
    public String getFullMessage() {
        return "Dosis de "+dose.getCatalogomedicamentosCODIGO().getNombre() + " caducada.";
    }
    
}
