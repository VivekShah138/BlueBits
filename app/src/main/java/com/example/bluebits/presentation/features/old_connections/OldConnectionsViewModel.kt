package com.example.bluebits.presentation.features.old_connections

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bluebits.domain.usecases.usecase_wrapper.OldConnectionsUseCaseWrapper
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "OldConnectionsVM"


class OldConnectionsViewModel(
    private val oldConnectionsUseCaseWrapper: OldConnectionsUseCaseWrapper
) : ViewModel() {

    private val _state = MutableStateFlow(OldConnectionsStates())
    val state: StateFlow<OldConnectionsStates> = _state.asStateFlow()

    // Keep track of discovery collection job to cancel when stopping discovery
    private var discoveryJob: Job? = null

    init {
        getBluetoothDetails()
    }

    fun onEvent(events: OldConnectionsEvents) {
        when (events) {
            OldConnectionsEvents.StartDeviceDiscovery -> {
                startDeviceDiscovery()
            }
            OldConnectionsEvents.StopDeviceDiscovery -> {
                stopDeviceDiscovery()
            }
        }
    }

    private fun startDeviceDiscovery() {
        Log.d(TAG, "Starting Bluetooth device discovery...")

        // Cancel previous discovery job if running
        discoveryJob?.cancel()

        discoveryJob = viewModelScope.launch {
            try {
                oldConnectionsUseCaseWrapper.startDeviceDiscoveryUseCase()
                    .collect { device ->
                        Log.d(
                            TAG, "Device found: name=${device.name}, address=${device.address}, paired=${device.isPaired}"
                        )

                        _state.update {
                            val currentList = it.discoverDeviceList
                            val updatedList = (currentList + device).distinctBy { d -> d.address }

                            Log.d(
                                TAG,
                                "Updated device list (${updatedList.size}): ${updatedList.joinToString { d -> d.name ?: "Unnamed" }}"
                            )

                            it.copy(discoverDeviceList = updatedList)
                        }
                    }
            } catch (e: Exception) {
                Log.e(TAG, "Error during device discovery: ${e.message}", e)
                _state.update { it.copy(errorMessage = e.message ?: "Unknown error") }
            }
        }
    }

    private fun stopDeviceDiscovery() {
        Log.d(TAG, "Stopping Bluetooth device discovery...")

        // Cancel collecting the discovery flow coroutine
        discoveryJob?.cancel()
        discoveryJob = null

        // Also stop discovery in the repository via the use case
        oldConnectionsUseCaseWrapper.stopDeviceDiscoveryUseCase()
    }

    private fun getBluetoothDetails(){
        val isBluetoothSupported = oldConnectionsUseCaseWrapper.isBluetoothSupported()
        val isBluetoothEnabled = oldConnectionsUseCaseWrapper.isBluetoothEnabled()

        _state.value = _state.value.copy(
            isBluetoothEnabled = isBluetoothEnabled,
            isBluetoothSupported = isBluetoothSupported
        )
    }

    override fun onCleared() {
        super.onCleared()
        stopDeviceDiscovery()
    }
}