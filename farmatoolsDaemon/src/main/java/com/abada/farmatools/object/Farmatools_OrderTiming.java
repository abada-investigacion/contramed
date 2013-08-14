/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abada.farmatools.object;

import java.util.Date;
import javax.validation.constraints.NotNull;

/**
 *
 * @author david
 *
 * hora a la que se le administra la dosis al paciente
 */
public class Farmatools_OrderTiming implements IFarmatoolsObject {
    @NotNull
    private String orderid;
    @NotNull
    private int pre_paut_linea_disp;
    @NotNull
    private Date pre_paut_hora;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public Date getPre_paut_hora() {
        return pre_paut_hora;
    }

    public void setPre_paut_hora(Date pre_paut_hora) {
        this.pre_paut_hora = pre_paut_hora;
    }

    public int getPre_paut_linea_disp() {
        return pre_paut_linea_disp;
    }

    public void setPre_paut_linea_disp(int pre_paut_linea_disp) {
        this.pre_paut_linea_disp = pre_paut_linea_disp;
    }


}
