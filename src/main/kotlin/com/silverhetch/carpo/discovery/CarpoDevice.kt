package com.silverhetch.carpo.discovery

import javax.annotation.PreDestroy

/**
 * Carpo device expose this device self to others and maintain device list we found.
 */
interface CarpoDevice {
    /**
     * Launch this device and its functions.
     * - Device discovery
     * - Device exposing
     */
    fun launch()

    /**
     * Shutdown this device and its functions.
     */
    fun shutdown()
}