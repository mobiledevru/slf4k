package ru.mobiledev.slf4k

import org.slf4j.LoggerFactory
import kotlin.test.Test

class LoggerTest {

    @Test
    fun testHello() {
        val logger = LoggerFactory.getLogger("test-logger")
        logger.debug("test message from JS LoggerTest")

        val logger2 = LoggerFactory.getLogger(LoggerTest::class)
        logger2.debug("test message from JS LoggerTest")
    }
}