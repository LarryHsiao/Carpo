package com.silverhetch.carpo.edge

import org.junit.Ignore
import org.junit.Test
import java.io.IOException
import java.util.*
import javax.bluetooth.*

@Ignore("Ignore edge tests")
class BlueCoveTest {
    @Test
    fun simple() {
        val devicesDiscovered = Vector<RemoteDevice>()
        val inquiryCompletedEvent = Object()

        val listener = object : DiscoveryListener {
            override fun deviceDiscovered(btDevice: RemoteDevice, cod: DeviceClass) {
                println("Device " + btDevice.bluetoothAddress + " found")
                devicesDiscovered.addElement(btDevice)
                try {
                    println("     name " + btDevice.getFriendlyName(false))
                } catch (cantGetDeviceName: IOException) {
                }
            }

            override fun inquiryCompleted(discType: Int) {
                println("Device Inquiry completed!")
                synchronized(inquiryCompletedEvent) {
                    inquiryCompletedEvent.notifyAll()
                }
            }

            override fun serviceSearchCompleted(transID: Int, respCode: Int) {
                /*leave empty*/
            }

            override fun servicesDiscovered(transID: Int, servRecord: Array<ServiceRecord>) {
                /*leave empty*/
            }
        }

        synchronized(inquiryCompletedEvent) {
            val started = LocalDevice.getLocalDevice().discoveryAgent.startInquiry(DiscoveryAgent.GIAC, listener)
            if (started) {
                println("wait for device inquiry to complete...")
                inquiryCompletedEvent.wait()
                println(devicesDiscovered.size.toString() + " device(s) found")
            }
        }
    }
}