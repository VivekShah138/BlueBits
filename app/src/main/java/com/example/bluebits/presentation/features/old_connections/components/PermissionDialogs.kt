package com.example.bluebits.presentation.features.old_connections.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.bluebits.R
import com.example.bluebits.presentation.core_components.RationaleDialog

@Composable
fun PermissionDialogs(
    showBluetoothRationaleDialog: Boolean,
    showNotificationsRationaleDialog: Boolean,
    showAllRationaleDialog: Boolean,
    onDismissBluetooth: () -> Unit,
    onDismissNotifications: () -> Unit,
    onDismissAll: () -> Unit,
    onConfirm: () -> Unit
) {
    val icon = R.drawable.ic_bluetooth
    val iconColor = MaterialTheme.colorScheme.primary

    if (showBluetoothRationaleDialog) {
        RationaleDialog(
            title = "Permission Denied",
            body = "You won't be able to use Bluetooth features in this app. Click on settings and grant permission which are revoked.",
            onDismiss = onDismissBluetooth,
            onConfirm = onConfirm,
            icon = icon,
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
            icon = icon,
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
            icon = icon,
            iconColor = iconColor,
            iconContentDescription = "App Icon"
        )
    }
}
