package org.astu.routes.single_window.routes

import api.request.client.apis.RequestControllerApi
import api.request.client.models.AddRequestDTO
import api.request.client.models.AddRequirementTypeDTO
import api.request.client.models.FailRequestDTO
import api.request.client.models.RequestDTO
import io.github.smiley4.ktorswaggerui.dsl.delete
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
import org.astu.plugins.CustomUserPrincipal
import org.astu.plugins.accountKey
import org.astu.plugins.checkRole
import java.io.File
import java.util.*

fun Route.request(host: String, client: HttpClient) {
    val api = RequestControllerApi(client, host)

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
            val response = api.getRequestsForUser(userId)
            call.respond(response)
        }
    }

    /**
     * Удаление запроса
     * @OpenAPITag request api
     */
    delete("user/request/{id}", {
        summary = "Удаление запроса"
        request {
            pathParameter<UUID>("id")
        }
    }) {
        val requestId = UUID.fromString(call.parameters["id"])
        call.principal<CustomUserPrincipal>()?.also { _ ->
            api.removeRequest(requestId)
            call.respond(HttpStatusCode.OK)
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
            val id = api.addRequest(dto, userId)
            call.respond(id)
        }
    }

    checkRole({ it.isEmployee }) {
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
            val account = call.attributes[accountKey]
            val departmentId = UUID.fromString(account.employeeInfo.departmentId)
            val response = api.getRequestsForEmployee(departmentId)
            call.respond(response)
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
                body<AddRequirementTypeDTO>()
            }
        }) {
            val requestId = UUID.fromString(call.parameters["id"])
            val parts = call.receiveMultipart().readAllParts()
            call.principal<CustomUserPrincipal>()?.also { _ ->
                api.success(requestId, parts)
                call.respond(HttpStatusCode.OK)
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
                api.fail(dto, requestId)
                call.respond(HttpStatusCode.OK)
            }
        }
    }

}
