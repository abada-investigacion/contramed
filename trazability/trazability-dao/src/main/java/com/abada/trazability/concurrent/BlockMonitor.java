/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.trazability.concurrent;

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

import java.util.concurrent.locks.ReentrantLock;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author katsu
 */
public class BlockMonitor extends ReentrantLock {

    private static Log logger = LogFactory.getLog(BlockMonitor.class);    

    public BlockMonitor() {
        super();
    }

    @Override
    public void lock() {
        logger.debug("Locking "+Thread.currentThread().getId());
        super.lock();
        logger.debug("Locked "+Thread.currentThread().getId());
    }

    @Override
    public void unlock() {
        logger.debug("Unlocking "+Thread.currentThread().getId());
        super.unlock();
        logger.debug("Unlocked "+Thread.currentThread().getId());
    }
}
