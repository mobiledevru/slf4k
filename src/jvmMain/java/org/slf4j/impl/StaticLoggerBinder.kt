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
package org.slf4j.impl

import org.slf4j.ILoggerFactory

/**
 * The binding of {@link org.slf4j.LoggerFactory} class with an actual instance of
 * {@link ILoggerFactory} is performed using information returned by this class.
 *
 * This class is meant to provide a dummy StaticLoggerBinder to the slf4j-api module.
 * Real implementations are found in  each SLF4J binding project, e.g. slf4j-nop,
 * slf4j-log4j12 etc.
 *
 * @author Ceki G&uuml;lc&uuml;
 */
actual class StaticLoggerBinder private constructor() {
    
    init {
        throw UnsupportedOperationException("This code should have never made it into slf4j-api.jar")
    }

    actual fun getLoggerFactory(): ILoggerFactory {
        throw UnsupportedOperationException("This code should never make it into slf4j-api.jar")
    }

    actual fun getLoggerFactoryClassStr(): String {
        throw UnsupportedOperationException("This code should never make it into slf4j-api.jar")

    }

    companion object {
        /**
         * The unique instance of this class.
         */
        private val SINGLETON: StaticLoggerBinder = StaticLoggerBinder()

        /**
         * Return the singleton of this class.
         *
         * @return the StaticLoggerBinder singleton
         */
        fun getSingleton(): StaticLoggerBinder {
            return SINGLETON
        }

        /**
         * Declare the version of the SLF4J API this implementation is compiled against.
         * The value of this field is modified with each major release.
         */
        // to avoid constant folding by the compiler, this field must *not* be final
        var REQUESTED_API_VERSION: String = "1.6.99" // !final
    }
}
