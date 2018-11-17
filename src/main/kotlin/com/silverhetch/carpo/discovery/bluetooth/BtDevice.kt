package com.silverhetch.carpo.discovery.bluetooth

import com.silverhetch.carpo.discovery.CarpoDevice
import com.silverhetch.carpo.discovery.bluetooth.server.SppServer
import javax.bluetooth.UUID


/**
 * Carpo device implement with JSR-82.
 */
class BtDevice() : CarpoDevice {
    private val discovery: BtDiscovery = BtDiscovery()
    private val sppserver = SppServer(
        UUID("07d4a697ece518a7d647ca12730cc5e6", false),
        "CarpoServer"
    )

    override fun launch() {
        discovery.start()
        sppserver.launch { conn, it ->
            ""
        }
    }

    override fun shutdown() {
        discovery.stop()
        sppserver.shutdown()
    }
}