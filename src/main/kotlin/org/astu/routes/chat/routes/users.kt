package org.astu.routes.chat.routes

import api.chat.client.apis.ChatApi
import api.chat.client.models.AddUserDTO
import io.ktor.client.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.astu.plugins.CustomUserPrincipal
import org.astu.routes.chat.fill
import org.astu.routes.chat.repository.SummaryAccountRepository
import org.koin.ktor.ext.inject

fun Route.users(chatHost: String, client: HttpClient) = route("/chats/{chatId}/users") {
    val chatApi = ChatApi(client, chatHost)
    val summaryAccountRepository by inject<SummaryAccountRepository>()

    /**
     * Добавление пользователя в чат
     * @OpenAPITag chat api
     */
    post {
        val chatId = call.parameters["chatId"] ?: throw IllegalArgumentException("chatId is required")
        val dto = call.receive<AddUserDTO>()
        call.principal<CustomUserPrincipal>()?.also { principal ->
            chatApi.apiChatIdUserPost(principal.id, dto, chatId)
            call.respond(HttpStatusCode.OK)
        }
    }

//    cacheOutput {
    /**
     * Получение всех пользователей в чате
     * @OpenAPITag chat api
     */
    get {
        val chatId = call.parameters["chatId"] ?: throw IllegalArgumentException("chatId is required")
        call.principal<CustomUserPrincipal>()?.also { principal ->
            val response =
                chatApi.apiChatIdUserGet(principal.id, chatId).map { it.fill(summaryAccountRepository, principal.id) }

            call.respond(response)
        }
    }
//    }
}