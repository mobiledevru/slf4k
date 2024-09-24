package ru.mobiledev.slf4k.impl

import org.slf4j.event.Level
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
) : SimpleLogger(name, isTraceEnabled, isDebugEnabled, isInfoEnabled, isWarnEnabled, isErrorEnabled) {

//    private val dateFormatter = NSDateFormatter().apply { dateFormat = "yyyy-MM-dd HH:mm:ss.SSS" }
//    private fun ts() = dateFormatter.stringFromDate(NSDate())

    override fun out(level: Level, message: String) {
        val prefix = "[$level]\t[$name]\t"
        NSLog(prefix + message)
    }

}