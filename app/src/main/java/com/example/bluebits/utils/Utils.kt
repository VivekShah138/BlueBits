package com.example.bluebits.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Message
import android.provider.Settings
import com.example.bluebits.presentation.features.chat.MessageType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale



fun Context.createSettingsIntent(){
    val settingsIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    val uri = Uri.fromParts("package", this.packageName, null)
    settingsIntent.data = uri
    this.startActivity(settingsIntent)
}

fun Long.formatTime():String{
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return sdf.format(Date(this))
}

fun Uri.mapToType(context : Context): MessageType{

    val mimeType = context.contentResolver.getType(this)

    if(mimeType == null){
        return MessageType.TEXT
    }

    if(mimeType.startsWith("image")){
        return MessageType.IMAGE
    }

    return MessageType.TEXT
}