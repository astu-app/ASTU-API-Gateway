package org.astu.routes.single_window.routes

import api.account.client.apis.AccountApi
import api.account.client.models.AccountDTO
import api.request.client.apis.RequestControllerApi
import api.request.client.models.AddRequestDTO
import api.request.client.models.FailRequestDTO
import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.post
import io.ktor.client.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.astu.notify.NotifyApi
import org.astu.plugins.CustomUserPrincipal
import org.astu.routes.single_window.dto.RequestDTO
import java.io.File
import java.util.*

fun Route.request(host: String, accountHost: String,notifyHost: String, token: String, client: HttpClient) {
    val api = RequestControllerApi(client, host)
    val accountApi = AccountApi(client, accountHost)
    val notifyApi = NotifyApi(client , token, notifyHost)

    /**
     * Получение списка запросов пользователя
     */
    get("user/request", {
        summary = "Получение списка запросов пользователя"
        response {
            default {
                body<List<RequestDTO>>()
            }
        }
    }) {
        call.principal<CustomUserPrincipal>()?.also { principal ->
            val userId = UUID.fromString(principal.id)
            val response = runCatching{ api.getRequestsForUser(userId) }
                .onFailure {
                    call.respondText(it.message ?: "", status = HttpStatusCode.BadRequest)
                    return@get
                }.getOrElse {
                    listOf()
                }

            val mappedRequests = response.map {
                RequestDTO(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    type = it.type,
                    status = it.status,
                    message = it.message,
                    userId = it.userId,
                    createdAt = it.createdAt,
                    userInfo = null,
                    fields = it.fields,
                )
            }
            call.respond(mappedRequests)
        }
    }

    /**
     * Удаление запроса
     * @OpenAPITag request api
     */
    post("user/request/{id}/remove", {
        summary = "Удаление запроса"
        request {
            pathParameter<UUID>("id")
        }
    }) {
        val requestId = UUID.fromString(call.parameters["id"])
        call.principal<CustomUserPrincipal>()?.also { _ ->
            runCatching {
                api.removeRequest(requestId)
            }.onFailure {
                call.respondText(it.message ?: "", status = HttpStatusCode.BadRequest)
            }.onSuccess {
                call.respond(HttpStatusCode.OK)
            }
        }
    }

    /**
     * Добавление запроса
     * @OpenAPITag request api
     */
    post("user/request", {
        summary = "Добавление запроса"
        request {
            body<AddRequestDTO>()
        }
        response {
            default {
                body<UUID>()
            }
        }
    }) {
        call.principal<CustomUserPrincipal>()?.also { principal ->
            val dto = call.receive<AddRequestDTO>()

            val userId = UUID.fromString(principal.id)
            runCatching {
                api.addRequest(dto, userId)
            }.onFailure {
                call.respondText(it.message ?: "", status = HttpStatusCode.BadRequest)
            }.onSuccess {
                call.respond(it)
            }
        }
    }

//    checkRole({ it.isEmployee }) {
    /**
     * Получение списка типов запросов для работника отдела
     * @OpenAPITag request api
     */
    get("employee/request", {
        summary = "Получение списка типов запросов для работника отдела"
        response {
            default {
                body<List<RequestDTO>>()
            }
        }
    }) {

//            val account = call.attributes[accountKey]
        call.principal<CustomUserPrincipal>()?.let { user ->
            println(user.id)
            val account = accountApi.getAccount(user.id)
            val departmentId = UUID.fromString(account.departmentId)
            val response = runCatching {
                api.getRequestsForEmployee(departmentId)
            }.onFailure {
                call.respondText(it.message ?: "", status = HttpStatusCode.BadRequest)
                return@get
            }.getOrElse {
                listOf()
            }
            if (response.isEmpty()) {
                call.respond(HttpStatusCode.OK, response)
                return@get
            }
            val request = response.first()

            val accountInfo = kotlin.runCatching { accountApi.getAccount(request.userId) }.getOrElse {
                AccountDTO(
                    request.userId, "NoName", "NoName", "NoName",
                    isEmployee = false,
                    isStudent = false,
                    isAdmin = false,
                    isTeacher = false
                )
            }
            val mappedRequests = response.map {
                RequestDTO(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    type = it.type,
                    status = it.status,
                    message = it.message,
                    userId = it.userId,
                    createdAt = it.createdAt,
                    userInfo = accountInfo,
                    fields = it.fields,
                )
            }
            call.respond(mappedRequests)
        }
    }

    /**
     * Одобрение заявки пользователя
     * @OpenAPITag request api
     */
    post("employee/request/{id}/success", {
        summary = "Одобрение заявки пользователя"
        request {
            pathParameter<UUID>("id")
            multipartBody {
                this.part<File>("files")
            }
        }
    }) {
        val requestId = UUID.fromString(call.parameters["id"])
        val parts = call.receiveMultipart().readAllParts()
        call.principal<CustomUserPrincipal>()?.also { _ ->
            runCatching {
                api.success(requestId, parts)
            }.onFailure {
                call.respondText(it.message ?: "", status = HttpStatusCode.BadRequest)
            }.onSuccess {
                runCatching {
                    notifyApi.notify(it, "Заявка одобрена", "Ваше заявление было одобрено")
                }
                call.respond(HttpStatusCode.OK)
            }
        }
    }

    /**
     * Одобрение заявки пользователя
     * @OpenAPITag request api
     */
    post("employee/request/{id}/approve", {
        summary = "Одобрение заявки пользователя(комментарий)"
        request {
            pathParameter<UUID>("id")
            queryParameter<String>("comment")
        }
    }) {
        val requestId = UUID.fromString(call.parameters["id"])
        val comment = call.request.queryParameters["comment"] ?: return@post call.respond(HttpStatusCode.BadRequest)
        call.principal<CustomUserPrincipal>()?.also { _ ->
            runCatching {
                api.approve(requestId, comment)
            }.onFailure {
                call.respondText(it.message ?: "", status = HttpStatusCode.BadRequest)
            }.onSuccess {
                runCatching {
                    notifyApi.notify(it, "Заявка одобрена", "Ваше заявление было одобрено")
                }
                call.respond(HttpStatusCode.OK)
            }
        }
    }

    /**
     * Отклонение заявки пользователя
     * @OpenAPITag request api
     */
    post("employee/request/{id}/fail", {
        summary = "Отклонение заявки пользователя"
        request {
            pathParameter<UUID>("id")
            body<FailRequestDTO>()
        }
    }) {
        val requestId = UUID.fromString(call.parameters["id"])
        val dto = call.receive<FailRequestDTO>()

        call.principal<CustomUserPrincipal>()?.also { _ ->
            runCatching {
                api.fail(dto, requestId)
            }.onFailure {
                call.respondText(it.message ?: "", status = HttpStatusCode.BadRequest)
            }.onSuccess {
                runCatching {
                    notifyApi.notify(it, "Заявка отклонена", "Ваше заявление было отклонено")
                }
                call.respond(HttpStatusCode.OK)
            }
        }
    }
//    }

}
