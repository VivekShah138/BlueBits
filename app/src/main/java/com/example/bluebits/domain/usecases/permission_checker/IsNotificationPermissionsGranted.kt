package com.example.bluebits.domain.usecases.permission_checker

import com.example.bluebits.domain.repository.PermissionCheckerRepository

class IsNotificationPermissionsGranted(
    private val permissionCheckerRepository: PermissionCheckerRepository
) {
    operator fun invoke(): Boolean{
        return permissionCheckerRepository.isNotificationPermissionGranted()
    }
}