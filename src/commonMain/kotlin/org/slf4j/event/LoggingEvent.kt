package org.slf4j.event

import org.slf4j.Marker

/**
 *
 * @author ceki
 * @since 1.7.15
 */
interface LoggingEvent {
    val level: Level?
    val marker: Marker?
    val loggerName: String?
    val message: String?
    val threadName: String?
    val argumentArray: Array<out Any?>?
    val timeStamp: Long?
    val throwable: Throwable?
}