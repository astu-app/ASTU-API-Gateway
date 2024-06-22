package org.astu.routes.single_window.routes

import api.account.client.apis.AccountApi
import api.request.client.apis.TemplateControllerApi
import api.request.client.models.AddTemplateDTO
import api.request.client.models.TemplateDTO
import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.post
import io.ktor.client.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.astu.plugins.CustomUserPrincipal
import java.util.*

fun Route.templates(host: String, accountHost: String, client: HttpClient)  = route("/template") {
    val api = TemplateControllerApi(client, host)
    val accountApi = AccountApi(client, accountHost)

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
            runCatching {
                val account = accountApi.getAccount(principal.id)
                api.getTemplates(id, account)
            }.onFailure {
                println(it.message)
                println(it)
                call.respondText(it.message ?: "", status = HttpStatusCode.BadRequest)
            }.onSuccess {
                call.respond(it)
            }
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
