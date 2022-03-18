package org.slf4j.impl

import org.slf4j.ILoggerFactory
import org.slf4j.Logger
import ru.mobiledev.slf4k.impl.NSLogger

/**
 *
 * The binding of {@link LoggerFactory} class with an actual instance of
 * {@link ILoggerFactory} is performed using information returned by this class.
 *
 * @author Ceki G&uuml;lc&uuml;</a>
 * @author Golubev Dmitrii
 */
actual class StaticLoggerBinder {

    actual fun getLoggerFactory(): ILoggerFactory = object : ILoggerFactory {
        override fun getLogger(name: String): Logger = NSLogger(name)
    }

    actual fun getLoggerFactoryClassStr(): String = NSLogger::class.qualifiedName.toString()

    companion object {
        private val INSTANCE = StaticLoggerBinder()
        fun getSingleton(): StaticLoggerBinder = INSTANCE
    }
}