package ru.mobiledev.slf4k

actual class Adapter {
    actual val platform: String
        get() = "NativeMain"
}