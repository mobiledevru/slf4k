package org.slf4j

actual object Util {

    actual fun report(msg: String?, t: Throwable) {
        println(msg)
        t.printStackTrace()
    }

    actual fun report(msg: String) {
        println(msg)
    }
}