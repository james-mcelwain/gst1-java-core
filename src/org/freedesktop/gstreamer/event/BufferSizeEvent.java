/* 
 * Copyright (c) 2008 Wayne Meissner
 * Copyright (C) 1999,2000 Erik Walthinsen <omega@cse.ogi.edu>
 *                    2000 Wim Taymans <wim.taymans@chello.be>
 *                    2005 Wim Taymans <wim@fluendo.com>
 * 
 * This file is part of gstreamer-java.
 *
 * This code is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License version 3 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * version 3 for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * version 3 along with this work.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.freedesktop.gstreamer.event;

import org.freedesktop.gstreamer.Event;
import org.freedesktop.gstreamer.Format;

import org.freedesktop.gstreamer.lowlevel.GstEventAPI;

/**
 * Notification of new latency adjustment.
 * <p>
 * The event is sent upstream from the sinks and
 * notifies elements that they should add an additional latency to the
 * timestamps before synchronising against the clock.
 * <p>
 * The latency is mostly used in live sinks and is always expressed in
 * the time format.
 */
public class BufferSizeEvent extends Event {

    private static final GstEventAPI gst = GstEventAPI.GSTEVENT_API;
    
    /**
     * This constructor is for internal use only.
     * @param init initialization data.
     */
    public BufferSizeEvent(Initializer init) {
        super(init);
    }
    
    /**
     * Creates a new buffersize event. 
     * <p> The event is sent downstream and notifies elements that they should 
     * provide a buffer of the specified dimensions.
     *
     * <p> When the <tt>async</tt> flag is set, a thread boundary is preferred.
     * 
     * @param format buffer format
     * @param minsize minimum buffer size
     * @param maxsize maximum buffer size
     * @param async thread behavior
     */
    public BufferSizeEvent(Format format, long minsize, long maxsize, boolean async) {
        super(initializer(gst.ptr_gst_event_new_buffer_size(format, minsize, maxsize, async)));
    }
    
    /**
     * Gets the format of the buffersize event.
     * 
     * @return the format.
     */
    public Format getFormat() {
        Format[] format = new Format[1];
        gst.gst_event_parse_buffer_size(this, format, null, null, null);
        return format[0];
    }
    
    /**
     * Gets the minimum buffer size.
     * 
     * @return the minimum buffer size.
     */
    public long getMinimumSize() {
        long[] size = { 0 };
        gst.gst_event_parse_buffer_size(this, null, size, null, null);
        return size[0];
    }
    
    /**
     * Gets the maximum buffer size.
     * 
     * @return the maximum buffer size.
     */
    public long getMaximumSize() {
        long[] size = { 0 };
        gst.gst_event_parse_buffer_size(this, null, null, size, null);
        return size[0];
    }
    
    /**
     * Gets the preferred thread behaviour.
     * 
     * @return <tt>true</tt> if a thread boundary is preferred.
     */
    public boolean isAsync() {
        boolean[] async = { false };
        gst.gst_event_parse_buffer_size(this, null, null, null, async);
        return async[0];
    }
}
