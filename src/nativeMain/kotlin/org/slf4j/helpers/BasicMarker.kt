package org.slf4j.helpers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.slf4j.Marker
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

/**
 * A simple implementation of the {@link Marker} interface.
 *
 * @author Ceki G&uuml;lc&uuml;
 * @author Joern Huxhorn
 * @author Golubev Dmitrii
 */
class BasicMarker(override val name: String) : Marker {

    private val referenceList: MutableList<Marker> = ArrayList()
    private val referenceListContext = Dispatchers.Default
    private val referenceListLock = Mutex(false)

    override fun add(reference: Marker) {
        // no point in adding the reference multiple times
        val thisMarker = this
        if (thisMarker.contains(reference)) {
            return
        } else if (reference.contains(thisMarker)) { // avoid recursion
            // a potential reference should not hold its future "parent" as a reference
            return
        } else {
            // double-checking
            runBlocking {
                withContext(referenceListContext) {
                    referenceListLock.withLock {
                        if (thisMarker.contains(reference)) {
                            // return
                        } else if (reference.contains(thisMarker)) { // avoid recursion
                            // a potential reference should not hold its future "parent" as a reference
                            // return
                        } else {
                            referenceList.add(reference)
                        }
                    }
                }
            }
        }
    }

    override fun hasReferences(): Boolean = referenceList.isNotEmpty()

    override fun hasChildren(): Boolean = hasReferences()

    /**
     * Thread-safe iterator over copy of current content
     */
    override fun iterator(): Iterator<Marker> {
        var cloneIterator: Iterator<Marker>
        runBlocking {
            withContext(referenceListContext) {
                referenceListLock.withLock {
                    cloneIterator = listOf(*referenceList.toTypedArray()).iterator()
                }
            }
        }
        return cloneIterator
    }

    override fun remove(reference: Marker): Boolean {
        var result: Boolean
        runBlocking {
            withContext(referenceListContext) {
                referenceListLock.withLock {
                    result = referenceList.remove(reference)
                }
            }
        }
        return result
    }

    override operator fun contains(other: Marker): Boolean {
        if (this == other) {
            return true
        } else if (hasReferences()) {
            try {
                for (ref in referenceList.iterator()) {
                    if (ref.contains(other)) {
                        return true
                    }
                }
                return false
            } catch (e: NoSuchElementException) {
                // ignore - size has been concurrently changed
                return false
            }
        } else {
            return false
        }
    }

    /**
     * This method is mainly used with Expression Evaluators.
     */
    override operator fun contains(name: String): Boolean {
        if (this.name == name) {
            return true
        } else if (hasReferences()) {
            try {
                for (ref in referenceList.iterator()) {
                    if (ref.contains(name)) {
                        return true
                    }
                }
                return false
            } catch (e: NoSuchElementException) {
                // ignore - size has been concurrently changed
                return false
            }
        } else {
            return false
        }
    }

    private val OPEN = "[ "
    private val CLOSE = " ]"
    private val SEP = ", "

    override fun equals(other: Any?): Boolean = when (other) {
        this -> true
        null -> false
        is Marker -> { name == other.name }
        else -> false
    }

    override fun hashCode(): Int = name.hashCode()

    override fun toString(): String {
        if (!hasReferences()) {
            return name
        } else {
            val it = this.iterator()
            var reference: Marker
            val sb = StringBuilder(name)
            sb.append(' ').append(OPEN)
            while (it.hasNext()) {
                try {
                    reference = it.next()
                    sb.append(reference.name)
                    if (it.hasNext()) {
                        sb.append(SEP)
                    }
                } catch (e: NoSuchElementException) {
                    // do nothing - size has been concurrently changed
                }
            }
            sb.append(CLOSE)
            return sb.toString()
        }
    }
}