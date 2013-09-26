/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.trazability.dao.catalogoHasTemplates;

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

import com.abada.springframework.orm.jpa.support.JpaDaoUtils;
import com.abada.trazability.entity.CatalogoHasTemplates;
import com.abada.trazability.entity.CatalogoMedicamentos;
import com.abada.trazability.entity.TemplatesMedication;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest;
import com.abada.trazability.exception.PrimaryKeyException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mmartin
 *
 * Dao de la entidad {@link CatalogoHasTemplates}. Trabaja con el template asignado a cada especialidad
 */
@Repository("catalogoHasTemplatesDao")
public class CatalogoHasTemplatesDaoImpl extends JpaDaoUtils implements CatalogoHasTemplatesDao {

    private static final Log logger = LogFactory.getLog(CatalogoHasTemplatesDaoImpl.class);
    @PersistenceContext(unitName = "trazabilityPU")
    private EntityManager entityManager;


    /**
     *Devuelve el total de registros de {@link CatalagoHasTemplates} a partir del filtro <br/>
     * @param filters Filtros para la JPQL
     * @return Long
     */
    @Transactional(value = "trazability-txm",readOnly = true)
    @Override
    public Long loadSizeAll(GridRequest filters) {
        List<Long> result = this.find(this.entityManager,"select count(*) from CatalogoHasTemplates ch" + filters.getQL("ch", true), filters.getParamsValues());
        return result.get(0);
    }

    /**
     * Obtiene la lista de {@link CatalogoHasTemplates} que cumplen el filtro <br/>
     * @param filters Filtros para la JPQL
     * @return List<CatalogoHasTemplates>
     */
    @Transactional(value = "trazability-txm",readOnly = true)
    @Override
    public List<CatalogoHasTemplates> loadAll(GridRequest filters) {
        List<CatalogoHasTemplates> catHasTemp = this.find(this.entityManager,"from CatalogoHasTemplates ch" + filters.getQL("ch", true), filters.getParamsValues(), filters.getStart(), filters.getLimit());
        return catHasTemp;
    }

    /**
     * Inserta en {@link CatalogoHasTemplates} a partir de los datos recibidos <br/>
     * @param codigo Identificador de {@link CatalogoMedicamentos}
     * @param idtemplate Identificador de {@link TemplatesMedication}
     */
    @Transactional(value = "trazability-txm")
    @Override
    public void insertCatalogoHasTemplates(String codigo, Long idtemplate) throws Exception {

        List<TemplatesMedication> aux = this.getTemplateFromEspecialidad(codigo);
        if (aux != null && aux.isEmpty()) {

            CatalogoHasTemplates ct = new CatalogoHasTemplates();

            CatalogoMedicamentos catalogoMedicamentos = new CatalogoMedicamentos();
            catalogoMedicamentos.setCodigo(codigo);

            TemplatesMedication templatesMedication = new TemplatesMedication();
            templatesMedication.setIdtemplatesMedication(idtemplate);

            ct.setCatalogoMedicamentos(catalogoMedicamentos);
            ct.setTemplatesId(templatesMedication);
            ct.setCatalogoCodigo(codigo);

            this.entityManager.persist(ct);
        } else {
            throw new PrimaryKeyException("La Especialidad no puede tener más de una Plantilla asignada.");
        }
    }

    /**
     * Modifica {@link CatalogoHasTemplates} a partir de los datos recibidos <br/>
     * @param oldcodigo Identificador de {@link CatalogoMedicamentos} antes de la modificacion
     * @param codigo Identificador de {@link CatalogoMedicamentos} posterior a la modificacion
     * @param idtemplate Identificador de {@link TemplatesMedication}
     */
    @Transactional(value = "trazability-txm")
    @Override
    public void updateCatalogoHasTemplates(final String oldcodigo, final String codigo, Long idtemplate) throws Exception {

        CatalogoHasTemplates ct = this.entityManager.find(CatalogoHasTemplates.class, oldcodigo);
        List<TemplatesMedication> aux = new ArrayList<TemplatesMedication>();

        final CatalogoMedicamentos catalogoMedicamentos = new CatalogoMedicamentos();
        catalogoMedicamentos.setCodigo(codigo);

        TemplatesMedication templatesMedication = new TemplatesMedication();
        templatesMedication.setIdtemplatesMedication(idtemplate);

        ct.setTemplatesId(templatesMedication);

        this.entityManager.flush();

        if (!oldcodigo.equals(codigo)) {
            aux = this.getTemplateFromEspecialidad(codigo);
        }

        if (aux != null && aux.isEmpty()) {
            this.entityManager.createNativeQuery("update catalogo_has_templates c SET c.catalogo_codigo =:codigo WHERE c.catalogo_codigo=:oldcodigo").setParameter("codigo", catalogoMedicamentos).setParameter("oldcodigo", oldcodigo).executeUpdate();                               
        } else {
            throw new PrimaryKeyException("La Especialidad no puede tener más de una Plantilla asignada.");
        }

    }

    /**
     * Devuelve la plantilla para una determinada especialidad
     * @param codigo
     * @return
     */
    @Transactional(value = "trazability-txm",readOnly = true)
    @Override
    public List<TemplatesMedication> getTemplateFromEspecialidad(String codigo) {

        List<TemplatesMedication> template = null;
        try {
            template = this.entityManager.createQuery("SELECT tm FROM TemplatesMedication tm, CatalogoHasTemplates cht WHERE cht.templatesId=tm.idtemplatesMedication and cht.catalogoMedicamentos.codigo=:codigo").setParameter("codigo", codigo).getResultList();

        } catch (Exception e) {
            TemplatesMedication temp = new TemplatesMedication();
            template.set(0, temp);
        }
        return template;
    }
}
