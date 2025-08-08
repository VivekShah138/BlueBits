package com.example.bluebits.presentation.features.old_connections.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.bluebits.R
import com.example.bluebits.presentation.core_components.RationaleDialog
import com.example.bluebits.utils.DialogState
import com.example.bluebits.utils.DialogType

@Composable
fun PermissionDialogs(
    dialogState: DialogState,
    onDismiss: (DialogType) -> Unit,
    onConfirm: () -> Unit,
    onBluetoothEnable: () -> Unit
) {
    val bluetoothIcon = R.drawable.ic_bluetooth
    val bluetoothDisabledIcon = R.drawable.bluetooth_disabled
    val locationOffIcon = R.drawable.location_off
    val notificationOffIcon = R.drawable.notifications_off
    val iconColor = MaterialTheme.colorScheme.primary

    when {
        dialogState.showBluetoothRationale -> {
            RationaleDialog(
                title = "Permission Denied",
                body = "You won't be able to use Bluetooth features in this app. Click on settings and grant permission which are revoked.",
                onDismiss = { onDismiss(DialogType.Bluetooth) },
                onConfirm = onConfirm,
                icon = bluetoothIcon,
                iconColor = iconColor,
                iconContentDescription = "App Icon"
            )
        }
        dialogState.showLocationRationale -> {
            RationaleDialog(
                title = "Permission Denied",
                body = "To discover nearby Bluetooth devices, location permission must be granted. Without it, device discovery won’t work.",
                onDismiss = { onDismiss(DialogType.Location) },
                onConfirm = onConfirm,
                icon = locationOffIcon,
                iconColor = iconColor,
                iconContentDescription = "App Icon"
            )
        }
        dialogState.showNotificationRationale -> {
            RationaleDialog(
                title = "Permission Denied",
                body = "You won't be able to use notifications in this app. Click on settings and grant permission which are revoked.",
                onDismiss = { onDismiss(DialogType.Notification) },
                onConfirm = onConfirm,
                icon = notificationOffIcon,
                iconColor = iconColor,
                iconContentDescription = "App Icon"
            )
        }
        dialogState.showAllRationale -> {
            RationaleDialog(
                title = "Permission Denied",
                body = "You won't be able to use Bluetooth/notifications features in this app. Click on settings and grant permission which are revoked.",
                onDismiss = { onDismiss(DialogType.All) },
                onConfirm = onConfirm,
                icon = bluetoothIcon,
                iconColor = iconColor,
                iconContentDescription = "App Icon"
            )
        }
        dialogState.showBluetoothEnable -> {
            RationaleDialog(
                title = "Turn On Bluetooth",
                body = "You won't be able to discover Bluetooth devices in this app. Please turn bluetooth ON.",
                onDismiss = { onDismiss(DialogType.BluetoothEnable) },
                onConfirm = onBluetoothEnable,
                icon = bluetoothDisabledIcon,
                iconColor = iconColor,
                iconContentDescription = "App Icon",
                confirmText = stringResource(R.string.turnOn)
            )
        }
    }
}