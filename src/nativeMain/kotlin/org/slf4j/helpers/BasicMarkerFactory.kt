package org.slf4j.helpers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import org.slf4j.IMarkerFactory
import org.slf4j.Marker

/**
 * An almost trivial implementation of the {@link IMarkerFactory}
 * interface which creates {@link BasicMarker} instances.
 *
 * <p>Simple logging systems can conform to the SLF4J API by binding
 * {@link org.slf4j.MarkerFactory} with an instance of this class.
 *
 * @author Ceki G&uuml;lc&uuml;
 * @author Golubev Dmitrii
 */
class BasicMarkerFactory : IMarkerFactory {

    private val markerMap: HashMap<String, Marker> = HashMap()
    private val markerMapContext = Dispatchers.Default
    private val markerMapLock = Mutex(false)

    /**
     * Regular users should *not* create
     * `BasicMarkerFactory` instances. `Marker`
     * instances can be obtained using the static [ ][org.slf4j.MarkerFactory.getMarker] method.
     */
    fun init() {}

    /**
     * Manufacture a [BasicMarker] instance by name. If the instance has been
     * created earlier, return the previously created instance.
     *
     * @param name the name of the marker to be created
     * @return a Marker instance
     */
    override fun getMarker(name: String): Marker {
        var marker: Marker? = markerMap.get(name)
        if (marker == null) {
            val newMarker: Marker
            runBlocking {
                withContext(markerMapContext) {
                    markerMapLock.withLock {
                        newMarker = BasicMarker(name)
                        marker = newMarker
                        val oldMarker = markerMap.get(name)
                        if (oldMarker != null) {
                            marker = oldMarker
                        } else {
                            markerMap.put(name, newMarker)
                        }
                    }
                }
            }
            return newMarker
        } else {
            return marker as Marker
        }
    }

    /**
     * Does the name marked already exist?
     */
    override fun exists(name: String?): Boolean {
        return if (name == null) {
            false
        } else {
            markerMap.containsKey(name)
        }
    }

    override fun detachMarker(name: String?): Boolean {
        if (name == null) {
            return false
        } else {
            val result: Boolean
            runBlocking {
                withContext(markerMapContext) {
                    markerMapLock.withLock {
                        result = markerMap.remove(name) != null
                    }
                }
            }
            return result;
        }
    }

    override fun getDetachedMarker(name: String): Marker {
        return BasicMarker(name)
    }
}