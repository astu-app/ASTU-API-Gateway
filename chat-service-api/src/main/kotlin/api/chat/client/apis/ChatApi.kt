package api.chat.client.apis

import api.chat.client.models.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.datetime.Instant
import kotlin.collections.Map

class ChatApi(private val client: HttpClient, private val baseUrl: String) {
    /**
     *
     * Получение всех чатов, где состоит авторизованный пользователь
     * @return kotlin.Array<Chat>
     */
    suspend fun apiChatGet(userId: String): List<ChatDTO> {
        val response = client.get("${baseUrl}api/user/$userId/chat") {
        }
        return when (response.status) {
            HttpStatusCode.OK -> response.body<List<ChatDTO>>()
            else -> throw RuntimeException()
        }
    }

    /**
     *
     * Информация о чате
     * @param id
     * @return Chat
     */
    suspend fun apiChatIdGet(userId: String, id: String): ChatDTO {
        val response = client.get("${baseUrl}api/user/$userId/chat/$id")
        return when (response.status) {
            HttpStatusCode.OK -> response.body<ChatDTO>()
            else -> throw RuntimeException()
        }
    }

    /**
     *
     * Выдача последних сообщений постранично
     * @param id
     * @param page  (optional)
     * @param elements  (optional)
     * @return kotlin.Array<Message>
     */
    suspend fun apiChatIdMsgPageGet(
        userId: String,
        id: String,
        page: Int,
        elements: Int? = null
    ): List<MessageDTO> {

        val response = client.get("${baseUrl}api/user/$userId/chat/$id/msg/page") {
            if (elements != null)
                parameter("elements", elements)
            parameter("page", page)
        }
        return when (response.status) {
            HttpStatusCode.OK -> response.body<List<MessageDTO>>()
            else -> throw RuntimeException()
        }
    }

    /**
     *
     * Добавление сообщения
     * @param body
     * @param id
     * @return MapString
     */
    suspend fun apiChatIdMsgPost(userId: String,body: AddMessageDTO, id: String): String {
        val response = client.post("${baseUrl}api/user/$userId/chat/$id/msg") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(body)
        }
        return when (response.status) {
            HttpStatusCode.OK -> response.body<Map<String, String>>()["id"]!!
            else -> throw RuntimeException()
        }
    }

    /**
     *
     * Выдача последних сообщений с последнего datetime
     * @param id
     * @param datetime  (optional)
     * @return kotlin.Array<Message>
     */
    suspend fun apiChatIdMsgTimeGet(userId: String, id: String, datetime: Instant): List<MessageDTO> {
        val response = client.get("${baseUrl}api/user/$userId/chat/$id/msg/time") {
            parameter("datetime", datetime)
        }
        return when (response.status) {
            HttpStatusCode.OK -> response.body<List<MessageDTO>>()
            else -> throw RuntimeException()
        }
    }

    /**
     *
     * Добавление пользователя в чат
     * @param body
     * @param id
     * @return kotlin.Any
     */
    suspend fun apiChatIdUserPost(userId: String, body: AddUserDTO, id: String) {
        val response = client.post("${baseUrl}api/user/$userId/chat/$id/user") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(body)
        }
        if (response.status == HttpStatusCode.OK)
            return
        throw RuntimeException()
    }

    /**
     *
     * Создание нового чата авторизованным пользователем
     * @param body
     * @return MapString
     */
    suspend fun apiChatPost(userId: String, body: AddChatDTO): String {
        val response = client.post("${baseUrl}api/user/$userId/chat") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(body)
        }
        return when (response.status) {
            HttpStatusCode.OK -> response.body<Map<String, String>>()["id"]!!
            else -> throw RuntimeException()
        }
    }

    /**
     *
     * Выдача последних сообщений постранично
     * @param id
     * @return kotlin.Array<ChatMemberDTO>
     */
    suspend fun apiChatIdUserGet(userId: String, id: String): Array<ChatMemberDTO> {
        val response = client.get("${baseUrl}api/user/$userId/chat/$id/user") {
        }
        return when (response.status) {
            HttpStatusCode.OK -> response.body<Array<ChatMemberDTO>>()
            else -> throw RuntimeException()
        }
    }
}