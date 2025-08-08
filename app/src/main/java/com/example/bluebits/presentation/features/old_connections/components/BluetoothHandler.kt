package com.example.bluebits.presentation.features.old_connections.components

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.bluebits.domain.repository.BluetoothManager

private const val TAG = "Bluetooth_Handler"

@Composable
fun BluetoothHandler(
    bluetoothManager: BluetoothManager,
    shouldEnable: Boolean,
    onBluetoothResult: (Boolean) -> Unit
) {
    val bluetoothEnableLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        onBluetoothResult(result.resultCode == Activity.RESULT_OK)
    }

    Log.d(TAG,"ShouldEnable: $shouldEnable")

    LaunchedEffect(shouldEnable) {
        if (shouldEnable && bluetoothManager.isBluetoothSupported() && !bluetoothManager.isBluetoothEnabled()) {
            bluetoothManager.enableBluetooth { enabled ->
                if (!enabled) {
                    val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    bluetoothEnableLauncher.launch(enableBtIntent)
                } else {
                    onBluetoothResult(true)
                }
            }
        }
    }
}