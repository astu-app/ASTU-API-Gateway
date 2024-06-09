package org.astu.routes.chat

import api.chat.client.models.ChatMemberDTO
import io.github.smiley4.ktorswaggerui.dsl.route
import io.ktor.client.*
import io.ktor.server.routing.*
import org.astu.routes.chat.dto.ChatMember
import org.astu.routes.chat.repository.SummaryAccountRepository
import org.astu.routes.chat.routes.chats
import org.astu.routes.chat.routes.messages
import org.astu.routes.chat.routes.users
import org.koin.ktor.ext.inject

fun Route.chatServiceDefinition() {
    val chatHost = environment?.config?.property("ktor.chat.host")?.getString() ?: throw Exception("Host not set")
    val client by inject<HttpClient>()

    route("/chat-service", {
        tags = listOf("chat-service")
    }) {
        chats(chatHost, client)
        messages(chatHost, client)
        users(chatHost, client)
    }
}

suspend fun ChatMemberDTO.fill(summaryAccountRepository: SummaryAccountRepository, userId: String): ChatMember {
    val account = summaryAccountRepository.getSummaryAccount(this.userId)
    return ChatMember(
        this.userId,
        account.firstName,
        account.secondName,
        account.patronymic,
        role,
        itMe = this.userId == userId
    )
}