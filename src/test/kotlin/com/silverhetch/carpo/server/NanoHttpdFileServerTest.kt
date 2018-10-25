package com.silverhetch.carpo.server

import com.silverhetch.clotho.connection.Get
import com.silverhetch.clotho.connection.TargetImpl
import org.junit.Assert
import org.junit.Test
import java.io.File

class NanoHttpdFileServerTest {
    @Test
    fun simple() {
        val server: Server = NanoHttpdFileServer(
            File(System.getProperty("user.dir"))
        )

        server.start()

        Get(
            TargetImpl(
                "http://${server.hostname()}:${server.port()}"
            )
        ).request().let { response ->
            Assert.assertEquals(
                200,
                response.code
            )
            Assert.assertFalse(
                String(response.bodyBytes).isEmpty()
            )
        }
    }
}