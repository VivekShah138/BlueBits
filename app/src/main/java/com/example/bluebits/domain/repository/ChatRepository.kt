package com.example.bluebits.domain.repository

import com.example.bluebits.domain.model.Result

interface ChatRepository {
    suspend fun sendTextMessage(message : String) : Result<String>
    suspend fun sendImageMessage(message : String): Result<String>

}