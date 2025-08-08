package com.example.bluebits.presentation.features.old_connections

import com.example.bluebits.domain.model.UserDevice
import com.example.bluebits.utils.BluetoothState
import com.example.bluebits.utils.DialogState

data class OldConnectionsStates(

    val discoverDeviceList: List<UserDevice> = emptyList(),
    val isScanning: Boolean = false,
    val bluetoothState: BluetoothState = BluetoothState.Unknown,
    val dialogState: DialogState = DialogState(),
)