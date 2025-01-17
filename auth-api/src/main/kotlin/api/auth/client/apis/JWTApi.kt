/**
 * AuthService API
 * Сервис авторизации пользователей
 *
 * OpenAPI spec version: 1.0.0
 *
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
package api.auth.client.apis

import api.auth.client.AuthServiceException
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import api.auth.client.models.JWTLoginDTO
import api.auth.client.models.JWTRegistrationDTO
import api.auth.client.models.Tokens
import io.ktor.client.statement.*

class JWTApi(val client: HttpClient, private val basePath: String = "/") {

    /**
     * Авторизация с помощью JWT
     * @param body
     * @return Tokens
     */
    suspend fun jwtLoginPost(body: JWTLoginDTO): Tokens {
        val response = client.post("${basePath}jwt/login") {
            this.contentType(ContentType.Application.Json)
            setBody(body)
        }
        return when (response.status) {
            HttpStatusCode.OK -> response.body<Tokens>()
            HttpStatusCode.BadRequest -> throw AuthServiceException(response.bodyAsText())
            else -> throw AuthServiceException(response.bodyAsText())
        }
    }

    suspend fun jwtRegistartionPost(body: JWTRegistrationDTO): Tokens {
        val response = client.post("${basePath}jwt/registration") {
            contentType(ContentType.Application.Json)
            setBody(body)
        }
        return when (response.status) {
            HttpStatusCode.OK -> response.body<Tokens>()
            else -> throw AuthServiceException("Не удалось зарегистрировать аккаунт")
        }
    }

    suspend fun jwtLoginExist(login: String): Boolean {
        val response = client.get("${basePath}login/${login}")
        return when (response.status) {
            HttpStatusCode.OK -> response.body<Boolean>()
            else -> throw AuthServiceException("Не удалось получить информацию о наличии логина")
        }
    }
}
