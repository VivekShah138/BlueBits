package com.example.bluebits.domain.usecases

import com.example.bluebits.domain.model.UserDevice
import com.example.bluebits.domain.repository.DeviceDiscoveryRepository
import kotlinx.coroutines.flow.Flow

class StartDeviceDiscoveryUseCase(
    private val repository: DeviceDiscoveryRepository
) {
    operator fun invoke(): Flow<UserDevice>  {
        return repository.startDiscovery()
    }
}