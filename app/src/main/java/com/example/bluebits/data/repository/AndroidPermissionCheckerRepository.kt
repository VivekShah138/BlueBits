package com.example.bluebits.data.repository

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.bluebits.domain.repository.PermissionCheckerRepository
import com.example.bluebits.utils.*

class AndroidPermissionCheckerRepository(
    private val activity: Activity
): PermissionCheckerRepository {
    private val bluetoothPermissions = listOf(
        BLUETOOTH_SCAN,
        BLUETOOTH_ADVERTISE,
        BLUETOOTH_CONNECT
    )

    override fun areCorePermissionsGranted(): Boolean {
        return bluetoothPermissions.all { isGranted(it) } &&
                isGranted(ACCESS_FINE_LOCATION)
    }

    override fun isNotificationPermissionGranted(): Boolean =
        isGranted(POST_NOTIFICATIONS)

    override fun shouldShowBluetoothRationale(): Boolean =
        bluetoothPermissions.any { shouldShowRationale(it) }

    override fun shouldShowLocationRationale(): Boolean =
        shouldShowRationale(ACCESS_FINE_LOCATION)

    override fun shouldShowNotificationRationale(): Boolean =
        shouldShowRationale(POST_NOTIFICATIONS)

    override fun getPermissionRequestArray(): Array<String> {
        return (bluetoothPermissions + ACCESS_FINE_LOCATION + POST_NOTIFICATIONS).toTypedArray()
    }

    private fun isGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
    }

    private fun shouldShowRationale(permission: String): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
    }
}