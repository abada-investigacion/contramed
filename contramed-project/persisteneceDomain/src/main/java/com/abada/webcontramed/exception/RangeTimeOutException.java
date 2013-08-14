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
import java.util.Date;

/**
 *
 * @author katsu
 */
public class RangeTimeOutException extends WebContramedException{
    private Dose dose;
    private Date downlimit;
    private Date uplimit;
    private String message;

    public RangeTimeOutException(Dose dose,Date downlimit,Date uplimit) {
        super("");
        if (dose!=null){
            this.message="Medicamento "+dose.getCatalogomedicamentosCODIGO().getNombre()+" fuera de fecha pautada";
        }else{
            this.message="Medicamentos fuera de fecha pautada";
        }
        this.dose=dose;
        this.downlimit=downlimit;
        this.uplimit=uplimit;
    }


    @Override
    public TypeIncidence getType() {
        return TypeIncidence.RANGE_TIME_OUT;
    }

    @Override
    public String getFullMessage() {
        if (dose!=null)
            return "Medicamento "+dose.getCatalogomedicamentosCODIGO().getNombre()+" fuera de fecha pautada de "+downlimit+" a "+uplimit;
        else
            return "Medicamentos fuera de fecha pautada de "+downlimit+" a "+uplimit;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
