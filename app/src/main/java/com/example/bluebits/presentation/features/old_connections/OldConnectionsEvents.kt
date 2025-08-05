package com.example.bluebits.presentation.features.old_connections

sealed interface OldConnectionsEvents {
    data object StartDeviceDiscovery: OldConnectionsEvents
    data object StopDeviceDiscovery: OldConnectionsEvents
}