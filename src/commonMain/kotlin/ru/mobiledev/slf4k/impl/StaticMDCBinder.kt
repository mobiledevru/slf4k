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
package ru.mobiledev.slf4k.impl

import ru.mobiledev.slf4k.spi.MDCAdapter

/**
 * This class is only a stub. Real implementations are found in
 * each SLF4J binding project, e.g. slf4j-nop, slf4j-log4j12 etc.
 *
 * @author Ceki G&uuml;lc&uuml;
 */
class StaticMDCBinder private constructor() {

    init {
        throw UnsupportedOperationException("This code should never make it into the jar")
    }

    /**
     * Currently this method always returns an instance of
     * [StaticMDCBinder].
     */
    val mDCA: MDCAdapter
        get() = throw UnsupportedOperationException("This code should never make it into the jar")

    val mDCAdapterClassStr: String
        get() = throw UnsupportedOperationException("This code should never make it into the jar")

    companion object {
        /**
         * Return the singleton of this class.
         *
         * @return the StaticMDCBinder singleton
         * @since 1.7.14
         */
        /**
         * The unique instance of this class.
         */
        private val SINGLETON = StaticMDCBinder()

        fun getSingleton(): StaticMDCBinder {
            return SINGLETON
        }
    }
}