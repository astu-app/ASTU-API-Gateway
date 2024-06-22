package org.astu.routes.bullletinBoard.routes

import api.bulletinBoard.client.infrastructure.HttpResponseContent
import io.github.smiley4.ktorswaggerui.dsl.delete
import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.post
import io.github.smiley4.ktorswaggerui.dsl.put
import io.ktor.client.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.astu.plugins.CustomUserPrincipal
import org.astu.routes.bullletinBoard.respond

fun Route.userGroups(bulletinBoardHost: String, client: HttpClient) = route("/usergroups") {
    val userGroupsApi = api.bulletinBoard.client.apis.UserGroupsApi(client, bulletinBoardHost)

    /**
     * Получить данные для создания группы пользователей
     * @OpenAPITag bulletin board api
     */
    get("get-create-content", {
        summary = "Получить данные для создания группы пользователей"
        tags = listOf("bulletin-board-service")
    }) {
        call.principal<CustomUserPrincipal>()?.also { principal ->
            val response = userGroupsApi.getUsergroupCreateContent(principal.id)
            if (response.content != null)
                call.respond(response.status, response.content!!)
            else
                call.respond(response.status)
        }
    }

    /**
     * Создать группу пользователей
     * @OpenAPITag bulletin board api
     */
    post("create", {
        summary = "Создать группу пользователей"
        tags = listOf("bulletin-board-service")
    }) {
        val dto = call.receive<api.bulletinBoard.client.models.userGroups.CreateUserGroupDto>()
        call.principal<CustomUserPrincipal>()?.also { principal ->
            val response = userGroupsApi.createUsergroup(dto, principal.id)
            respond(call, response)
        }
    }

    /**
     * Получить список групп пользователей, управляемых пользователем
     * @OpenAPITag bulletin board api
     */
    get("get-owned-list", {
        summary = "Получить список групп пользователей, управляемых пользователем"
        tags = listOf("bulletin-board-service")
    }) {
        call.principal<CustomUserPrincipal>()?.also { principal ->
            val response = userGroupsApi.getOwnedUsergroups(principal.id)
            respond(call, response)
        }
    }

    /**
     * Получение иерархии управляемых групп пользователей для пользователя
     * @OpenAPITag bulletin board api
     */
    get("get-owned-hierarchy", {
        summary = "Получение иерархии управляемых групп пользователей для пользователя"
        tags = listOf("bulletin-board-service")
    }) {
        call.principal<CustomUserPrincipal>()?.also { principal ->
            val response = userGroupsApi.getOwnedHierarchy(principal.id)
            respond(call, response)
        }
    }

    /**
     * Получение подробной информации о группе пользователей
     * @OpenAPITag bulletin board api
     */
    get("get-details/{id}", {
        summary = "Получение подробной информации о группе пользователей"
        tags = listOf("bulletin-board-service")
    }) {
        val userGroupId = call.parameters["id"] ?: throw IllegalArgumentException("usergroup id is required")
        call.principal<CustomUserPrincipal>()?.also { principal ->
            val response = userGroupsApi.getDetails(userGroupId, principal.id)
            respond(call, response)
        }
    }

    /**
     * Получение данных для редактирования группы пользователей
     * @OpenAPITag bulletin board api
     */
    get("get-update-content/{id}", {
        summary = "Получение данных для редактирования группы пользователей"
        tags = listOf("bulletin-board-service")
    }) {
        val userGroupId = call.parameters["id"] ?: throw IllegalArgumentException("usergroup id is required")
        call.principal<CustomUserPrincipal>()?.also { principal ->
            val response = userGroupsApi.getUsergroupUpdateContent(userGroupId, principal.id)
            respond(call, response)
        }
    }

    /**
     * Редактирование группы пользователей
     * @OpenAPITag bulletin board api
     */
    put("update", {
        summary = "Редактирование группы пользователей"
        tags = listOf("bulletin-board-service")
    }) {
        val dto = call.receive<api.bulletinBoard.client.models.userGroups.UpdateUserGroupDto>()
        call.principal<CustomUserPrincipal>()?.also { principal ->
            val response = userGroupsApi.updateUsergroup(dto, principal.id)
            respond(call, response)
        }
    }

    /**
     * Удалить группу пользователей
     * @OpenAPITag bulletin board api
     */
    delete("delete", {
        summary = "Удалить группу пользователей"
        tags = listOf("bulletin-board-service")
    }) {
        val userGroupId = call.receive<String>()
        call.principal<CustomUserPrincipal>()?.also { principal ->
            val response = userGroupsApi.deleteUsergroup(userGroupId, principal.id)
            respond(call, response)
        }
    }
}
