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

import org.slf4j.event.SubstituteLoggingEvent
import java.util.concurrent.LinkedBlockingQueue
import kotlin.jvm.Synchronized

/**
 * SubstituteLoggerFactory manages instances of [SubstituteLogger].
 *
 * @author Ceki G&uuml;lc&uuml;
 * @author Chetan Mehrotra
 */
class SubstituteLoggerFactory : ILoggerFactory {
    var postInitialization = false
    val loggers: MutableMap<String, SubstituteLogger> = HashMap()
    private val eventQueue: LinkedBlockingQueue<SubstituteLoggingEvent> = LinkedBlockingQueue<SubstituteLoggingEvent>()
    
    @Synchronized
    override fun getLogger(name: String): Logger {
        var logger = loggers[name]
        if (logger == null) {
            logger = SubstituteLogger(name, eventQueue, postInitialization)
            loggers[name] = logger
        }
        return logger
    }

    val loggerNames: List<String>
        get() = ArrayList<String>(loggers.keys)

    fun getLoggers(): List<SubstituteLogger> {
        return ArrayList<SubstituteLogger>(loggers.values)
    }

    fun getEventQueue(): LinkedBlockingQueue<SubstituteLoggingEvent> {
        return eventQueue
    }

    fun postInitialization() {
        postInitialization = true
    }

    fun clear() {
        loggers.clear()
        eventQueue.clear()
    }
}