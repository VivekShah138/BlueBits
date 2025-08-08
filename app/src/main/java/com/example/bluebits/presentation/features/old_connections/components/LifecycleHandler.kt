package com.example.bluebits.presentation.features.old_connections.components

import android.content.Context
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.bluebits.presentation.features.old_connections.OldConnectionsEvents
import com.example.bluebits.utils.requestMultiplePermissionLogic

@Composable
fun LifecycleHandler(
    onStop: () -> Unit,
    permissionLauncher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>,
    context: Context,
    onBluetoothGranted: () -> Unit,
    onRationale: () -> Unit,
    onBluetoothRationale: () -> Unit,
    onNotificationsRationaleDialog: () -> Unit,
    onLocationRationale: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current


    DisposableEffect (lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    requestMultiplePermissionLogic(
                        context = context,
                        launcher = permissionLauncher,
                        onBluetoothPermissionGranted = onBluetoothGranted,
                        onRationale = onRationale,
                        onNotificationsRationaleDialog = onNotificationsRationaleDialog,
                        onBluetoothRationale = onBluetoothRationale,
                        onLocationRationale = onLocationRationale
                    )
                }
                Lifecycle.Event.ON_STOP -> onStop()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}