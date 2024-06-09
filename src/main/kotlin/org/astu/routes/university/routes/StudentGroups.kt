package org.astu.routes.university.routes

import api.university.client.apis.StudentGroupControllerApi
import api.university.client.models.AddStudentGroupRequest
import api.university.client.models.StudentGroupDto
import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.post
import io.ktor.client.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.studentGroups(host: String, client: HttpClient) = route("/student-groups") {
    val api = StudentGroupControllerApi(client, host)

    /**
     * Получение списка групп
     * @OpenAPITag university api
     */
    get({
        summary = "Получение списка групп"
        response {
            default {
                body<List<StudentGroupDto>>()
            }
        }
    }) {
        val response = api.getAllStudentGroups()
        call.respond(response)
    }

//    checkRole({ it.isAdmin }) {
    /**
     * Добавление типа требований
     * @OpenAPITag university api
     */
    post({
        summary = "Добавление типа требований"
        request {
            body<AddStudentGroupRequest>()
        }
        response {
            default {
                body<StudentGroupDto>()
            }
        }
    }) {
        val dto = call.receive<AddStudentGroupRequest>()
        val id = api.addStudentGroup(dto)
        call.respond(id)
    }
//    }
}
