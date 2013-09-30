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
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest;
import java.util.List;

/**
 *
 * @author mmartin
 *
 * Implementa todos los metodos de {@link TemplatesMedicationDaoImpl}
 */
public interface TemplatesMedicationDao {

    /**
     * Obtiene el tamaño de {@link TemplatesMedication} a partir del filtro <br/>
     * @param filters Filtros para la JPQL
     * @return Long
     */
    public Long loadSizeAll(GridRequest filters);

    /**
     * Obtiene la lista de {@link TemplatesMedication} que cumplen el filtro <br/>
     * @param filters Filtros para la JPQL
     * @return List<TemplatesMedication>
     */
    public List<TemplatesMedication> loadAll(GridRequest filters);

    /**
     * Obtiene la lista de {@link TemplatesMedication} <br/>
     * @return List<TemplatesMedication>
     */
    public List<TemplatesMedication> listAll();

    /**
     * Inserta un {@link TemplatesMedication} a partir de los datos recibidos <br/>
     * @param template Nombre de la plantilla
     * @param uuid UUID bajo el que esta almacenado el fichero de plantilla
     * @param uuidStyle UUID bajo el que esta almacenado el fichero de estilos
     */
    public void insertTemplate(String template, String uuid, String uuidStyle) throws Exception;

    /**
     * Modifica {@link TemplatesMedication} a partir de los datos recibidos <br/>
     * @param idtemplatesmedication Identificador de la plantilla
     * @param template Nombre de la plantilla
     * @param uuid UUID bajo el que esta almacenado el fichero de plantilla
     * @param uuidStyle UUID bajo el que esta almacenado el fichero de estilos
     */
    public void updateTemplatesMedication(Long idtemplatesmedication, String template, String uuid, String uuidStyle) throws Exception;
}
