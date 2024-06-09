package org.astu.routes.chat.dto

import api.chat.client.models.MessageDTO
import kotlinx.serialization.Serializable

@Serializable
data class Chat(
    val id: String,
    val name: String,
    val members: List<ChatMember>,
    val messages: List<MessageDTO>
)