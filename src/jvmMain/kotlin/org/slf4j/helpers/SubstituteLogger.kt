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

import org.slf4j.Logger
import org.slf4j.Marker
import org.slf4j.event.EventRecodingLogger
import org.slf4j.event.SubstituteLoggingEvent
import org.slf4j.event.LoggingEvent
import java.util.*
import kotlin.jvm.Volatile

/**
 * A logger implementation which logs via a delegate logger. By default, the delegate is a
 * [NOPLogger]. However, a different delegate can be set at any time.
 *
 *
 * See also the [relevant
 * error code](http://www.slf4j.org/codes.html#substituteLogger) documentation.
 *
 * @author Chetan Mehrotra
 * @author Ceki Gulcu
 */
open class SubstituteLogger(
    override val name: String,
    eventQueue: Queue<SubstituteLoggingEvent>?,
    createdPostInitialization: Boolean
) : Logger {

    @Volatile
    private var _delegate: Logger? = null

    var isDelegateEventAware: Boolean? = null
        get() {
            if (field != null) return field
            try {
                logMethodCache = _delegate?.javaClass?.getMethod("log", LoggingEvent::class.java)
                field = true
            } catch (e: java.lang.NoSuchMethodException) {
                field = false
            }
            return field
        }
        private set

    private var logMethodCache: java.lang.reflect.Method? = null

    private var eventRecodingLogger: EventRecodingLogger? = null

    private val eventQueue: Queue<SubstituteLoggingEvent>?
    
    private val createdPostInitialization: Boolean

    init {
        this.eventQueue = eventQueue
        this.createdPostInitialization = createdPostInitialization
    }

    override val isTraceEnabled: Boolean
        get() = delegate().isTraceEnabled

    override fun trace(msg: String?) {
        delegate().trace(msg)
    }

    override fun trace(format: String?, arg: Any?) {
        delegate().trace(format, arg)
    }

    override fun trace(format: String?, arg1: Any?, arg2: Any?) {
        delegate().trace(format, arg1, arg2)
    }

    override fun trace(format: String?, vararg arguments: Any?) {
        delegate().trace(format, *arguments)
    }

    override fun trace(msg: String?, t: Throwable?) {
        delegate().trace(msg, t)
    }

    override fun isTraceEnabled(marker: Marker?): Boolean {
        return delegate().isTraceEnabled(marker)
    }

    override fun trace(marker: Marker?, msg: String?) {
        delegate().trace(marker, msg)
    }

    override fun trace(marker: Marker?, format: String?, arg: Any?) {
        delegate().trace(marker, format, arg)
    }

    override fun trace(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) {
        delegate().trace(marker, format, arg1, arg2)
    }

    override fun trace(marker: Marker?, format: String?, vararg arguments: Any?) {
        delegate().trace(marker, format, *arguments)
    }

    override fun trace(marker: Marker?, msg: String?, t: Throwable?) {
        delegate().trace(marker, msg, t)
    }

    override val isDebugEnabled: Boolean
        get() = delegate().isDebugEnabled

    override fun debug(msg: String?) {
        delegate().debug(msg)
    }

    override fun debug(format: String?, arg: Any?) {
        delegate().debug(format, arg)
    }

    override fun debug(format: String?, arg1: Any?, arg2: Any?) {
        delegate().debug(format, arg1, arg2)
    }

    override fun debug(format: String?, vararg arguments: Any?) {
        delegate().debug(format, *arguments)
    }

    override fun debug(msg: String?, t: Throwable?) {
        delegate().debug(msg, t)
    }

    override fun isDebugEnabled(marker: Marker?): Boolean {
        return delegate().isDebugEnabled(marker)
    }

    override fun debug(marker: Marker?, msg: String?) {
        delegate().debug(marker, msg)
    }

    override fun debug(marker: Marker?, format: String?, arg: Any?) {
        delegate().debug(marker, format, arg)
    }

    override fun debug(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) {
        delegate().debug(marker, format, arg1, arg2)
    }

    override fun debug(marker: Marker?, format: String?, vararg arguments: Any?) {
        delegate().debug(marker, format, *arguments)
    }

    override fun debug(marker: Marker?, msg: String?, t: Throwable?) {
        delegate().debug(marker, msg, t)
    }

    override val isInfoEnabled: Boolean
        get() = delegate().isInfoEnabled

    override fun info(msg: String?) {
        delegate().info(msg)
    }

    override fun info(format: String?, arg: Any?) {
        delegate().info(format, arg)
    }

    override fun info(format: String?, arg1: Any?, arg2: Any?) {
        delegate().info(format, arg1, arg2)
    }

    override fun info(format: String?, vararg arguments: Any?) {
        delegate().info(format, *arguments)
    }

    override fun info(msg: String?, t: Throwable?) {
        delegate().info(msg, t)
    }

    override fun isInfoEnabled(marker: Marker?): Boolean {
        return delegate().isInfoEnabled(marker)
    }

    override fun info(marker: Marker?, msg: String?) {
        delegate().info(marker, msg)
    }

    override fun info(marker: Marker?, format: String?, arg: Any?) {
        delegate().info(marker, format, arg)
    }

    override fun info(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) {
        delegate().info(marker, format, arg1, arg2)
    }

    override fun info(marker: Marker?, format: String?, vararg arguments: Any?) {
        delegate().info(marker, format, *arguments)
    }

    override fun info(marker: Marker?, msg: String?, t: Throwable?) {
        delegate().info(marker, msg, t)
    }

    override val isWarnEnabled: Boolean
        get() = delegate().isWarnEnabled

    override fun warn(msg: String?) {
        delegate().warn(msg)
    }

    override fun warn(format: String?, arg: Any?) {
        delegate().warn(format, arg)
    }

    override fun warn(format: String?, arg1: Any?, arg2: Any?) {
        delegate().warn(format, arg1, arg2)
    }

    override fun warn(format: String?, vararg arguments: Any?) {
        delegate().warn(format, *arguments)
    }

    override fun warn(msg: String?, t: Throwable?) {
        delegate().warn(msg, t)
    }

    override fun isWarnEnabled(marker: Marker?): Boolean {
        return delegate().isWarnEnabled(marker)
    }

    override fun warn(marker: Marker?, msg: String?) {
        delegate().warn(marker, msg)
    }

    override fun warn(marker: Marker?, format: String?, arg: Any?) {
        delegate().warn(marker, format, arg)
    }

    override fun warn(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) {
        delegate().warn(marker, format, arg1, arg2)
    }

    override fun warn(marker: Marker?, format: String?, vararg arguments: Any?) {
        delegate().warn(marker, format, *arguments)
    }

    override fun warn(marker: Marker?, msg: String?, t: Throwable?) {
        delegate().warn(marker, msg, t)
    }

    override val isErrorEnabled: Boolean
        get() = delegate().isErrorEnabled

    override fun error(msg: String?) {
        delegate().error(msg)
    }

    override fun error(format: String?, arg: Any?) {
        delegate().error(format, arg)
    }

    override fun error(format: String?, arg1: Any?, arg2: Any?) {
        delegate().error(format, arg1, arg2)
    }

    override fun error(format: String?, vararg arguments: Any?) {
        delegate().error(format, *arguments)
    }

    override fun error(msg: String?, t: Throwable?) {
        delegate().error(msg, t)
    }

    override fun isErrorEnabled(marker: Marker?): Boolean {
        return delegate().isErrorEnabled(marker)
    }

    override fun error(marker: Marker?, msg: String?) {
        delegate().error(marker, msg)
    }

    override fun error(marker: Marker?, format: String?, arg: Any?) {
        delegate().error(marker, format, arg)
    }

    override fun error(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) {
        delegate().error(marker, format, arg1, arg2)
    }

    override fun error(marker: Marker?, format: String?, vararg arguments: Any?) {
        delegate().error(marker, format, *arguments)
    }

    override fun error(marker: Marker?, msg: String?, t: Throwable?) {
        delegate().error(marker, msg, t)
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as SubstituteLogger
        return if (name != that.name) false else true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    /**
     * Return the delegate logger instance if set. Otherwise, return a [NOPLogger]
     * instance.
     */
    fun delegate(): Logger {
        val delegate = _delegate
        if (delegate != null) {
            return delegate
        }
        return if (createdPostInitialization) {
            NOPLogger.NOP_LOGGER
        } else {
            eventRecordingLogger
        }
    }

    private val eventRecordingLogger: Logger
        private get() {
            if (eventRecodingLogger == null) {
                eventRecodingLogger = EventRecodingLogger(this, eventQueue)
            }
            return eventRecodingLogger!!
        }

    /**
     * Typically called after the [org.slf4j.LoggerFactory] initialization phase is completed.
     * @param delegate
     */
    fun setDelegate(delegate: Logger?) {
        _delegate = delegate
    }

    fun log(event: LoggingEvent?) {
        if (isDelegateEventAware == true) {
            try {
                logMethodCache?.invoke(_delegate, event)
            } catch (e: java.lang.IllegalAccessException) {
            } catch (e: java.lang.IllegalArgumentException) {
            } catch (e: java.lang.reflect.InvocationTargetException) {
            }
        }
    }

    val isDelegateNull: Boolean
        get() = _delegate == null

    val isDelegateNOP: Boolean
        get() = _delegate is NOPLogger
}