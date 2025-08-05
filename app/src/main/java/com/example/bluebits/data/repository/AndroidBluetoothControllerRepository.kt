package com.example.bluebits.data.repository

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import com.example.bluebits.domain.repository.BluetoothControllerRepository

class AndroidBluetoothControllerRepository(
    private val bluetoothAdapter: BluetoothAdapter?
) : BluetoothControllerRepository {

    override fun isSupported(): Boolean = bluetoothAdapter != null

    override fun isEnabled(): Boolean = bluetoothAdapter?.isEnabled == true

    fun getEnableIntent(): Intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
}