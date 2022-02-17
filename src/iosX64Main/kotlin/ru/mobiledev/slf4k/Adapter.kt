package ru.mobiledev.slf4k

actual class Adapter {
    actual val platform: String
        get() = "iOS x64"

    actual fun timeStamp(): Long {
        return kotlin.system.getTimeMillis()
    }

    actual fun threadName(): String? {
        TODO("Not yet implemented")
    }
}