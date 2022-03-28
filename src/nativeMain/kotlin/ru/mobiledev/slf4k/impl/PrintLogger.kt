package ru.mobiledev.slf4k.impl

import org.slf4j.event.Level

/**
 * Simple implementation of logger based on println
 *
 * @author Golubev Dmitrii
 */
class PrintLogger(
    override var name: String,
    override val isTraceEnabled: Boolean = true,
    override val isDebugEnabled: Boolean = true,
    override val isInfoEnabled: Boolean = true,
    override val isWarnEnabled: Boolean = true,
    override val isErrorEnabled: Boolean = true,
) : SimpleLogger(name, isTraceEnabled, isDebugEnabled, isInfoEnabled, isWarnEnabled, isErrorEnabled) {

    override fun out(level: Level, message: String) {
        val prefix = "[$level]\t "
        println(prefix + message)
    }
}