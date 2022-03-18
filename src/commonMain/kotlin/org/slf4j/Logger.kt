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

/**
 * The org.slf4j.Logger interface is the main user entry point of SLF4J API.
 * It is expected that logging takes place through concrete implementations
 * of this interface.
 *
 *
 * <h3>Typical usage pattern:</h3>
 * <pre>
 * import org.slf4j.Logger;
 * import org.slf4j.LoggerFactory;
 *
 * public class Wombat {
 *
 *   <span style="color:green">final static Logger logger = LoggerFactory.getLogger(Wombat.class);</span>
 *   Integer t;
 *   Integer oldT;
 *
 *   public void setTemperature(Integer temperature) {
 *     oldT = t;
 *     t = temperature;
 *     <span style="color:green">logger.debug("Temperature set to {}. Old temperature was {}.", t, oldT);</span>
 *     if(temperature.intValue() > 50) {
 *       <span style="color:green">logger.info("Temperature has risen above 50 degrees.");</span>
 *     }
 *   }
 * }
 * </pre>
 *
 * Be sure to read the FAQ entry relating to [parameterized
 * logging](../../../faq.html#logging_performance). Note that logging statements can be parameterized in
 * [presence of an exception/throwable](../../../faq.html#paramException).
 *
 *
 * Once you are comfortable using loggers, i.e. instances of this interface, consider using
 * [MDC](MDC.html) as well as [Markers](Marker.html).
 *
 * @author Ceki G&uuml;lc&uuml;
 */
interface Logger {
    /**
     * Return the name of this `Logger` instance.
     * @return name of this logger instance
     */
    val name: String

    /**
     * Is the logger instance enabled for the TRACE level?
     *
     * @return True if this Logger is enabled for the TRACE level,
     * false otherwise.
     * @since 1.4
     */
    val isTraceEnabled: Boolean

    /**
     * Log a message at the TRACE level.
     *
     * @param msg the message string to be logged
     * @since 1.4
     */
    fun trace(msg: String?)

    /**
     * Log a message at the TRACE level according to the specified format
     * and argument.
     *
     *
     *
     * This form avoids superfluous object creation when the logger
     * is disabled for the TRACE level.
     *
     * @param format the format string
     * @param arg    the argument
     * @since 1.4
     */
    fun trace(format: String?, arg: Any?)

    /**
     * Log a message at the TRACE level according to the specified format
     * and arguments.
     *
     *
     *
     * This form avoids superfluous object creation when the logger
     * is disabled for the TRACE level.
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @since 1.4
     */
    fun trace(format: String?, arg1: Any?, arg2: Any?)

    /**
     * Log a message at the TRACE level according to the specified format
     * and arguments.
     *
     *
     *
     * This form avoids superfluous string concatenation when the logger
     * is disabled for the TRACE level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an `Object[]` before invoking the method,
     * even if this logger is disabled for TRACE. The variants taking [one][.trace] and
     * [two][.trace] arguments exist solely in order to avoid this hidden cost.
     *
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     * @since 1.4
     */
    fun trace(format: String?, vararg arguments: Any?)

    /**
     * Log an exception (throwable) at the TRACE level with an
     * accompanying message.
     *
     * @param msg the message accompanying the exception
     * @param t   the exception (throwable) to log
     * @since 1.4
     */
    fun trace(msg: String?, t: Throwable?)

    /**
     * Similar to [.isTraceEnabled] method except that the
     * marker data is also taken into account.
     *
     * @param marker The marker data to take into consideration
     * @return True if this Logger is enabled for the TRACE level,
     * false otherwise.
     *
     * @since 1.4
     */
    fun isTraceEnabled(marker: Marker?): Boolean

    /**
     * Log a message with the specific Marker at the TRACE level.
     *
     * @param marker the marker data specific to this log statement
     * @param msg    the message string to be logged
     * @since 1.4
     */
    fun trace(marker: Marker?, msg: String?)

    /**
     * This method is similar to [.trace] method except that the
     * marker data is also taken into consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg    the argument
     * @since 1.4
     */
    fun trace(marker: Marker?, format: String?, arg: Any?)

    /**
     * This method is similar to [.trace]
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @since 1.4
     */
    fun trace(marker: Marker?, format: String?, arg1: Any?, arg2: Any?)

    /**
     * This method is similar to [.trace]
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker    the marker data specific to this log statement
     * @param format    the format string
     * @param arguments an array of arguments
     * @since 1.4
     */
    fun trace(marker: Marker?, format: String?, vararg arguments: Any?)

    /**
     * This method is similar to [.trace] method except that the
     * marker data is also taken into consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param msg    the message accompanying the exception
     * @param t      the exception (throwable) to log
     * @since 1.4
     */
    fun trace(marker: Marker?, msg: String?, t: Throwable?)

    /**
     * Is the logger instance enabled for the DEBUG level?
     *
     * @return True if this Logger is enabled for the DEBUG level,
     * false otherwise.
     */
    val isDebugEnabled: Boolean

    /**
     * Log a message at the DEBUG level.
     *
     * @param msg the message string to be logged
     */
    fun debug(msg: String?)

    /**
     * Log a message at the DEBUG level according to the specified format
     * and argument.
     *
     *
     *
     * This form avoids superfluous object creation when the logger
     * is disabled for the DEBUG level.
     *
     * @param format the format string
     * @param arg    the argument
     */
    fun debug(format: String?, arg: Any?)

    /**
     * Log a message at the DEBUG level according to the specified format
     * and arguments.
     *
     *
     *
     * This form avoids superfluous object creation when the logger
     * is disabled for the DEBUG level.
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    fun debug(format: String?, arg1: Any?, arg2: Any?)

    /**
     * Log a message at the DEBUG level according to the specified format
     * and arguments.
     *
     *
     *
     * This form avoids superfluous string concatenation when the logger
     * is disabled for the DEBUG level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an `Object[]` before invoking the method,
     * even if this logger is disabled for DEBUG. The variants taking
     * [one][.debug] and [two][.debug]
     * arguments exist solely in order to avoid this hidden cost.
     *
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    fun debug(format: String?, vararg arguments: Any?)

    /**
     * Log an exception (throwable) at the DEBUG level with an
     * accompanying message.
     *
     * @param msg the message accompanying the exception
     * @param t   the exception (throwable) to log
     */
    fun debug(msg: String?, t: Throwable?)

    /**
     * Similar to [.isDebugEnabled] method except that the
     * marker data is also taken into account.
     *
     * @param marker The marker data to take into consideration
     * @return True if this Logger is enabled for the DEBUG level,
     * false otherwise.
     */
    fun isDebugEnabled(marker: Marker?): Boolean

    /**
     * Log a message with the specific Marker at the DEBUG level.
     *
     * @param marker the marker data specific to this log statement
     * @param msg    the message string to be logged
     */
    fun debug(marker: Marker?, msg: String?)

    /**
     * This method is similar to [.debug] method except that the
     * marker data is also taken into consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg    the argument
     */
    fun debug(marker: Marker?, format: String?, arg: Any?)

    /**
     * This method is similar to [.debug]
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    fun debug(marker: Marker?, format: String?, arg1: Any?, arg2: Any?)

    /**
     * This method is similar to [.debug]
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker    the marker data specific to this log statement
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    fun debug(marker: Marker?, format: String?, vararg arguments: Any?)

    /**
     * This method is similar to [.debug] method except that the
     * marker data is also taken into consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param msg    the message accompanying the exception
     * @param t      the exception (throwable) to log
     */
    fun debug(marker: Marker?, msg: String?, t: Throwable?)

    /**
     * Is the logger instance enabled for the INFO level?
     *
     * @return True if this Logger is enabled for the INFO level,
     * false otherwise.
     */
    val isInfoEnabled: Boolean

    /**
     * Log a message at the INFO level.
     *
     * @param msg the message string to be logged
     */
    fun info(msg: String?)

    /**
     * Log a message at the INFO level according to the specified format
     * and argument.
     *
     *
     *
     * This form avoids superfluous object creation when the logger
     * is disabled for the INFO level.
     *
     * @param format the format string
     * @param arg    the argument
     */
    fun info(format: String?, arg: Any?)

    /**
     * Log a message at the INFO level according to the specified format
     * and arguments.
     *
     *
     *
     * This form avoids superfluous object creation when the logger
     * is disabled for the INFO level.
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    fun info(format: String?, arg1: Any?, arg2: Any?)

    /**
     * Log a message at the INFO level according to the specified format
     * and arguments.
     *
     *
     *
     * This form avoids superfluous string concatenation when the logger
     * is disabled for the INFO level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an `Object[]` before invoking the method,
     * even if this logger is disabled for INFO. The variants taking
     * [one][.info] and [two][.info]
     * arguments exist solely in order to avoid this hidden cost.
     *
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    fun info(format: String?, vararg arguments: Any?)

    /**
     * Log an exception (throwable) at the INFO level with an
     * accompanying message.
     *
     * @param msg the message accompanying the exception
     * @param t   the exception (throwable) to log
     */
    fun info(msg: String?, t: Throwable?)

    /**
     * Similar to [.isInfoEnabled] method except that the marker
     * data is also taken into consideration.
     *
     * @param marker The marker data to take into consideration
     * @return true if this logger is warn enabled, false otherwise
     */
    fun isInfoEnabled(marker: Marker?): Boolean

    /**
     * Log a message with the specific Marker at the INFO level.
     *
     * @param marker The marker specific to this log statement
     * @param msg    the message string to be logged
     */
    fun info(marker: Marker?, msg: String?)

    /**
     * This method is similar to [.info] method except that the
     * marker data is also taken into consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg    the argument
     */
    fun info(marker: Marker?, format: String?, arg: Any?)

    /**
     * This method is similar to [.info]
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    fun info(marker: Marker?, format: String?, arg1: Any?, arg2: Any?)

    /**
     * This method is similar to [.info]
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker    the marker data specific to this log statement
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    fun info(marker: Marker?, format: String?, vararg arguments: Any?)

    /**
     * This method is similar to [.info] method
     * except that the marker data is also taken into consideration.
     *
     * @param marker the marker data for this log statement
     * @param msg    the message accompanying the exception
     * @param t      the exception (throwable) to log
     */
    fun info(marker: Marker?, msg: String?, t: Throwable?)

    /**
     * Is the logger instance enabled for the WARN level?
     *
     * @return True if this Logger is enabled for the WARN level,
     * false otherwise.
     */
    val isWarnEnabled: Boolean

    /**
     * Log a message at the WARN level.
     *
     * @param msg the message string to be logged
     */
    fun warn(msg: String?)

    /**
     * Log a message at the WARN level according to the specified format
     * and argument.
     *
     *
     *
     * This form avoids superfluous object creation when the logger
     * is disabled for the WARN level.
     *
     * @param format the format string
     * @param arg    the argument
     */
    fun warn(format: String?, arg: Any?)

    /**
     * Log a message at the WARN level according to the specified format
     * and arguments.
     *
     *
     *
     * This form avoids superfluous object creation when the logger
     * is disabled for the WARN level.
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    fun warn(format: String?, arg1: Any?, arg2: Any?)

    /**
     * Log a message at the WARN level according to the specified format
     * and arguments.
     *
     *
     *
     * This form avoids superfluous string concatenation when the logger
     * is disabled for the WARN level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an `Object[]` before invoking the method,
     * even if this logger is disabled for WARN. The variants taking
     * [one][.warn] and [two][.warn]
     * arguments exist solely in order to avoid this hidden cost.
     *
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    fun warn(format: String?, vararg arguments: Any?)

    /**
     * Log an exception (throwable) at the WARN level with an
     * accompanying message.
     *
     * @param msg the message accompanying the exception
     * @param t   the exception (throwable) to log
     */
    fun warn(msg: String?, t: Throwable?)

    /**
     * Similar to [.isWarnEnabled] method except that the marker
     * data is also taken into consideration.
     *
     * @param marker The marker data to take into consideration
     * @return True if this Logger is enabled for the WARN level,
     * false otherwise.
     */
    fun isWarnEnabled(marker: Marker?): Boolean

    /**
     * Log a message with the specific Marker at the WARN level.
     *
     * @param marker The marker specific to this log statement
     * @param msg    the message string to be logged
     */
    fun warn(marker: Marker?, msg: String?)

    /**
     * This method is similar to [.warn] method except that the
     * marker data is also taken into consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg    the argument
     */
    fun warn(marker: Marker?, format: String?, arg: Any?)

    /**
     * This method is similar to [.warn]
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    fun warn(marker: Marker?, format: String?, arg1: Any?, arg2: Any?)

    /**
     * This method is similar to [.warn]
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker    the marker data specific to this log statement
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    fun warn(marker: Marker?, format: String?, vararg arguments: Any?)

    /**
     * This method is similar to [.warn] method
     * except that the marker data is also taken into consideration.
     *
     * @param marker the marker data for this log statement
     * @param msg    the message accompanying the exception
     * @param t      the exception (throwable) to log
     */
    fun warn(marker: Marker?, msg: String?, t: Throwable?)

    /**
     * Is the logger instance enabled for the ERROR level?
     *
     * @return True if this Logger is enabled for the ERROR level,
     * false otherwise.
     */
    val isErrorEnabled: Boolean

    /**
     * Log a message at the ERROR level.
     *
     * @param msg the message string to be logged
     */
    fun error(msg: String?)

    /**
     * Log a message at the ERROR level according to the specified format
     * and argument.
     *
     *
     *
     * This form avoids superfluous object creation when the logger
     * is disabled for the ERROR level.
     *
     * @param format the format string
     * @param arg    the argument
     */
    fun error(format: String?, arg: Any?)

    /**
     * Log a message at the ERROR level according to the specified format
     * and arguments.
     *
     *
     *
     * This form avoids superfluous object creation when the logger
     * is disabled for the ERROR level.
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    fun error(format: String?, arg1: Any?, arg2: Any?)

    /**
     * Log a message at the ERROR level according to the specified format
     * and arguments.
     *
     *
     *
     * This form avoids superfluous string concatenation when the logger
     * is disabled for the ERROR level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an `Object[]` before invoking the method,
     * even if this logger is disabled for ERROR. The variants taking
     * [one][.error] and [two][.error]
     * arguments exist solely in order to avoid this hidden cost.
     *
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    fun error(format: String?, vararg arguments: Any?)

    /**
     * Log an exception (throwable) at the ERROR level with an
     * accompanying message.
     *
     * @param msg the message accompanying the exception
     * @param t   the exception (throwable) to log
     */
    fun error(msg: String?, t: Throwable?)

    /**
     * Similar to [.isErrorEnabled] method except that the
     * marker data is also taken into consideration.
     *
     * @param marker The marker data to take into consideration
     * @return True if this Logger is enabled for the ERROR level,
     * false otherwise.
     */
    fun isErrorEnabled(marker: Marker?): Boolean

    /**
     * Log a message with the specific Marker at the ERROR level.
     *
     * @param marker The marker specific to this log statement
     * @param msg    the message string to be logged
     */
    fun error(marker: Marker?, msg: String?)

    /**
     * This method is similar to [.error] method except that the
     * marker data is also taken into consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg    the argument
     */
    fun error(marker: Marker?, format: String?, arg: Any?)

    /**
     * This method is similar to [.error]
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    fun error(marker: Marker?, format: String?, arg1: Any?, arg2: Any?)

    /**
     * This method is similar to [.error]
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker    the marker data specific to this log statement
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    fun error(marker: Marker?, format: String?, vararg arguments: Any?)

    /**
     * This method is similar to [.error]
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param msg    the message accompanying the exception
     * @param t      the exception (throwable) to log
     */
    fun error(marker: Marker?, msg: String?, t: Throwable?)

    companion object {
        /**
         * Case insensitive String constant used to retrieve the name of the root logger.
         *
         * @since 1.3
         */
        const val ROOT_LOGGER_NAME = "ROOT"
    }
}