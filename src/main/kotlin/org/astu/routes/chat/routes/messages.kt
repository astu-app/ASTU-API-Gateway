package org.astu.routes.chat.routes

import api.chat.client.apis.ChatApi
import api.chat.client.models.AddMessageDTO
import io.ktor.client.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.Instant
import org.astu.plugins.CustomUserPrincipal

fun Route.messages(host: String, client: HttpClient) = route("/chats/{chatId}/messages") {
    val api = ChatApi(client, host)

    /**
     * Отправка сообщения
     * @OpenAPITag chat api
     */
    post {
        val chatId = call.parameters["chatId"] ?: throw IllegalArgumentException("chatId is required")
        val dto = call.receive<AddMessageDTO>()

        call.principal<CustomUserPrincipal>()?.also { principal ->
            val id = api.apiChatIdMsgPost(principal.id, dto, chatId)
            call.respond(id)
        }
    }

    /**
     * Постраничная выдача сообщений
     * @OpenAPITag chat api
     */
    get("page") {
        val chatId = call.parameters["chatId"] ?: throw IllegalArgumentException("chatId is required")
        val page = call.request.queryParameters["page"]?.toInt() ?: throw IllegalArgumentException("page is required")
        val elements = call.request.queryParameters["elements"]?.toInt()

        call.principal<CustomUserPrincipal>()?.also { principal ->
            val response = api.apiChatIdMsgPageGet(principal.id, chatId, page, elements)
            call.respond(response)
        }
    }

    /**
     * Выдача сообщений по времени
     * @OpenAPITag chat api
     */
    get("time") {
        val chatId = call.parameters["chatId"] ?: throw IllegalArgumentException("chatId is required")
        val datetime = call.request.queryParameters["datetime"]
            ?: throw IllegalArgumentException("datetime is required")
        val utcDatetime = Instant.parse(datetime)

        call.principal<CustomUserPrincipal>()?.also { principal ->
            val response = api.apiChatIdMsgTimeGet(principal.id, chatId, utcDatetime)
            call.respond(response)
        }
    }
}