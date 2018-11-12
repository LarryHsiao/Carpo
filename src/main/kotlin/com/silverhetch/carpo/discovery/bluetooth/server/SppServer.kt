package com.silverhetch.carpo.discovery.bluetooth.server

import javax.bluetooth.DiscoveryAgent
import javax.bluetooth.LocalDevice
import javax.bluetooth.UUID
import javax.microedition.io.Connector
import javax.microedition.io.StreamConnectionNotifier

/**
 * Text base Server implemented with Serial Port Profile(SPP).
 */
class SppServer(private val uuid: UUID, private val serverName: String) {
    private lateinit var serverThread: Thread
    private lateinit var server: StreamConnectionNotifier
    private val clientConn = ArrayList<ClientConnection>()
    private var running = false

    /**
     * launch SPP server.
     */
    fun launch(onMessage: (msg: String) -> String) {
        running = true
        serverThread = Thread {
            try {
                LocalDevice.getLocalDevice().discoverable = DiscoveryAgent.GIAC
                server = Connector.open("btspp://localhost:$uuid;name=$serverName;") as StreamConnectionNotifier
                while (running) {
                    clientConn.add(ClientConnection(server.acceptAndOpen(), onMessage).apply {
                        launch()
                    })
                }
            } catch (ignore: InterruptedException) {
                // thrown if shutdown invoked which we don`t handle
            }
        }.apply { start() }
    }

    /**
     * Shutdown SPP server.
     */
    fun shutdown() {
        if (serverThread.isAlive) {
            running = false

            if (::server.isInitialized) {
                server.close()
            }

            clientConn.forEach {
                it.close()
            }
        }
    }

    /**
     * @return true if SPP Server alive.
     */
    fun isRunning(): Boolean {
        return serverThread.isAlive
    }
}