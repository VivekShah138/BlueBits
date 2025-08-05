package com.example.bluebits.presentation.features.old_connections.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bluebits.domain.model.UserDevice

@Composable
fun DeviceListItem(device: UserDevice, onClick: () -> Unit) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 8.dp)
    ) {
        Text(text = device.name ?: "Unknown device", style = MaterialTheme.typography.bodyLarge)
        Text(text = device.address, style = MaterialTheme.typography.bodySmall)
    }
}