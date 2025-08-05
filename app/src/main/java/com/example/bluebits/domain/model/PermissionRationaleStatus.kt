package com.example.bluebits.domain.model

data class PermissionRationaleStatus(
    val bluetooth: Boolean,
    val location: Boolean,
    val notification: Boolean,
    val allRationale: Boolean
)
