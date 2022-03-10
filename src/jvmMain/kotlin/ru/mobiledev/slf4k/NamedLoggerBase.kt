package ru.mobiledev.slf4k

import ru.mobiledev.slf4k.helpers.NamedLoggerBase
import java.io.ObjectStreamException


/**
 * Replace this instance with a homonymous (same name) logger returned
 * by LoggerFactory. Note that this method is only called during
 * deserialization.
 *
 *
 *
 * This approach will work well if the desired ILoggerFactory is the one
 * references by LoggerFactory. However, if the user manages its logger hierarchy
 * through a different (non-static) mechanism, e.g. dependency injection, then
 * this approach would be mostly counterproductive.
 *
 * @return logger with same name as returned by LoggerFactory
 * @throws ObjectStreamException
 */
@Throws(ObjectStreamException::class)
fun NamedLoggerBase.readResolve(): Any {
    // using getName() instead of this.name works even for
    // NOPLogger
    return LoggerFactory.getLogger(name ?: throw RuntimeException("Null-valued name arguments are considered invalid."))
}
