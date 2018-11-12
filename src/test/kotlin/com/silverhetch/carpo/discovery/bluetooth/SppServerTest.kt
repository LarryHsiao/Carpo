package com.silverhetch.carpo.discovery.bluetooth

import com.silverhetch.carpo.discovery.bluetooth.server.SppServer
import org.junit.Assert
import org.junit.Test
import javax.bluetooth.UUID

class SppServerTest {

    @Test
    fun simple() {
        SppServer(
            UUID("07d4a697ece518a7d647ca12730cc5e6", false),
            "CarpoServer"
        ).let { server ->
            server.launch {
                System.out.println("Received $it")
                "response"
            }
            Thread.sleep(5000000)
            server.shutdown()
            Thread.sleep(500)
            Assert.assertFalse(server.isRunning())
        }
    }
}