package com.abada.trazability.dao.catalogoMedicamentos;

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

import com.abada.trazability.entity.CatalogoMedicamentos;
import com.abada.trazability.entity.FrecuenciaPredef;
import com.abada.trazability.entity.Table0162;
import com.abada.springframework.orm.jpa.support.JpaDaoUtils;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest;
import com.abada.trazability.exception.PrimaryKeyException;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mmartin
 *
 * Dao de la entidad {@link CatalogoMedicamentos}. Trabaja con los datos de las distintas especialidades
 */
@Repository("catalogoMedicamentosDao")
public class CatalogoMedicamentosDaoImpl extends JpaDaoUtils implements CatalogoMedicamentosDao {

    @PersistenceContext(unitName = "trazabilityPU")
    private EntityManager entityManager;
    /**
     * Obtiene la lista de {@link CatalogoMedicamentos} a partir de codigo <br/>
     * @param codigo
     * @return List<{@link CatalogoMedicamentos}>
     */
    @Transactional(value="trazability-txm",readOnly = true)
    @Override
    public List<CatalogoMedicamentos> findByCodigo(String codigo) {        
        return this.entityManager.createNamedQuery("CatalogoMedicamentos.findByCodigo").setParameter("codigo", codigo).getResultList();
    }

    /**
     * Obtiene el tamaño de {@link CatalogoMedicamentos} a partir del filtro <br/>
     * @param filters Filtros para la JPQL
     * @return Long
     */
    @Transactional(value="trazability-txm",readOnly = true)
    @Override
    public Long loadSizeAll(GridRequest filters) {
        List<Long> result = this.find(this.entityManager,"select count(*) from CatalogoMedicamentos c" + filters.getQL("c", true), filters.getParamsValues());
        return result.get(0);
    }

    /**
     * Obtiene la lista de {@link CatalogoMedicamentos} que cumplen el filtro <br/>
     * @param filters Filtros para la JPQL
     * @return List<CatalogoMedicamentos>
     */
    @Transactional(value="trazability-txm",readOnly = true)
    @Override
    public List<CatalogoMedicamentos> loadAll(GridRequest filters) {
        List<CatalogoMedicamentos> catMedicamentos = this.find(this.entityManager,"from CatalogoMedicamentos c" + filters.getQL("c", true), filters.getParamsValues(), filters.getStart(), filters.getLimit());
        return catMedicamentos;
    }

    /**
     * Inserta un {@link CatalagoMedicamentos} a partir de los datos recibidos <br/>
     * @param codigo Codigo nacional de la especialidad
     * @param nombre Nombre de la especialidad
     * @param anulado
     * @param bioequivalente
     * @param caducidad
     * @param caracteristicas
     * @param codGrupo
     * @param codLab
     * @param conservacion
     * @param dispensacion
     * @param especialControl
     * @param fechaAlta
     * @param fechaBaja
     * @param ficha
     * @param formaFarma
     * @param generico
     * @param largaDuracion
     * @param nombreLab
     * @param precioRef
     * @param pvp
     * @param regimen
     * @param dosis
     * @param dosisSuperficie
     * @param dosisPeso
     * @param uDosis
     * @param uDosisSuperficie
     * @param uDosisPeso
     * @param via
     * @param frecuencia
     * @param diluyente
     * @param aditivo
     * @param dosisDiluyente
     */
    @Transactional
    @Override
    public void insertCatalogoMedicamentos(String codigo, String nombre, String anulado, String bioequivalente, Date caducidad,
            String caracteristicas, String codGrupo, String codLab, String conservacion, String dispensacion, String especialControl, Date fechaAlta,
            Date fechaBaja, BigInteger ficha, String formaFarma, String generico, String largaDuracion, String nombreLab, String precioRef,
            String pvp, String regimen, BigDecimal dosis, BigDecimal dosisSuperficie, BigDecimal dosisPeso, Long uDosis, Long uDosisSuperficie,
            Long uDosisPeso, Integer via, BigDecimal frecuencia, String diluyente, String aditivo, BigDecimal dosisDiluyente) throws Exception {
        List lcodigo = findByCodigo(codigo);
        if (lcodigo == null || lcodigo.isEmpty()) {
            CatalogoMedicamentos c = new CatalogoMedicamentos();

            if (frecuencia == null) {
                c.setFrecuencia(null);

            } else {
                FrecuenciaPredef frecuenciaPredef = new FrecuenciaPredef();
                frecuenciaPredef.setIdTarea(frecuencia);
                c.setFrecuencia(frecuenciaPredef);
            }

            if (via == null) {
                c.setVia(null);

            } else {
                Table0162 tVia = new Table0162();
                tVia.setId(via);
                c.setVia(tVia);
            }

            c.setAditivo(aditivo);
            c.setAnulado(anulado);
            c.setBioequivalente(bioequivalente);
            c.setCaducidad(caducidad);
            c.setCaracteristicas(caracteristicas);
            c.setCodGrupo(codGrupo);
            c.setCodLab(codLab);
            c.setCodigo(codigo);
            c.setConservacion(conservacion);
            c.setDiluyente(diluyente);
            c.setDispensacion(dispensacion);
            c.setDosis(dosis);
            c.setDosisDiluyente(dosisDiluyente);
            c.setDosisPeso(dosisPeso);
            c.setDosisSuperficie(dosisSuperficie);
            c.setEspecialControl(especialControl);
            c.setFechaAlta(fechaAlta);
            c.setFechaBaja(fechaBaja);
            c.setFicha(ficha);
            c.setFormaFarma(formaFarma);
            c.setGenerico(generico);
            c.setLargaDuracion(largaDuracion);
            c.setNombre(nombre);
            c.setNombreLab(nombreLab);
            c.setPrecioRef(precioRef);
            c.setPvp(pvp);
            c.setRegimen(regimen);
            c.setUDosis(uDosis);
            c.setUDosisPeso(uDosisPeso);
            c.setUDosisSuperficie(uDosisSuperficie);

            this.entityManager.persist(c);
        } else {
            throw new PrimaryKeyException("La Especialidad ya existe");
        }
    }

    /**
     * Modifica {@link CatalogoMedicamentos} a partir de los datos recibidos <br/>
     * @param newcodigo Nuevo codigo nacional que se asignara a la especialidad
     * @param codigo Codigo nacional de la especialidad
     * @param nombre Nombre de la especialidad
     * @param anulado
     * @param bioequivalente
     * @param caducidad
     * @param caracteristicas
     * @param codGrupo
     * @param codLab
     * @param conservacion
     * @param dispensacion
     * @param especialControl
     * @param fechaAlta
     * @param fechaBaja
     * @param ficha
     * @param formaFarma
     * @param generico
     * @param largaDuracion
     * @param nombreLab
     * @param precioRef
     * @param pvp
     * @param regimen
     * @param dosis
     * @param dosisSuperficie
     * @param dosisPeso
     * @param uDosis
     * @param uDosisSuperficie
     * @param uDosisPeso
     * @param via
     * @param frecuencia
     * @param diluyente
     * @param aditivo
     * @param dosisDiluyente
     */
    @Transactional
    @Override
    public void updateCatalogoMedicamentos(final String newcodigo, final String codigo, String nombre, String anulado, String bioequivalente, Date caducidad,
            String caracteristicas, String codGrupo, String codLab, String conservacion, String dispensacion, String especialControl, Date fechaAlta,
            Date fechaBaja, BigInteger ficha, String formaFarma, String generico, String largaDuracion, String nombreLab, String precioRef,
            String pvp, String regimen, BigDecimal dosis, BigDecimal dosisSuperficie, BigDecimal dosisPeso, Long uDosis, Long uDosisSuperficie,
            Long uDosisPeso, Integer via, BigDecimal frecuencia, String diluyente, String aditivo, BigDecimal dosisDiluyente) throws Exception {
        List lcodigo = null;
        if (!newcodigo.equals(codigo)) {
            lcodigo = findByCodigo(newcodigo);
        }
        if (lcodigo == null || lcodigo.isEmpty()) {
            CatalogoMedicamentos cm = (CatalogoMedicamentos) this.entityManager.find(CatalogoMedicamentos.class, codigo);

            //Comprobamos si frecuencia es null
            if (frecuencia == null) {
                cm.setFrecuencia(null);

            } else {
                FrecuenciaPredef frecuenciaPredef = new FrecuenciaPredef();
                frecuenciaPredef.setIdTarea(frecuencia);
                cm.setFrecuencia(frecuenciaPredef);
            }

            //Comprobamos si via es null
            if (via == null) {
                cm.setVia(null);

            } else {
                Table0162 tVia = new Table0162();
                tVia.setId(via);
                cm.setVia(tVia);
            }

            cm.setAditivo(aditivo);
            cm.setAnulado(anulado);
            cm.setBioequivalente(bioequivalente);
            cm.setCaducidad(caducidad);
            cm.setCaracteristicas(caracteristicas);
            cm.setCodGrupo(codGrupo);
            cm.setCodLab(codLab);
            cm.setConservacion(conservacion);
            cm.setDiluyente(diluyente);
            cm.setDispensacion(dispensacion);
            cm.setDosis(dosis);
            cm.setDosisDiluyente(dosisDiluyente);
            cm.setDosisPeso(dosisPeso);
            cm.setDosisSuperficie(dosisSuperficie);
            cm.setEspecialControl(especialControl);
            cm.setFechaAlta(fechaAlta);
            cm.setFechaBaja(fechaBaja);
            cm.setFicha(ficha);
            cm.setFormaFarma(formaFarma);
            cm.setGenerico(generico);
            cm.setLargaDuracion(largaDuracion);
            cm.setNombre(nombre);
            cm.setNombreLab(nombreLab);
            cm.setPrecioRef(precioRef);
            cm.setPvp(pvp);
            cm.setRegimen(regimen);
            cm.setUDosis(uDosis);
            cm.setUDosisPeso(uDosisPeso);
            cm.setUDosisSuperficie(uDosisSuperficie);

            this.entityManager.flush();

            this.entityManager.createNativeQuery("update catalogo_medicamentos c SET c.codigo =:newcodigo WHERE c.codigo=:codigo").setParameter("newcodigo", newcodigo).setParameter("codigo", codigo).executeUpdate();

        } else {
            throw new PrimaryKeyException("La Especialidad ya existe");
        }


    }
}
