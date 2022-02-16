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
package ru.mobiledev.slf4k

/**
 * Implementations of this interface are used to manufacture [Marker]
 * instances.
 *
 *
 * See the section [Implementing
 * the SLF4J API](http://slf4j.org/faq.html#3) in the FAQ for details on how to make your logging
 * system conform to SLF4J.
 * 
 * @author Ceki G&uuml;lc&uuml;
 */
interface IMarkerFactory {
    /**
     * Manufacture a [Marker] instance by name. If the instance has been
     * created earlier, return the previously created instance.
     *
     *
     * Null name values are not allowed.
     *
     * @param name the name of the marker to be created, null value is
     * not allowed.
     *
     * @return a Marker instance
     */
    fun getMarker(name: String): Marker

    /**
     * Checks if the marker with the name already exists. If name is null, then false
     * is returned.
     *
     * @param name logger name to check for
     * @return true id the marker exists, false otherwise.
     */
    fun exists(name: String?): Boolean

    /**
     * Detach an existing marker.
     *
     *
     * Note that after a marker is detached, there might still be "dangling" references
     * to the detached marker.
     *
     *
     * @param name The name of the marker to detach
     * @return whether the marker  could be detached or not
     */
    fun detachMarker(name: String?): Boolean

    /**
     * Create a marker which is detached (even at birth) from this IMarkerFactory.
     *
     * @param name marker name
     * @return a dangling marker
     * @since 1.5.1
     */
    fun getDetachedMarker(name: String): Marker
}