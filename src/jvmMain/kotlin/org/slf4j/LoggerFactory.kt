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

import org.slf4j.Util.report
import org.slf4j.Util.safeGetBooleanSystemProperty
import org.slf4j.Util.safeGetSystemProperty
import org.slf4j.event.SubstituteLoggingEvent
import org.slf4j.helpers.NOPLoggerFactory
import org.slf4j.impl.StaticLoggerBinder
import java.io.IOException
import java.util.*
import java.util.concurrent.LinkedBlockingQueue

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
    const val CODES_PREFIX = "http://www.slf4j.org/codes.html"
    const val NO_STATICLOGGERBINDER_URL = "$CODES_PREFIX#StaticLoggerBinder"
    const val MULTIPLE_BINDINGS_URL = "$CODES_PREFIX#multiple_bindings"
    const val NULL_LF_URL = "$CODES_PREFIX#null_LF"
    const val VERSION_MISMATCH = "$CODES_PREFIX#version_mismatch"
    const val SUBSTITUTE_LOGGER_URL = "$CODES_PREFIX#substituteLogger"
    const val LOGGER_NAME_MISMATCH_URL = "$CODES_PREFIX#loggerNameMismatch"
    const val REPLAY_URL = "$CODES_PREFIX#replay"
    const val UNSUCCESSFUL_INIT_URL = "$CODES_PREFIX#unsuccessfulInit"
    const val UNSUCCESSFUL_INIT_MSG = "org.slf4j.LoggerFactory in failed state. Original exception was thrown EARLIER. See also $UNSUCCESSFUL_INIT_URL"
    const val UNINITIALIZED = 0
    const val ONGOING_INITIALIZATION = 1
    const val FAILED_INITIALIZATION = 2
    const val SUCCESSFUL_INITIALIZATION = 3
    const val NOP_FALLBACK_INITIALIZATION = 4

    @Volatile
    var INITIALIZATION_STATE = UNINITIALIZED
    val SUBST_FACTORY = SubstituteLoggerFactory()
    val NOP_FALLBACK_FACTORY = NOPLoggerFactory()

    // Support for detecting mismatched logger names.
    const val DETECT_LOGGER_NAME_MISMATCH_PROPERTY = "slf4j.detectLoggerNameMismatch"
    const val JAVA_VENDOR_PROPERTY = "java.vendor.url"
    var DETECT_LOGGER_NAME_MISMATCH = safeGetBooleanSystemProperty(DETECT_LOGGER_NAME_MISMATCH_PROPERTY)

    /**
     * It is LoggerFactory's responsibility to track version changes and manage
     * the compatibility list.
     *
     *
     *
     *
     * It is assumed that all versions in the 1.6 are mutually compatible.
     */
    private val API_COMPATIBILITY_LIST = arrayOf("1.6", "1.7")

    /**
     * Force LoggerFactory to consider itself uninitialized.
     *
     *
     *
     *
     * This method is intended to be called by classes (in the same package) for
     * testing purposes. This method is internal. It can be modified, renamed or
     * removed at any time without notice.
     *
     *
     *
     *
     * You are strongly discouraged from calling this method in production code.
     */
    fun reset() {
        INITIALIZATION_STATE = UNINITIALIZED
    }

    private fun performInitialization() {
        bind()
        if (INITIALIZATION_STATE == SUCCESSFUL_INITIALIZATION) {
            versionSanityCheck()
        }
    }

    private fun messageContainsOrgSlf4jImplStaticLoggerBinder(msg: String?): Boolean {
        if (msg == null) return false
        if (msg.contains("org/slf4j/impl/StaticLoggerBinder")) return true
        return if (msg.contains("org.slf4j.impl.StaticLoggerBinder")) true else false
    }

    private fun bind() {
        try {
            var staticLoggerBinderPathSet: Set<java.net.URL>? = null
            // skip check under android, see also
            // http://jira.qos.ch/browse/SLF4J-328
            if (!isAndroid) {
                staticLoggerBinderPathSet = findPossibleStaticLoggerBinderPathSet()
                reportMultipleBindingAmbiguity(staticLoggerBinderPathSet)
            }
            // the next line does the binding
            StaticLoggerBinder.singleton
            INITIALIZATION_STATE = SUCCESSFUL_INITIALIZATION
            reportActualBinding(staticLoggerBinderPathSet)
        } catch (ncde: NoClassDefFoundError) {
            val msg: String? = ncde.message
            if (messageContainsOrgSlf4jImplStaticLoggerBinder(msg)) {
                INITIALIZATION_STATE = NOP_FALLBACK_INITIALIZATION
                report("Failed to load class \"org.slf4j.impl.StaticLoggerBinder\".")
                report("Defaulting to no-operation (NOP) logger implementation")
                report("See " + NO_STATICLOGGERBINDER_URL + " for further details.")
            } else {
                failedBinding(ncde)
                throw ncde
            }
        } catch (nsme: NoSuchMethodError) {
            val msg: String? = nsme.message
            if (msg != null && msg.contains("org.slf4j.impl.StaticLoggerBinder.getSingleton()")) {
                INITIALIZATION_STATE = FAILED_INITIALIZATION
                report("slf4j-api 1.6.x (or later) is incompatible with this binding.")
                report("Your binding is version 1.5.5 or earlier.")
                report("Upgrade your binding to version 1.6.x.")
            }
            throw nsme
        } catch (e: Exception) {
            failedBinding(e)
            throw IllegalStateException("Unexpected initialization failure", e)
        } finally {
            postBindCleanUp()
        }
    }

    private fun postBindCleanUp() {
        fixSubstituteLoggers()
        replayEvents()
        // release all resources in SUBST_FACTORY
        SUBST_FACTORY.clear()
    }

    private fun fixSubstituteLoggers() {
        synchronized(SUBST_FACTORY) {
            SUBST_FACTORY.postInitialization()
            for (substLogger in SUBST_FACTORY.getLoggers()) {
                val logger: Logger = getLogger(substLogger.name)
                substLogger.setDelegate(logger)
            }
        }
    }

    fun failedBinding(t: Throwable) {
        INITIALIZATION_STATE = FAILED_INITIALIZATION
        report("Failed to instantiate SLF4J LoggerFactory", t)
    }

    private fun replayEvents() {
        val queue: LinkedBlockingQueue<SubstituteLoggingEvent> = SUBST_FACTORY.getEventQueue()
        val queueSize: Int = queue.size
        var count = 0
        val maxDrain = 128
        val eventList: MutableList<SubstituteLoggingEvent> = ArrayList<SubstituteLoggingEvent>(maxDrain)
        while (true) {
            val numDrained: Int = queue.drainTo(eventList, maxDrain)
            if (numDrained == 0) break
            for (event in eventList) {
                replaySingleEvent(event)
                if (count++ == 0) emitReplayOrSubstituionWarning(event, queueSize)
            }
            eventList.clear()
        }
    }

    private fun emitReplayOrSubstituionWarning(event: SubstituteLoggingEvent, queueSize: Int) {
        if (event.getLogger()!!.isDelegateEventAware!!) {
            emitReplayWarning(queueSize)
        } else if (event.getLogger()!!.isDelegateNOP) {
            // nothing to do
        } else {
            emitSubstitutionWarning()
        }
    }

    private fun replaySingleEvent(event: SubstituteLoggingEvent?) {
        if (event == null) return
        val substLogger = event.getLogger()
        val loggerName = substLogger!!.name
        check(!substLogger.isDelegateNull) { "Delegate logger cannot be null at this state." }
        if (substLogger.isDelegateNOP) {
            // nothing to do
        } else if (substLogger.isDelegateEventAware!!) {
            substLogger.log(event)
        } else {
            report(loggerName)
        }
    }

    private fun emitSubstitutionWarning() {
        report("The following set of substitute loggers may have been accessed")
        report("during the initialization phase. Logging calls during this")
        report("phase were not honored. However, subsequent logging calls to these")
        report("loggers will work as normally expected.")
        report("See also " + SUBSTITUTE_LOGGER_URL)
    }

    private fun emitReplayWarning(eventCount: Int) {
        report("A number ($eventCount) of logging calls during the initialization phase have been intercepted and are")
        report("now being replayed. These are subject to the filtering rules of the underlying logging system.")
        report("See also " + REPLAY_URL)
    }

    private fun versionSanityCheck() {
        try {
            val requested = StaticLoggerBinder.REQUESTED_API_VERSION
            var match = false
            for (aAPI_COMPATIBILITY_LIST in API_COMPATIBILITY_LIST) {
                if (requested.startsWith(aAPI_COMPATIBILITY_LIST)) {
                    match = true
                }
            }
            if (!match) {
                report("The requested version $requested by your slf4j binding is not compatible with ${listOf(*API_COMPATIBILITY_LIST)}")
                report("See $VERSION_MISMATCH for further details.")
            }
        } catch (e: Throwable) {
            if (e::class.simpleName?.equals("NoSuchFieldError") == true) {
                // expecting java.lang.NoSuchFieldError (qualifiedName not supported by JS now)

                // given our large user base and SLF4J's commitment to backward
                // compatibility, we cannot cry here. Only for implementations
                // which willingly declare a REQUESTED_API_VERSION field do we
                // emit compatibility warnings.
            } else {
                // we should never reach here
                report("Unexpected problem occured during version sanity check", e)
            }
        }
    }

    // We need to use the name of the StaticLoggerBinder class, but we can't
    // reference
    // the class itself.
    private const val STATIC_LOGGER_BINDER_PATH = "org/slf4j/impl/StaticLoggerBinder.class"
    fun findPossibleStaticLoggerBinderPathSet(): Set<java.net.URL> {
        // use Set instead of list in order to deal with bug #138
        // LinkedHashSet appropriate here because it preserves insertion order
        // during iteration
        val staticLoggerBinderPathSet: MutableSet<java.net.URL> = LinkedHashSet<java.net.URL>()
        try {
            val loggerFactoryClassLoader: java.lang.ClassLoader? = LoggerFactory::class.java.getClassLoader()
            val paths: Enumeration<java.net.URL>
            paths = if (loggerFactoryClassLoader == null) {
                java.lang.ClassLoader.getSystemResources(STATIC_LOGGER_BINDER_PATH)
            } else {
                loggerFactoryClassLoader.getResources(STATIC_LOGGER_BINDER_PATH)
            }
            while (paths.hasMoreElements()) {
                val path: java.net.URL = paths.nextElement()
                staticLoggerBinderPathSet.add(path)
            }
        } catch (ioe: IOException) {
            report("Error getting resources from path", ioe)
        }
        return staticLoggerBinderPathSet
    }

    private fun isAmbiguousStaticLoggerBinderPathSet(binderPathSet: Set<java.net.URL>?): Boolean {
        return binderPathSet?.isNotEmpty() ?: false
    }

    /**
     * Prints a warning message on the console if multiple bindings were found
     * on the class path. No reporting is done otherwise.
     *
     */
    private fun reportMultipleBindingAmbiguity(binderPathSet: Set<java.net.URL>?) {
        if (binderPathSet != null && isAmbiguousStaticLoggerBinderPathSet(binderPathSet)) {
            report("Class path contains multiple SLF4J bindings.")
            for (path in binderPathSet) {
                report("Found binding in [$path]")
            }
            report("See " + MULTIPLE_BINDINGS_URL + " for an explanation.")
        }
    }

    private val isAndroid: Boolean
        private get() {
            val vendor = safeGetSystemProperty(JAVA_VENDOR_PROPERTY) ?: return false
            return vendor.lowercase(java.util.Locale.getDefault()).contains("android")
        }

    private fun reportActualBinding(binderPathSet: Set<java.net.URL>?) {
        // binderPathSet can be null under Android
        if (binderPathSet != null && isAmbiguousStaticLoggerBinderPathSet(binderPathSet)) {
            report(
                "Actual binding is of type [" + StaticLoggerBinder.singleton.getLoggerFactoryClassStr() + "]"
            )
        }
    }

    /**
     * Return a logger named according to the name parameter using the
     * statically bound [ILoggerFactory] instance.
     *
     * @param name
     * The name of the logger.
     * @return logger
     */
    @JvmStatic
    actual fun getLogger(name: String): Logger {
        val iLoggerFactory = iLoggerFactory
        return iLoggerFactory.getLogger(name)
    }

    /**
     * Return a logger named corresponding to the class passed as parameter,
     * using the statically bound [ILoggerFactory] instance.
     *
     *
     *
     * In case the the `clazz` parameter differs from the name of the
     * caller as computed internally by SLF4J, a logger name mismatch warning
     * will be printed but only if the
     * `slf4j.detectLoggerNameMismatch` system property is set to
     * true. By default, this property is not set and no warnings will be
     * printed even in case of a logger name mismatch.
     *
     * @param clazz
     * the returned logger will be named after clazz
     * @return logger
     *
     *
     * @see [Detected
     * logger name mismatch](http://www.slf4j.org/codes.html.loggerNameMismatch)
     */
    @JvmStatic
    fun getLogger(clazz: Class<*>): Logger {
        val logger: Logger = getLogger(clazz.name)
        if (DETECT_LOGGER_NAME_MISMATCH) {
            val autoComputedCallingClass: Class<*>? = Util.getCallingClass()
            if (autoComputedCallingClass != null && LoggerFactory.nonMatchingClasses(
                    clazz,
                    autoComputedCallingClass
                )
            ) {
                report(
                    String.format(
                        "Detected logger name mismatch. Given name: \"%s\"; computed name: \"%s\".", logger.name,
                        autoComputedCallingClass.name
                    )
                )
                report("See " + LOGGER_NAME_MISMATCH_URL + " for an explanation")
            }
        }
        return logger
    }

    /*fun getLogger(clazz: KClass<*>): Logger {
        val logger: Logger = getLogger(clazz.qualifiedName ?: Logger.ROOT_LOGGER_NAME)
        if (DETECT_LOGGER_NAME_MISMATCH) {
            val autoComputedCallingClass: KClass<*>? = getCallingClass()
            if (autoComputedCallingClass != null && nonMatchingClasses(clazz, autoComputedCallingClass)) {
                report(
                    String.format(
                        "Detected logger name mismatch. Given name: \"%s\"; computed name: \"%s\".",
                        logger.name,
                        autoComputedCallingClass.qualifiedName
                    )
                )
                report("See " + LOGGER_NAME_MISMATCH_URL + " for an explanation")
            }
        }
        return logger
    }*/

    /*private fun nonMatchingClasses(clazz: KClass<*>, autoComputedCallingClass: KClass<*>): Boolean {
        return !autoComputedCallingClass.isAssignableFrom(clazz)
    }*/

    private fun nonMatchingClasses(clazz: Class<*>, autoComputedCallingClass: Class<*>): Boolean {
        return !autoComputedCallingClass.isAssignableFrom(clazz)
    }

    // support re-entrant behavior.
    // See also http://jira.qos.ch/browse/SLF4J-97

    /**
     * Return the [ILoggerFactory] instance in use.
     *
     *
     *
     *
     * ILoggerFactory instance is bound with this class at compile time.
     *
     * @return the ILoggerFactory instance in use
     */
    val iLoggerFactory: ILoggerFactory
        get() {
            if (INITIALIZATION_STATE == UNINITIALIZED) {
                synchronized(LoggerFactory::class.java) {
                    if (INITIALIZATION_STATE == UNINITIALIZED) {
                        INITIALIZATION_STATE = ONGOING_INITIALIZATION
                        performInitialization()
                    }
                }
            }
            when (INITIALIZATION_STATE) {
                SUCCESSFUL_INITIALIZATION -> return StaticLoggerBinder.singleton.getLoggerFactory()
                NOP_FALLBACK_INITIALIZATION -> return NOP_FALLBACK_FACTORY
                FAILED_INITIALIZATION -> throw IllegalStateException(UNSUCCESSFUL_INIT_MSG)
                ONGOING_INITIALIZATION ->             // support re-entrant behavior.
                    // See also http://jira.qos.ch/browse/SLF4J-97
                    return SUBST_FACTORY
            }
            throw IllegalStateException("Unreachable code")
        }
}