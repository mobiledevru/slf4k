package ru.mobiledev.slf4k.impl

import org.slf4j.helpers.MarkerIgnoringBase
import org.slf4j.helpers.MessageFormatter
import platform.Foundation.NSLog

/**
 * Simple implementation of logger based on NSLog
 * 
 * @author Golubev Dmitrii
 */
class NSLogger(
    override var name: String,
    override val isTraceEnabled: Boolean = true,
    override val isDebugEnabled: Boolean = true,
    override val isInfoEnabled: Boolean = true,
    override val isWarnEnabled: Boolean = true,
    override val isErrorEnabled: Boolean = true,
) : MarkerIgnoringBase() {

//    private val dateFormatter = NSDateFormatter().apply { dateFormat = "yyyy-MM-dd HH:mm:ss.SSS" }
//    private fun ts() = dateFormatter.stringFromDate(NSDate())

    override fun trace(msg: String?) {
        val message = MessageFormatter.arrayFormat(msg, emptyArray()).message
        if (isTraceEnabled) { msg?.apply { NSLog("TRACE: $message") } }
    }

    override fun trace(format: String?, arg: Any?) {
        val message = MessageFormatter.arrayFormat(format, arrayOf(arg)).message
        if (isTraceEnabled) { format?.apply { NSLog("TRACE: $message") } }
    }

    override fun trace(format: String?, arg1: Any?, arg2: Any?) {
        val message = MessageFormatter.arrayFormat(format, arrayOf(arg1, arg2)).message
        if (isTraceEnabled) { format?.apply { NSLog("TRACE: $message") } }
    }

    override fun trace(format: String?, vararg arguments: Any?) {
//        val arg: Array<String> = arguments.map { it.toString() }.toTypedArray()/*.toCStringArray()*/
//        if (isTraceEnabled) { format?.apply { NSLog("TRACE: $message") } }
        val message = MessageFormatter.arrayFormat(format, arrayOf(*arguments)).message
        if (isTraceEnabled) { format?.apply { NSLog("TRACE: $message") } }
    }

    override fun trace(msg: String?, t: Throwable?) {
        val message = MessageFormatter.arrayFormat(msg, emptyArray(), t).message
        if (isTraceEnabled) { msg?.apply { NSLog("TRACE: $message $t") }; t?.printStackTrace() }
    }

    override fun debug(msg: String?) {
        val message = MessageFormatter.arrayFormat(msg, emptyArray()).message
        if (isDebugEnabled) { msg?.apply { NSLog("DEBUG: $message") } }
    }

    override fun debug(format: String?, arg: Any?) {
        val message = MessageFormatter.arrayFormat(format, arrayOf(arg)).message
        if (isDebugEnabled) { format?.apply { NSLog("DEBUG: $message") } }
    }

    override fun debug(format: String?, arg1: Any?, arg2: Any?) {
        val message = MessageFormatter.arrayFormat(format, arrayOf(arg1, arg2)).message
        if (isDebugEnabled) { format?.apply { NSLog("DEBUG: $message") } }
    }

    override fun debug(format: String?, vararg arguments: Any?) {
        val message = MessageFormatter.arrayFormat(format, arrayOf(arrayOf(*arguments))).message
        if (isDebugEnabled) { format?.apply { NSLog("DEBUG: $message") } }
    }

    override fun debug(msg: String?, t: Throwable?) {
        val message = MessageFormatter.arrayFormat(msg, emptyArray()).message
        if (isDebugEnabled) { msg?.apply { NSLog("DEBUG: $message $t") }; t?.printStackTrace() }
    }

    override fun info(msg: String?) {
        val message = MessageFormatter.arrayFormat(msg, emptyArray()).message
        if (isInfoEnabled) { msg?.apply { NSLog("INFO: $message") } }
    }

    override fun info(format: String?, arg: Any?) {
        val message = MessageFormatter.arrayFormat(format, arrayOf(arg)).message
        if (isInfoEnabled) { format?.apply { NSLog("INFO: $message") } }
    }

    override fun info(format: String?, arg1: Any?, arg2: Any?) {
        val message = MessageFormatter.arrayFormat(format, arrayOf(arg1, arg2)).message
        if (isInfoEnabled) { format?.apply { NSLog("INFO: $message") } }
    }

    override fun info(format: String?, vararg arguments: Any?) {
        val message = MessageFormatter.arrayFormat(format, arrayOf(arrayOf(*arguments))).message
        if (isInfoEnabled) { format?.apply { NSLog("INFO: $message") } }
    }

    override fun info(msg: String?, t: Throwable?) {
        val message = MessageFormatter.arrayFormat(msg, emptyArray()).message
        if (isInfoEnabled) { msg?.apply { NSLog("INFO: $message $t") }; t?.printStackTrace() }
    }

    override fun warn(msg: String?) {
        val message = MessageFormatter.arrayFormat(msg, emptyArray()).message
        if (isWarnEnabled) { msg?.apply { NSLog("WARN: $message") } }
    }

    override fun warn(format: String?, arg: Any?) {
        val message = MessageFormatter.arrayFormat(format, arrayOf(arg)).message
        if (isWarnEnabled) { format?.apply { NSLog("WARN: $message") } }
    }

    override fun warn(format: String?, arg1: Any?, arg2: Any?) {
        val message = MessageFormatter.arrayFormat(format, arrayOf(arg1, arg2)).message
        if (isWarnEnabled) { format?.apply { NSLog("WARN: $message") } }
    }

    override fun warn(format: String?, vararg arguments: Any?) {
        val message = MessageFormatter.arrayFormat(format, arrayOf(arrayOf(*arguments))).message
        if (isWarnEnabled) { format?.apply { NSLog("WARN: $message") } }
    }

    override fun warn(msg: String?, t: Throwable?) {
        val message = MessageFormatter.arrayFormat(msg, emptyArray()).message
        if (isWarnEnabled) { msg?.apply { NSLog("WARN: $message $t") }; t?.printStackTrace() }
    }

    override fun error(msg: String?) {
        val message = MessageFormatter.arrayFormat(msg, emptyArray()).message
        if (isErrorEnabled) { msg?.apply { NSLog("ERROR: $message") } }
    }

    override fun error(format: String?, arg: Any?) {
        val message = MessageFormatter.arrayFormat(format, arrayOf(arg)).message
        if (isErrorEnabled) { format?.apply { NSLog("ERROR: $message") } }
    }

    override fun error(format: String?, arg1: Any?, arg2: Any?) {
        val message = MessageFormatter.arrayFormat(format, arrayOf(arg1, arg2)).message
        if (isErrorEnabled) { format?.apply { NSLog("ERROR: $message") } }
    }

    override fun error(format: String?, vararg arguments: Any?) {
        val message = MessageFormatter.arrayFormat(format, arrayOf(*arguments)).message
        if (isErrorEnabled) { format?.apply { NSLog("ERROR: $message") } }
    }

    override fun error(msg: String?, t: Throwable?) {
        val message = MessageFormatter.arrayFormat(msg, emptyArray(), t).message
        if (isErrorEnabled) { msg?.apply { NSLog("ERROR: $message $t") }; t?.printStackTrace() }
    }
}