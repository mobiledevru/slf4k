package org.slf4j

expect object Util {

    fun report(msg: String?, t: Throwable)

    fun report(msg: String)
}