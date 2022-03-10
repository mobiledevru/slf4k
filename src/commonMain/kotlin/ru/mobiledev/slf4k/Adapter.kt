package ru.mobiledev.slf4k

expect interface SerializableAdapter

expect object Adapter {
    val platform: String

    fun timeStamp(): Long

    fun threadName(): String?

    /**
     * Qualified name of class 
     */
    fun className(obj: Any): String
}