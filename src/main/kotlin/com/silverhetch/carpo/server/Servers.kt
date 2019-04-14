package com.silverhetch.carpo.server

/**
 * Represents Servers using in Carpo.
 */
interface Servers {
    /**
     * File server instance.
     */
    fun fileServer(): Server
}