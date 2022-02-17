package ru.mobiledev.slf4k

actual class Adapter {
    actual val platform: String
        get() = "JVM"

    actual fun timeStamp(): Long {
        return System.currentTimeMillis()
//        return kotlin.system.getTimeMillis()
    }

    actual fun threadName(): String? = Thread.currentThread().name


}