package com.example.bluebits.utils

data class DialogState(
    val showBluetoothRationale: Boolean = false,
    val showLocationRationale: Boolean = false,
    val showNotificationRationale: Boolean = false,
    val showAllRationale: Boolean = false,
    val showBluetoothEnable: Boolean = false
) {
    val hasAnyDialog: Boolean
        get() = showBluetoothRationale || showLocationRationale ||
                showNotificationRationale || showAllRationale || showBluetoothEnable
}
