/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abada.selene.v25.message;

import ca.uhn.hl7v2.model.Message;
import com.abada.farmatools.object.IFarmatoolsObject;

/**
 *
 * @author aroldan
 */
public interface IMessage<M extends Message,F extends IFarmatoolsObject> {
    public M exec(F object);
}
