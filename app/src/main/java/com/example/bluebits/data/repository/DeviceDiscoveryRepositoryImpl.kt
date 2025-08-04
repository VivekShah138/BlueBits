//package com.example.bluebits.data.repository
//
//import android.bluetooth.BluetoothAdapter
//import android.bluetooth.BluetoothDevice
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.content.IntentFilter
//import com.example.bluebits.Manifest
//import com.example.bluebits.domain.model.UserDevice
//import com.example.bluebits.domain.repository.DeviceDiscoveryRepository
//import com.example.bluebits.utils.isPermisssionGranted
//import kotlinx.coroutines.channels.awaitClose
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.callbackFlow
//import android.bluetooth.BluetoothDevice as AndroidBluetoothDevice
//
//class DeviceDiscoveryRepositoryImpl(
//    private val context: Context,
//    private val bluetoothAdapter: BluetoothAdapter?
//): DeviceDiscoveryRepository {
//
//    private var discoveryReceiver: BroadcastReceiver? = null
//
//    override fun startDiscovery(): Flow<UserDevice> = callbackFlow{
//        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled) {
//            close(Exception("Bluetooth is disabled or unavailable"))
//            return@callbackFlow
//        }
//
//        if (bluetoothAdapter.isDiscovering) {
//            bluetoothAdapter.cancelDiscovery()
//        }
//
//        discoveryReceiver = object : BroadcastReceiver() {
//            override fun onReceive(context: Context?, intent: Intent?) {
//                if (intent?.action == BluetoothDevice.ACTION_FOUND) {
//                    val device: AndroidBluetoothDevice? =
//                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
//                    device?.let {
//                        trySend(
//                            UserDevice(
//                                name = it.name,
//                                address = it.address,
//                                isPaired = it.bondState == AndroidBluetoothDevice.BOND_BONDED,
//                                isConnected = false // Could update with connection info later
//                            )
//                        )
//                    }
//                }
//            }
//
//        }
//
//    }
//
//    override fun stopDiscovery() {
//
//
//    }
//}

//package com.example.bluebits.data.repository
//
//import android.Manifest
//import android.bluetooth.BluetoothAdapter
//import android.bluetooth.BluetoothDevice
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.content.IntentFilter
//import android.util.Log
//import com.example.bluebits.domain.model.UserDevice
//import com.example.bluebits.domain.repository.DeviceDiscoveryRepository
//import com.example.bluebits.utils.isPermisssionGranted
//import kotlinx.coroutines.channels.awaitClose
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.callbackFlow
//import android.bluetooth.BluetoothDevice as AndroidBluetoothDevice
//
//class DeviceDiscoveryRepositoryImpl(
//    private val context: Context,
//    private val bluetoothAdapter: BluetoothAdapter?
//) : DeviceDiscoveryRepository {
//
//    private var discoveryReceiver: BroadcastReceiver? = null
//
////    override fun startDiscovery(): Flow<UserDevice> = callbackFlow {
////        // Permission checks for scanning
////        if (!context.isPermisssionGranted(Manifest.permission.BLUETOOTH_SCAN)) {
////            close(Exception("BLUETOOTH_SCAN permission not granted"))
////            return@callbackFlow
////        }
////        // Permission checks for accessing device info
////        if (!context.isPermisssionGranted(Manifest.permission.BLUETOOTH_CONNECT)) {
////            close(Exception("BLUETOOTH_CONNECT permission not granted"))
////            return@callbackFlow
////        }
////
////        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled) {
////            close(Exception("Bluetooth is disabled or unavailable"))
////            return@callbackFlow
////        }
////
////        if (bluetoothAdapter.isDiscovering) {
////            bluetoothAdapter.cancelDiscovery()
////        }
////
////        discoveryReceiver = object : BroadcastReceiver() {
////            override fun onReceive(context: Context?, intent: Intent?) {
////                if (intent?.action == BluetoothDevice.ACTION_FOUND) {
////                    val device: AndroidBluetoothDevice? =
////                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
////                    device?.let {
////                        // No additional permission needed here because checked already above
////                        trySend(
////                            UserDevice(
////                                name = it.name,
////                                address = it.address,
////                                isPaired = it.bondState == AndroidBluetoothDevice.BOND_BONDED,
////                                isConnected = false
////                            )
////                        ).isSuccess
////                    }
////                }
////            }
////        }
////
////        // Register receiver with proper intent filter
////        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
////        context.registerReceiver(discoveryReceiver, filter)
////
////        // Start discovery
////        bluetoothAdapter.startDiscovery()
////
////        awaitClose {
////            // Cleanup: unregister receiver and cancel discovery
////            try {
////                context.unregisterReceiver(discoveryReceiver)
////            } catch (e: IllegalArgumentException) {
////                // Receiver was not registered, ignore
////            }
////            discoveryReceiver = null
////            if (bluetoothAdapter.isDiscovering) {
////                bluetoothAdapter.cancelDiscovery()
////            }
////        }
////    }
//
//    override fun startDiscovery(): Flow<UserDevice> = callbackFlow {
//        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled) {
//            close(Exception("Bluetooth is disabled or unavailable"))
//            return@callbackFlow
//        }
//
//        if (!context.isPermisssionGranted(Manifest.permission.BLUETOOTH_SCAN)) {
//            close(Exception("BLUETOOTH_SCAN permission not granted"))
//            return@callbackFlow
//        }
//
//        if (bluetoothAdapter.isDiscovering) {
//            bluetoothAdapter.cancelDiscovery()
//        }
//
//        discoveryReceiver = object : BroadcastReceiver() {
//            override fun onReceive(context: Context?, intent: Intent?) {
//                val action = intent?.action
//                Log.d("DeviceDiscoveryRepo", "🔔 Broadcast received: $action")
//
//                if (action == BluetoothDevice.ACTION_FOUND) {
//                    val device: AndroidBluetoothDevice? =
//                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
//                    device?.let {
//                        Log.d("DeviceDiscoveryRepo", "📡 Found device: ${it.name} | ${it.address}")
//                        trySend(
//                            UserDevice(
//                                name = it.name,
//                                address = it.address,
//                                isPaired = it.bondState == AndroidBluetoothDevice.BOND_BONDED,
//                                isConnected = false
//                            )
//                        )
//                    }
//                } else if (action == BluetoothAdapter.ACTION_DISCOVERY_FINISHED) {
//                    Log.d("DeviceDiscoveryRepo", "✅ Discovery finished.")
//                    close() // or keep open if continuous
//                }
//            }
//        }
//
//        context.registerReceiver(discoveryReceiver, IntentFilter(BluetoothDevice.ACTION_FOUND))
//        context.registerReceiver(discoveryReceiver, IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED))
//
//        val started = bluetoothAdapter.startDiscovery()
//        Log.d("DeviceDiscoveryRepo", "🚀 Discovery started: $started")
//
//        awaitClose {
//            context.unregisterReceiver(discoveryReceiver)
//            bluetoothAdapter.cancelDiscovery()
//        }
//    }
//
//
//    override fun stopDiscovery() {
//        if (bluetoothAdapter?.isDiscovering == true) {
//            bluetoothAdapter.cancelDiscovery()
//        }
//        discoveryReceiver?.let {
//            try {
//                context.unregisterReceiver(it)
//            } catch (e: IllegalArgumentException) {
//                // ignore if receiver not registered
//            }
//            discoveryReceiver = null
//        }
//    }
//}


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
import com.example.bluebits.utils.isPermisssionGranted
import kotlinx.coroutines.delay
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import android.bluetooth.BluetoothDevice as AndroidBluetoothDevice

class DeviceDiscoveryRepositoryImpl(
    private val context: Context,
    private val bluetoothAdapter: BluetoothAdapter?
): DeviceDiscoveryRepository {

    private var discoveryReceiver: BroadcastReceiver? = null

    override fun startDiscovery(): Flow<UserDevice> = callbackFlow {
        Log.d("DeviceDiscoveryRepo", "🌐 startDiscovery() called")

        // Check adapter availability and enabled state
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled) {
            val err = "Bluetooth is disabled or unavailable"
            Log.e("DeviceDiscoveryRepo", "❌ $err")
            close(Exception(err))
            return@callbackFlow
        }

        // Check runtime permissions
        if (!context.isPermisssionGranted(android.Manifest.permission.BLUETOOTH_SCAN)) {
            val err = "BLUETOOTH_SCAN permission not granted"
            Log.e("DeviceDiscoveryRepo", "❌ $err")
            close(Exception(err))
            return@callbackFlow
        }
        if (!context.isPermisssionGranted(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            val err = "ACCESS_FINE_LOCATION permission not granted"
            Log.e("DeviceDiscoveryRepo", "❌ $err")
            close(Exception(err))
            return@callbackFlow
        }

        Log.d("DeviceDiscoveryRepo", "Bluetooth Enabled: ${bluetoothAdapter.isEnabled}")
        Log.d("DeviceDiscoveryRepo", "Scan permission granted: true")
        Log.d("DeviceDiscoveryRepo", "Fine location permission granted: true")
        Log.d("DeviceDiscoveryRepo", "Is Discovering (before start): ${bluetoothAdapter.isDiscovering}")

        // Cancel any existing discovery before starting new one
        if (bluetoothAdapter.isDiscovering) {
            Log.d("DeviceDiscoveryRepo", "Cancelling ongoing discovery before starting new one...")
            bluetoothAdapter.cancelDiscovery()
            delay(1000) // wait 1 sec for system to settle
        }

        val started = bluetoothAdapter.startDiscovery()
        Log.d("DeviceDiscoveryRepo", "🚀 Discovery started: $started")

        if (!started) {
            val err = "Failed to start discovery"
            Log.e("DeviceDiscoveryRepo", "❌ $err")
            close(Exception(err))
            return@callbackFlow
        }

        // Setup BroadcastReceiver for found devices
        discoveryReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val action = intent?.action
                if (action == BluetoothDevice.ACTION_FOUND) {
                    val device: AndroidBluetoothDevice? =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    device?.let {
                        Log.d("DeviceDiscoveryRepo", "📡 Device found: ${it.name} / ${it.address}")
                        trySend(
                            UserDevice(
                                name = it.name,
                                address = it.address,
                                isPaired = it.bondState == AndroidBluetoothDevice.BOND_BONDED,
                                isConnected = false // you can enhance connection info later
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
