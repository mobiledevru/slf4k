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
package org.slf4j.helpers

import org.slf4j.Marker

/**
 * A simple implementation of the [Marker] interface.
 *
 * @author Ceki G&uuml;lc&uuml;
 * @author Joern Huxhorn
 */
open class BasicMarker internal constructor(name: String) : Marker, java.io.Serializable {

    final override val name: String
        get() = field
    private val referenceList: MutableList<Marker> = java.util.concurrent.CopyOnWriteArrayList<Marker>()

    override fun add(reference: Marker) {
        requireNotNull(reference) { "A null value cannot be added to a Marker as reference." }

        // no point in adding the reference multiple times
        if (this.contains(reference)) {
            return
        } else if (reference.contains(this)) { // avoid recursion
            // a potential reference should not hold its future "parent" as a reference
            return
        } else {
            referenceList.add(reference)
        }
    }

    override fun hasReferences(): Boolean {
        return referenceList.size > 0
    }

    @Deprecated("Deprecated", ReplaceWith("hasReferences()"))
    override fun hasChildren(): Boolean {
        return hasReferences()
    }

    override fun iterator(): Iterator<Marker> {
        return referenceList.iterator()
    }

    override fun remove(referenceToRemove: Marker): Boolean {
        return referenceList.remove(referenceToRemove)
    }

    override operator fun contains(other: Marker): Boolean {
        requireNotNull(other) { "Other cannot be null" }
        if (this == other) {
            return true
        }
        if (hasReferences()) {
            for (ref in referenceList) {
                if (ref.contains(other)) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * This method is mainly used with Expression Evaluators.
     */
    override fun contains(name: String): Boolean {
        requireNotNull(name) { "Other cannot be null" }
        if (this.name == name) {
            return true
        }
        if (hasReferences()) {
            for (ref in referenceList) {
                if (ref.contains(name)) {
                    return true
                }
            }
        }
        return false
    }

    init {
        requireNotNull(name) { "A marker name cannot be null" }
        this.name = name
    }

    override fun equals(obj: Any?): Boolean {
        if (this === obj) return true
        if (obj == null) return false
        if (obj !is Marker) return false
        return name == obj.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    override fun toString(): String {
        if (!hasReferences()) {
            return name
        }
        val it = this.iterator()
        var reference: Marker
        val sb = StringBuilder(name)
        sb.append(' ').append(OPEN)
        while (it.hasNext()) {
            reference = it.next()
            sb.append(reference.name)
            if (it.hasNext()) {
                sb.append(SEP)
            }
        }
        sb.append(CLOSE)
        return sb.toString()
    }

    companion object {
        private const val serialVersionUID = -2849567615646933777L
        private const val OPEN = "[ "
        private const val CLOSE = " ]"
        private const val SEP = ", "
    }
}