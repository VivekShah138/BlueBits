package com.example.bluebits.navigation.core

import kotlinx.serialization.Serializable

sealed class Screens{

    @Serializable
    data object SplashScreen: Screens()

    @Serializable
    data object OldConnectionScreen: Screens()

    @Serializable
    data object DeviceDiscoveryScreen: Screens()

    @Serializable
    data object DeviceDetailsScreen: Screens()

    @Serializable
    data object SettingsScreen: Screens()
}
