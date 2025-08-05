package com.example.bluebits.domain.usecases.usecase_wrapper

import com.example.bluebits.domain.usecases.bluetooth_controller.IsBluetoothEnabled
import com.example.bluebits.domain.usecases.bluetooth_controller.IsBluetoothSupported
import com.example.bluebits.domain.usecases.device_discovery.StartDeviceDiscoveryUseCase
import com.example.bluebits.domain.usecases.device_discovery.StopDeviceDiscoveryUseCase

data class OldConnectionsUseCaseWrapper(
    val startDeviceDiscoveryUseCase: StartDeviceDiscoveryUseCase,
    val stopDeviceDiscoveryUseCase: StopDeviceDiscoveryUseCase,
    val isBluetoothEnabled: IsBluetoothEnabled,
    val isBluetoothSupported: IsBluetoothSupported
//    val areCorePermissionsGranted: AreCorePermissionsGranted,
//    val getPermissionRationaleStatus: GetPermissionRationaleStatus,
//    val isNotificationPermissionsGranted: IsNotificationPermissionsGranted
)
