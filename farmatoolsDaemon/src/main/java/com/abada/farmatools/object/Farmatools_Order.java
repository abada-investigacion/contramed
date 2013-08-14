/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.farmatools.object;

import com.abada.farmatools.object.checks.ArticuloChecks;
import com.abada.farmatools.object.checks.DuracionChecks;
import com.abada.farmatools.object.checks.PrincipioActivoChecks;
import com.abada.selene.utils.Utils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author david
 *
 * Datos de prescripción
 */
public class Farmatools_Order extends Farmatools_Patient implements IFarmatoolsObject {

    @NotNull(message = "orderid no dado")
    private String orderid;
    private String control;
    @NotNull(message = "nproceso no dado")
    private Integer nproceso;
    @NotNull(message = "linea no dada")
    private Integer linea;
    private String login_usu;
    private Date fecha_pres;
    private Integer cod_medico;
    @NotNull(message = "fecha_ini no dada")
    private Date fecha_ini;
    @NotNull(message = "hora duracion no dada", groups = DuracionChecks.class)
    private Integer hora_duracion;
    @NotNull(message = "fecha fin no dada", groups = DuracionChecks.class)
    private Date fecha_fin;
    @NotNull(message = "dosis_pa no dada")
    private Double give_amount;//dosis_pa
    private String instruccion;//descripcion_pau
    private String repetition_pattern;//cod_pau
    @Pattern(message="Precisa debe ser Y o N",regexp = "Y|N")
    @NotNull(message = "Cod_pau no dado")
    private String if_necesary;
    @NotNull(message = "unidad de medida no dada")
    private Integer id_measure_unit;
    @Pattern(message="measure_unit_en_mambrino debe ser Y o N",regexp = "Y|N")
    private String measure_unit_en_mambrino;
    @Pattern(message="alergia_s_n debe ser Y o N",regexp = "Y|N")
    @NotNull(message = "alergia_s_n no dado")
    private String alergia_s_n;
    @NotNull(message = "principio activo no dado", groups = PrincipioActivoChecks.class)
    private Integer cod_principioActivo;    //SI P no es nulo
    @Pattern(message="modo_prescrip debe ser P o A",regexp = "P|A")
    private String modo_prescripcion;       //P o A
    @NotNull(message = "cod_via no dado")
    private Integer cod_via;
    @NotNull(message = "Cod articulo no dado", groups = ArticuloChecks.class)
    private String cod_articulo;            //Si A no es nulo
    private String obs_dispensa;
    private String obs_enferme;
    private String obs_medico;
    private String descripcion_secuencia;
    private Date fh_ult_mod;
    @NotEmpty(message = "No viene tiempo de tratamiento")
    @Valid
    List<Farmatools_OrderTiming> orderTimings;
    private Farmatools_Patient farmatools_Patient;

    public Farmatools_Patient getFarmatools_patient() {
        return farmatools_Patient;
    }

    public void setFarmatools_patient(Farmatools_Patient farmatools_Patient) {
        this.farmatools_Patient = farmatools_Patient;
    }

    public Farmatools_Order() {
        this.orderTimings = new ArrayList<Farmatools_OrderTiming>();
    }

    public String getAlergia_s_n() {
        return alergia_s_n;
    }

    public void setAlergia_s_n(String alergia_s_n) {
        this.alergia_s_n = alergia_s_n;
    }

    public String getCod_articulo() {
        return cod_articulo;
    }

    public void setCod_articulo(String cod_articulo) {
        this.cod_articulo = cod_articulo;
    }

    public Integer getCod_medico() {
        return cod_medico;
    }

    public void setCod_medico(Integer cod_medico) {
        this.cod_medico = cod_medico;
    }

    public Integer getCod_via() {
        return cod_via;
    }

    public void setCod_via(Integer cod_via) {
        this.cod_via = cod_via;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public Integer getCod_principioActivo() {
        return cod_principioActivo;
    }

    public void setCod_principioActivo(Integer cod_principioActivo) {
        this.cod_principioActivo = cod_principioActivo;
    }

    public String getModo_prescripcion() {
        return modo_prescripcion;
    }

    public void setModo_prescripcion(String modo_prescripcion) {
        this.modo_prescripcion = modo_prescripcion;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public Date getFecha_ini() {
        return fecha_ini;
    }

    public void setFecha_ini(Date fecha_ini) {
        this.fecha_ini = fecha_ini;
    }

    public Date getFecha_pres() {
        return fecha_pres;
    }

    public void setFecha_pres(Date fecha_pres) {
        this.fecha_pres = fecha_pres;
    }

    public Date getFh_ult_mod() {
        return fh_ult_mod;
    }

    public void setFh_ult_mod(Date fh_ult_mod) {
        this.fh_ult_mod = fh_ult_mod;
    }

    public Double getGive_amount() {
        return give_amount;
    }

    public void setGive_amount(Double give_amount) {
        this.give_amount = give_amount;
    }

    public Integer getHora_duracion() {
        return hora_duracion;
    }

    public void setHora_duracion(Integer hora_duracion) {
        this.hora_duracion = hora_duracion;
    }

    public Integer getId_measure_unit() {
        return id_measure_unit;
    }

    public void setId_measure_unit(Integer id_measure_unit) {
        this.id_measure_unit = id_measure_unit;
    }

    public String getMeasure_unit_en_mambrino() {
        return measure_unit_en_mambrino;
    }

    public void setMeasure_unit_en_mambrino(String measure_unit_en_mambrino) {
        this.measure_unit_en_mambrino = measure_unit_en_mambrino;
    }

    public String getIf_necesary() {
        return if_necesary;
    }

    public void setIf_necesary(String if_necesary) {
        this.if_necesary = if_necesary;
    }

    public String getInstruccion() {
        return instruccion;
    }

    public void setInstruccion(String instruccion) {
        this.instruccion = instruccion;
    }

    public Integer getLinea() {
        return linea;
    }

    public void setLinea(Integer linea) {
        this.linea = linea;
    }

    public String getLogin_usu() {
        return login_usu;
    }

    public void setLogin_usu(String login_usu) {
        this.login_usu = login_usu;
    }

    public Integer getNproceso() {
        return nproceso;
    }

    public void setNproceso(Integer nproceso) {
        this.nproceso = nproceso;
    }

    public String getObs_dispensa() {
        return obs_dispensa;
    }

    public void setObs_dispensa(String obs_dispensa) {
        this.obs_dispensa = Utils.replaceWeirdCharacters(obs_dispensa);
    }

    public String getObs_enferme() {
        return obs_enferme;
    }

    public void setObs_enferme(String obs_enferme) {
        this.obs_enferme = Utils.replaceWeirdCharacters(obs_enferme);
    }

    public String getObs_medico() {
        return obs_medico;
    }

    public void setObs_medico(String obs_medico) {
        this.obs_medico = Utils.replaceWeirdCharacters(obs_medico);
    }

    public String getDescripcion_secuencia() {
        return descripcion_secuencia;
    }

    public void setDescripcion_secuencia(String descripcion_secuencia) {
        this.descripcion_secuencia = descripcion_secuencia;
    }
    
    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getRepetition_pattern() {
        return repetition_pattern;
    }

    public void setRepetition_pattern(String repetition_pattern) {
        this.repetition_pattern = repetition_pattern;
    }

    public List<Farmatools_OrderTiming> getOrderTimings() {
        return orderTimings;
    }

    public void setOrderTimings(List<Farmatools_OrderTiming> orderTimings) {
        this.orderTimings = orderTimings;
    }

    public void addOrderTiming(Farmatools_OrderTiming orderTiming) {
        orderTimings.add(orderTiming);
    }

    /**
     * This toString method shows only fields with null values
     * @return
     */
    @Override
    public String toString() {
        return "";
    }
}
