package com.silverhetch.carpo.server

import com.silverhetch.carpo.Carpo

/**
 * Implementation of [Servers].
 */
class CarpoServers(private val carpo: Carpo) : Servers {

    override fun fileServer(): Server {
        return SingletonFileServer.getInstance(carpo)
    }


}