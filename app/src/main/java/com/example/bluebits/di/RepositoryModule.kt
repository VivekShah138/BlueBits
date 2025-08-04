package com.example.bluebits.di

import android.bluetooth.BluetoothAdapter
import android.content.Context
import com.example.bluebits.data.repository.DeviceDiscoveryRepositoryImpl
import com.example.bluebits.domain.repository.DeviceDiscoveryRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {

    // Provide BluetoothAdapter
    single<BluetoothAdapter?> {
        BluetoothAdapter.getDefaultAdapter()
    }

    // Provide DeviceDiscoveryRepository
    single<DeviceDiscoveryRepository> {
        DeviceDiscoveryRepositoryImpl(
            context = androidContext(),
            bluetoothAdapter = get()
        )
    }

}