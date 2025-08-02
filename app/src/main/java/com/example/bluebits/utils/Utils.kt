package com.example.bluebits.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

fun Context.createSettingsIntent(){
    val settingsIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    val uri = Uri.fromParts("package", this.packageName, null)
    settingsIntent.data = uri
    this.startActivity(settingsIntent)
}