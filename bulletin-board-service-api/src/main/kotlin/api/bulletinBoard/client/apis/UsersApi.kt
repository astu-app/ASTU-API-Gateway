package api.bulletinBoard.client.apis

import api.account.client.AccountServiceException
import api.account.client.ErrorResponse
import api.bulletinBoard.client.infrastructure.ErrorCode
import api.bulletinBoard.client.infrastructure.HttpResponseContent
import api.bulletinBoard.client.models.userGroups.*
import api.bulletinBoard.client.models.users.UserSummaryDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class UsersApi(private val client: HttpClient, private val baseUrl: String) {
    /**
     * Зарегистрировать пользователя
     */
    suspend fun register(user: UserSummaryDto): String {
        val response = client.post("${baseUrl}api/users/register") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }
        if (response.status.isSuccess())
            return response.body<String>().removeSurrounding("\"")
        else
            throw AccountServiceException("Произошла ошибка при добавлении пользователя")
    }
}