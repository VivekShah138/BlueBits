package com.example.bluebits.di

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataSourceModule = module {

    // Provide BluetoothAdapter
    single<BluetoothAdapter?> {
        val context = androidContext()
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }
}