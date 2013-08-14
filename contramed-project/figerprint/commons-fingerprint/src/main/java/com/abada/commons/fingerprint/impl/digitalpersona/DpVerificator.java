/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.commons.fingerprint.impl.digitalpersona;

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

import com.abada.commons.fingerprint.Verificator;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch._impl.DPFPFeatureSetFactoryImpl;
import com.digitalpersona.onetouch._impl.DPFPTemplateFactoryImpl;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;

/**
 *
 * @author katsu
 */
public class DpVerificator implements Verificator {

    public boolean verification(byte [] original,byte [] compared){
        DPFPVerification matcher = DPFPGlobal.getVerificationFactory().createVerification();
        matcher.setFARRequested(DPFPVerification.MEDIUM_SECURITY_FAR);

        DPFPTemplate template=new DPFPTemplateFactoryImpl().createTemplate(original);
        DPFPFeatureSet feaureSet =new DPFPFeatureSetFactoryImpl().createFeatureSet(compared);

        DPFPVerificationResult result = matcher.verify(feaureSet, template);
        return result.isVerified();
    }
}
