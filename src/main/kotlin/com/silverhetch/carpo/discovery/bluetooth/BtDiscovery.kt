package com.silverhetch.carpo.discovery.bluetooth

import com.silverhetch.carpo.discovery.CRemoteDevice
import com.sun.javafx.collections.ObservableListWrapper
import javafx.collections.ObservableList
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import javax.bluetooth.*
import javax.bluetooth.DiscoveryAgent.GIAC

/**
 * Bluetooth device discovery.
 */
class BtDiscovery {
    private lateinit var thread: ScheduledExecutorService
    private val discoveredDevices = ObservableListWrapper<CRemoteDevice>(ArrayList())

    fun devices(): ObservableList<CRemoteDevice> {
        return discoveredDevices
    }

    /**
     * Launch discovery function in fixed rate thread.
     */
    fun start() {
        if (isRunning()) {
            stop()
        }
        thread = Executors.newScheduledThreadPool(1)
        thread.scheduleAtFixedRate({
            /**
             * Don`t care the result of startInquiry which present that this device support this operation
             * Just remains the discover list empty.
             */
            LocalDevice.getLocalDevice().discoveryAgent.startInquiry(GIAC, Listener())
        }, 0, 30, TimeUnit.SECONDS)
    }

    /**
     * @return true if discovery thread alive
     */
    fun isRunning(): Boolean {
        return ::thread.isInitialized && thread.isShutdown.not()
    }

    /**
     * Stop discovery function.
     */
    fun stop() {
        thread.shutdownNow()
    }

    private inner class Listener : DiscoveryListener { // inner class for not expose interface
        private val temp = ArrayList<RemoteDevice>()
        override fun serviceSearchCompleted(transID: Int, respCode: Int) {
            // not handling service discovery
        }

        override fun deviceDiscovered(btDevice: RemoteDevice?, cod: DeviceClass?) {
            if (btDevice != null) {
                temp.add(btDevice)
            }
        }

        override fun servicesDiscovered(transID: Int, servRecord: Array<out ServiceRecord>?) {
            // not handling service discovery
        }

        override fun inquiryCompleted(discType: Int) {
            discoveredDevices.clear()
            discoveredDevices.addAll(Array<CRemoteDevice>(temp.size) {
                BtRemoteDevice(temp[it])
            })
        }
    }
}