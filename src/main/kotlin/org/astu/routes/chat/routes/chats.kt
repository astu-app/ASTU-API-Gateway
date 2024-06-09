package org.astu.routes.chat.routes

import api.chat.client.apis.ChatApi
import api.chat.client.models.AddChatDTO
import api.chat.client.models.ChatDTO
import io.ktor.client.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.astu.plugins.CustomUserPrincipal
import org.astu.routes.chat.dto.Chat
import org.astu.routes.chat.fill
import org.astu.routes.chat.repository.SummaryAccountRepository
import org.koin.ktor.ext.inject

fun Route.chats(chatHost: String, client: HttpClient) = route("/chats") {
    val chatApi = ChatApi(client, chatHost)

    val summaryAccountRepository by inject<SummaryAccountRepository>()

//    cacheOutput {
    /**
     * Получение списка чатов
     * @OpenAPITag chat api
     */
    get {
        call.principal<CustomUserPrincipal>()?.also { principal ->
            val response = chatApi.apiChatGet(principal.id).map {
                it.toChat(summaryAccountRepository, principal.id)
            }
            call.respond(response)
        }
    }

    /**
     * Получение чата
     * @OpenAPITag chat api
     */
    get("{chatId}") {
        val chatId = call.parameters["chatId"] ?: throw IllegalArgumentException("chatId is required")
        call.principal<CustomUserPrincipal>()?.also { principal ->
            val response =
                chatApi.apiChatIdGet(principal.id, chatId).toChat(summaryAccountRepository, userId = principal.id)
            call.respond(response)
        }
    }
//    }

    /**
     * Добавление чата
     * @OpenAPITag chat api
     */
    post {
        val dto = call.receive<AddChatDTO>()
        call.principal<CustomUserPrincipal>()?.also { principal ->
            val id = chatApi.apiChatPost(principal.id, dto)
            call.respond(id)
        }
    }
}

suspend fun ChatDTO.toChat(repository: SummaryAccountRepository, userId: String): Chat = Chat(
    id = id,
    name = name,
    messages = messages,
    members = members.map {
        it.fill(repository, userId)
    }
)