package com.example.bluebits.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

const val POST_NOTIFICATIONS = Manifest.permission.POST_NOTIFICATIONS
const val BLUETOOTH_SCAN = Manifest.permission.BLUETOOTH_SCAN
const val BLUETOOTH_ADVERTISE = Manifest.permission.BLUETOOTH_ADVERTISE
const val BLUETOOTH_CONNECT = Manifest.permission.BLUETOOTH_CONNECT
const val BLUETOOTH = Manifest.permission.BLUETOOTH
const val BLUETOOTH_ADMIN = Manifest.permission.BLUETOOTH_ADMIN
const val ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION

private const val TAG = "PermissionUtils"

fun Context.isPermissionGranted(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

fun requestMultiplePermissionLogic(
    context: Context,
    launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>,
    onBluetoothPermissionGranted: () -> Unit,
    onRationale: () -> Unit,
    onBluetoothRationale: () -> Unit,
    onNotificationsRationaleDialog: () -> Unit,
    onLocationRationale: () -> Unit
) {
    val activity = context as Activity
    val permissionsToRequest = mutableListOf<String>()

    // Check if permissions are already granted
    val bluetoothPermissionsGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        context.isPermissionGranted(BLUETOOTH_SCAN) &&
                context.isPermissionGranted(BLUETOOTH_ADVERTISE) &&
                context.isPermissionGranted(BLUETOOTH_CONNECT)
    } else {
        context.isPermissionGranted(BLUETOOTH) &&
                context.isPermissionGranted(BLUETOOTH_ADMIN)
    }

    val locationPermissionGranted = context.isPermissionGranted(ACCESS_FINE_LOCATION)

    val notificationPermissionGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        context.isPermissionGranted(POST_NOTIFICATIONS)
    } else true

    // Check if rationale dialogs should be shown
    val bluetoothRationale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        ActivityCompat.shouldShowRequestPermissionRationale(activity, BLUETOOTH_SCAN) ||
        ActivityCompat.shouldShowRequestPermissionRationale(activity, BLUETOOTH_ADVERTISE) ||
        ActivityCompat.shouldShowRequestPermissionRationale(activity, BLUETOOTH_CONNECT)
    } else {
        ActivityCompat.shouldShowRequestPermissionRationale(activity, BLUETOOTH) ||
        ActivityCompat.shouldShowRequestPermissionRationale(activity, BLUETOOTH_ADMIN)
    }

    val locationRationale =
        ActivityCompat.shouldShowRequestPermissionRationale(activity, ACCESS_FINE_LOCATION)

    val notificationRationale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ActivityCompat.shouldShowRequestPermissionRationale(activity, POST_NOTIFICATIONS)
    } else false


    when {
        bluetoothPermissionsGranted && locationPermissionGranted -> {
            onBluetoothPermissionGranted()

            if (!notificationPermissionGranted &&
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
            ) {
                onNotificationsRationaleDialog()
            }

            Log.i(TAG, "All required permissions granted")
        }

        bluetoothRationale && locationRationale && notificationRationale -> onRationale()
        bluetoothRationale && locationRationale -> onBluetoothRationale()
        locationRationale -> onLocationRationale()

        else -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (!context.isPermissionGranted(BLUETOOTH_SCAN)) permissionsToRequest.add(BLUETOOTH_SCAN)
                if (!context.isPermissionGranted(BLUETOOTH_ADVERTISE)) permissionsToRequest.add(BLUETOOTH_ADVERTISE)
                if (!context.isPermissionGranted(BLUETOOTH_CONNECT)) permissionsToRequest.add(BLUETOOTH_CONNECT)
            } else {
                if (!context.isPermissionGranted(BLUETOOTH)) permissionsToRequest.add(BLUETOOTH)
                if (!context.isPermissionGranted(BLUETOOTH_ADMIN)) permissionsToRequest.add(BLUETOOTH_ADMIN)
            }

            if (!locationPermissionGranted) permissionsToRequest.add(ACCESS_FINE_LOCATION)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !notificationPermissionGranted) {
                permissionsToRequest.add(POST_NOTIFICATIONS)
            }

            launcher.launch(permissionsToRequest.toTypedArray())
        }
    }
}

