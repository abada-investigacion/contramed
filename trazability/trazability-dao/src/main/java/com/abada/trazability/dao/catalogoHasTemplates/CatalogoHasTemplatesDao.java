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

import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest;
import com.abada.trazability.entity.CatalogoHasTemplates;
import com.abada.trazability.entity.CatalogoMedicamentos;
import com.abada.trazability.entity.TemplatesMedication;
import java.util.List;

/**
 *
 * @author mmartin
 * 
 * Implementa todos los metodos de {@link CatalogoHasTemplatesDaoImpl}
 */
public interface CatalogoHasTemplatesDao {

    /**
     *Devuelve el total de registros de {@link CatalagoHasTemplates} a partir del filtro <br/>
     * @param filters Filtros para la JPQL
     * @return Long
     */
    public Long loadSizeAll(GridRequest filters);

    /**
     * Obtiene la lista de {@link CatalogoHasTemplates} que cumplen el filtro <br/>
     * @param filters Filtros para la JPQL
     * @return List<CatalogoHasTemplates>
     */
    public List<CatalogoHasTemplates> loadAll(GridRequest filters);

    /**
     * Inserta en {@link CatalogoHasTemplates} a partir de los datos recibidos <br/>
     * @param codigo Identificador de {@link CatalogoMedicamentos}
     * @param idtemplate Identificador de {@link TemplatesMedication}
     */
    public void insertCatalogoHasTemplates(String codigo, Long idtemplate) throws Exception;

    /**
     * Modifica {@link CatalogoHasTemplates} a partir de los datos recibidos <br/>
     * @param oldcodigo Identificador de {@link CatalogoMedicamentos} antes de la modificacion
     * @param codigo Identificador de {@link CatalogoMedicamentos} posterior a la modificacion
     * @param idtemplate Identificador de {@link TemplatesMedication}
     */
    public void updateCatalogoHasTemplates(final String oldcodigo, final String codigo, Long idtemplate) throws Exception;

    /**
     * Devuelve la plantilla para una determinada especialidad
     * @param codigo
     * @return
     */
    public List<TemplatesMedication> getTemplateFromEspecialidad(String codigo);
}
