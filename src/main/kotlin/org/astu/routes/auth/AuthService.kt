package org.astu.routes.auth

import api.auth.client.apis.JWTApi
import api.auth.client.models.JWTLoginDTO
import api.auth.client.models.Tokens
import io.github.smiley4.ktorswaggerui.dsl.post
import io.ktor.client.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.authServiceDefinition() {
    val host = environment?.config?.property("ktor.auth.host")?.getString()
        ?: throw IllegalArgumentException("ktor.auth.host must be provided")
    val client by inject<HttpClient>()

    /**
     * Авторизация пользователя с помощью JWT
     * @OpenAPITag auth api
     */
    post("/auth/jwt/login", {
        summary = "Авторизация пользователя с помощью JWT"
        tags = listOf("auth")
        request {
            body<JWTLoginDTO>()
        }
        response {
            default {
                body<Tokens>()
            }
        }
    }) {
        val dto = call.receive<JWTLoginDTO>()
        val response = JWTApi(client, host).jwtLoginPost(dto)
        call.respond(response)
    }
}