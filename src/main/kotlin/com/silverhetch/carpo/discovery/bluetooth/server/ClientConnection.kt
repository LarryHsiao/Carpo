package com.silverhetch.carpo.discovery.bluetooth.server

import com.silverhetch.clotho.connection.socket.TextBaseConn
import com.silverhetch.clotho.connection.socket.TextBaseConnImpl
import javax.microedition.io.StreamConnection

/**
 * Represent a client connection.
 */
class ClientConnection(private val connection: StreamConnection, private val proceedMessage: (TextBaseConn, String) -> Unit) : TextBaseConn {
    private lateinit var textBaseConnectionImpl: TextBaseConnImpl

    override fun launch() {
        textBaseConnectionImpl = TextBaseConnImpl(
            connection.openDataInputStream().bufferedReader(),
            connection.openOutputStream().bufferedWriter(),
            proceedMessage
        )
        textBaseConnectionImpl.launch()
    }

    override fun send(msg: String) {
        textBaseConnectionImpl.send(msg)
    }

    override fun close() {
        connection.close()
    }
}