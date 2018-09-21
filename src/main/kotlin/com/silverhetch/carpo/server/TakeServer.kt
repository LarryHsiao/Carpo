package com.silverhetch.carpo.server

import com.silverhetch.carpo.Carpo
import org.takes.facets.fork.TkFork
import org.takes.http.FtBasic

class TakeServer(private val carpo: Carpo) : CarpoServer {
    private var exit = true
    override fun start() {
        exit = false
        Thread {
            FtBasic(
                TkFork(
                    TkGetTags(
                        carpo.tags()
                    )
                ), 8080
            ).start { exit }
        }.start()
    }

    override fun shutdown() {
        exit = true
    }

}
