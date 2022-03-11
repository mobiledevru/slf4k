package org.slf4j

import ru.mobiledev.slf4k.Adapter

actual object LoggerFactory {
    actual fun getLogger(name: String): Logger {
        TODO("Not yet implemented for ${Adapter.platform}")
    }
}