package com.silverhetch.carpo.discovery.bluetooth

import org.junit.Assert
import org.junit.Test

class SppServerTest {

    @Test
    fun simple() {
        SppServer().let { server ->
            server.launch { "" }
            Thread.sleep(500)
            server.shutdown()
            Thread.sleep(500)
            Assert.assertFalse(server.isRunning())
        }
    }
}