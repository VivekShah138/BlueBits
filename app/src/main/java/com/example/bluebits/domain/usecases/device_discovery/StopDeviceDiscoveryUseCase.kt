package com.example.bluebits.domain.usecases.device_discovery

import com.example.bluebits.domain.repository.DeviceDiscoveryRepository

class StopDeviceDiscoveryUseCase(
    private val repository: DeviceDiscoveryRepository
) {
    operator fun invoke() {
        return repository.stopDiscovery()
    }
}