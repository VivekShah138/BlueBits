package com.example.bluebits.data.repository

import com.example.bluebits.domain.model.Result
import com.example.bluebits.domain.repository.ChatRepository

class ChatRepositoryImpl : ChatRepository {
    override suspend fun sendTextMessage(message: String): com.example.bluebits.domain.model.Result<String> {
        return Result.Success("Message Send Successfully")
    }

    override suspend fun sendImageMessage(message: String): Result<String> {
        return Result.Success("Message Send Successfully")
    }
}