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

import org.slf4j.Util.report
import org.slf4j.helpers.NOPMDCAdapter
import org.slf4j.impl.StaticMDCBinder
import org.slf4j.spi.MDCAdapter

/**
 * This class hides and serves as a substitute for the underlying logging
 * system's MDC implementation.
 *
 *
 *
 * If the underlying logging system offers MDC functionality, then SLF4J's MDC,
 * i.e. this class, will delegate to the underlying system's MDC. Note that at
 * this time, only two logging systems, namely log4j and logback, offer MDC
 * functionality. For java.util.logging which does not support MDC,
 * [BasicMDCAdapter] will be used. For other systems, i.e. slf4j-simple
 * and slf4j-nop, [NOPMDCAdapter] will be used.
 *
 *
 *
 * Thus, as a SLF4J user, you can take advantage of MDC in the presence of log4j,
 * logback, or java.util.logging, but without forcing these systems as
 * dependencies upon your users.
 *
 *
 *
 * For more information on MDC please see the [chapter on MDC](http://logback.qos.ch/manual/mdc.html) in the
 * logback manual.
 *
 *
 *
 * Please note that all methods in this class are static.
 *
 * @author Ceki Glc
 * @since 1.4.1
 */
object MDC {
    const val NULL_MDCA_URL = "http://www.slf4j.org/codes.html#null_MDCA"
    const val NO_STATIC_MDC_BINDER_URL = "http://www.slf4j.org/codes.html#no_static_mdc_binder"

    /**
     * Returns the MDCAdapter instance currently in use.
     *
     * @return the MDcAdapter instance currently in use.
     * @since 1.4.2
     */
    var mDCAdapter: MDCAdapter? = null

    /**
     * As of SLF4J version 1.7.14, StaticMDCBinder classes shipping in various bindings
     * come with a getSingleton() method. Previously only a public field called SINGLETON
     * was available.
     *
     * @return MDCAdapter
     * @throws NoClassDefFoundError in case no binding is available
     * @since 1.7.14
     */
    private fun bwCompatibleGetMDCAdapterFromBinder(): MDCAdapter = StaticMDCBinder.getSingleton().getMDCA()
//    try {
//        return StaticMDCBinder.getSingleton().getMDCA()
//    } catch (nsme:NoSuchMethodError){
//        // binding is probably a version of SLF4J older than 1.7.14
//        return StaticMDCBinder.SINGLETON.getMDCA()
//    }

    init {
        try {
            mDCAdapter = bwCompatibleGetMDCAdapterFromBinder()
//        } catch (ncde: NoClassDefFoundError) {
//            mDCAdapter = NOPMDCAdapter()
//            val msg: String = ncde.message
//            if (msg != null && msg.contains("StaticMDCBinder")) {
//                report("Failed to load class \"org.slf4j.impl.StaticMDCBinder\".")
//                report("Defaulting to no-operation MDCAdapter implementation.")
//                report("See " + NO_STATIC_MDC_BINDER_URL + " for further details.")
//            } else {
//                throw ncde
//            }
        } catch (e: Exception) {
            // we should never get here
            report("MDC binding unsuccessful.", e)
        }
    }

    /**
     * Put a diagnostic context value (the `val` parameter) as identified with the
     * `key` parameter into the current thread's diagnostic context map. The
     * `key` parameter cannot be null. The `val` parameter
     * can be null only if the underlying implementation supports it.
     *
     *
     *
     * This method delegates all work to the MDC of the underlying logging system.
     *
     * @param key non-null key
     * @param val value to put in the map
     *
     * @throws IllegalArgumentException
     * in case the "key" parameter is null
     */
    @Throws(IllegalArgumentException::class)
    fun put(key: String, `val`: String?) {
        requireNotNull(key) { "key parameter cannot be null" }
        checkNotNull(mDCAdapter) { "MDCAdapter cannot be null. See also $NULL_MDCA_URL" }
        mDCAdapter!!.put(key, `val`)
    }
    
    /**
     * Get the diagnostic context identified by the `key` parameter. The
     * `key` parameter cannot be null.
     *
     *
     *
     * This method delegates all work to the MDC of the underlying logging system.
     *
     * @param key
     * @return the string value identified by the `key` parameter.
     * @throws IllegalArgumentException
     * in case the "key" parameter is null
     */
    @Throws(IllegalArgumentException::class)
    operator fun get(key: String): String? {
        requireNotNull(key) { "key parameter cannot be null" }
        checkNotNull(mDCAdapter) { "MDCAdapter cannot be null. See also $NULL_MDCA_URL" }
        return mDCAdapter!![key]
    }

    /**
     * Remove the diagnostic context identified by the `key` parameter using
     * the underlying system's MDC implementation. The `key` parameter
     * cannot be null. This method does nothing if there is no previous value
     * associated with `key`.
     *
     * @param key
     * @throws IllegalArgumentException
     * in case the "key" parameter is null
     */
    @Throws(IllegalArgumentException::class)
    fun remove(key: String) {
        requireNotNull(key) { "key parameter cannot be null" }
        checkNotNull(mDCAdapter) { "MDCAdapter cannot be null. See also $NULL_MDCA_URL" }
        mDCAdapter!!.remove(key)
    }

    /**
     * Clear all entries in the MDC of the underlying implementation.
     */
    fun clear() {
        checkNotNull(mDCAdapter) { "MDCAdapter cannot be null. See also $NULL_MDCA_URL" }
        mDCAdapter!!.clear()
    }

    /**
     * Return a copy of the current thread's context map, with keys and values of
     * type String. Returned value may be null.
     *
     * @return A copy of the current thread's context map. May be null.
     * @since 1.5.1
     */
    fun getCopyOfContextMap(): Map<String?, String?>? {
        checkNotNull(mDCAdapter) { "MDCAdapter cannot be null. See also $NULL_MDCA_URL" }
        return mDCAdapter!!.getCopyOfContextMap()
    }

    /**
     * Set the current thread's context map by first clearing any existing map and
     * then copying the map passed as parameter. The context map passed as
     * parameter must only contain keys and values of type String.
     *
     * @param contextMap
     * must contain only keys and values of type String
     * @since 1.5.1
     */
    fun setContextMap(contextMap: Map<String?, String?>?) {
        checkNotNull(mDCAdapter) { "MDCAdapter cannot be null. See also $NULL_MDCA_URL" }
        mDCAdapter!!.setContextMap(contextMap)
    }
}