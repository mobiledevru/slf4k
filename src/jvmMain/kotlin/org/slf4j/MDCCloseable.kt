package org.slf4j

/**
 * Put a diagnostic context value (the `val` parameter) as identified with the
 * `key` parameter into the current thread's diagnostic context map. The
 * `key` parameter cannot be null. The `val` parameter
 * can be null only if the underlying implementation supports it.
 *
 *
 *
 * This method delegates all work to the MDC of the underlying logging system.
 *
 *
 * This method return a `Closeable` object who can remove `key` when
 * `close` is called.
 *
 *
 *
 * Useful with Java 7 for example :
 * `
 * try(MDC.MDCCloseable closeable = MDC.putCloseable(key, value)) {
 * ....
 * }
` *
 *
 * @param key non-null key
 * @param val value to put in the map
 * @return a `Closeable` who can remove `key` when `close`
 * is called.
 *
 * @throws IllegalArgumentException
 * in case the "key" parameter is null
 */
@Throws(IllegalArgumentException::class)
fun MDC.putCloseable(key: String, `val`: String?): MDCCloseable {
    put(key, `val`)
    return MDCCloseable(key)
}

/**
 * An adapter to remove the key when done.
 */
class MDCCloseable(private val key: String) : java.io.Closeable {
    override fun close() {
        MDC.remove(key)
    }
}