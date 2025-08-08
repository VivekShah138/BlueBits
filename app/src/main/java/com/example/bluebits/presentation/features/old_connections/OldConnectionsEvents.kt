package com.example.bluebits.presentation.features.old_connections

sealed class OldConnectionsEvents {
    data object OnScreenStop : OldConnectionsEvents()
    data object RequestBluetoothEnable : OldConnectionsEvents()
    data class OnBluetoothEnableResult(val enabled: Boolean) : OldConnectionsEvents()

    data class BluetoothEnableRationaleVisibility(val isVisible: Boolean): OldConnectionsEvents()
    data class BluetoothRationaleVisibility(val isVisible: Boolean): OldConnectionsEvents()
    data class LocationRationaleVisibility(val isVisible: Boolean): OldConnectionsEvents()
    data class NotificationRationaleVisibility(val isVisible: Boolean): OldConnectionsEvents()
    data class AllRationaleVisibility(val isVisible: Boolean): OldConnectionsEvents()
    data object DisableAllRationale: OldConnectionsEvents()
}