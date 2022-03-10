/**
 * Copyright (c) 2004-2011 QOS.ch
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package ru.mobiledev.slf4k.helpers

import ru.mobiledev.slf4k.IMarkerFactory
import ru.mobiledev.slf4k.Marker

/**
 * An almost trivial implementation of the [IMarkerFactory]
 * interface which creates [BasicMarker] instances.
 *
 *
 * Simple logging systems can conform to the SLF4J API by binding
 * [org.slf4j.MarkerFactory] with an instance of this class.
 *
 * @author Ceki G&uuml;lc&uuml;
 */
class BasicMarkerFactory
/**
 * Regular users should *not* create
 * `BasicMarkerFactory` instances. `Marker`
 * instances can be obtained using the static [ ][org.slf4j.MarkerFactory.getMarker] method.
 */
    : IMarkerFactory {
    private val markerMap: java.util.concurrent.ConcurrentMap<String, Marker> =
        java.util.concurrent.ConcurrentHashMap<String, Marker>()

    /**
     * Manufacture a [BasicMarker] instance by name. If the instance has been
     * created earlier, return the previously created instance.
     *
     * @param name the name of the marker to be created
     * @return a Marker instance
     */
    override fun getMarker(name: String): Marker {
        requireNotNull(name) { "Marker name cannot be null" }
        var marker: Marker? = markerMap.get(name)
        if (marker == null) {
            marker = BasicMarker(name)
            val oldMarker: Marker? = markerMap.putIfAbsent(name, marker)
            if (oldMarker != null) {
                marker = oldMarker
            }
        }
        return marker
    }

    /**
     * Does the name marked already exist?
     */
    override fun exists(name: String?): Boolean {
        return if (name == null) {
            false
        } else markerMap.containsKey(name)
    }

    override fun detachMarker(name: String?): Boolean {
        return if (name == null) {
            false
        } else markerMap.remove(name) != null
    }

    override fun getDetachedMarker(name: String): Marker {
        return BasicMarker(name)
    }
}