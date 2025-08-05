package com.example.bluebits.domain.usecases.bluetooth_controller

import com.example.bluebits.domain.repository.BluetoothControllerRepository

class IsBluetoothEnabled(
    private val bluetoothControllerRepository: BluetoothControllerRepository
) {
    operator fun invoke(): Boolean {
        return bluetoothControllerRepository.isEnabled()
    }
}