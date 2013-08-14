/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abada.selene.v25.segment;

import ca.uhn.hl7v2.model.Segment;


/**
 *
 * @author aroldan
 */
public interface ISegment<S extends Segment,F extends Object> {
    public S createSegment(F object, S currentSegment);
}
