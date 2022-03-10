package ru.mobiledev.slf4k

expect object LoggerFactory {
    fun getLogger(name: String): Logger
}