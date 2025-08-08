package com.example.bluebits.di

import com.example.bluebits.presentation.features.chat.ChatViewModel
import com.example.bluebits.presentation.features.old_connections.OldConnectionsViewModel
import com.example.bluebits.presentation.features.settings.SettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {


    viewModel {
        OldConnectionsViewModel(
            oldConnectionsUseCaseWrapper = get(),
            bluetoothManager = get()
        )
    }

    viewModel {
        ChatViewModel(get())
    }

    viewModel {
        SettingsViewModel()
    }

}