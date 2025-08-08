package com.example.bluebits.presentation.features.old_connections

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bluebits.domain.repository.BluetoothManager
import com.example.bluebits.domain.usecases.usecase_wrapper.OldConnectionsUseCaseWrapper
import com.example.bluebits.utils.BluetoothState
import com.example.bluebits.utils.DialogState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "OldConnectionsVM"


class OldConnectionsViewModel(
    private val bluetoothManager: BluetoothManager,
    private val oldConnectionsUseCaseWrapper: OldConnectionsUseCaseWrapper
) : ViewModel() {

    private val _state = MutableStateFlow(OldConnectionsStates())
    val state: StateFlow<OldConnectionsStates> = _state.asStateFlow()


    fun onEvent(events: OldConnectionsEvents) {
        when (events) {
            is OldConnectionsEvents.OnScreenStop -> handleScreenStop()
            is OldConnectionsEvents.RequestBluetoothEnable -> handleRequestBluetoothEnable()
            is OldConnectionsEvents.OnBluetoothEnableResult -> handleBluetoothEnableResult(events.enabled)
            is OldConnectionsEvents.BluetoothEnableRationaleVisibility -> {
                _state.value = _state.value.copy(
                    dialogState = DialogState(showBluetoothEnable = events.isVisible)
                )
            }

            is OldConnectionsEvents.BluetoothRationaleVisibility -> {
                _state.value = _state.value.copy(
                    dialogState = DialogState(showBluetoothRationale = events.isVisible)
                )
            }

            is OldConnectionsEvents.LocationRationaleVisibility -> {
                _state.value = _state.value.copy(
                    dialogState = DialogState(showLocationRationale = events.isVisible)
                )
            }

            is OldConnectionsEvents.NotificationRationaleVisibility -> {
                _state.value = _state.value.copy(
                    dialogState = DialogState(showNotificationRationale = events.isVisible)
                )
            }

            is OldConnectionsEvents.AllRationaleVisibility -> {
                _state.value = _state.value.copy(
                    dialogState = DialogState(showAllRationale = events.isVisible)
                )
            }

            is OldConnectionsEvents.DisableAllRationale -> {
                _state.value = _state.value.copy(
                    dialogState = DialogState()
                )
            }
        }
    }


    private fun handleScreenStop() {
        handleStopDiscovery()
    }


    private fun handleStopDiscovery() {
        _state.update { it.copy(isScanning = false) }
        stopDeviceDiscovery()
    }


    private fun handleRequestBluetoothEnable() {
        when {
            !bluetoothManager.isBluetoothSupported() -> {
                _state.update {
                    it.copy(
                        bluetoothState = BluetoothState.NotSupported,
                    )
                }
            }

            bluetoothManager.isBluetoothEnabled() -> {
                _state.update { it.copy(bluetoothState = BluetoothState.Enabled) }
                startDeviceDiscovery()
            }

            else -> {
                _state.update { it.copy(bluetoothState = BluetoothState.Enabling) }
            }
        }
    }


    private fun handleBluetoothEnableResult(enabled: Boolean) {
        if (enabled) {
            _state.update { it.copy(bluetoothState = BluetoothState.Enabled) }
            startDeviceDiscovery()
        } else {
            _state.update {
                it.copy(
                    bluetoothState = BluetoothState.Disabled,
                )
            }
        }
    }


    private fun startDeviceDiscovery() {
        Log.d(TAG, "Starting Bluetooth device discovery...")

        viewModelScope.launch {
            try {
                oldConnectionsUseCaseWrapper.startDeviceDiscoveryUseCase()
                    .collect { device ->
                        Log.d(
                            TAG,
                            "Device found: name=${device.name}, address=${device.address}, paired=${device.isPaired}"
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
            }
        }
    }

    private fun stopDeviceDiscovery() {
        Log.d(TAG, "Stopping Bluetooth device discovery...")

        // Also stop discovery in the repository via the use case
        oldConnectionsUseCaseWrapper.stopDeviceDiscoveryUseCase()
    }


    override fun onCleared() {
        super.onCleared()
        stopDeviceDiscovery()
    }
}