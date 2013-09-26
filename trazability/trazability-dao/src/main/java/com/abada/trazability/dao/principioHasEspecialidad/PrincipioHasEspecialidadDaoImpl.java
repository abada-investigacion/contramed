/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.trazability.dao.principioHasEspecialidad;

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
import com.abada.trazability.entity.PrincipioActivo;
import com.abada.trazability.entity.PrincipioHasEspecialidad;
import com.abada.springframework.orm.jpa.support.JpaDaoUtils;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest;
import com.abada.trazability.exception.PrimaryKeyException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mmartin
 */
@Repository("principioHasEspecialidadDao")
public class PrincipioHasEspecialidadDaoImpl extends JpaDaoUtils implements PrincipioHasEspecialidadDao {

    @PersistenceContext(unitName = "trazabilityPU")
    private EntityManager entityManager;

    /**
     * Obtiene la lista de {@link PrincipioHasEspecialidad} que cumplen el filtro <br/>
     * @param filters
     * @return List<{@link PrincipioHasEspecialidad}>
     */
    @Transactional(value="trazability-txm",readOnly = true)
    @Override
    public List<PrincipioHasEspecialidad> loadAll(GridRequest filters) {
        List<PrincipioHasEspecialidad> pHasEspecialidad = this.find(this.entityManager,"from PrincipioHasEspecialidad phe" + filters.getQL("phe", true), filters.getParamsValues(), filters.getStart(), filters.getLimit());
        return pHasEspecialidad;
    }

    /**
     * Obtiene el tamaño de {@link PrincipioHasEspecialidad}
     * @param filters
     * @return
     */
    @Transactional(value="trazability-txm",readOnly = true)
    @Override
    public Long loadSizeAll(GridRequest filters) {
        List<Long> result = this.find(this.entityManager,"select count(*) from PrincipioHasEspecialidad phe" + filters.getQL("phe", true), filters.getParamsValues());
        return result.get(0);
    }

    /**
     * Obtiene la lista de {@link PrincipioHasEspecialidad} a partir del codigo de especialidad <br/>
     * @param codigo
     * @return List<{@link PrincipioHasEspecialidad}>
     */
    @Transactional(value="trazability-txm",readOnly = true)
    @Override
    public List<PrincipioHasEspecialidad> getPrincipioHasEspecialidadByEspecialidad(String codigo) {

        List<PrincipioHasEspecialidad> phe = new ArrayList<PrincipioHasEspecialidad>();
        try {
            phe = this.entityManager.createQuery("SELECT phe FROM PrincipioHasEspecialidad phe, CatalogoMedicamentos cm WHERE phe.codigoEspec=cm.codigo and phe.codigoEspec.codigo=:codigo").setParameter("codigo", codigo).getResultList();

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
    @Transactional(value="trazability-txm",readOnly = true)
    @Override
    public List<PrincipioHasEspecialidad> getPrincipioHasEspecialidadById(Long id) {

        List<PrincipioHasEspecialidad> phe = new ArrayList<PrincipioHasEspecialidad>();
        try {
            phe = this.entityManager.createNamedQuery("PrincipioHasEspecialidad.findById").setParameter("id", id).getResultList();

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

                CatalogoMedicamentos cm = this.entityManager.find(CatalogoMedicamentos.class, codigoespec);
                PrincipioActivo pa = this.entityManager.find(PrincipioActivo.class, codigoprincipio);

                phe.setCodigoEspec(cm);
                phe.setCodigoPrincipio(pa);
                phe.setComposicion(composicion);
                phe.setId(id);
                phe.setIdUnidad(id_unidad);

                this.entityManager.persist(phe);
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
                PrincipioHasEspecialidad phe = this.entityManager.find(PrincipioHasEspecialidad.class, oldid);

                CatalogoMedicamentos cm = this.entityManager.find(CatalogoMedicamentos.class, codigoespec);
                PrincipioActivo pa = this.entityManager.find(PrincipioActivo.class, codigoprincipio);

                phe.setCodigoEspec(cm);
                phe.setCodigoPrincipio(pa);
                phe.setComposicion(composicion);
                phe.setIdUnidad(id_unidad);

                this.entityManager.flush();

                this.entityManager.createNativeQuery("update PRINCIPIO_HAS_ESPECIALIDAD phe SET phe.id=:id WHERE phe.id=:oldid").setParameter("id", id).setParameter("oldid", oldid).executeUpdate();
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
        PrincipioHasEspecialidad phe = this.entityManager.find(PrincipioHasEspecialidad.class, id);
        if (phe != null) {
            this.entityManager.remove(phe);
        }
    }
}
