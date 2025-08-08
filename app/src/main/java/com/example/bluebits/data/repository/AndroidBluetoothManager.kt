package com.example.bluebits.data.repository

import android.bluetooth.BluetoothAdapter
import android.content.Context
import com.example.bluebits.domain.repository.BluetoothManager

class AndroidBluetoothManager(
    private val context: Context
) : BluetoothManager {


    private val bluetoothAdapter: BluetoothAdapter? by lazy {
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as android.bluetooth.BluetoothManager
        bluetoothManager.adapter
    }

    override fun isBluetoothSupported(): Boolean {
        return bluetoothAdapter != null
    }

    override fun isBluetoothEnabled(): Boolean {
        return bluetoothAdapter?.isEnabled ?: false
    }

    override fun enableBluetooth(onResult: (Boolean) -> Unit) {
        if (bluetoothAdapter?.isEnabled == true) {
            onResult(true)
        } else {
            onResult(false)
        }
    }
}