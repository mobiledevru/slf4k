package ru.mobiledev.slf4k.event

import ru.mobiledev.slf4k.Marker
import ru.mobiledev.slf4k.helpers.SubstituteLogger

class SubstituteLoggingEvent : LoggingEvent {
    override var level: Level? = null
    override var marker: Marker? = null
    override var loggerName: String? = null
    var logger: SubstituteLogger? = null
    override var threadName: String? = null
    override var message: String? = null
    override var argumentArray: Array<out Any>? = null
    override var timeStamp: Long = 0
    override var throwable: Throwable? = null

    fun getLogger(): SubstituteLogger? {
        return logger
    }

    fun setLogger(logger: SubstituteLogger?) {
        this.logger = logger
    }
}