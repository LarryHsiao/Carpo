package com.silverhetch.carpo.discovery.bluetooth

import java.io.ByteArrayOutputStream
import javax.bluetooth.DiscoveryAgent
import javax.bluetooth.LocalDevice
import javax.microedition.io.Connector
import javax.microedition.io.StreamConnectionNotifier

/**
 * Server implemented with Serial Port Profile(SPP).
 */
class SppServer() {
    private lateinit var thread: Thread
    private lateinit var server: StreamConnectionNotifier
    private val uuid = javax.bluetooth.UUID("1101", true)
    private var running = false

    /**
     * launch SPP server.
     */
    fun launch(onMessage: (msg: String) -> String) {
        running = true
        thread = Thread {
            try {
                LocalDevice.getLocalDevice().discoverable = DiscoveryAgent.GIAC
                server = Connector.open("btspp://localhost:$uuid;name=ObexPushServer;") as StreamConnectionNotifier
                val buffer = ByteArray(4096)
                while (running) {
                    val connection = server.acceptAndOpen()
                    val requestStream = connection.openInputStream()
                    val result = ByteArrayOutputStream()
                    var length: Int = requestStream.read(buffer)
                    while (length != -1) {
                        result.write(buffer, 0, length)
                        length = requestStream.read(buffer)
                    }
                    result.close()
                    requestStream.close()
                    val response = onMessage(result.toString("utf-8"))
                    val responseStream = connection.openOutputStream()
                    responseStream.write(response.toByteArray())

                    responseStream.close()
                    connection.close()
                }
            } catch (ignore: InterruptedException) {
            }
        }.apply { start() }
    }

    /**
     * Shutdown SPP server.
     */
    fun shutdown() {
        if (thread.isAlive) {
            running = false
            server.close()
        }
    }

    /**
     * @return true if SPP Server alive.
     */
    fun isRunning(): Boolean {
        return thread.isAlive
    }
}