package ru.mobiledev.slf4k

actual class Adapter {
    actual val platform: String get() = "NativeMain"

    actual fun timeStamp(): Long = kotlin.system.getTimeMillis()

    actual fun className(obj: Any): String = this::class.qualifiedName ?: "NULL"
}