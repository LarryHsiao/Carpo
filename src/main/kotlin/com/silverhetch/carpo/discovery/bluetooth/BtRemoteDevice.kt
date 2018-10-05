package com.silverhetch.carpo.discovery.bluetooth

import com.silverhetch.carpo.discovery.CRemoteDevice
import javax.bluetooth.RemoteDevice

/**
 * Bluetooth remote device.
 */
class BtRemoteDevice(private val remoteDevice: RemoteDevice) : CRemoteDevice {
    override fun name(): String {

        return remoteDevice.getFriendlyName(false)
    }
}