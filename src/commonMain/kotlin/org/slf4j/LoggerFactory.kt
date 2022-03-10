package org.slf4j

expect object LoggerFactory {
    fun getLogger(name: String): Logger
}