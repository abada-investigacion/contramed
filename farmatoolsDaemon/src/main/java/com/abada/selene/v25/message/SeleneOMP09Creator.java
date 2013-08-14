/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.selene.v25.message;

import ca.uhn.hl7v2.model.v25.group.OMP_O09_ORDER;
import ca.uhn.hl7v2.model.v25.group.OMP_O09_TIMING;
import ca.uhn.hl7v2.model.v25.message.OMP_O09;
import ca.uhn.hl7v2.model.v25.segment.MSH;
import ca.uhn.hl7v2.model.v25.segment.ORC;
import ca.uhn.hl7v2.model.v25.segment.PID;
import ca.uhn.hl7v2.model.v25.segment.RXO;
import ca.uhn.hl7v2.model.v25.segment.RXR;
import ca.uhn.hl7v2.model.v25.segment.TQ1;
import com.abada.farmatools.object.Farmatools_Order;
import com.abada.selene.v25.segment.SeleneMSHCreator;
import com.abada.selene.v25.segment.SeleneORCCreator;
import com.abada.selene.v25.segment.SelenePIDCreator;
import com.abada.selene.v25.segment.SeleneRXOCreator;
import com.abada.selene.v25.segment.SeleneRXRCreator;
import com.abada.selene.v25.segment.SeleneTQ1Creator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author aroldan
 *
 * Generado mensaje omp_O09
 */
public class SeleneOMP09Creator implements IMessage<OMP_O09, Farmatools_Order> {

    private static Log log = LogFactory.getLog(SeleneOMP09Creator.class);

    public OMP_O09 exec(Farmatools_Order object) {

        OMP_O09 message = new OMP_O09();
        MSH mshSegment = message.getMSH();
        PID pidSegment = message.getPATIENT().getPID();
        OMP_O09_ORDER orderSegment = message.getORDER();
        ORC orcSegment = orderSegment.getORC();
        OMP_O09_TIMING orderTiming = orderSegment.getTIMING();
        TQ1 TQ1Segment = orderTiming.getTQ1();
        RXO RXOSegment = orderSegment.getRXO();
        RXR RXRSegment = orderSegment.getRXR();
        SeleneMSHCreator mshCreator = new SeleneMSHCreator();
        mshCreator.createSegment(object, mshSegment);
        SelenePIDCreator pidCreator = new SelenePIDCreator();
        pidCreator.createSegment(object, pidSegment);
        SeleneORCCreator orcCreator = new SeleneORCCreator();
        orcCreator.createSegment(object, orcSegment);
        SeleneTQ1Creator tq1Creator = new SeleneTQ1Creator();
        tq1Creator.createSegment(object, TQ1Segment);
        SeleneRXOCreator rxoCreator = new SeleneRXOCreator();
        rxoCreator.createSegment(object, RXOSegment);
        SeleneRXRCreator rxrCreator = new SeleneRXRCreator();
        rxrCreator.createSegment(object, RXRSegment);

        return message;
    }
}
