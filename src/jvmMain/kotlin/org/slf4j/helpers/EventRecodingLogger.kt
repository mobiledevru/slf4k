package org.slf4j.helpers

import ru.mobiledev.slf4k.Adapter
import org.slf4j.Logger
import org.slf4j.Marker
import org.slf4j.SubstituteLogger
import org.slf4j.event.Level
import org.slf4j.event.SubstituteLoggingEvent
import java.util.*

/**
 *
 * This class is used to record events during the initialization phase of the
 * underlying logging framework. It is called by [SubstituteLogger].
 *
 *
 * @author Ceki G&uuml;lc&uuml;
 * @author Wessel van Norel
 */
class EventRecodingLogger(logger: SubstituteLogger, eventQueue: AbstractQueue<SubstituteLoggingEvent>) : Logger {

    override var name: String = logger.name
    var logger: SubstituteLogger
    var eventQueue: AbstractQueue<SubstituteLoggingEvent>

    init {
        this.logger = logger
        this.eventQueue = eventQueue
    }

    override val isTraceEnabled: Boolean
        get() = RECORD_ALL_EVENTS
    override val isDebugEnabled: Boolean
        get() = RECORD_ALL_EVENTS
    override val isInfoEnabled: Boolean
        get() = RECORD_ALL_EVENTS
    override val isWarnEnabled: Boolean
        get() = RECORD_ALL_EVENTS
    override val isErrorEnabled: Boolean
        get() = RECORD_ALL_EVENTS

    override fun trace(msg: String) {
        recordEvent_0Args(Level.TRACE, null, msg, null)
    }

    override fun trace(format: String, arg: Any?) {
        recordEvent_1Args(Level.TRACE, null, format, arg)
    }

    override fun trace(format: String, arg1: Any?, arg2: Any?) {
        recordEvent2Args(Level.TRACE, null, format, arg1, arg2)
    }

    override fun trace(format: String, vararg arguments: Any?) {
        recordEventArgArray(Level.TRACE, null, format, arguments)
    }

    override fun trace(msg: String, t: Throwable?) {
        recordEvent_0Args(Level.TRACE, null, msg, t)
    }

    override fun isTraceEnabled(marker: Marker?): Boolean {
        return isTraceEnabled
    }

    override fun trace(marker: Marker?, msg: String) {
        recordEvent_0Args(Level.TRACE, marker, msg, null)
    }

    override fun trace(marker: Marker?, format: String, arg: Any?) {
        recordEvent_1Args(Level.TRACE, marker, format, arg)
    }

    override fun trace(marker: Marker?, format: String, arg1: Any?, arg2: Any?) {
        recordEvent2Args(Level.TRACE, marker, format, arg1, arg2)
    }

    override fun trace(marker: Marker?, format: String, vararg arguments: Any?) {
        recordEventArgArray(Level.TRACE, marker, format, arguments)
    }

    override fun trace(marker: Marker?, msg: String, t: Throwable?) {
        recordEvent_0Args(Level.TRACE, marker, msg, t)
    }

    override fun debug(msg: String) {
        recordEvent_0Args(Level.DEBUG, null, msg, null)
    }

    override fun debug(format: String, arg: Any?) {
        recordEvent_1Args(Level.DEBUG, null, format, arg)
    }

    override fun debug(format: String, arg1: Any?, arg2: Any?) {
        recordEvent2Args(Level.DEBUG, null, format, arg1, arg2)
    }

    override fun debug(format: String, vararg arguments: Any?) {
        recordEventArgArray(Level.DEBUG, null, format, arguments)
    }

    override fun debug(msg: String, t: Throwable?) {
        recordEvent_0Args(Level.DEBUG, null, msg, t)
    }

    override fun isDebugEnabled(marker: Marker?): Boolean {
        return isDebugEnabled
    }

    override fun debug(marker: Marker?, msg: String) {
        recordEvent_0Args(Level.DEBUG, marker, msg, null)
    }

    override fun debug(marker: Marker?, format: String, arg: Any?) {
        recordEvent_1Args(Level.DEBUG, marker, format, arg)
    }

    override fun debug(marker: Marker?, format: String, arg1: Any?, arg2: Any?) {
        recordEvent2Args(Level.DEBUG, marker, format, arg1, arg2)
    }

    override fun debug(marker: Marker?, format: String, vararg arguments: Any?) {
        recordEventArgArray(Level.DEBUG, marker, format, arguments)
    }

    override fun debug(marker: Marker?, msg: String, t: Throwable?) {
        recordEvent_0Args(Level.DEBUG, marker, msg, t)
    }

    override fun info(msg: String) {
        recordEvent_0Args(Level.INFO, null, msg, null)
    }

    override fun info(format: String, arg: Any?) {
        recordEvent_1Args(Level.INFO, null, format, arg)
    }

    override fun info(format: String, arg1: Any?, arg2: Any?) {
        recordEvent2Args(Level.INFO, null, format, arg1, arg2)
    }

    override fun info(format: String, vararg arguments: Any?) {
        recordEventArgArray(Level.INFO, null, format, arguments)
    }

    override fun info(msg: String, t: Throwable?) {
        recordEvent_0Args(Level.INFO, null, msg, t)
    }

    override fun isInfoEnabled(marker: Marker?): Boolean {
        return isInfoEnabled
    }

    override fun info(marker: Marker?, msg: String) {
        recordEvent_0Args(Level.INFO, marker, msg, null)
    }

    override fun info(marker: Marker?, format: String, arg: Any?) {
        recordEvent_1Args(Level.INFO, marker, format, arg)
    }

    override fun info(marker: Marker?, format: String, arg1: Any?, arg2: Any?) {
        recordEvent2Args(Level.INFO, marker, format, arg1, arg2)
    }

    override fun info(marker: Marker?, format: String, vararg arguments: Any?) {
        recordEventArgArray(Level.INFO, marker, format, arguments)
    }

    override fun info(marker: Marker?, msg: String, t: Throwable?) {
        recordEvent_0Args(Level.INFO, marker, msg, t)
    }

    override fun warn(msg: String) {
        recordEvent_0Args(Level.WARN, null, msg, null)
    }

    override fun warn(format: String, arg: Any?) {
        recordEvent_1Args(Level.WARN, null, format, arg)
    }

    override fun warn(format: String, arg1: Any?, arg2: Any?) {
        recordEvent2Args(Level.WARN, null, format, arg1, arg2)
    }

    override fun warn(format: String, vararg arguments: Any?) {
        recordEventArgArray(Level.WARN, null, format, arguments)
    }

    override fun warn(msg: String, t: Throwable?) {
        recordEvent_0Args(Level.WARN, null, msg, t)
    }

    override fun isWarnEnabled(marker: Marker?): Boolean {
        return isWarnEnabled
    }

    override fun warn(marker: Marker?, msg: String) {
        recordEvent_0Args(Level.WARN, marker, msg, null)
    }

    override fun warn(marker: Marker?, format: String, arg: Any?) {
        recordEvent_1Args(Level.WARN, marker, format, arg)
    }

    override fun warn(marker: Marker?, format: String, arg1: Any?, arg2: Any?) {
        recordEvent2Args(Level.WARN, marker, format, arg1, arg2)
    }

    override fun warn(marker: Marker?, format: String, vararg arguments: Any?) {
        recordEventArgArray(Level.WARN, marker, format, arguments)
    }

    override fun warn(marker: Marker?, msg: String, t: Throwable?) {
        recordEvent_0Args(Level.WARN, marker, msg, t)
    }

    override fun error(msg: String) {
        recordEvent_0Args(Level.ERROR, null, msg, null)
    }

    override fun error(format: String, arg: Any?) {
        recordEvent_1Args(Level.ERROR, null, format, arg)
    }

    override fun error(format: String, arg1: Any?, arg2: Any?) {
        recordEvent2Args(Level.ERROR, null, format, arg1, arg2)
    }

    override fun error(format: String, vararg arguments: Any?) {
        recordEventArgArray(Level.ERROR, null, format, arguments)
    }

    override fun error(msg: String, t: Throwable?) {
        recordEvent_0Args(Level.ERROR, null, msg, t)
    }

    override fun isErrorEnabled(marker: Marker?): Boolean {
        return isErrorEnabled
    }

    override fun error(marker: Marker?, msg: String) {
        recordEvent_0Args(Level.ERROR, marker, msg, null)
    }

    override fun error(marker: Marker?, format: String, arg: Any?) {
        recordEvent_1Args(Level.ERROR, marker, format, arg)
    }

    override fun error(marker: Marker?, format: String, arg1: Any?, arg2: Any?) {
        recordEvent2Args(Level.ERROR, marker, format, arg1, arg2)
    }

    override fun error(marker: Marker?, format: String, vararg arguments: Any?) {
        recordEventArgArray(Level.ERROR, marker, format, arguments)
    }

    override fun error(marker: Marker?, msg: String, t: Throwable?) {
        recordEvent_0Args(Level.ERROR, marker, msg, t)
    }

    private fun recordEvent_0Args(level: Level, marker: Marker?, msg: String, t: Throwable?) {
        recordEvent(level, marker, msg, null, t)
    }

    private fun recordEvent_1Args(level: Level, marker: Marker?, msg: String, arg1: Any?) {
        recordEvent(level, marker, msg, arrayOf(arg1), null)
    }

    private fun recordEvent2Args(level: Level, marker: Marker?, msg: String, arg1: Any?, arg2: Any?) {
        if (arg2 is Throwable) {
            recordEvent(level, marker, msg, arrayOf(arg1), arg2)
        } else {
            recordEvent(level, marker, msg, arrayOf(arg1, arg2), null)
        }
    }

    private fun recordEventArgArray(level: Level, marker: Marker?, msg: String, args: Array<out Any?>?) {
        val throwableCandidate: Throwable? = MessageFormatter.getThrowableCandidate(args)
        if (throwableCandidate != null) {
            val trimmedCopy: Array<*> = MessageFormatter.trimmedCopy(args)
            recordEvent(level, marker, msg, trimmedCopy, throwableCandidate)
        } else {
            recordEvent(level, marker, msg, args, null)
        }
    }

    // WARNING: this method assumes that any throwable is properly extracted
    private fun recordEvent(level: Level, marker: Marker?, msg: String, args: Array<out Any?>?, throwable: Throwable?) {
        val loggingEvent = SubstituteLoggingEvent()
        loggingEvent.timeStamp = Adapter.timeStamp()
        loggingEvent.level = level
        loggingEvent.setLogger(logger)
        loggingEvent.loggerName = name
        loggingEvent.marker = marker
        loggingEvent.message = msg
        loggingEvent.threadName = Adapter.threadName()
        loggingEvent.argumentArray = args
        loggingEvent.throwable = throwable
        eventQueue.add(loggingEvent)
    }

    companion object {
        // as an event recording logger we have no choice but to record all events
        const val RECORD_ALL_EVENTS = true
    }
}