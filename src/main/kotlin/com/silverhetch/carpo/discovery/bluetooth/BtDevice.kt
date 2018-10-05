package com.silverhetch.carpo.discovery.bluetooth

import com.silverhetch.carpo.discovery.CarpoDevice


/**
 * Carpo device implement with JSR-82.
 */
class BtDevice() : CarpoDevice {
    private val discovery: BtDiscovery = BtDiscovery()
    private val sppserver = SppServer ()

    override fun launch() {
        discovery.start()
        sppserver.launch{
            ""
        }
    }

    override fun shutdown() {
        discovery.stop()
        sppserver.shutdown()
    }
}