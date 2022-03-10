package org.slf4j.event

/**
 *
 * @author ceki
 * @since 1.7.15
 */
enum class Level(private val levelInt: Int, private val levelStr: String) {
    ERROR(EventConstants.ERROR_INT, "ERROR"),
    WARN(EventConstants.WARN_INT, "WARN"),
    INFO(EventConstants.INFO_INT, "INFO"),
    DEBUG(EventConstants.DEBUG_INT, "DEBUG"),
    TRACE(EventConstants.TRACE_INT,"TRACE");

    fun toInt(): Int {
        return levelInt
    }

    /**
     * Returns the string representation of this Level.
     */
    override fun toString(): String {
        return levelStr
    }
}