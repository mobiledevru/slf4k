package ru.mobiledev.slf4k.impl

import org.slf4j.event.Level
import org.slf4j.helpers.MarkerIgnoringBase
import org.slf4j.helpers.MessageFormatter

/**
 * Simple implementation of logger based on Console
 *
 * @author Golubev Dmitrii
 */
class JSLogger(
    override var name: String,
    override val isTraceEnabled: Boolean = true,
    override val isDebugEnabled: Boolean = true,
    override val isInfoEnabled: Boolean = true,
    override val isWarnEnabled: Boolean = true,
    override val isErrorEnabled: Boolean = true,
) : MarkerIgnoringBase() {

    private fun out(level: Level, message: String) {
        val prefix = if (hasPrefix) "[$level]\t " else ""
        when (level) {
            Level.ERROR -> console.error(prefix + message + suffix)
            Level.WARN -> console.warn(prefix + message + suffix)
            Level.INFO -> console.info(prefix + message + suffix)
            Level.DEBUG -> js("console.debug(prefix + message + suffix)")
            Level.TRACE -> js("console.trace(prefix + message + suffix)")
        }
    }

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

    companion object {
        private val isBrowser = js("typeof window !== \"undefined\" && typeof window.document !== \"undefined\"") == true
        private val userAgent = js("navigator.userAgent") as String?
        private val isHeadless = userAgent?.contains("Headless") ?: true

        private val hasPrefix = !isBrowser || isHeadless
        private val suffix: String = if (!isBrowser || isHeadless) "\n" else ""
    }
}