package ru.mobiledev.slf4k

expect class Adapter() {
    val platform: String

    fun timeStamp(): Long
    fun threadName(): String?
}