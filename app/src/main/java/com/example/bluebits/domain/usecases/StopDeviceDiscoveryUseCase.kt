package com.example.bluebits.domain.usecases

import com.example.bluebits.domain.model.UserDevice
import com.example.bluebits.domain.repository.DeviceDiscoveryRepository
import kotlinx.coroutines.flow.Flow

class StopDeviceDiscoveryUseCase(
    private val repository: DeviceDiscoveryRepository
) {
    operator fun invoke() {
        return repository.stopDiscovery()
    }
}