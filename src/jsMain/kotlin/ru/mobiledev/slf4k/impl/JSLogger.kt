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
        val prefix = (if (hasLevelPrefix) "[$level]\t" else "") + "[$name]\t"
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
        private val isHeadlessBrowser = isBrowser && (userAgent?.contains("Headless") ?: true)
        private val isNode = userAgent == null || userAgent == "null"

        private val hasLevelPrefix = !isBrowser || isHeadlessBrowser || isNode
        private val suffix: String = "" // if (!isBrowser || isHeadlessBrowser) "\n" else ""
    }
}