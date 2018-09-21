package com.silverhetch.carpo.server

import org.takes.facets.fork.FkRegex
import org.takes.facets.fork.TkFork
import org.takes.http.FtBasic

class TakeServer : CarpoServer {
    private var exit = true
    override fun start() {
        exit = false
        Thread {
            FtBasic(
                TkFork(
                    FkRegex("/", "Hello world!")
                ), 8080
            ).start { exit }
        }.start()
    }

    override fun shutdown() {
        exit = true
    }

}
