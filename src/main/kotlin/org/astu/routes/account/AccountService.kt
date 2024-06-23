package org.astu.routes.account

import api.account.client.AccountServiceException
import api.account.client.apis.AccountApi
import api.account.client.models.AccountDTO
import api.account.client.models.SummaryAccountDTO
import api.auth.client.apis.JWTApi
import api.auth.client.models.JWTRegistrationDTO
import api.bulletinBoard.client.apis.UsersApi
import api.bulletinBoard.client.models.users.UserSummaryDto
import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.post
import io.github.smiley4.ktorswaggerui.dsl.route
import io.ktor.client.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.astu.plugins.CustomUserPrincipal
import org.astu.routes.account.dto.RegistrationDTO
import org.koin.ktor.ext.inject

fun Route.accountServiceDefinition() {
    val client by inject<HttpClient>()
    val accountHost = environment?.config?.property("ktor.account.host")?.getString()
        ?: throw Exception("Account host not set")
    val bulletinBoardHost = environment?.config?.property("ktor.bulletin-board.host")?.getString()
        ?: throw Exception("Bulletin board host not set")
    val authHost = environment?.config?.property("ktor.auth.host")?.getString()
        ?: throw Exception("Auth host not set")

    val accountApi = AccountApi(client, accountHost)
    val userApi = UsersApi(client, bulletinBoardHost)
    val authApi = JWTApi(client, authHost)

    route("/account-service", {
        tags = listOf("account-service")
    }) {
        /**
         * Создание аккаунта
         * @OpenAPITag account api
         */
        post("account", {
            summary = "Создание аккаунта"
            request {
                body<RegistrationDTO>()
            }
        }) {
            val dto = call.receive<RegistrationDTO>()

            val exist = authApi.jwtLoginExist(dto.auth.login)
            if (exist)
                call.respond(HttpStatusCode.Conflict)

            val id = accountApi.addAccount(dto.account)
            println(id)

            val userSummaryDto = UserSummaryDto(id, dto.account.firstName, dto.account.secondName, dto.account.patronymic)
            userApi.register(userSummaryDto)

            val jwtRegDTO = JWTRegistrationDTO(dto.auth.login, dto.auth.password, id)
            authApi.jwtRegistartionPost(jwtRegDTO)
            call.respond(HttpStatusCode.OK)
        }

        get("find", {
            summary = "Поиск аккаунта"
            request {
                queryParameter<String>("u")
            }
            response {
                default {
                    body<List<SummaryAccountDTO>>()
                }
            }
        }) {
            val text = call.request.queryParameters["u"] ?: throw Exception("parameter u is required")
            val result = accountApi.search(text)
            call.respond(HttpStatusCode.OK, result)
        }

        authenticate(("custom")) {
            get("me", {
                summary = "Получение информации о текущем аккаунте"
                response {
                    default {
                        body<AccountDTO>()
                    }
                }
            }) {
                call.principal<CustomUserPrincipal>()?.also { principal ->
                    runCatching {
                        accountApi.getAccount(principal.id)
                    }.onFailure {
                        when (it) {
                            is AccountServiceException ->
                                call.respondText(it.message ?: "", status = HttpStatusCode.BadRequest)

                            else ->
                                call.respondText(it.message ?: "", status = HttpStatusCode.InternalServerError)
                        }
                    }.onSuccess {
                        call.respond(HttpStatusCode.OK, it)
                    }
                }
            }
        }
    }
}