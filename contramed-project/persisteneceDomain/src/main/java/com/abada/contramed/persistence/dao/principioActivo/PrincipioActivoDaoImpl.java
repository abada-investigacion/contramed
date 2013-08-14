/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.contramed.persistence.dao.principioActivo;

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

import com.abada.contramed.persistence.entity.PrincipioActivo;
import com.abada.springframework.orm.jpa.support.JpaDaoSupport;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest_Extjs_v3;
import com.abada.webcontramed.exception.PrimaryKeyException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import org.springframework.orm.jpa.JpaCallback;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mmartin
 */
@Repository("principioActivoDao")
public class PrincipioActivoDaoImpl extends JpaDaoSupport implements PrincipioActivoDao {

    /**
     * Constructor
     * @param entityManagerFactory
     */
    @Resource(name = "entityManager")
    public void setEntityManagerFactory2(EntityManagerFactory entityManagerFactory) {
        setEntityManagerFactory(entityManagerFactory);
    }

    /**
     *Obtiene el nombre de {@link PrincipioActivo} a partir de su codigo <br/>
     * @param filters Filtros para la JPQL
     * @return List<PrincipioActivo>
     */
    @Transactional(readOnly = true)
    @Override
    public String loadByCodigo(String codigo) {
        List<PrincipioActivo> pa = this.getJpaTemplate().find("SELECT pa FROM PrincipioActivo pa, PrincipioHasEspecialidad phe WHERE phe.codigoPrincipio=pa.codigo and phe.codigoEspec.codigo=?", codigo);
        if (pa != null && !pa.isEmpty()) {
            return pa.get(0).getNombre();
        } else {
            return null;
        }
    }

    /**
     * Obtiene la lista de {@link PrincipioActivo} a partir de codigo <br/>
     * @param codigo
     * @return List<{@link PrincipioActivo}>
     */
    @Transactional(readOnly = true)
    @Override
    public List<PrincipioActivo> findByCodigo(String codigo) {
        Map<String, String> parametros = new HashMap<String, String>();
        parametros.put("codigo", codigo);
        return this.getJpaTemplate().findByNamedQueryAndNamedParams("PrincipioActivo.findByCodigo", parametros);
    }

    /**
     * Obtiene la lista de {@link PrincipioActivo} que cumplen el filtro <br/>
     * @param filters
     * @return List<{@link PrincipioActivo}>
     */
    @Transactional(readOnly = true)
    @Override
    public List<PrincipioActivo> loadAll(GridRequest_Extjs_v3 filters) {
        List<PrincipioActivo> pactivo = this.find("from PrincipioActivo pa" + filters.getQL("pa", true), filters.getParamsValues(), filters.getStart(), filters.getLimit());
        return pactivo;
    }

    /**
     * Obtiene el tamaño de {@link PrincipioActivo}
     * @param filters
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Long loadSizeAll(GridRequest_Extjs_v3 filters) {
        String a="select count(*) from PrincipioActivo pa" + filters.getQL("pa", true);
        List<Long> result = this.find(a, filters.getParamsValues());
        return result.get(0);
    }

    /**
     * Inserta en {@link PrincipioActivo} a partir de los datos recibidos <br/>
     * @param codigo
     * @param nombre
     * @param anulado
     * @param codigo_ca
     * @param composicion
     * @param denominacion
     * @param formula
     * @param peso
     * @param dosis
     * @param dosis_superficie
     * @param dosis_peso
     * @param u_dosis
     * @param u_dosis_superficie
     * @param u_dosis_peso
     * @param via
     * @param frecuencia
     * @param aditivo
     * @throws Exception
     */
    @Transactional
    @Override
    public void insertPrincipioActivo(String codigo, String nombre, String anulado, String codigo_ca, String composicion,
            String denominacion, String formula, String peso, String dosis, String dosis_superficie, String dosis_peso, String u_dosis,
            String u_dosis_superficie, String u_dosis_peso, Long via, Long frecuencia, String aditivo) throws Exception {

        if (codigo != null) {
            List<PrincipioActivo> aux = this.findByCodigo(codigo);

            if (aux != null && aux.isEmpty()) {

                PrincipioActivo pa = new PrincipioActivo();

                pa.setCodigo(codigo);
                pa.setNombre(nombre);
                pa.setAnulado(anulado);
                pa.setCodigoCa(codigo_ca);
                pa.setComposicion(composicion);
                pa.setDenominacion(denominacion);
                pa.setFormula(formula);
                pa.setPeso(peso);
                pa.setDosis(dosis);
                pa.setDosisSuperficie(dosis_superficie);
                pa.setDosisPeso(dosis_peso);
                pa.setUDosis(u_dosis);
                pa.setUDosisSuperficie(u_dosis_superficie);
                pa.setUDosisPeso(u_dosis_peso);
                pa.setVia(via);
                pa.setFrecuencia(frecuencia);
                pa.setAditivo(aditivo);

                this.getJpaTemplate().persist(pa);

            } else {
                throw new PrimaryKeyException("El Principio Activo ya existe");
            }
        }
    }

    /**
     * Modifica {@link PrincipioActivo} a partir de los datos recibidos <br/>
     * @param codigo
     * @param oldcodigo
     * @param nombre
     * @param anulado
     * @param codigo_ca
     * @param composicion
     * @param denominacion
     * @param formula
     * @param peso
     * @param dosis
     * @param dosis_superficie
     * @param dosis_peso
     * @param u_dosis
     * @param u_dosis_superficie
     * @param u_dosis_peso
     * @param via
     * @param frecuencia
     * @param aditivo
     * @throws Exception
     */
    @Transactional
    @Override
    public void updatePrincipioActivo(final String codigo, final String oldcodigo, String nombre, String anulado, String codigo_ca, String composicion,
            String denominacion, String formula, String peso, String dosis, String dosis_superficie, String dosis_peso, String u_dosis,
            String u_dosis_superficie, String u_dosis_peso, Long via, Long frecuencia, String aditivo) throws Exception {

        PrincipioActivo aux = new PrincipioActivo();
        aux = null;
        if (!codigo.equals(oldcodigo)) {
            aux = (PrincipioActivo) this.getJpaTemplate().find(PrincipioActivo.class, codigo);
        }
        if (aux == null) {
            PrincipioActivo pa = (PrincipioActivo) this.getJpaTemplate().find(PrincipioActivo.class, oldcodigo);

            pa.setNombre(nombre);
            pa.setAnulado(anulado);
            pa.setCodigoCa(codigo_ca);
            pa.setComposicion(composicion);
            pa.setDenominacion(denominacion);
            pa.setFormula(formula);
            pa.setPeso(peso);
            pa.setDosis(dosis);
            pa.setDosisSuperficie(dosis_superficie);
            pa.setDosisPeso(dosis_peso);
            pa.setUDosis(u_dosis);
            pa.setUDosisSuperficie(u_dosis_superficie);
            pa.setUDosisPeso(u_dosis_peso);
            pa.setVia(via);
            pa.setFrecuencia(frecuencia);
            pa.setAditivo(aditivo);

            this.getJpaTemplate().flush();

            this.getJpaTemplate().execute(new JpaCallback() {

                public Object doInJpa(EntityManager em) throws PersistenceException {
                    Query query = em.createNativeQuery("update principio_activo pa SET pa.codigo =:codigo WHERE pa.codigo=:oldcodigo");
                    query.setParameter("codigo", codigo);
                    query.setParameter("oldcodigo", oldcodigo);
                    return query.executeUpdate();
                }
            });

        } else {
            throw new PrimaryKeyException("El Principio Activo ya existe");

        }
    }

    /**
     * Borra un {@link PrincipioActivo} a partir del identificador recibido <br/>
     * @param id
     */
    @Transactional
    @Override
    public void delete(String id) {
        PrincipioActivo pa = this.getJpaTemplate().find(PrincipioActivo.class, id);








        if (pa != null) {
            this.getJpaTemplate().remove(pa);
        }
    }
}
