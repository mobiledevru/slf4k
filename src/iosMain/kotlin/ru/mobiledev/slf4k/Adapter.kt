package ru.mobiledev.slf4k

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

actual interface SerializableAdapter

actual object Adapter {
    actual val platform: String get() = "iOS Arm64"

    actual fun timeStamp(): Long = kotlin.system.getTimeMillis()

    actual fun className(obj: Any): String = this::class.qualifiedName ?: "NIL"
}

actual fun LoggerFactory.getLogger(clazz: KClass<*>): Logger = getLogger(clazz.qualifiedName ?: Logger.ROOT_LOGGER_NAME)
