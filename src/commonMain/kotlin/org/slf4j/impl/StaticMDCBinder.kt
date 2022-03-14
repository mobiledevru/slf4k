package org.slf4j.impl

import org.slf4j.spi.MDCAdapter

expect class StaticMDCBinder {

    /**
     * Currently this method always returns an instance of
     * [StaticMDCBinder].
     */
    fun getMDCA(): MDCAdapter

    fun getMDCAdapterClassStr(): String
}