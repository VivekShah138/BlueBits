package com.example.bluebits.domain.usecases.bluetooth_controller

import com.example.bluebits.domain.repository.BluetoothControllerRepository

class IsBluetoothSupported(
    private val bluetoothControllerRepository: BluetoothControllerRepository
) {
    operator fun invoke():Boolean{
        return bluetoothControllerRepository.isSupported()
    }
}