package api.bulletinBoard.client.apis

import api.bulletinBoard.client.infrastructure.ErrorCode
import api.bulletinBoard.client.infrastructure.HttpResponseContent
import api.bulletinBoard.client.models.userGroups.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class UserGroupsApi(private val client: HttpClient, private val baseUrl: String) {
    /**
     * Получить данные для создания группы пользователей
     */
    suspend fun getUsergroupCreateContent(principalId: String, rootUserGroupId: String): HttpResponseContent {
        val response = client.get("${baseUrl}api/usergroups/get-create-content") {
            headers {
                append("X-User-Id", principalId)
                append("X-Root-UserGroup-Id", rootUserGroupId)
            }
        }
        return when (response.status) {
            HttpStatusCode.OK -> HttpResponseContent(response.body<GetUsergroupCreateContentDto>(), response.status)
            HttpStatusCode.Unauthorized -> HttpResponseContent(null, response.status)
            HttpStatusCode.Forbidden -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.InternalServerError -> HttpResponseContent(null, response.status)
            else -> HttpResponseContent(null, response.status)
        }
    }

    /**
     * Создать группу пользователей
     */
    suspend fun createUsergroup(createUserGroupDto: CreateUserGroupDto, principalId: String, rootUserGroupId: String): HttpResponseContent {
        val response = client.post("${baseUrl}api/usergroups/create") {
            headers {
                append("X-User-Id", principalId)
                append("X-Root-UserGroup-Id", rootUserGroupId)
                append("Content-Type", "application/json")
            }
            setBody(createUserGroupDto)
        }
        return when (response.status) {
            HttpStatusCode.Created -> HttpResponseContent(response.body<String>(), response.status)
            HttpStatusCode.BadRequest -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.Unauthorized -> HttpResponseContent(null, response.status)
            HttpStatusCode.Forbidden -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.NotFound -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.Conflict -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.InternalServerError -> HttpResponseContent(null, response.status)
            else -> HttpResponseContent(null, response.status)
        }
    }

    /**
     * Получение списка групп пользователей, администратором которой является пользователь, запрашивающий операцию
     */
    suspend fun getOwnedUsergroups(principalId: String): HttpResponseContent {
        val response = client.get("${baseUrl}api/usergroups/get-owned-list") {
            headers {
                append("X-User-Id", principalId)
            }
        }
        return when (response.status) {
            HttpStatusCode.OK -> HttpResponseContent(response.body<List<UserGroupSummaryDto>>(), response.status)
            HttpStatusCode.Unauthorized -> HttpResponseContent(null, response.status)
            HttpStatusCode.Forbidden -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.InternalServerError -> HttpResponseContent(null, response.status)
            else -> HttpResponseContent(null, response.status)
        }
    }

    /**
     * Получение иерархии управляемых групп пользователей для пользователя
     */
    suspend fun getOwnedHierarchy(principalId: String): HttpResponseContent {
        val response = client.get("${baseUrl}api/usergroups/get-owned-hierarchy") {
            headers {
                append("X-User-Id", principalId)
            }
        }
        return when (response.status) {
            HttpStatusCode.OK -> HttpResponseContent(response.body<UserGroupHierarchyDto>(), response.status)
            HttpStatusCode.BadRequest -> HttpResponseContent(null, response.status)
            HttpStatusCode.Unauthorized -> HttpResponseContent(null, response.status)
            HttpStatusCode.Forbidden -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.InternalServerError -> HttpResponseContent(null, response.status)
            else -> HttpResponseContent(null, response.status)
        }
    }

    /**
     * Получение подробной информации о группе пользователей
     */
    suspend fun getDetails(userGroupId: String, principalId: String, rootUserGroupId: String): HttpResponseContent {
        val response = client.get("${baseUrl}api/usergroups/get-details/$userGroupId") {
            headers {
                append("X-User-Id", principalId)
                append("X-Root-UserGroup-Id", rootUserGroupId)
            }
        }
        return when (response.status) {
            HttpStatusCode.OK -> HttpResponseContent(response.body<UserGroupDetailsDto>(), response.status)
            HttpStatusCode.BadRequest -> HttpResponseContent(null, response.status)
            HttpStatusCode.Unauthorized -> HttpResponseContent(null, response.status)
            HttpStatusCode.Forbidden -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.NotFound -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.InternalServerError -> HttpResponseContent(null, response.status)
            else -> HttpResponseContent(null, response.status)
        }
    }

    /**
     * Получение данных для редактирования группы пользователей
     */
    suspend fun getUsergroupUpdateContent(userGroupId: String, principalId: String, rootUserGroupId: String): HttpResponseContent {
        val response = client.get("${baseUrl}api/usergroups/get-update-content/$userGroupId") {
            headers {
                append("X-User-Id", principalId)
                append("X-Root-UserGroup-Id", rootUserGroupId)
            }
        }
        return when (response.status) {
            HttpStatusCode.OK -> HttpResponseContent(response.body<ContentForUserGroupEditingDto>(), response.status)
            HttpStatusCode.BadRequest -> HttpResponseContent(null, response.status)
            HttpStatusCode.Unauthorized -> HttpResponseContent(null, response.status)
            HttpStatusCode.Forbidden -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.NotFound -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.InternalServerError -> HttpResponseContent(null, response.status)
            else -> HttpResponseContent(null, response.status)
        }
    }

    /**
     * Редактирование группы пользователей
     */
    suspend fun updateUsergroup(updateUserGroupDto: UpdateUserGroupDto, principalId: String, rootUserGroupId: String): HttpResponseContent {
        val response = client.put("${baseUrl}api/usergroups/update") {
            headers {
                append("X-User-Id", principalId)
                append("X-Root-UserGroup-Id", rootUserGroupId)
                append("Content-Type", "application/json")
            }
            setBody(updateUserGroupDto)
        }
        return when (response.status) {
            HttpStatusCode.OK -> HttpResponseContent(null, response.status)
            HttpStatusCode.BadRequest -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.Unauthorized -> HttpResponseContent(null, response.status)
            HttpStatusCode.Forbidden -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.NotFound -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.Conflict -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.InternalServerError -> HttpResponseContent(null, response.status)
            else -> HttpResponseContent(null, response.status)
        }
    }

    /**
     * Удалить группу пользователей
     */
    suspend fun deleteUsergroup(userGroupId: String, principalId: String, rootUserGroupId: String): HttpResponseContent {
        val response = client.delete("${baseUrl}api/usergroups/delete") {
            headers {
                append("X-User-Id", principalId)
                append("X-Root-UserGroup-Id", rootUserGroupId)
                append("Content-Type", "application/json")
            }
            setBody("$userGroupId")
        }
        return when (response.status) {
            HttpStatusCode.OK -> HttpResponseContent(null, response.status)
            HttpStatusCode.BadRequest -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.Unauthorized -> HttpResponseContent(null, response.status)
            HttpStatusCode.Forbidden -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.NotFound -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.InternalServerError -> HttpResponseContent(null, response.status)
            else -> HttpResponseContent(null, response.status)
        }
    }
}