/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.contramed.persistence.dao.principioHasEspecialidad;

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

import com.abada.contramed.persistence.entity.CatalogoMedicamentos;
import com.abada.contramed.persistence.entity.PrincipioActivo;
import com.abada.contramed.persistence.entity.PrincipioHasEspecialidad;
import com.abada.springframework.orm.jpa.support.JpaDaoSupport;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest_Extjs_v3;
import com.abada.webcontramed.exception.PrimaryKeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mmartin
 */
@Repository("principioHasEspecialidadDao")
public class PrincipioHasEspecialidadDaoImpl extends JpaDaoSupport implements PrincipioHasEspecialidadDao {

    /**
     *
     * @param entityManagerFactory
     */
    @Resource(name = "entityManager")
    public void setEntityManagerFactory2(EntityManagerFactory entityManagerFactory) {
        setEntityManagerFactory(entityManagerFactory);
    }

    /**
     * Obtiene la lista de {@link PrincipioHasEspecialidad} que cumplen el filtro <br/>
     * @param filters
     * @return List<{@link PrincipioHasEspecialidad}>
     */
    @Transactional(readOnly = true)
    @Override
    public List<PrincipioHasEspecialidad> loadAll(GridRequest_Extjs_v3 filters) {
        List<PrincipioHasEspecialidad> pHasEspecialidad = this.find("from PrincipioHasEspecialidad phe" + filters.getQL("phe", true), filters.getParamsValues(), filters.getStart(), filters.getLimit());
        return pHasEspecialidad;
    }

    /**
     * Obtiene el tamaño de {@link PrincipioHasEspecialidad}
     * @param filters
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Long loadSizeAll(GridRequest_Extjs_v3 filters) {
        List<Long> result = this.find("select count(*) from PrincipioHasEspecialidad phe" + filters.getQL("phe", true), filters.getParamsValues());
        return result.get(0);
    }

    /**
     * Obtiene la lista de {@link PrincipioHasEspecialidad} a partir del codigo de especialidad <br/>
     * @param codigo
     * @return List<{@link PrincipioHasEspecialidad}>
     */
    @Transactional(readOnly = true)
    @Override
    public List<PrincipioHasEspecialidad> getPrincipioHasEspecialidadByEspecialidad(String codigo) {

        List<PrincipioHasEspecialidad> phe = new ArrayList<PrincipioHasEspecialidad>();
        try {
            phe = this.getJpaTemplate().find("SELECT phe FROM PrincipioHasEspecialidad phe, CatalogoMedicamentos cm WHERE phe.codigoEspec=cm.codigo and phe.codigoEspec.codigo=?", codigo);

        } catch (Exception e) {
            PrincipioHasEspecialidad p = new PrincipioHasEspecialidad();
            phe.set(0, p);
        }
        return phe;
    }

    /**
     * Obtiene la lista de {@link PrincipioHasEspecialidad} a partir del identificador <br/>
     * @param id
     * @return List<{@link PrincipioHasEspecialidad}>
     */
    @Transactional(readOnly = true)
    @Override
    public List<PrincipioHasEspecialidad> getPrincipioHasEspecialidadById(Long id) {

        List<PrincipioHasEspecialidad> phe = new ArrayList<PrincipioHasEspecialidad>();
        try {
            Map<String, Long> parametros = new HashMap<String, Long>();
            parametros.put("id", id);
            phe = this.getJpaTemplate().findByNamedQueryAndNamedParams("PrincipioHasEspecialidad.findById", parametros);

        } catch (Exception e) {
            PrincipioHasEspecialidad p = new PrincipioHasEspecialidad();
            phe.set(0, p);

        }
        return phe;
    }

    /**
     * Inserta en {@link PrincipioHasEspecialidad} a partir de los datos recibidos <br/>
     * @param codigoespec
     * @param codigoprincipio
     * @param id
     * @param composicion
     * @param id_unidad
     * @throws Exception
     */
    @Transactional
    @Override
    public void insertPrincipioHasEspecialidad(String codigoespec, String codigoprincipio, Long id, String composicion, String id_unidad) throws Exception {

        List<PrincipioHasEspecialidad> aux = this.getPrincipioHasEspecialidadById(id);
        if (aux != null && aux.isEmpty()) {
            List<PrincipioHasEspecialidad> aux2 = this.getPrincipioHasEspecialidadByEspecialidad(codigoespec);

            if (aux2 != null && aux2.isEmpty()) {
                PrincipioHasEspecialidad phe = new PrincipioHasEspecialidad();

                CatalogoMedicamentos cm = this.getJpaTemplate().find(CatalogoMedicamentos.class, codigoespec);
                PrincipioActivo pa = this.getJpaTemplate().find(PrincipioActivo.class, codigoprincipio);

                phe.setCodigoEspec(cm);
                phe.setCodigoPrincipio(pa);
                phe.setComposicion(composicion);
                phe.setId(id);
                phe.setIdUnidad(id_unidad);

                this.getJpaTemplate().persist(phe);
            } else {
                throw new PrimaryKeyException("La especialidad introducida ya esta asociada a un principio activo");
            }
        } else {
            throw new PrimaryKeyException("El identificador de la asignación de especialidades a principios activos ya existe");
        }
    }

    /**
     * Modifica en {@link PrincipioHasEspecialidad} a partir de los datos recibidos <br/>
     * @param codigoespec
     * @param codigoprincipio
     * @param id
     * @param composicion
     * @param id_unidad
     * @throws Exception
     */
    @Transactional
    @Override
    public void updatePrincipioHasEspecialidad(final Long oldid, String oldcodigoespec, String codigoespec, String codigoprincipio, final Long id, String composicion, String id_unidad) throws Exception {

        List<PrincipioHasEspecialidad> aux = new ArrayList<PrincipioHasEspecialidad>();
        List<PrincipioHasEspecialidad> aux2 = new ArrayList<PrincipioHasEspecialidad>();
        if (oldid.compareTo(id) != 0) {
            aux = this.getPrincipioHasEspecialidadById(id);
        }

        if (aux != null && aux.isEmpty()) {
            if (!oldcodigoespec.equals(codigoespec)) {
                aux2 = this.getPrincipioHasEspecialidadByEspecialidad(codigoespec);
            }

            if (aux2 != null && aux2.isEmpty()) {
                PrincipioHasEspecialidad phe = this.getJpaTemplate().find(PrincipioHasEspecialidad.class, oldid);

                CatalogoMedicamentos cm = this.getJpaTemplate().find(CatalogoMedicamentos.class, codigoespec);
                PrincipioActivo pa = this.getJpaTemplate().find(PrincipioActivo.class, codigoprincipio);

                phe.setCodigoEspec(cm);
                phe.setCodigoPrincipio(pa);
                phe.setComposicion(composicion);
                phe.setIdUnidad(id_unidad);

                this.getJpaTemplate().flush();

                this.getJpaTemplate().execute(new JpaCallback() {

                    public Object doInJpa(EntityManager em) throws PersistenceException {
                        Query query = em.createNativeQuery("update PRINCIPIO_HAS_ESPECIALIDAD phe SET phe.id=:id WHERE phe.id=:oldid");
                        query.setParameter("id", id);
                        query.setParameter("oldid", oldid);
                        return query.executeUpdate();

                    }
                });

            } else {
                throw new PrimaryKeyException("La especialidad introducida ya esta asociada a un principio activo");
            }
        } else {
            throw new PrimaryKeyException("El identificador de la asignación de especialidades a principios activos ya existe");
        }
    }

    /**
     * Borra {@link PrincipioHasEspecialidad} a partir del identificador recibido <br/>
     * @param id
     */
    @Transactional
    @Override
    public void delete(Long id) {
        PrincipioHasEspecialidad phe = this.getJpaTemplate().find(PrincipioHasEspecialidad.class, id);
        if (phe != null) {
            this.getJpaTemplate().remove(phe);
        }
    }
}
