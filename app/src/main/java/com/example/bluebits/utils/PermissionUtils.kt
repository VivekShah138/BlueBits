package com.example.bluebits.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

const val POST_NOTIFICATIONS = Manifest.permission.POST_NOTIFICATIONS
const val BLUETOOTH_SCAN = Manifest.permission.BLUETOOTH_SCAN
const val BLUETOOTH_ADVERTISE = Manifest.permission.BLUETOOTH_ADVERTISE
const val BLUETOOTH_CONNECT = Manifest.permission.BLUETOOTH_CONNECT
const val ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION

private const val TAG = "PermissionUtils"

fun Context.isPermisssionGranted(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED
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

    val bluetoothRationale =
        ActivityCompat.shouldShowRequestPermissionRationale(activity, BLUETOOTH_SCAN) &&
                ActivityCompat.shouldShowRequestPermissionRationale(activity, BLUETOOTH_ADVERTISE) ||
                ActivityCompat.shouldShowRequestPermissionRationale(activity, BLUETOOTH_CONNECT)

    val locationRationale =
        ActivityCompat.shouldShowRequestPermissionRationale(activity, ACCESS_FINE_LOCATION)

    val notificationRationale =
        ActivityCompat.shouldShowRequestPermissionRationale(activity, POST_NOTIFICATIONS)

    when {
        context.isPermisssionGranted(BLUETOOTH_SCAN) &&
                context.isPermisssionGranted(BLUETOOTH_ADVERTISE) &&
                context.isPermisssionGranted(BLUETOOTH_CONNECT) &&
                context.isPermisssionGranted(ACCESS_FINE_LOCATION) -> {
            onBluetoothPermissionGranted()

            if (!context.isPermisssionGranted(POST_NOTIFICATIONS)) {
                onNotificationsRationaleDialog()
            }

            Log.i(TAG, "All required permissions granted")
        }

        bluetoothRationale && locationRationale && notificationRationale -> onRationale()
        bluetoothRationale && locationRationale -> onBluetoothRationale()
        locationRationale -> onLocationRationale()

        else -> launcher.launch(
            arrayOf(
                BLUETOOTH_SCAN,
                BLUETOOTH_ADVERTISE,
                BLUETOOTH_CONNECT,
                ACCESS_FINE_LOCATION,
                POST_NOTIFICATIONS
            )
        )
    }
}
