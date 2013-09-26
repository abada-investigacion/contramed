
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.trazability.dao.dose;

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
import com.abada.trazability.entity.Dose;
import com.abada.trazability.entity.MeasureUnit;
import com.abada.springframework.orm.jpa.support.JpaDaoUtils;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mmartin
 *
 * Dao de la entidad {@link Dose}. Trabaja con los datos de las distintas dosis.
 */
@Repository("doseDao")
public class DoseDaoImpl extends JpaDaoUtils implements DoseDao {

    @PersistenceContext(unitName = "trazabilityPU")
    private EntityManager entityManager;

    /**
     *Obtiene el tamaño de {@link Dose}
     * @param filters Filtros para la JPQL
     * @return Long
     */
    @Transactional(value="trazability-txm",readOnly = true)
    @Override
    public Long loadSizeAll(GridRequest filters) {
        List<Long> result = this.find(this.entityManager,"select count(*) from Dose d" + filters.getQL("d", true), filters.getParamsValues());
        return result.get(0);
    }

    /**
     *Obtiene la lista de {@link Dose} que cumplen el filtro <br/>
     * @param filters Filtros para la JPQL
     * @return List<Dose>
     */
    @Transactional(value="trazability-txm",readOnly = true)
    @Override
    public List<Dose> loadAll(GridRequest filters) {
        List<Dose> dose = this.find(this.entityManager,"from Dose d" + filters.getQL("d", true), filters.getParamsValues(), filters.getStart(), filters.getLimit());
        return dose;
    }

    /**
     * Inserta en {@link Dose} a partir de los datos recibidos <br/>
     * @param codigo Codigo de dosis
     * @param batch  Lote de una dosis
     * @param expiration_date Fecha de caducidad de una dosis
     * @param give_amount Cantidad de administracion de una dosis
     * @param idmeasure_unit  Unidad de medida de una dosis
     */
    @Transactional
    @Override
    public void insertDose(String codigo, String batch, Date expiration_date, BigDecimal give_amount, Integer idmeasure_unit) throws Exception {
        this.insertPrintDose(codigo, batch, expiration_date, give_amount, idmeasure_unit);
    }

    /**
     * Inserta en {@link Dose} a partir de los datos recibidos <br/>
     * @param codigo Codigo de dosis
     * @param batch  Lote de una dosis
     * @param expiration_date Fecha de caducidad de una dosis
     * @param give_amount Cantidad de administracion de una dosis
     * @param idmeasure_unit  Unidad de medida de una dosis
     */
    @Transactional
    @Override
    public Dose insertPrintDose(String codigo, String batch, Date expiration_date, BigDecimal give_amount, Integer idmeasure_unit) throws Exception {


        Dose d = new Dose();
        try {
            CatalogoMedicamentos catalogoMedicamentos = new CatalogoMedicamentos();
            catalogoMedicamentos.setCodigo(codigo);

            MeasureUnit measureUnit = new MeasureUnit();
            measureUnit.setIdmeasureUnit(idmeasure_unit);

            d.setCatalogomedicamentosCODIGO(catalogoMedicamentos);
            d.setBatch(batch);
            d.setExpirationDate(expiration_date);
            d.setGiveAmount(give_amount);
            d.setMeasureUnitIdmeasureUnit(measureUnit);

            this.entityManager.persist(d);

            return d;
        } catch (Exception ex) {
            throw new Exception("Inserción fallida.");
        }
    }

    /**
     *Modifica {@link Dose} a partir de los datos recibidos <br/>
     * @param iddose Identificador de dosis
     * @param codigo Codigo de dosis
     * @param batch  Lote de una dosis
     * @param expiration_date Fecha de caducidad de una dosis
     * @param give_amount Cantidad de administracion de una dosis
     * @param idmeasure_unit  Unidad de medida de una dosis
     */
    @Transactional
    @Override
    public void updateDose(Long iddose, String codigo, String batch, Date expiration_date, BigDecimal give_amount, Integer idmeasure_unit) throws Exception {

        Dose dose = (Dose) this.entityManager.find(Dose.class, new Long(iddose));

        if (dose.getIddose().compareTo(iddose) == 0) {

            try {
                CatalogoMedicamentos catalogoMedicamentos = new CatalogoMedicamentos();
                catalogoMedicamentos.setCodigo(codigo);

                MeasureUnit measureUnit = new MeasureUnit();
                measureUnit.setIdmeasureUnit(idmeasure_unit);

                dose.setIddose(iddose);
                dose.setCatalogomedicamentosCODIGO(catalogoMedicamentos);
                dose.setBatch(batch);
                dose.setExpirationDate(expiration_date);
                dose.setGiveAmount(give_amount);
                dose.setMeasureUnitIdmeasureUnit(measureUnit);
            } catch (Exception ex) {
                throw new Exception("Modificación fallida.");
            }

        }

    }
}
