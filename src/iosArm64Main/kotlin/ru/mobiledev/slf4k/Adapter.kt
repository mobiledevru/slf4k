package ru.mobiledev.slf4k

actual interface SerializableAdapter

actual object Adapter {
    actual val platform: String get() = "iOS Arm64"

    actual fun timeStamp(): Long = kotlin.system.getTimeMillis()

    actual fun className(obj: Any): String = this::class.qualifiedName ?: "NIL"
}