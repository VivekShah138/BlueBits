package com.example.bluebits.di

import com.example.bluebits.domain.usecases.OldConnectionsUseCaseWrapper
import com.example.bluebits.domain.usecases.StartDeviceDiscoveryUseCase
import com.example.bluebits.domain.usecases.StopDeviceDiscoveryUseCase
import org.koin.dsl.module
import kotlin.math.sin

val useCaseWrapperModule = module {

    single<OldConnectionsUseCaseWrapper> {
        OldConnectionsUseCaseWrapper(
            startDeviceDiscoveryUseCase = StartDeviceDiscoveryUseCase(repository = get()),
            stopDeviceDiscoveryUseCase = StopDeviceDiscoveryUseCase(repository = get())
        )
    }

}