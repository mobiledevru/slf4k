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

import kotlin.jvm.JvmField

/**
 * A direct NOP (no operation) implementation of [Logger].
 *
 * @author Ceki G&uuml;lc&uuml;
 */
class NOPLogger
/**
 * There is no point in creating multiple instances of NOPLogger,
 * except by derived classes, hence the protected  access for the constructor.
 */
protected constructor() : MarkerIgnoringBase() {
    /**
     * Always returns the string value "NOP".
     */
    override var name: String = "NOP"


    /**
     * Always returns false.
     * @return always false
     */
    override val isTraceEnabled: Boolean
        get() = false

    /** A NOP implementation.  */
    override fun trace(msg: String) {
        // NOP
    }

    /** A NOP implementation.   */
    override fun trace(format: String, arg: Any?) {
        // NOP
    }

    /** A NOP implementation.   */
    override fun trace(format: String, arg1: Any?, arg2: Any?) {
        // NOP
    }

    /** A NOP implementation.   */
    override fun trace(format: String, vararg arguments: Any?) {
        // NOP
    }

    /** A NOP implementation.  */
    override fun trace(msg: String, t: Throwable?) {
        // NOP
    }

    /**
     * Always returns false.
     * @return always false
     */
    override val isDebugEnabled: Boolean
        get() = false

    /** A NOP implementation.  */
    override fun debug(msg: String) {
        // NOP
    }

    /** A NOP implementation.   */
    override fun debug(format: String, arg: Any?) {
        // NOP
    }

    /** A NOP implementation.   */
    override fun debug(format: String, arg1: Any?, arg2: Any?) {
        // NOP
    }

    /** A NOP implementation.   */
    override fun debug(format: String, vararg arguments: Any?) {
        // NOP
    }

    /** A NOP implementation.  */
    override fun debug(msg: String, t: Throwable?) {
        // NOP
    }// NOP

    /**
     * Always returns false.
     * @return always false
     */
    override val isInfoEnabled: Boolean
        get() =// NOP
            false

    /** A NOP implementation.  */
    override fun info(msg: String) {
        // NOP
    }

    /** A NOP implementation.  */
    override fun info(format: String, arg: Any?) {
        // NOP
    }

    /** A NOP implementation.  */
    override fun info(format: String, arg1: Any?, arg2: Any?) {
        // NOP
    }

    /** A NOP implementation.   */
    override fun info(format: String, vararg arguments: Any?) {
        // NOP
    }

    /** A NOP implementation.  */
    override fun info(msg: String, t: Throwable?) {
        // NOP
    }

    /**
     * Always returns false.
     * @return always false
     */
    override val isWarnEnabled: Boolean
        get() = false

    /** A NOP implementation.  */
    override fun warn(msg: String) {
        // NOP
    }

    /** A NOP implementation.  */
    override fun warn(format: String, arg: Any?) {
        // NOP
    }

    /** A NOP implementation.  */
    override fun warn(format: String, arg1: Any?, arg2: Any?) {
        // NOP
    }

    /** A NOP implementation.   */
    override fun warn(format: String, vararg arguments: Any?) {
        // NOP
    }

    /** A NOP implementation.  */
    override fun warn(msg: String, t: Throwable?) {
        // NOP
    }

    /** A NOP implementation.  */
    override val isErrorEnabled: Boolean
        get() = false

    /** A NOP implementation.  */
    override fun error(msg: String) {
        // NOP
    }

    /** A NOP implementation.  */
    override fun error(format: String, arg: Any?) {
        // NOP
    }

    /** A NOP implementation.  */
    override fun error(format: String, arg1: Any?, arg2: Any?) {
        // NOP
    }

    /** A NOP implementation.   */
    override fun error(format: String, vararg arguments: Any?) {
        // NOP
    }

    /** A NOP implementation.  */
    override fun error(msg: String, t: Throwable?) {
        // NOP
    }

    companion object {
        private const val serialVersionUID = -517220405410904473L

        /**
         * The unique instance of NOPLogger.
         */
        @JvmField
        val NOP_LOGGER = NOPLogger()
    }
}