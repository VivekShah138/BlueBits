package com.example.bluebits.di

import com.example.bluebits.domain.usecases.usecase_wrapper.OldConnectionsUseCaseWrapper
import com.example.bluebits.domain.usecases.device_discovery.StartDeviceDiscoveryUseCase
import com.example.bluebits.domain.usecases.device_discovery.StopDeviceDiscoveryUseCase
import org.koin.dsl.module

val useCaseWrapperModule = module {

    single<OldConnectionsUseCaseWrapper> {
        OldConnectionsUseCaseWrapper(
            startDeviceDiscoveryUseCase = StartDeviceDiscoveryUseCase(repository = get()),
            stopDeviceDiscoveryUseCase = StopDeviceDiscoveryUseCase(repository = get()),
        )
    }

}