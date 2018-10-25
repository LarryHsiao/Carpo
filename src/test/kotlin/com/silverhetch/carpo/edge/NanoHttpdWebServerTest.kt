package com.silverhetch.carpo.edge

import fi.iki.elonen.SimpleWebServer
import org.junit.Ignore
import org.junit.Test
import java.io.File
import java.lang.Thread.sleep
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.concurrent.TimeUnit.SECONDS

@Ignore("Edge test")
class NanoHttpdWebServerTest {
    @Test
    fun simple() {
        SimpleWebServer(
            InetAddress.getLocalHost().hostAddress,
            8081,
            File(System.getProperty("user.dir")),
            true
        ).start()

        sleep(SECONDS.toMillis(3000))
    }
}