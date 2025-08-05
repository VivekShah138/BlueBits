package com.example.bluebits.domain.repository

interface PermissionCheckerRepository {
    fun areCorePermissionsGranted(): Boolean
    fun isNotificationPermissionGranted(): Boolean
    fun shouldShowBluetoothRationale(): Boolean
    fun shouldShowLocationRationale(): Boolean
    fun shouldShowNotificationRationale(): Boolean
    fun getPermissionRequestArray(): Array<String>
}