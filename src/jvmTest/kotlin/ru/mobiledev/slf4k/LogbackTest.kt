package ru.mobiledev.slf4k

import org.junit.Test
import org.slf4j.LoggerFactory
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class LogbackTest {
    @Test
    fun testHello(){
        val logback = Class.forName("ch.qos.logback.classic.Logger")
        assertNotNull(logback) // in fact, ClassNotFoundException already will be thrown
        val logger = LoggerFactory.getLogger("test-logger")
        logger.debug("test")
    }
}