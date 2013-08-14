/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.contramedadministrationweb.entities;

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
 * entidad recursoTag creada por JsonExclude
 * @author david
 */
public class BedTag {

    private Long idrecurso;
    private RecursoBed recurso;
    private String tag;
    private Long id;

    /**
     *
     * @param id
     * @param idrecurso
     * @param recurso
     * @param tag
     */
    public BedTag(Long id,Long idrecurso, RecursoBed recurso, String tag) {
        this.idrecurso = idrecurso;
        this.recurso = recurso;
        this.tag = tag;
        this.id=id;
    }

    /**
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Long getIdrecurso() {
        return idrecurso;
    }

    /**
     *
     * @return
     */
    public RecursoBed getRecurso() {
        return recurso;
    }

    /**
     *
     * @param recurso
     */
    public void setRecurso(RecursoBed recurso) {
        this.recurso = recurso;
    }

    /**
     *
     * @param idrecurso
     */
    public void setIdrecurso(Long idrecurso) {
        this.idrecurso = idrecurso;
    }

    /**
     *
     * @return
     */
    public String getTag() {
        return tag;
    }

    /**
     *
     * @param tag
     */
    public void setTag(String tag) {
        this.tag = tag;
    }
}
