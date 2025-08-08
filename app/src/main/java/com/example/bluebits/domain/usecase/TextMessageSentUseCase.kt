package com.example.bluebits.domain.usecase

import com.example.bluebits.domain.model.Result
import com.example.bluebits.domain.repository.ChatRepository

class TextMessageSentUseCase(
    val repository: ChatRepository
) {
    suspend operator fun invoke(text : String): Result<String>{
        return repository.sendTextMessage(text)
    }

}