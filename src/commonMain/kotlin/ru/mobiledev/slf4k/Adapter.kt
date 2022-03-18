package ru.mobiledev.slf4k

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

expect interface SerializableAdapter

expect object Adapter {
    val platform: String

    fun timeStamp(): Long

    /**
     * Qualified name of class 
     */
    fun className(obj: Any): String
}

expect fun LoggerFactory.getLogger(clazz: KClass<*>): Logger
