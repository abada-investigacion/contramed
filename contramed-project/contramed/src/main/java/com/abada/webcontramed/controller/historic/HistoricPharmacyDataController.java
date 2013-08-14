/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.webcontramed.controller.historic;

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

import com.abada.contramed.persistence.dao.preparehistoric.PrepareHistoricDao;
import com.abada.contramed.persistence.entity.PrepareHistoric;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.GridRequest_Extjs_v3;
import com.abada.springframework.web.servlet.command.extjs.gridpanel.factory.GridRequest_Extjs_v3Factory;
import com.abada.springframework.web.servlet.handler.interceptor.annotation.Role;
import com.abada.springframework.web.servlet.view.JsonView;
import com.abada.web.exjs.ExtjsStore;
import com.abada.webcontramed.controller.CommonControllerConstants;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author katsu
 */
@Controller
@Role({"SYSTEM_ADMINISTRATOR",    
    "PHARMACIST"})
public class HistoricPharmacyDataController extends CommonControllerConstants {

    @Resource(name = "prepareHistoricDao")
    private PrepareHistoricDao historicDao;

    @RequestMapping("/prepareHistoricData.htm")
    public ModelAndView givesHistoricData(String filter, String dir, String sort, Integer limit, Integer start) throws UnsupportedEncodingException {
        GridRequest_Extjs_v3 request = GridRequest_Extjs_v3Factory.parse(sort, dir, start, limit, filter);
        ExtjsStore result = new ExtjsStore();
        result.setTotal(historicDao.loadPrepareHistoricSizeAll(request).intValue());
        List<PrepareHistoric> aux = historicDao.loadPrepareHistoricAll(request);
        result.setData(aux);
        return new ModelAndView(new JsonView(result));
    }

}
