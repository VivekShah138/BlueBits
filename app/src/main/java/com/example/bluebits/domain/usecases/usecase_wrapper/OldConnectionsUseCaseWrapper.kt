package com.example.bluebits.domain.usecases.usecase_wrapper

import com.example.bluebits.domain.usecases.device_discovery.StartDeviceDiscoveryUseCase
import com.example.bluebits.domain.usecases.device_discovery.StopDeviceDiscoveryUseCase

data class OldConnectionsUseCaseWrapper(
    val startDeviceDiscoveryUseCase: StartDeviceDiscoveryUseCase,
    val stopDeviceDiscoveryUseCase: StopDeviceDiscoveryUseCase,
)
