package com.example.bluebits.presentation.features.old_connections.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bluebits.domain.model.UserDevice

@Composable
fun AvailableDevices(
    devices: List<UserDevice>,
    isScanning: Boolean,
    onRefreshClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Available Devices",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = onRefreshClicked) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh Devices"
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (isScanning) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (devices.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No devices found")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(devices) { device ->
                    DeviceListItem(
                        device = device,
                        onClick = {

                        }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PreviewAvailableDevicesSimple() {
    val sampleDevices = listOf(
        UserDevice(name = "Wireless Mouse", address = "12:34:56:78:9A:BC", isConnected = true, isPaired = true),
        UserDevice(name = "Smart Watch", address = "AB:CD:EF:12:34:56", isConnected = false, isPaired = true),
        UserDevice(name = "Car Audio", address = "98:76:54:32:10:FE", isConnected = true, isPaired = true),
        UserDevice(name = "Bluetooth Speaker", address = "01:23:45:67:89:AB", isConnected = false, isPaired = false)

    )

    AvailableDevices (
        devices = sampleDevices,
        onRefreshClicked = { /* trigger refresh scan */ },
        isScanning = true
    )
}
