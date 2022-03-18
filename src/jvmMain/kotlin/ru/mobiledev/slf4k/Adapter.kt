package ru.mobiledev.slf4k

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

actual interface SerializableAdapter : java.io.Serializable

actual object Adapter {
    actual val platform: String
        get() = "JVM"

    actual fun timeStamp(): Long {
        return System.currentTimeMillis()
//        return kotlin.system.getTimeMillis()
    }

    actual fun className(obj: Any): String = obj.javaClass.name
}

actual fun LoggerFactory.getLogger(clazz: KClass<*>): Logger = getLogger(clazz.java.javaClass.name ?: Logger.ROOT_LOGGER_NAME)