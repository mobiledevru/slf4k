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
package org.slf4j

import ru.mobiledev.slf4k.SerializableAdapter

/**
 * Markers are named objects used to enrich log statements. Conforming logging
 * system Implementations of SLF4J determine how information conveyed by markers
 * are used, if at all. In particular, many conforming logging systems ignore
 * marker data.
 * 
 * <p>
 * Markers can contain references to other markers, which in turn may contain 
 * references of their own.
 * 
 * @author Ceki G&uuml;lc&uuml;
 */
interface Marker : SerializableAdapter {
    /**
     * Get the name of this Marker.
     * 
     * @return name of marker
     */
    val name: String

    /**
     * Add a reference to another Marker.
     * 
     * @param reference
     *                a reference to another marker
     * @throws IllegalArgumentException
     *                 if 'reference' is null
     */
    fun add(reference: Marker)

    /**
     * Remove a marker reference.
     * 
     * @param reference
     *                the marker reference to remove
     * @return true if reference could be found and removed, false otherwise.
     */
    fun remove(reference: Marker): Boolean

    /**
     * @deprecated Replaced by {@link #hasReferences()}.
     */
    @Deprecated("Replaced by {@link #hasReferences()}.")
    fun hasChildren(): Boolean

    /**
     * Does this marker have any references?
     * 
     * @return true if this marker has one or more references, false otherwise.
     */
    fun hasReferences(): Boolean

    /**
     * Returns an Iterator which can be used to iterate over the references of this
     * marker. An empty iterator is returned when this marker has no references.
     *
     * @return Iterator over the references of this marker
     */
    operator fun iterator(): Iterator<Marker>

    /**
     * Does this marker contain a reference to the 'other' marker? Marker A is defined 
     * to contain marker B, if A == B or if B is referenced by A, or if B is referenced
     * by any one of A's references (recursively).
     * 
     * @param other
     *                The marker to test for inclusion.
     * @throws IllegalArgumentException
     *                 if 'other' is null
     * @return Whether this marker contains the other marker.
     */
    operator fun contains(other: Marker): Boolean

    /**
     * Does this marker contain the marker named 'name'?
     *
     * If 'name' is null the returned value is always false.
     *
     * @param name The marker name to test for inclusion.
     * @return Whether this marker contains the other marker.
     */
    operator fun contains(name: String): Boolean

    /**
     * Markers are considered equal if they have the same name.
     *
     * @param other
     * @return true, if this.name equals o.name
     *
     * @since 1.5.1
     */
    override fun equals(other: Any?): Boolean

    /**
     * Compute the hash code based on the name of this marker.
     * Note that markers are considered equal if they have the same name.
     * 
     * @return the computed hashCode
     * @since 1.5.1
     */
    override fun hashCode(): Int

    companion object {
        /**
         * This constant represents any marker, including a null marker.
         */
        const val ANY_MARKER = "*"

        /**
         * This constant represents any non-null marker.
         */
        const val ANY_NON_NULL_MARKER = "+"
    }
}