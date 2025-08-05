package com.example.bluebits.di

import com.example.bluebits.data.repository.AndroidBluetoothControllerRepository
import com.example.bluebits.data.repository.DeviceDiscoveryRepositoryImpl
import com.example.bluebits.domain.repository.BluetoothControllerRepository
import com.example.bluebits.domain.repository.DeviceDiscoveryRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {

    // Provide DeviceDiscoveryRepository
    single<DeviceDiscoveryRepository> {
        DeviceDiscoveryRepositoryImpl(
            context = androidContext(),
            bluetoothAdapter = get()
        )
    }

    single<BluetoothControllerRepository> { AndroidBluetoothControllerRepository(get()) }

}