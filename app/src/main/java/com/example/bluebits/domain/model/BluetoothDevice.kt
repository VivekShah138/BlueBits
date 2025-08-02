package com.example.bluebits.domain.model

data class BluetoothDevice(
    val name: String?,
    val address: String,
    val isPaired: Boolean,
    val isConnected: Boolean = false
)
