package com.example.bluebits.domain.repository

import com.example.bluebits.domain.model.UserDevice
import kotlinx.coroutines.flow.Flow

interface DeviceDiscoveryRepository {
    fun startDiscovery(): Flow<UserDevice>
    fun stopDiscovery()
}