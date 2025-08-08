package com.example.bluebits.presentation.features.chat


import java.util.UUID

/**
 * Represents the overall state of a chat, including the list of messages exchanged.
 *
 * @property messages A list of [MessageState] objects representing each message in the chat.
 *
 * @see MessageState
 */
data class ChatState(
    val messages: List<MessageState> = emptyList()
)

/**
 * Represents the state and metadata of a message in a chat conversation.
 *
 * @property messageId A unique identifier for the message.
 * @property isMessageFromMe `true` if the message was sent by the local user, `false` otherwise.
 * @property data The content of the message:
 * - A [String] if the [messageType] is [MessageType.TEXT]
 * - A [android.net.Uri.toString] if the [messageType] is [MessageType.IMAGE]
 * @property timeStamp The time at which the message was created, in milliseconds since epoch.
 * @property messageType The type of message content, defined by [MessageType] (e.g., [MessageType.TEXT], [MessageType.IMAGE]).
 */
data class MessageState(
    val messageId: UUID = UUID.randomUUID(),
    val isMessageFromMe: Boolean = (0..1).random() == 1,
    val data: String = "",
    val timeStamp: Long = System.currentTimeMillis(),
    val messageType: MessageType = MessageType.TEXT
)


/**
 * Represents the type of a chat message.
 *
 * Used to distinguish between different content formats in a conversation.
 */
enum class MessageType {

    /**
     * A text-only message.
     */
    TEXT,

    /**
     * A message that contains an image.
     */
    IMAGE,
}


