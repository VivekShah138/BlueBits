package com.example.bluebits.data.repository

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.example.bluebits.domain.model.UserDevice
import com.example.bluebits.domain.repository.DeviceDiscoveryRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import android.bluetooth.BluetoothDevice as AndroidBluetoothDevice

private const val TAG = "DeviceDiscoverRepository"

class DeviceDiscoveryRepositoryImpl(
    private val context: Context,
    private val bluetoothAdapter: BluetoothAdapter?
): DeviceDiscoveryRepository {

    private var discoveryReceiver: BroadcastReceiver? = null

    override fun startDiscovery(): Flow<UserDevice> = callbackFlow {

        if (bluetoothAdapter!!.isDiscovering) {
            bluetoothAdapter.cancelDiscovery()
            delay(1000) // wait 1 sec for system to settle
        }

        val started = bluetoothAdapter!!.startDiscovery()

        if (!started) {
            val err = "Failed to start discovery"
            Log.e(TAG, "❌ $err")
            close(Exception(err))
            return@callbackFlow
        }

        // Setup BroadcastReceiver for found devices
        discoveryReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val action = intent?.action
                if (action == BluetoothDevice.ACTION_FOUND) {
                    val device: AndroidBluetoothDevice? =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE, AndroidBluetoothDevice::class.java)
                    device?.let {
                        Log.d(TAG, "📡 Device found: ${it.name} / ${it.address}")
                        trySend(
                            UserDevice(
                                name = it.name,
                                address = it.address,
                                isPaired = it.bondState == AndroidBluetoothDevice.BOND_BONDED,
                                isConnected = false
                            )
                        )
                    }
                }
            }
        }
        context.registerReceiver(
            discoveryReceiver,
            IntentFilter(BluetoothDevice.ACTION_FOUND)
        )

        // Wait for cancellation/close
        awaitClose {
            Log.d("DeviceDiscoveryRepo", "Stopping discovery and unregistering receiver...")
            try {
                bluetoothAdapter.cancelDiscovery()
            } catch (e: Exception) {
                Log.e("DeviceDiscoveryRepo", "Error cancelling discovery: ${e.message}")
            }
            try {
                discoveryReceiver?.let { context.unregisterReceiver(it) }
            } catch (e: Exception) {
                Log.e("DeviceDiscoveryRepo", "Error unregistering receiver: ${e.message}")
            }
        }
    }

    override fun stopDiscovery() {
        try {
            if (bluetoothAdapter?.isDiscovering == true) {
                bluetoothAdapter.cancelDiscovery()
                Log.d("DeviceDiscoveryRepo", "Discovery stopped by stopDiscovery()")
            }
        } catch (e: Exception) {
            Log.e("DeviceDiscoveryRepo", "Error stopping discovery: ${e.message}")
        }
    }
}
