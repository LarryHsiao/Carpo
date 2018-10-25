package com.silverhetch.carpo.server

/**
 * Carpo style of server.
 */
interface Server {
    /**
     * Start the server
     */
    fun start()

    /**
     * Stop the server instance
     */
    fun stop()

    /**
     * Server running.
     */
    fun isRunning(): Boolean

    /**
     * Returns current hostname
     */
    fun hostname(): String

    /**
     * Current port.
     */
    fun port(): Int
}