/**
 * Copyright (c) 2004-2011 QOS.ch
 * All rights reserved.
 *
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package ru.mobiledev.slf4k.spi

import ru.mobiledev.slf4k.Logger
import ru.mobiledev.slf4k.Marker

/**
 * An **optional** interface helping integration with logging systems capable of
 * extracting location information. This interface is mainly used by SLF4J bridges
 * such as jcl-over-slf4j, jul-to-slf4j and log4j-over-slf4j or [Logger] wrappers
 * which need to provide hints so that the underlying logging system can extract
 * the correct location information (method name, line number).
 *
 * @author Ceki Gulcu
 * @since 1.3
 */
interface LocationAwareLogger : Logger {

    /**
     * Printing method with support for location information.
     *
     * @param marker The marker to be used for this event, may be null.
     * @param fqcn The fully qualified class name of the **logger instance**,
     * typically the logger class, logger bridge or a logger wrapper.
     * @param level One of the level integers defined in this interface
     * @param message The message for the log event
     * @param t Throwable associated with the log event, may be null.
     */
    fun log(marker: Marker?, fqcn: String, level: Int, message: String, argArray: Array<Any>?, t: Throwable?)

    companion object {
        // these constants should be in EventContants. However, in order to preserve binary backward compatibility
        // we keep these constants here
        const val TRACE_INT = 0
        const val DEBUG_INT = 10
        const val INFO_INT = 20
        const val WARN_INT = 30
        const val ERROR_INT = 40
    }
}