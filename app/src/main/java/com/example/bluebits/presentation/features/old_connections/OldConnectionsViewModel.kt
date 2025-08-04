package com.example.bluebits.presentation.features.old_connections

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bluebits.domain.usecases.OldConnectionsUseCaseWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class OldConnectionsViewModel(
    private val oldConnectionsUseCaseWrapper: OldConnectionsUseCaseWrapper
): ViewModel() {

    private val _state = MutableStateFlow(OldConnectionsStates())
    val state: StateFlow<OldConnectionsStates> = _state.asStateFlow()

    init {
        startDeviceDiscovery()
    }

    fun onEvent(events: OldConnectionsEvents) {
        when (events) {
            else -> TODO("Handle actions")
        }
    }

    private fun startDeviceDiscovery() {
        Log.d("OldConnectionsVM", "🔍 Starting Bluetooth device discovery...")

        viewModelScope.launch {
            try {
                oldConnectionsUseCaseWrapper.startDeviceDiscoveryUseCase()
                    .collect { device ->
                        Log.d("OldConnectionsVM", "📡 Device found: name=${device.name}, address=${device.address}, paired=${device.isPaired}")

                        _state.update {
                            val currentList = it.discoverDeviceList
                            val updatedList = (currentList + device).distinctBy { d -> d.address }

                            Log.d("OldConnectionsVM", "📋 Updated device list (${updatedList.size}): ${updatedList.joinToString { d -> d.name ?: "Unnamed" }}")

                            it.copy(discoverDeviceList = updatedList)
                        }
                    }
            } catch (e: Exception) {
                Log.e("OldConnectionsVM", "❌ Error during device discovery: ${e.message}", e)
                _state.update { it.copy(errorMessage = e.message ?: "Unknown error") }
            }
        }
    }

}