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

import org.slf4j.IMarkerFactory
import org.slf4j.spi.MarkerFactoryBinder
import org.slf4j.helpers.BasicMarkerFactory

/**
 *
 * The binding of [MarkerFactory] class with an actual instance of
 * [IMarkerFactory] is performed using information returned by this class.
 *
 * This class is meant to provide a *dummy* StaticMarkerBinder to the slf4j-api module.
 * Real implementations are found in  each SLF4J binding project, e.g. slf4j-nop,
 * slf4j-simple, slf4j-log4j12 etc.
 *
 * @author Ceki G&uuml;lc&uuml;
 * @author Golubev Dmitrii
 */
class StaticMarkerBinder private constructor() : MarkerFactoryBinder {

    private val markerFactory: IMarkerFactory = BasicMarkerFactory()

    /**
     * Currently this method always returns an instance of
     * [BasicMarkerFactory].
     */
    override fun getMarkerFactory(): IMarkerFactory = markerFactory

    /**
     * Currently, this method returns the class name of
     * [BasicMarkerFactory].
     */
    override fun getMarkerFactoryClassStr(): String = BasicMarkerFactory::class.qualifiedName.toString()

    companion object {
        /**
         * Return the singleton of this class.
         *
         * @return the StaticMarkerBinder singleton
         * @since 1.7.14
         */
        /**
         * The unique instance of this class.
         */
        val SINGLETON = StaticMarkerBinder()

        fun getSingleton() : StaticMarkerBinder = SINGLETON
    }
}