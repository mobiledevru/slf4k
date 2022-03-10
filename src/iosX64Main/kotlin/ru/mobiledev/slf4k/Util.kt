package ru.mobiledev.slf4k

actual object Util {

    actual fun report(msg: String?, t: Throwable) {
        println(msg)
        t.printStackTrace()
    }

    actual fun report(msg: String) {
        println(msg)
    }
}