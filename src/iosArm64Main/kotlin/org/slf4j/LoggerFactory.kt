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
package org.slf4j

import org.slf4j.impl.StaticLoggerBinder

/**
 * The `LoggerFactory` is a utility class producing Loggers for
 * various logging APIs, most notably for log4j, logback and JDK 1.4 logging.
 * Other implementations such as [NOPLogger][org.slf4j.impl.NOPLogger] and
 * [SimpleLogger][org.slf4j.impl.SimpleLogger] are also supported.
 *
 *
 *
 *
 * `LoggerFactory` is essentially a wrapper around an
 * [ILoggerFactory] instance bound with `LoggerFactory` at
 * compile time.
 *
 *
 *
 *
 * Please note that all methods in `LoggerFactory` are static.
 *
 *
 * @author Alexander Dorokhine
 * @author Robert Elliot
 * @author Ceki G&uuml;lc&uuml;
 */
actual object LoggerFactory {

    /**
     * Return a logger named according to the name parameter using the
     * statically bound [ILoggerFactory] instance.
     *
     * @param name
     * The name of the logger.
     * @return logger
     */
    actual fun getLogger(name: String): Logger {
        val iLoggerFactory = iLoggerFactory
        return iLoggerFactory.getLogger(name)
    }

    private val iLoggerFactory: ILoggerFactory get() = StaticLoggerBinder.getSingleton().getLoggerFactory()
}