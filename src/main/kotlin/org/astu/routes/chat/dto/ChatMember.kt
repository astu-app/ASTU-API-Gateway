package org.astu.routes.chat.dto

import kotlinx.serialization.Serializable

@Serializable
data class ChatMember(
    val id: String,
    val firstName: String,
    val secondName: String,
    val patronymic: String?,
    val role: String,
    val itMe: Boolean = false
)
