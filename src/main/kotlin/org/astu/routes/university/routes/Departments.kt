package org.astu.routes.university.routes

import api.university.client.apis.DepartmentControllerApi
import api.university.client.models.AddDepartmentRequest
import api.university.client.models.DepartmentDto
import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.post
import io.ktor.client.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.departments(host: String, client: HttpClient) = route("/departments") {
    val api = DepartmentControllerApi(client, host)

    /**
     * Получение списка отделов
     * @OpenAPITag university api
     */
    get({
        summary = "Получение списка отделов"
        response {
            default {
                body<List<DepartmentDto>>()
            }
        }
    }) {
        val response = api.getDepartments()
        call.respond(response)
    }

//    checkRole({ it.isAdmin }) {
    /**
     * Добавление отдела
     * @OpenAPITag university api
     */
    post({
        summary = "Добавление отдела"
        request {
            body<AddDepartmentRequest>()
        }
        response {
            default {
                body<DepartmentDto>()
            }
        }
    }){
        val dto = call.receive<AddDepartmentRequest>()
        val id = api.addDepartment(dto)
        call.respond(id)
    }
//    }
}
