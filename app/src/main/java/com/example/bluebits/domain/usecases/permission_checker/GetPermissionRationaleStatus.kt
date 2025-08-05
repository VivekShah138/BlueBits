package com.example.bluebits.domain.usecases.permission_checker

import com.example.bluebits.domain.model.PermissionRationaleStatus
import com.example.bluebits.domain.repository.PermissionCheckerRepository

class GetPermissionRationaleStatus(
    private val permissionCheckerRepository: PermissionCheckerRepository
) {
    operator fun invoke(): PermissionRationaleStatus {
        val bluetooth = permissionCheckerRepository.shouldShowBluetoothRationale()
        val location = permissionCheckerRepository.shouldShowLocationRationale()
        val notification = permissionCheckerRepository.shouldShowNotificationRationale()
        val all = bluetooth || location || notification
        return PermissionRationaleStatus(
            bluetooth = bluetooth,
            location = location,
            notification = notification,
            allRationale = all
        )
    }
}
