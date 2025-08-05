package com.example.bluebits.domain.usecases.permission_checker

import com.example.bluebits.domain.repository.PermissionCheckerRepository

class AreCorePermissionsGranted(
    private val permissionCheckerRepository: PermissionCheckerRepository
) {
    operator fun invoke(): Boolean{
        return permissionCheckerRepository.areCorePermissionsGranted()
    }
}