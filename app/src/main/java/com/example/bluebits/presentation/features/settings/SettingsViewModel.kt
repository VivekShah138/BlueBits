package com.example.bluebits.presentation.features.settings

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class SettingsViewModel : ViewModel() {

    private val _state = MutableStateFlow(SettingsStates())
    val state: StateFlow<SettingsStates> = _state.asStateFlow()

    init {

    }

    fun onEvent(events: SettingsEvents) {
        when (events) {
            else -> TODO("Handle actions")
        }
    }
}