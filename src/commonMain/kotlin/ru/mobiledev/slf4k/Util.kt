package ru.mobiledev.slf4k

expect object Util {

    fun report(msg: String?, t: Throwable)

    fun report(msg: String)
}