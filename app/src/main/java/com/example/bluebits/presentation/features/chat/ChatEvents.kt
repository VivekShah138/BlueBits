package com.example.bluebits.presentation.features.chat

import android.net.Uri

sealed class ChatEvents {
    data class TextMessageSent(val text : String): ChatEvents()
    data class ImageMessageSend(val uri : Uri): ChatEvents()
    data class MessageDelete(val message : MessageState) : ChatEvents()
}