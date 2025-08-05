package com.example.bluebits.presentation.features.old_connections

import com.example.bluebits.domain.model.UserDevice

data class OldConnectionsStates(
    val oldConnectionList: List<String> = emptyList(),
    val discoverDeviceList: List<UserDevice> = emptyList(),
    val errorMessage: String? = null,

    // Permissions states
    val corePermissionsGranted: Boolean = false,
    val notificationPermissionGranted: Boolean = false,
    val showBluetoothRationaleDialog: Boolean = false,
    val showLocationRationaleDialog: Boolean = false,
    val showNotificationsRationaleDialog: Boolean = false,
    val showAllRationaleDialog: Boolean = false,

    // Bluetooth enablement states
    val isBluetoothEnabled: Boolean = false,
    val isBluetoothSupported: Boolean = false,
    val bluetoothEnableRequested: Boolean = false,
    val bluetoothEnableDenied: Boolean = false,
    val turnOnBluetoothRationaleDialog: Boolean = false,

    // Loading and error states
    val isDiscovering: Boolean = false
)