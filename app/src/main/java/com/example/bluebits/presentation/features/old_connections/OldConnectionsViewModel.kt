package com.example.bluebits.presentation.features.old_connections

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class OldConnectionsViewModel : ViewModel() {

    private val _state = MutableStateFlow(OldConnectionsStates())
    val state: StateFlow<OldConnectionsStates> = _state.asStateFlow()

    init {

    }

    fun onEvent(events: OldConnectionsEvents) {
        when (events) {
            else -> TODO("Handle actions")
        }
    }
}