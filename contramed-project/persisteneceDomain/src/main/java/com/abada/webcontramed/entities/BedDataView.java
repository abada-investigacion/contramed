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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mmartin
 */
public class BedDataView {

    private String idBed;
    private String namePatient;
    private String surname1;
    private String surname2;
    private String nameBed;
    private Integer genre;
    private Integer age;
    private boolean inbed;

    public boolean isInbed() {
        return inbed;
    }

    public void setInbed(boolean inbed) {
        this.inbed = inbed;
    }

    public BedDataView() {
    }

    public BedDataView(String idBed, String namePatient, String surname1, String surname2, String nameBed, Integer genre, Integer age) {
        this.idBed = idBed;
        this.namePatient = namePatient;
        this.surname1 = surname1;
        this.surname2 = surname2;
        this.nameBed = nameBed;
        this.genre = genre;
        this.age = age;
    }

    public String getSurname1() {
        return surname1;
    }

    public void setSurname1(String surname1) {
        this.surname1 = surname1;
    }

    public String getSurname2() {
        return surname2;
    }

    public void setSurname2(String surname2) {
        this.surname2 = surname2;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getGenre() {
        return genre;
    }

    public void setGenre(Integer genre) {
        this.genre = genre;
    }

    public String getIdBed() {
        return idBed;
    }

    public void setIdBed(String idBed) {
        this.idBed = idBed;
    }

    public String getNameBed() {
        return nameBed;
    }

    public void setNameBed(String nameBed) {
        this.nameBed = nameBed;
    }

    public String getNamePatient() {
        return namePatient;
    }

    public void setNamePatient(String namePatient) {
        this.namePatient = namePatient;
    }
    //TODO Clean code
     /*
    public String getSurname1() {
    return surname1;
    }

    public void setSurname1(String surname1) {
    this.surname1 = surname1;
    }

    public String getSurname2() {
    return surname2;
    }

    public void setSurname2(String surname2) {
    this.surname2 = surname2;
    }*/
}
