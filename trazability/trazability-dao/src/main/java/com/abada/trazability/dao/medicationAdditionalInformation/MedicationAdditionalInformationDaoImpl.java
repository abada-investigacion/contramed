/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.trazability.dao.medicationAdditionalInformation;

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

import com.abada.trazability.entity.MedicationAdditionalInformation;
import com.abada.springframework.orm.jpa.support.JpaDaoUtils;
import com.abada.trazability.entity.Dose;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mmartin
 */
//@Repository("medicationAdditionalInformationDao")
public class MedicationAdditionalInformationDaoImpl extends JpaDaoUtils implements MedicationAdditionalInformationDao {

    @PersistenceContext(unitName = "trazabilityPU")
    private EntityManager entityManager;
    /**
     *Obtiene la lista de {@link Dose} que cumplen el filtro <br/>
     * @param filters Filtros para la JPQL
     * @return List<Dose>
     */
    @Transactional(value="trazability-txm",readOnly = true)
    @Override
    public MedicationAdditionalInformation loadByCodigo(String codigo) {
       List<MedicationAdditionalInformation> med = this.entityManager.createQuery("SELECT m FROM MedicationAdditionalInformation m WHERE m.catalogomedicamentosCODIGO.codigo=:codigo").setParameter("codigo", codigo).getResultList();
        return med.get(0);

    }
    
}
