package ru.mobiledev.slf4k

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.js.Date
import kotlin.reflect.KClass

actual interface SerializableAdapter

actual object Adapter {

    actual val platform: String
        get() = "JS"

    actual fun timeStamp(): Long {
        return Date.now().toLong()
    }

    actual fun className(obj: Any): String = this::class.simpleName ?: "undefined"
}

actual fun LoggerFactory.getLogger(clazz: KClass<*>): Logger = getLogger(clazz.simpleName ?: Logger.ROOT_LOGGER_NAME)