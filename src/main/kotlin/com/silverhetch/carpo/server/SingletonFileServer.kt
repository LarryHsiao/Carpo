package com.silverhetch.carpo.server

import com.silverhetch.carpo.Carpo

object SingletonFileServer {
    private lateinit var fileServer: Server

    fun getInstance(carpo: Carpo): Server {
        if (!::fileServer.isInitialized) {
            fileServer = NanoHttpdFileServer(carpo.workspace().rootJFile())
        }
        return fileServer
    }
}