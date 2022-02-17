/**
 * Copyright (c) 2004-2011 QOS.ch
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package ru.mobiledev.slf4k.helpers

import ru.mobiledev.slf4k.Logger
import ru.mobiledev.slf4k.Logger.isTraceEnabled
import ru.mobiledev.slf4k.Logger.trace
import ru.mobiledev.slf4k.Logger.isDebugEnabled
import ru.mobiledev.slf4k.Logger.debug
import ru.mobiledev.slf4k.Logger.isInfoEnabled
import ru.mobiledev.slf4k.Logger.info
import ru.mobiledev.slf4k.Logger.isWarnEnabled
import ru.mobiledev.slf4k.Logger.warn
import ru.mobiledev.slf4k.Logger.isErrorEnabled
import ru.mobiledev.slf4k.Logger.error
import ru.mobiledev.slf4k.Logger.name
import ru.mobiledev.slf4k.Marker

/**
 * This class serves as base for adapters or native implementations of logging systems
 * lacking Marker support. In this implementation, methods taking marker data
 * simply invoke the corresponding method without the Marker argument, discarding
 * any marker data passed as argument.
 *
 * @author Ceki Gulcu
 */
abstract class MarkerIgnoringBase : NamedLoggerBase(), Logger {
    override fun isTraceEnabled(marker: Marker?): Boolean {
        return isTraceEnabled
    }

    override fun trace(marker: Marker?, msg: String) {
        trace(msg)
    }

    override fun trace(marker: Marker?, format: String, arg: Any) {
        trace(format, arg)
    }

    override fun trace(marker: Marker?, format: String, arg1: Any, arg2: Any) {
        trace(format, arg1, arg2)
    }

    override fun trace(marker: Marker?, format: String, vararg arguments: Any) {
        trace(format, *arguments)
    }

    override fun trace(marker: Marker?, msg: String, t: Throwable?) {
        trace(msg, t)
    }

    override fun isDebugEnabled(marker: Marker?): Boolean {
        return isDebugEnabled
    }

    override fun debug(marker: Marker?, msg: String) {
        debug(msg)
    }

    override fun debug(marker: Marker?, format: String, arg: Any) {
        debug(format, arg)
    }

    override fun debug(marker: Marker?, format: String, arg1: Any, arg2: Any) {
        debug(format, arg1, arg2)
    }

    override fun debug(marker: Marker?, format: String, vararg arguments: Any) {
        debug(format, *arguments)
    }

    override fun debug(marker: Marker?, msg: String, t: Throwable?) {
        debug(msg, t)
    }

    override fun isInfoEnabled(marker: Marker?): Boolean {
        return isInfoEnabled
    }

    override fun info(marker: Marker?, msg: String) {
        info(msg)
    }

    override fun info(marker: Marker?, format: String, arg: Any) {
        info(format, arg)
    }

    override fun info(marker: Marker?, format: String, arg1: Any, arg2: Any) {
        info(format, arg1, arg2)
    }

    override fun info(marker: Marker?, format: String, vararg arguments: Any) {
        info(format, *arguments)
    }

    override fun info(marker: Marker?, msg: String, t: Throwable?) {
        info(msg, t)
    }

    override fun isWarnEnabled(marker: Marker?): Boolean {
        return isWarnEnabled
    }

    override fun warn(marker: Marker?, msg: String) {
        warn(msg)
    }

    override fun warn(marker: Marker?, format: String, arg: Any) {
        warn(format, arg)
    }

    override fun warn(marker: Marker?, format: String, arg1: Any, arg2: Any) {
        warn(format, arg1, arg2)
    }

    override fun warn(marker: Marker?, format: String, vararg arguments: Any) {
        warn(format, *arguments)
    }

    override fun warn(marker: Marker?, msg: String, t: Throwable?) {
        warn(msg, t)
    }

    override fun isErrorEnabled(marker: Marker?): Boolean {
        return isErrorEnabled
    }

    override fun error(marker: Marker?, msg: String) {
        error(msg)
    }

    override fun error(marker: Marker?, format: String, arg: Any) {
        error(format, arg)
    }

    override fun error(marker: Marker?, format: String, arg1: Any, arg2: Any) {
        error(format, arg1, arg2)
    }

    override fun error(marker: Marker?, format: String, vararg arguments: Any) {
        error(format, *arguments)
    }

    override fun error(marker: Marker?, msg: String, t: Throwable?) {
        error(msg, t)
    }

    override fun toString(): String {
//        return (this::class.qualifiedName ?: "noName") + "(" + name + ")"
        return this.javaClass.getName() + "(" + name + ")"
    }

    companion object {
        private const val serialVersionUID = 9044267456635152283L
    }
}