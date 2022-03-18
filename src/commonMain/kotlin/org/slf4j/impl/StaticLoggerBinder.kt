package org.slf4j.impl

import org.slf4j.ILoggerFactory

expect class StaticLoggerBinder {

    fun getLoggerFactory(): ILoggerFactory

    fun getLoggerFactoryClassStr(): String
}