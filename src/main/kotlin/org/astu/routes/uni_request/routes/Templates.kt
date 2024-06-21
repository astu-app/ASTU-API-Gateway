package org.astu.routes.uni_request.routes

import api.universal_request.client.apis.TemplateControllerApi
import api.universal_request.client.models.TemplateDTO
import api.universal_request.client.models.TemplateFieldDTO
import api.universal_request.client.models.TemplateInfo
import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.post
import io.ktor.client.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File
import java.util.*

fun Route.templates(host: String, client: HttpClient) = route("/templates") {
    val uniRequestApi = TemplateControllerApi(client, host)

    post("upload", {
        summary = "Загрузка шаблона"
        request {
            multipartBody {
                part<File>("file") {
                    ContentType.MultiPart.FormData
                }
                part<TemplateInfo>("info") {
                    ContentType.Application.Json
                }
            }
        }
    }) {
        val parts = call.receiveMultipart().readAllParts()
        parts.forEach { part ->
            when  (part)  {
                is PartData.BinaryChannelItem -> {
                    println("Был найден binary channel ${part.name}")
                }
                is PartData.BinaryItem -> {
                    println("Был найден binary ${part.name}")
                }
                is PartData.FileItem -> {
                    println("Был найден file  ${part.name}")
                }
                is PartData.FormItem -> {
                    println("Был найден form ${part.name}")
                }
            }
        }
        runCatching {
            uniRequestApi.addTemplate(parts)
        }.onFailure {
            call.respondText(it.message ?: "", status = HttpStatusCode.BadRequest)
        }.onSuccess {
            call.respond(HttpStatusCode.OK)
        }
    }
    get({
        summary = "Получение списка шаблонов"
        response {
            default {
                body<List<TemplateDTO>>()
            }
        }
    }) {
        runCatching {
            uniRequestApi.getTemplates()
        }.onFailure {
            call.respondText(it.message ?: "", status = HttpStatusCode.BadRequest)
        }.onSuccess {
            call.respond(it)
        }
    }

    post("{id}", {
        summary = "Заполение шаблона"
        request {
            pathParameter<UUID>("id")
            body<List<TemplateFieldDTO>>()
        }
        response {
            default {
                body<ByteArray>()
            }
        }
    }) {
        val id = call.parameters["id"]!!
        val dto = call.receive<List<TemplateFieldDTO>>()
        runCatching {
            uniRequestApi.fillTemplate(UUID.fromString(id), dto)
        }.onFailure {
            call.respondText(it.message ?: "", status = HttpStatusCode.BadRequest)
        }.onSuccess {
            call.respondBytes(it.content, it.contentType)
        }
    }
}
