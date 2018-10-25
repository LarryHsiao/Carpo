package com.silverhetch.carpo.server

import com.silverhetch.carpo.Carpo
import fi.iki.elonen.SimpleWebServer
import java.io.File
import java.net.InetAddress

class NanoHttpdFileServer(rootFile: File) : Server {
    private val server = SimpleWebServer(
        InetAddress.getLocalHost().hostName,
        0,
        rootFile,
        true
    )

    override fun start() {
        server.start()
    }

    override fun hostname(): String {
        return server.hostname
    }

    override fun port(): Int {
        return server.listeningPort
    }

    override fun stop() {
        server.stop()
    }
}