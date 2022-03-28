package ru.mobiledev.slf4k.impl

import org.slf4j.event.Level

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
) : SimpleLogger(name, isTraceEnabled, isDebugEnabled, isInfoEnabled, isWarnEnabled, isErrorEnabled) {

    override fun out(level: Level, message: String) {
        val prefix = if (hasPrefix) "[$level]\t " else ""
        when (level) {
            Level.ERROR -> console.error(prefix + message + suffix)
            Level.WARN -> console.warn(prefix + message + suffix)
            Level.INFO -> console.info(prefix + message + suffix)
            Level.DEBUG -> js("console.debug(prefix + message + suffix)")
            Level.TRACE -> js("console.trace(prefix + message + suffix)")
        }
    }

    companion object {
        private val isBrowser = js("typeof window !== \"undefined\" && typeof window.document !== \"undefined\"") == true
        private val userAgent = js("(typeof navigator !== \"undefined\") ? navigator['userAgent'] : null") as String?
        private val isHeadless = userAgent?.contains("Headless") ?: true

        private val hasPrefix = !isBrowser || isHeadless
        private val suffix: String = if (!isBrowser || isHeadless) "\n" else ""
    }
}