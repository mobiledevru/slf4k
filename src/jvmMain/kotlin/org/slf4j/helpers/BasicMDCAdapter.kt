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

import org.slf4j.spi.MDCAdapter

/**
 * Basic MDC implementation, which can be used with logging systems that lack
 * out-of-the-box MDC support.
 *
 * This code was initially inspired by  logback's LogbackMDCAdapter. However,
 * LogbackMDCAdapter has evolved and is now considerably more sophisticated.
 *
 * @author Ceki Gulcu
 * @author Maarten Bosteels
 * @author Lukasz Cwik
 *
 * @since 1.5.0
 */
open class BasicMDCAdapter : MDCAdapter {
    private val inheritableThreadLocal: InheritableThreadLocal<MutableMap<String?, String?>?> =
        object : InheritableThreadLocal<MutableMap<String?, String?>?>() {
            protected override fun childValue(parentValue: MutableMap<String?, String?>?): MutableMap<String?, String?>? {
                return if (parentValue == null) {
                    null
                } else HashMap(parentValue)
            }
        }

    /**
     * Put a context value (the `val` parameter) as identified with
     * the `key` parameter into the current thread's context map.
     * Note that contrary to log4j, the `val` parameter can be null.
     *
     *
     *
     * If the current thread does not have a context map it is created as a side
     * effect of this call.
     *
     * @throws IllegalArgumentException
     * in case the "key" parameter is null
     */
    override fun put(key: String, `val`: String?) {
        requireNotNull(key) { "key cannot be null" }
        var map: MutableMap<String?, String?>? = inheritableThreadLocal.get()
        if (map == null) {
            map = HashMap()
            inheritableThreadLocal.set(map)
        }
        map[key] = `val`
    }

    /**
     * Get the context identified by the `key` parameter.
     */
    override operator fun get(key: String?): String? {
        val map: Map<String?, String?>? = inheritableThreadLocal.get()
        return if (map != null && key != null) {
            map[key]
        } else {
            null
        }
    }

    /**
     * Remove the the context identified by the `key` parameter.
     */
    override fun remove(key: String?) {
        val map: MutableMap<String?, String?>? = inheritableThreadLocal.get()
        if (map != null) {
            map.remove(key)
        }
    }

    /**
     * Clear all entries in the MDC.
     */
    override fun clear() {
        val map: MutableMap<String?, String?>? = inheritableThreadLocal.get()
        if (map != null) {
            map.clear()
            inheritableThreadLocal.remove()
        }
    }

    /**
     * Returns the keys in the MDC as a [Set] of [String]s The
     * returned value can be null.
     *
     * @return the keys in the MDC
     */
    val keys: Set<String?>?
        get() {
            return inheritableThreadLocal.get()?.keys
        }

    /**
     * Return a copy of the current thread's context map.
     * Returned value may be null.
     *
     */
    override fun getCopyOfContextMap(): Map<String?, String?>? {
        val oldMap: Map<String?, String?>? = inheritableThreadLocal.get()
        return if (oldMap != null) {
            HashMap(oldMap)
        } else {
            null
        }
    }

    override fun setContextMap(contextMap: Map<String?, String?>?) {
//        inheritableThreadLocal.set(HashMap(contextMap))
        inheritableThreadLocal.set(HashMap(contextMap ?: emptyMap()))
    }
}