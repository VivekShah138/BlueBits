package com.example.bluebits.presentation.features.old_connections

import com.example.bluebits.domain.model.UserDevice

data class OldConnectionsStates(
    val oldConnectionList: List<String> = emptyList(),
    val discoverDeviceList: List<UserDevice> = emptyList(),
    val errorMessage: String? = null
)