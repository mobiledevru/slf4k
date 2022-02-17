package ru.mobiledev.slf4k

actual class Adapter {
    actual val platform: String
        get() = "JS"

    actual fun timeStamp(): Long {
        TODO("Not yet implemented")
    }

    actual fun threadName(): String? = null
}