package com.example.bluebits.presentation.features.old_connections.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.bluebits.R
import com.example.bluebits.presentation.core_components.RationaleDialog

@Composable
fun PermissionDialogs(
    showBluetoothRationaleDialog: Boolean,
    showNotificationsRationaleDialog: Boolean,
    showLocationRationaleDialog: Boolean,
    turnOnBluetoothRationaleDialog: Boolean,
    showAllRationaleDialog: Boolean,
    onDismissBluetooth: () -> Unit,
    onDismissNotifications: () -> Unit,
    onDismissTurnOnBluetooth: () -> Unit,
    onDismissLocation: () -> Unit,
    onDismissAll: () -> Unit,
    onConfirm: () -> Unit,
    onBluetoothEnable: () -> Unit,

) {
    val bluetoothIcon = R.drawable.ic_bluetooth
    val bluetoothDisabledIcon = R.drawable.bluetooth_disabled
    val locationOffIcon = R.drawable.location_off
    val notificationOffIcon = R.drawable.notifications_off
    val iconColor = MaterialTheme.colorScheme.primary

    if (showBluetoothRationaleDialog) {
        RationaleDialog(
            title = "Permission Denied",
            body = "You won't be able to use Bluetooth features in this app. Click on settings and grant permission which are revoked.",
            onDismiss = onDismissBluetooth,
            onConfirm = onConfirm,
            icon = bluetoothIcon,
            iconColor = iconColor,
            iconContentDescription = "App Icon"
        )
    }

    if (showNotificationsRationaleDialog) {
        RationaleDialog(
            title = "Permission Denied",
            body = "You won't be able to use notifications in this app. Click on settings and grant permission which are revoked.",
            onDismiss = onDismissNotifications,
            onConfirm = onConfirm,
            icon = notificationOffIcon,
            iconColor = iconColor,
            iconContentDescription = "App Icon"
        )
    }

    if (showAllRationaleDialog) {
        RationaleDialog(
            title = "Permission Denied",
            body = "You won't be able to use Bluetooth/notifications features in this app. Click on settings and grant permission which are revoked.",
            onDismiss = onDismissAll,
            onConfirm = onConfirm,
            icon = bluetoothIcon,
            iconColor = iconColor,
            iconContentDescription = "App Icon"
        )
    }

    if(showLocationRationaleDialog){
        RationaleDialog(
            title = "Permission Denied",
            body = "To discover nearby Bluetooth devices, location permission must be granted. Without it, device discovery won’t work.",
            onDismiss = onDismissLocation,
            onConfirm = onConfirm,
            icon = locationOffIcon,
            iconColor = iconColor,
            iconContentDescription = "App Icon"
        )
    }

    if(turnOnBluetoothRationaleDialog){
        RationaleDialog(
            title = "Turn On Bluetooth",
            body = "You won't be able to discover Bluetooth devices in this app. Please turn bluetooth ON.",
            onDismiss = onDismissTurnOnBluetooth,
            onConfirm = onBluetoothEnable,
            icon = bluetoothDisabledIcon,
            iconColor = iconColor,
            iconContentDescription = "App Icon",
            confirmText = stringResource(R.string.turnOn)
        )
    }
}
