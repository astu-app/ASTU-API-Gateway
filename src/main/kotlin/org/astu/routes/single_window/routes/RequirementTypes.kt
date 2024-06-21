package org.astu.routes.single_window.routes

import api.request.client.apis.RequirementTypeControllerApi
import api.request.client.models.AddRequirementTypeDTO
import api.request.client.models.RequirementTypeDTO
import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.post
import io.ktor.client.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.astu.plugins.checkRole

fun Route.requirementTypes(host: String, client: HttpClient) = route("/requirement-types") {
    val api = RequirementTypeControllerApi(client, host)

    /**
     * Получение списка типов требований
     * @OpenAPITag request api
     */
    get({
        summary = "Получение списка типов требований"
        response {
            default {
                body<List<RequirementTypeDTO>>()
            }
        }
    }) {
        runCatching { api.get() }
            .onFailure {
                call.respondText(it.message ?: "", status = HttpStatusCode.BadRequest)
            }.onSuccess {
                call.respond(it)
            }
    }

    checkRole({ it.isAdmin }) {
        /**
         * Добавление типа требований
         * @OpenAPITag request api
         */
        post({
            summary = "Добавление типа требований"
            request {
                body<AddRequirementTypeDTO>()
            }
            response {
                default {
                    body<RequirementTypeDTO>()
                }
            }
        }) {
            val dto = call.receive<AddRequirementTypeDTO>()
            val id = api.add(dto)
            call.respond(id)
        }
    }
}
