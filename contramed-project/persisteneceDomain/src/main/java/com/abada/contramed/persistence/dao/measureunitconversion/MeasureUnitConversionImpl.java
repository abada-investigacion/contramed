/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.contramed.persistence.dao.measureunitconversion;

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

import com.abada.contramed.persistence.entity.MeasureUnit;
import com.abada.contramed.persistence.entity.MeasureUnitConversion;
import com.abada.springframework.orm.jpa.support.JpaDaoSupport;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author katsu
 */
@Repository("measureUnitConversionDao")
public class MeasureUnitConversionImpl extends JpaDaoSupport implements MeasureUnitConversionDao {

    @Resource(name = "entityManager")
    public void setEntityManagerFactory2(EntityManagerFactory entityManagerFactory) {
        setEntityManagerFactory(entityManagerFactory);
    }

    @Transactional(readOnly = false)
    @Override
    public MeasureUnitConversion getMeasureUnitConversion(MeasureUnit from, MeasureUnit to) {
        List<MeasureUnitConversion> result = this.getJpaTemplate().find("SELECT muc FROM MeasureUnitConversion muc WHERE muc.mu_from=? AND muc.mu_to=? ", from, to);
        if (result != null && !result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }

    @Transactional(readOnly = false)
    @Override
    public BigDecimal convert(BigDecimal valueFrom, MeasureUnit from, MeasureUnit to) {
        if (from != null && to != null && !from.equals(to)) {
            MeasureUnitConversion muc = this.getMeasureUnitConversion(from, to);
            if (muc != null) {
                return muc.getMultiplier().multiply(valueFrom);
            }
            return null;
        }
        return valueFrom;
    }
}
