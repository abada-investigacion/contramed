package com.abada.trazability.dao.templatesMedication;

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

import com.abada.trazability.entity.TemplatesMedication;
import com.abada.springframework.orm.jpa.support.JpaDaoUtils;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mmartin
 *
 * Dao de la entidad {@link TemplatesMedication}. Trabaja con los datos de los templates y styles.
 */
@Repository("templatesMedicationDao")
public class TemplatesMedicationDaoImpl extends JpaDaoUtils implements TemplatesMedicationDao {

    /**
     * Constructor
     * @param entityManagerFactory
     */
    @Resource(name = "entityManager")
    public void setEntityManagerFactory2(EntityManagerFactory entityManagerFactory) {
        setEntityManagerFactory(entityManagerFactory);
    }

    /**
     * Obtiene el tamaño de {@link TemplatesMedication} a partir del filtro <br/>
     * @param filters Filtros para la JPQL
     * @return Long
     */
    @Transactional(value="trazability-txm",readOnly = true)
    @Override
    public Long loadSizeAll(GridRequest filters) {
        List<Long> result = this.find("select count(*) from TemplatesMedication t" + filters.getQL("t", true), filters.getParamsValues());
        return result.get(0);
    }

    /**
     * Obtiene la lista de {@link TemplatesMedication} que cumplen el filtro <br/>
     * @param filters Filtros para la JPQL
     * @return List<TemplatesMedication>
     */
    @Transactional(value="trazability-txm",readOnly = true)
    @Override
    public List<TemplatesMedication> loadAll(GridRequest filters) {
        List<TemplatesMedication> tempmed = this.find("from TemplatesMedication t" + filters.getQL("t", true), filters.getParamsValues(), filters.getStart(), filters.getLimit());
        return tempmed;
    }

    /**
     * Obtiene la lista de {@link TemplatesMedication} <br/>
     * @return List<TemplatesMedication>
     */
    @Transactional(value="trazability-txm",readOnly = true)
    @Override
    public List<TemplatesMedication> listAll() {
        List<TemplatesMedication> tempmed = this.entityManager.find("from TemplatesMedication t ORDER BY Template ASC");
        return tempmed;
    }

    /**
     * Inserta un {@link TemplatesMedication} a partir de los datos recibidos <br/>
     * @param template Nombre de la plantilla
     * @param uuid UUID bajo el que esta almacenado el fichero de plantilla
     * @param uuidStyle UUID bajo el que esta almacenado el fichero de estilos
     */
    @Transactional
    @Override
    public void insertTemplate(String template, String uuid, String uuidStyle)throws Exception {

        try {
            TemplatesMedication t = new TemplatesMedication();

            t.setTemplate(template);
            t.setPathTemplate(uuid.toString());
            t.setPathStyle(uuidStyle);

            this.entityManager.persist(t);
        } catch (Exception ex) {
            throw new Exception("Inserción fallida.");
        }
    }

    /**
     * Modifica {@link TemplatesMedication} a partir de los datos recibidos <br/>
     * @param idtemplatesmedication Identificador de la plantilla
     * @param template Nombre de la plantilla
     * @param uuid UUID bajo el que esta almacenado el fichero de plantilla
     * @param uuidStyle UUID bajo el que esta almacenado el fichero de estilos
     */
    @Transactional
    @Override
    public void updateTemplatesMedication(Long idtemplatesmedication, String template, String uuid, String uuidStyle) throws Exception {

        try{
        TemplatesMedication temp = (TemplatesMedication) this.entityManager.find(TemplatesMedication.class, new Long(idtemplatesmedication));

        if (temp.getIdtemplatesMedication().compareTo(idtemplatesmedication) == 0) {

            temp.setTemplate(template);
            temp.setPathTemplate(uuid);
            temp.setPathStyle(uuidStyle);
        }
        }catch(Exception ex){
        throw new Exception("Modificación fallida.");
        }

    }
}
