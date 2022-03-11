package ru.mobiledev.slf4k

actual interface SerializableAdapter

actual object Adapter {

    actual val platform: String
        get() = "JS"

    actual fun timeStamp(): Long {
        TODO("Not yet implemented for $platform")
    }

    actual fun className(obj: Any): String = this::class.simpleName ?: "undefined"
}