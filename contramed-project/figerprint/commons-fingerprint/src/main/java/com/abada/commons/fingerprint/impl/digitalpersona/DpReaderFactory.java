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

import com.abada.commons.fingerprint.Reader;
import com.abada.commons.fingerprint.ReaderFactory;
import com.abada.commons.fingerprint.ReaderInformation;
import com.abada.commons.fingerprint.Verificator;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.readers.DPFPReaderDescription;
import com.digitalpersona.onetouch.readers.DPFPReadersCollection;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author katsu
 */
public class DpReaderFactory implements ReaderFactory
{
    public List<ReaderInformation> getReaders() {
        List<ReaderInformation> result = new ArrayList<ReaderInformation>();
        DPFPReadersCollection readers = DPFPGlobal.getReadersFactory().getReaders();
        for (DPFPReaderDescription r : readers) {
            result.add(new DpReaderInformation(r));
        }
        return result;
    }

    public Reader getReader(ReaderInformation readerInformation) {
        return new DpReader(readerInformation);
    }

    public Verificator getVerificator() {
       return new DpVerificator();
    }
}
