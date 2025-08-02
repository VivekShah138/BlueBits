package com.example.bluebits.domain.model

data class UserProfile(
    val deviceAddress: String,
    val firstName: String,
    val lastName: String,
    val nickname: String? = null,
    val avatarUri: String? = null
)
