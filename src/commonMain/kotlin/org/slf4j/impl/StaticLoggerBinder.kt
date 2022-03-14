package org.slf4j.impl

import org.slf4j.ILoggerFactory

expect class StaticLoggerBinder {

    /**
     * Return the singleton of this class.
     *
     * @return the StaticLoggerBinder singleton
     */
//    fun getSingleton(): StaticLoggerBinder

    fun StaticLoggerBinder()

    fun getLoggerFactory(): ILoggerFactory

    fun getLoggerFactoryClassStr(): String
}