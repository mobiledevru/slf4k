package org.slf4j.event

import org.slf4j.spi.LocationAwareLogger

object EventConstants {
    val ERROR_INT: Int = LocationAwareLogger.ERROR_INT
    val WARN_INT: Int = LocationAwareLogger.WARN_INT
    val INFO_INT: Int = LocationAwareLogger.INFO_INT
    val DEBUG_INT: Int = LocationAwareLogger.DEBUG_INT
    val TRACE_INT: Int = LocationAwareLogger.TRACE_INT
    const val NA_SUBST = "NA/SubstituteLogger"
}