package org.astu.routes.single_window.routes

import api.request.client.apis.TemplateControllerApi
import api.request.client.models.AddTemplateDTO
import api.request.client.models.TemplateDTO
import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.post
import io.ktor.client.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.astu.plugins.CustomUserPrincipal
import org.astu.plugins.checkRole
import java.util.*

fun Route.templates(host: String, client: HttpClient) = route("/template") {
    val api = TemplateControllerApi(client, host)

    /**
     * Получение списка шаблонов для пользователя
     * @OpenAPITag request api
     */
    get({
        summary = "Получение списка шаблонов для пользователя"
        response {
            default {
                body<List<TemplateDTO>>()
            }
        }
    }) {
        call.principal<CustomUserPrincipal>()?.also { principal ->
            val id = UUID.fromString(principal.id)
            val response = api.getTemplates(id)
            call.respond(response)
        }
    }

//    checkRole({ it.isAdmin }) {
        /**
         * Добавление шаблона
         * @OpenAPITag request api
         */
        post({
            summary = "Добавление шаблона"
            request {
                body<AddTemplateDTO>()
            }
            response {
                default {
                    body<String>()
                }
            }
        }) {
            val dto = call.receive<AddTemplateDTO>()
            val id = api.addTemplate(dto)
            call.respond(id)
        }
//    }
}
