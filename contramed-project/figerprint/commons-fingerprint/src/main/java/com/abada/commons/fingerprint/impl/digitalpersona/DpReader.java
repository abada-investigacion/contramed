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
import com.abada.commons.fingerprint.ReaderFacade;
import com.abada.commons.fingerprint.ReaderInformation;
import com.abada.commons.fingerprint.enums.FingerIndex;
import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPFingerIndex;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.DPFPCapturePriority;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPDataListener;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author katsu
 */
public class DpReader implements Reader{
    private final static Log logger=LogFactory.getLog(DpReader.class);
    private ReaderInformation reader;

    public DpReader(ReaderInformation reader) {
        this.reader=reader;
    }

    public byte[] readForVerification() {
        try {
            DPFPSample sample = getSample(null);
            if (sample != null) {
                DPFPFeatureExtraction featureExtractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
                DPFPFeatureSet featureSet = featureExtractor.createFeatureSet(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);

                return featureSet.serialize();
            }
        } catch (Exception e) {
            logger.error("Failed to perform verification.",e);
        }
        return null;
    }

    public byte[] readForSave(FingerIndex fi, ReaderFacade facade) {
        try {
            DPFPFeatureExtraction featureExtractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
            DPFPEnrollment enrollment = DPFPGlobal.getEnrollmentFactory().createEnrollment();

            while (enrollment.getFeaturesNeeded() > 0) {
                DPFPSample sample = getSample(facade);
                if (sample == null) {
                    continue;
                }

                DPFPFeatureSet featureSet;
                try {
                    featureSet = featureExtractor.createFeatureSet(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);
                } catch (DPFPImageQualityException e) {
                    logger.error("Bad image quality: "+e.getCaptureFeedback().toString()+". Try again. \n",e);
                    continue;
                }

                enrollment.addFeatures(featureSet);
                if (facade!=null)
                    facade.onNextRead(fi);
            }

            DPFPTemplate template = enrollment.getTemplate();
            logger.debug("The "+fi.toString()+" was enrolled.\n");
            return template.serialize();
        } catch (DPFPImageQualityException e) {
           logger.error("Failed to enroll the finger.\n",e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private DPFPSample getSample(ReaderFacade facade) throws InterruptedException {
        final LinkedBlockingQueue<DPFPSample> samples = new LinkedBlockingQueue<DPFPSample>();
        DPFPCapture capture = DPFPGlobal.getCaptureFactory().createCapture();
        capture.setReaderSerialNumber(reader.getSerialNumber());
        capture.setPriority(DPFPCapturePriority.CAPTURE_PRIORITY_LOW);
        capture.addDataListener(new DPFPDataListener() {

            public void dataAcquired(DPFPDataEvent e) {
                if (e != null && e.getSample() != null) {
                    try {
                        samples.put(e.getSample());
                    } catch (InterruptedException e1) {
                        logger.error("Couldn't read",e1);
                    }
                }
            }
        });
        capture.addReaderStatusListener(new DPFPReaderStatusAdapter() {

            int lastStatus = DPFPReaderStatusEvent.READER_CONNECTED;

            @Override
            public void readerConnected(DPFPReaderStatusEvent e) {
                if (lastStatus != e.getReaderStatus()) {
                    logger.debug("Reader is connected");
                }
                lastStatus = e.getReaderStatus();
            }

            @Override
            public void readerDisconnected(DPFPReaderStatusEvent e) {
                if (lastStatus != e.getReaderStatus()) {
                    logger.debug("Reader is disconnected");
                }
                lastStatus = e.getReaderStatus();
            }
        });
        try {
            capture.startCapture();            
            DPFPSample result=samples.take();
            logger.debug("Read sample.");
            return result;
        } catch (RuntimeException e) {
            logger.error("Failed to start capture. Check that reader is not used by another application.\n",e);
            throw e;
        } finally {
            capture.stopCapture();
        }
    }

    private DPFPFingerIndex to(FingerIndex fi){
        switch(fi){
            case LEFT_INDEX: return DPFPFingerIndex.LEFT_INDEX;
            case LEFT_MIDDLE: return DPFPFingerIndex.LEFT_MIDDLE;
            case LEFT_PINKY: return DPFPFingerIndex.LEFT_PINKY;
            case LEFT_RING: return DPFPFingerIndex.LEFT_RING;
            case LEFT_THUMB: return DPFPFingerIndex.LEFT_THUMB;
            case RIGHT_INDEX: return DPFPFingerIndex.RIGHT_INDEX;
            case RIGHT_MIDDLE: return DPFPFingerIndex.RIGHT_MIDDLE;
            case RIGHT_PINKY: return DPFPFingerIndex.RIGHT_PINKY;
            case RIGHT_RING: return DPFPFingerIndex.RIGHT_RING;
            case RIGHT_THUMB: return DPFPFingerIndex.RIGHT_THUMB;
        }
        return null;
    }
}
