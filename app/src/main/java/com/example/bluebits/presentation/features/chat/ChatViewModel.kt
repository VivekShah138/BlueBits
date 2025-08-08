package com.example.bluebits.presentation.features.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bluebits.domain.usecase.ChatUseCaseWrapper
import com.example.bluebits.utils.mapToType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel(
    val chatUseCaseWrapper: ChatUseCaseWrapper
) : ViewModel() {
    companion object {
        private const val TAG = "ChatViewModel"
    }

    private val _state = MutableStateFlow<ChatState>(ChatState())
    val state = _state.asStateFlow()


    fun onEvent(events : ChatEvents){
        when(events){
            is ChatEvents.TextMessageSent -> {
                val message = MessageState(
                    data = events.text.trimEnd(),
                    messageType = MessageType.TEXT
                )
                viewModelScope.launch {
                    val result = chatUseCaseWrapper.textMessageSentUseCase(message.data)
                    _state.update { it ->
                        it.copy(
                            messages = _state.value.messages + message
                        )
                    }
                }

                Log.d(TAG, "onEvent: ${message}")
                Log.d(TAG, "onEvent: TextMessageState Called")
            }

            is ChatEvents.MessageDelete -> {
                _state.update { it ->
                    it.copy(
                        messages = _state.value.messages - events.message
                    )
                }
            }

            is ChatEvents.ImageMessageSend -> {
                val message = MessageState(
                    data = events.uri.toString(),
                    messageType = MessageType.IMAGE
                )
                _state.update { it ->
                    it.copy(
                        messages = _state.value.messages + message
                    )

                }
            }
        }

    }
}

