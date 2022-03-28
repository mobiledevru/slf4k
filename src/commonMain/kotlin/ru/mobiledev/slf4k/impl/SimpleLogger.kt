package ru.mobiledev.slf4k.impl

import org.slf4j.event.Level
import org.slf4j.helpers.MarkerIgnoringBase
import org.slf4j.helpers.MessageFormatter

/**
 * Abstract simple implementation of logger
 *
 * @author Golubev Dmitrii
 */
abstract class SimpleLogger(
    override var name: String,
    override val isTraceEnabled: Boolean = true,
    override val isDebugEnabled: Boolean = true,
    override val isInfoEnabled: Boolean = true,
    override val isWarnEnabled: Boolean = true,
    override val isErrorEnabled: Boolean = true,
) : MarkerIgnoringBase() {

    protected abstract fun out(level: Level, message: String)

    override fun trace(msg: String?) {
        val message = MessageFormatter.arrayFormat(msg, emptyArray()).message
        if (isTraceEnabled) { msg?.apply { out(Level.INFO, "$message") } }
    }

    override fun trace(format: String?, arg: Any?) {
        val message = MessageFormatter.arrayFormat(format, arrayOf(arg)).message
        if (isTraceEnabled) { format?.apply { out(Level.INFO, "$message") } }
    }

    override fun trace(format: String?, arg1: Any?, arg2: Any?) {
        val message = MessageFormatter.arrayFormat(format, arrayOf(arg1, arg2)).message
        if (isTraceEnabled) { format?.apply { out(Level.INFO, "$message") } }
    }

    override fun trace(format: String?, vararg arguments: Any?) {
        val message = MessageFormatter.arrayFormat(format, arrayOf(*arguments)).message
        if (isTraceEnabled) { format?.apply { out(Level.INFO, "$message") } }
    }

    override fun trace(msg: String?, t: Throwable?) {
        val message = MessageFormatter.arrayFormat(msg, emptyArray(), t).message
        if (isTraceEnabled) { msg?.apply { out(Level.INFO, "$message $t") }; t?.printStackTrace() }
    }

    override fun debug(msg: String?) {
        val message = MessageFormatter.arrayFormat(msg, emptyArray()).message
        if (isDebugEnabled) { msg?.apply { out(Level.INFO, "$message") } }
    }

    override fun debug(format: String?, arg: Any?) {
        val message = MessageFormatter.arrayFormat(format, arrayOf(arg)).message
        if (isDebugEnabled) { format?.apply { out(Level.INFO, "$message") } }
    }

    override fun debug(format: String?, arg1: Any?, arg2: Any?) {
        val message = MessageFormatter.arrayFormat(format, arrayOf(arg1, arg2)).message
        if (isDebugEnabled) { format?.apply { out(Level.INFO, "$message") } }
    }

    override fun debug(format: String?, vararg arguments: Any?) {
        val message = MessageFormatter.arrayFormat(format, arrayOf(arrayOf(*arguments))).message
        if (isDebugEnabled) { format?.apply { out(Level.INFO, "$message") } }
    }

    override fun debug(msg: String?, t: Throwable?) {
        val message = MessageFormatter.arrayFormat(msg, emptyArray()).message
        if (isDebugEnabled) { msg?.apply { out(Level.INFO, "$message $t") }; t?.printStackTrace() }
    }

    override fun info(msg: String?) {
        val message = MessageFormatter.arrayFormat(msg, emptyArray()).message
        if (isInfoEnabled) { msg?.apply { out(Level.INFO, "$message") } }
    }

    override fun info(format: String?, arg: Any?) {
        val message = MessageFormatter.arrayFormat(format, arrayOf(arg)).message
        if (isInfoEnabled) { format?.apply { out(Level.INFO, "$message") } }
    }

    override fun info(format: String?, arg1: Any?, arg2: Any?) {
        val message = MessageFormatter.arrayFormat(format, arrayOf(arg1, arg2)).message
        if (isInfoEnabled) { format?.apply { out(Level.INFO, "$message") } }
    }

    override fun info(format: String?, vararg arguments: Any?) {
        val message = MessageFormatter.arrayFormat(format, arrayOf(arrayOf(*arguments))).message
        if (isInfoEnabled) { format?.apply { out(Level.INFO, "$message") } }
    }

    override fun info(msg: String?, t: Throwable?) {
        val message = MessageFormatter.arrayFormat(msg, emptyArray()).message
        if (isInfoEnabled) { msg?.apply { out(Level.INFO, "$message $t") }; t?.printStackTrace() }
    }

    override fun warn(msg: String?) {
        val message = MessageFormatter.arrayFormat(msg, emptyArray()).message
        if (isWarnEnabled) { msg?.apply { out(Level.WARN, "$message") } }
    }

    override fun warn(format: String?, arg: Any?) {
        val message = MessageFormatter.arrayFormat(format, arrayOf(arg)).message
        if (isWarnEnabled) { format?.apply { out(Level.WARN, "$message") } }
    }

    override fun warn(format: String?, arg1: Any?, arg2: Any?) {
        val message = MessageFormatter.arrayFormat(format, arrayOf(arg1, arg2)).message
        if (isWarnEnabled) { format?.apply { out(Level.WARN, "$message") } }
    }

    override fun warn(format: String?, vararg arguments: Any?) {
        val message = MessageFormatter.arrayFormat(format, arrayOf(arrayOf(*arguments))).message
        if (isWarnEnabled) { format?.apply { out(Level.WARN, "$message") } }
    }

    override fun warn(msg: String?, t: Throwable?) {
        val message = MessageFormatter.arrayFormat(msg, emptyArray()).message
        if (isWarnEnabled) { msg?.apply { out(Level.WARN, "$message $t") }; t?.printStackTrace() }
    }

    override fun error(msg: String?) {
        val message = MessageFormatter.arrayFormat(msg, emptyArray()).message
        if (isErrorEnabled) { msg?.apply { out(Level.ERROR, "$message") } }
    }

    override fun error(format: String?, arg: Any?) {
        val message = MessageFormatter.arrayFormat(format, arrayOf(arg)).message
        if (isErrorEnabled) { format?.apply { out(Level.ERROR, "$message") } }
    }

    override fun error(format: String?, arg1: Any?, arg2: Any?) {
        val message = MessageFormatter.arrayFormat(format, arrayOf(arg1, arg2)).message
        if (isErrorEnabled) { format?.apply { out(Level.ERROR, "$message") } }
    }

    override fun error(format: String?, vararg arguments: Any?) {
        val message = MessageFormatter.arrayFormat(format, arrayOf(*arguments)).message
        if (isErrorEnabled) { format?.apply { out(Level.ERROR, "$message") } }
    }

    override fun error(msg: String?, t: Throwable?) {
        val message = MessageFormatter.arrayFormat(msg, emptyArray(), t).message
        if (isErrorEnabled) { msg?.apply { out(Level.ERROR, "$message $t") }; t?.printStackTrace() }
    }
}