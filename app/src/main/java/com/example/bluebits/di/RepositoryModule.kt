package com.example.bluebits.di

import com.example.bluebits.data.repository.AndroidBluetoothManager
import com.example.bluebits.data.repository.DeviceDiscoveryRepositoryImpl
import com.example.bluebits.domain.repository.BluetoothManager
import com.example.bluebits.domain.repository.DeviceDiscoveryRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {

    single<DeviceDiscoveryRepository> {
        DeviceDiscoveryRepositoryImpl(
            context = androidContext(),
            bluetoothAdapter = get()
        )
    }

    single<BluetoothManager> { AndroidBluetoothManager(androidContext()) }

}