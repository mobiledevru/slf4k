package ru.mobiledev.slf4k


actual interface SerializableAdapter : java.io.Serializable

actual object Adapter {
    actual val platform: String
        get() = "JVM"

    actual fun timeStamp(): Long {
        return System.currentTimeMillis()
//        return kotlin.system.getTimeMillis()
    }

    actual fun className(obj: Any): String = obj.javaClass.name
}