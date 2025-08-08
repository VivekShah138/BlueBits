package com.example.bluebits.domain.repository

interface BluetoothManager {
    fun isBluetoothSupported(): Boolean
    fun isBluetoothEnabled(): Boolean
    fun enableBluetooth(onResult: (Boolean) -> Unit)
}