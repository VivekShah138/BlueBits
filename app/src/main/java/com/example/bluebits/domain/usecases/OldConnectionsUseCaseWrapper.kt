package com.example.bluebits.domain.usecases

data class OldConnectionsUseCaseWrapper(
    val startDeviceDiscoveryUseCase: StartDeviceDiscoveryUseCase,
    val stopDeviceDiscoveryUseCase: StopDeviceDiscoveryUseCase
)
