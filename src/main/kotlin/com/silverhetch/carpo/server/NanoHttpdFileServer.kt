package com.silverhetch.carpo.server

import fi.iki.elonen.SimpleWebServer
import java.io.File
import java.net.InetAddress

class NanoHttpdFileServer(rootFile: File) : Server {
    private val server = SimpleWebServer(
        "localhost",
        0,
        rootFile,
        true
    )

    override fun start() {
        server.start()
    }

    override fun isRunning(): Boolean {
        return server.isAlive
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