package com.example.bluebits.domain.repository

interface BluetoothControllerRepository {
    fun isSupported(): Boolean
    fun isEnabled(): Boolean
}