/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.farmatools.object;

import com.abada.farmatools.object.checks.Adt17Checks;
import com.abada.farmatools.object.checks.AdtCamaChecks;
import java.util.Date;
import javax.validation.constraints.NotNull;

/**
 *
 * @author david
 *
 * datos del paciente
 */
public class Farmatools_Patient extends Farmatools_Msh implements IFarmatoolsObject {

    @NotNull(message = "numerohc no dado")
    private Integer numerohc;
    private Integer cod_centro;
    private String apellid1;
    private String nombre;
    private String apellid2;
    private Date fechanac;
    private String sexo;
    private String direccion;
    private String cod_postal;
    private Integer cod_poblacion;
    private String provincia;
    private String cod_pais;
    private String telefono;
    private String estadocivil;
    private String nifdni;
    @NotNull(message = "paciente para intercambio cama no dado", groups = Adt17Checks.class)
    private Integer patientswapnumerohc;
    @NotNull(message = "cama intercambio no dada", groups = Adt17Checks.class)
    private String patientswapcama;
    @NotNull(message = "cama no dada", groups = AdtCamaChecks.class)
    private String paciente_ncama;
    private String cip;
    private String numeroseguridadSocial;
    private Integer numerohcanterior;
    private String numerohcActual;

    public String getNumerohcActual() {
        return numerohcActual;
    }

    public void setNumerohcActual(String numerohcActual) {
        this.numerohcActual = numerohcActual;
    }

    public String getCip() {
        return cip;
    }

    public void setCip(String cip) {
        this.cip = cip;
    }

    public Integer getNumerohcanterior() {
        return numerohcanterior;
    }

    public void setNumerohcanterior(Integer numerohcanterior) {
        this.numerohcanterior = numerohcanterior;
    }

    public String getNumeroseguridadSocial() {
        return numeroseguridadSocial;
    }

    public void setNumeroseguridadSocial(String numeroseguridadSocial) {
        this.numeroseguridadSocial = numeroseguridadSocial;
    }

    public Integer getCod_centro() {
        return cod_centro;
    }

    public void setCod_centro(Integer cod_centro) {
        this.cod_centro = cod_centro;
    }

    public String getApellid1() {
        return apellid1;
    }

    public void setApellid1(String apellid1) {
        this.apellid1 = apellid1;
    }

    public String getApellid2() {
        return apellid2;
    }

    public void setApellid2(String apellid2) {
        this.apellid2 = apellid2;
    }

    public String getCod_pais() {
        return cod_pais;
    }

    public void setCod_pais(String cod_pais) {
        this.cod_pais = cod_pais;
    }

    public String getCod_postal() {
        return cod_postal;
    }

    public void setCod_postal(String cod_postal) {
        this.cod_postal = cod_postal;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNifdni() {
        return nifdni;
    }

    public void setNifdni(String nifdni) {
        this.nifdni = nifdni;
    }

    public Date getFechanac() {
        return fechanac;
    }

    public void setFechanac(Date fechanac) {
        this.fechanac = fechanac;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNumerohc() {
        return numerohc;
    }

    public void setNumerohc(Integer numerohc) {
        this.numerohc = numerohc;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEstadocivil() {
        return estadocivil;
    }

    public void setEstadocivil(String estadocivil) {
        this.estadocivil = estadocivil;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPaciente_ncama() {
        return paciente_ncama;
    }

    public void setPaciente_ncama(String paciente_ncama) {
        this.paciente_ncama = paciente_ncama;
    }

    public Integer getCod_poblacion() {
        return cod_poblacion;
    }

    public void setCod_poblacion(Integer cod_poblacion) {
        this.cod_poblacion = cod_poblacion;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public Integer getPatientswapnumerohc() {
        return patientswapnumerohc;
    }

    public void setPatientswapnumerohc(Integer patientswapnumerohc) {
        this.patientswapnumerohc = patientswapnumerohc;
    }

    public String getPatientswapcama() {
        return patientswapcama;
    }

    public void setPatientswapcama(String patientswapcama) {
        this.patientswapcama = patientswapcama;
    }
}
