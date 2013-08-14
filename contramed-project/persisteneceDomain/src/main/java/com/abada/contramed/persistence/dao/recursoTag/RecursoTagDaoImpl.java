package com.abada.contramed.persistence.dao.recursoTag;

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

import com.abada.contramed.persistence.dao.recurso.RecursoDao;
import com.abada.contramed.persistence.entity.Recurso;
import com.abada.contramed.persistence.entity.RecursoTag;
import com.abada.springframework.orm.jpa.support.JpaDaoSupport;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest_Extjs_v3;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author david
 *
 * Dao de la entidad RecursoTag, trabajamos con los datos de la cama y el tag
 */
@Repository("recursoTagDao")
public class RecursoTagDaoImpl extends JpaDaoSupport implements RecursoTagDao {

    private RecursoDao recursoDao;

    /**
     *
     * @param recursoDao
     */
    @Resource(name = "recursoDao")
    public void setRecursoDao(RecursoDao recursoDao) {
        this.recursoDao = recursoDao;
    }

    /**
     *
     * @param entityManagerFactory
     */
    @Resource(name = "entityManager")
    public void setEntityManagerFactory2(EntityManagerFactory entityManagerFactory) {
        setEntityManagerFactory(entityManagerFactory);
    }

    /**
     * obtenemos count de los datos filtrados
     * @param filters
     * @return Long
     */
    @Transactional(readOnly = true)
    @Override
    public Long loadSizeAll(GridRequest_Extjs_v3 filters) {
        List<Long> result = this.find("select count(*) from RecursoTag d" + filters.getQL("d", true), filters.getParamsValues());
        return result.get(0);
    }

    /**
     * obtenemos la lista de RecursoTag por el filtro
     * @param filters
     * @return List {@link RecursoTag}
     */
    @Transactional(readOnly = true)
    @Override
    public List<RecursoTag> loadAll(GridRequest_Extjs_v3 filters) {
        List<RecursoTag> RecursoTag = this.find("from RecursoTag r" + filters.getQL("r", true), filters.getParamsValues(), filters.getStart(), filters.getLimit());
        return RecursoTag;
    }

    /**
     *obtenemos la lista de todos los RecursoTag
     * @return List {@link RecursoTag}
     */
    @Transactional(readOnly = true)
    @Override
    public List<RecursoTag> findAll() {
        return this.getJpaTemplate().findByNamedQuery("RecursoTag.findAll");
    }

    /**
     *Añadimos RecursoTag
     * @param tag
     * @param idrecurso
     * @return boolean
     */
    @Transactional
    @Override
    public boolean save(String tag, Long idrecurso) {
        RecursoTag rt = new RecursoTag();
        rt.setTag(tag);
        List<Recurso> r = recursoDao.findByIdRecurso(idrecurso);
        if (r != null && r.size() == 1) {
            r.get(0).setIdRecurso(idrecurso);
            r.get(0).addRecursoTag(rt);
            this.getJpaTemplate().persist(rt);
            return true;
        } else {
            return false;
        }

    }

    /**
     *modificamos RecursoTag a partir del recurso traido de nuestra base de datos
     * @param recursoTag
     * @param idrecursoOrigen
     * @param tagorigen
     */
    @Transactional
    @Override
    public void update(RecursoTag recursoTag, Long idrecursoOrigen, String tagorigen) {
        List<RecursoTag> recursoTagorigen = findByRecursotag(idrecursoOrigen, tagorigen);
        if (recursoTagorigen != null && recursoTagorigen.size() == 1) {
            recursoTagorigen.get(0).setTag(recursoTag.getTag());
            recursoTagorigen.get(0).setRecurso(recursoTag.getRecurso());
        }
    }

    /**
     *borrado de un RecursoTag
     */
    @Transactional
    @Override
    public void delete(Long id) {
        RecursoTag r = this.getJpaTemplate().find(RecursoTag.class, id);
        if (r != null) {
            this.getJpaTemplate().remove(r);
        }

    }

    /**
     *obtenemos la lista de RecursoTag a partir de recursoIDRECURSO y el tag
     * @param recursoIDRECURSO
     * @param tag
     * @return List {@link RecursoTag}
     */
    @Transactional(readOnly = true)
    @Override
    public List<RecursoTag> findByRecursotag(Long recursoIDRECURSO, String tag) {
        return this.getJpaTemplate().find("SELECT r FROM RecursoTag r WHERE r.tag=? and r.recurso.idRecurso=?", tag, recursoIDRECURSO);
    }

    /**
     * contamos los recursos que tiene asociados un tag
     * @param recursoIDRECURSO
     * @return Long
     */
    @Transactional(readOnly = true)
    @Override
    public Long findByRecursoCount(Long recursoIDRECURSO) {
        List<Long> result = this.getJpaTemplate().find("select count(*)  FROM RecursoTag r WHERE r.recurso.idRecurso=?", recursoIDRECURSO);
        return result.get(0);
    }

    /**
     * dado el tag obtenemos su Recurso tag; y si no existe devulve null
     * @param tag
     * @return {@link RecursoTag}
     */
    @Transactional(readOnly = true)
    @Override
    public RecursoTag findtagunique(String tag) {
        List<RecursoTag> lrecursoTag = this.getJpaTemplate().find("SELECT r FROM RecursoTag r WHERE r.tag=?", tag);
        if (lrecursoTag != null && lrecursoTag.size() == 1) {
            return lrecursoTag.get(0);
        } else {
            return null;
        }
    }
}

