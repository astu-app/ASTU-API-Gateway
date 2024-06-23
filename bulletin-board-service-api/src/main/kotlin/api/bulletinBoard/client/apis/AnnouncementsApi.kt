package api.bulletinBoard.client.apis

import api.bulletinBoard.client.infrastructure.ErrorCode
import api.bulletinBoard.client.infrastructure.HttpResponseContent
import api.bulletinBoard.client.models.announcements.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class AnnouncementsApi(private val client: HttpClient, private val baseUrl: String) {
    /**
     * Создать объявление
     */
    suspend fun createAnnouncement(createAnnouncementDto: CreateAnnouncementDto, principalId: String, rootUserGroupId: String): HttpResponseContent {
        val response = client.post("${baseUrl}api/announcements/create") {
            headers {
                append("X-User-Id", principalId)
                append("X-Root-UserGroup-Id", rootUserGroupId)
                append("Content-Type", "application/json")
            }
            setBody(createAnnouncementDto)
        }
        return when (response.status) {
            HttpStatusCode.Created -> HttpResponseContent(null, response.status)
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
     * Получить подробности о выбранном объявлении
     */
    suspend fun getAnnouncementDetails(announcementId: String, principalId: String): HttpResponseContent {
        val response = client.get("${baseUrl}api/announcements/get-details/$announcementId") {
            headers {
                append("X-User-Id", principalId)
            }
        }
        return when (response.status) {
            HttpStatusCode.OK -> HttpResponseContent(response.body<AnnouncementDetailsDto>(), response.status)
            HttpStatusCode.BadRequest -> HttpResponseContent(null, response.status)
            HttpStatusCode.Unauthorized -> HttpResponseContent(null, response.status)
            HttpStatusCode.Forbidden -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.NotFound -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.InternalServerError -> HttpResponseContent(null, response.status)
            else -> HttpResponseContent(null, response.status)
        }
    }

    /**
     * Получить данные для редактирования объявления
     */
    suspend fun getAnnouncementUpdateContent(announcementId: String, principalId: String): HttpResponseContent {
        val response = client.get("${baseUrl}api/announcements/get-update-content/$announcementId") {
            headers {
                append("X-User-Id", principalId)
            }
        }
        return when (response.status) {
            HttpStatusCode.OK -> HttpResponseContent(response.body<ContentForAnnouncementUpdatingDto>(), response.status)
            HttpStatusCode.BadRequest -> HttpResponseContent(null, response.status)
            HttpStatusCode.Unauthorized -> HttpResponseContent(null, response.status)
            HttpStatusCode.Forbidden -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.NotFound -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.InternalServerError -> HttpResponseContent(null, response.status)
            else -> HttpResponseContent(null, response.status)
        }
    }

    /**
     * Редактировать объявление
     */
    suspend fun updateAnnouncement(updateAnnouncementDto: UpdateAnnouncementDto, principalId: String): HttpResponseContent {
        val response = client.put("${baseUrl}api/announcements/update") {
            headers {
                append("X-User-Id", principalId)
                append("Content-Type", "application/json")
            }
            setBody(updateAnnouncementDto)
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
     * Добавить просмотр объявлению
     */
    suspend fun addViewToAnnouncement(announcementId: String, principalId: String): HttpResponseContent {
        val response = client.post("${baseUrl}api/announcements/addView") {
            headers {
                append("X-User-Id", principalId)
                append("Content-Type", "application/json")
            }
            setBody(announcementId)
        }
        return when (response.status) {
            HttpStatusCode.OK -> HttpResponseContent(null, response.status)
            HttpStatusCode.Unauthorized -> HttpResponseContent(null, response.status)
            HttpStatusCode.NotFound -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.InternalServerError -> HttpResponseContent(null, response.status)
            else -> HttpResponseContent(null, response.status)
        }
    }

    /**
     * Удалить объявление
     */
    suspend fun deleteAnnouncement(announcementId: String, principalId: String): HttpResponseContent {
        val response = client.delete("${baseUrl}api/announcements/delete") {
            headers {
                append("X-User-Id", principalId)
                append("Content-Type", "application/json")
            }
            setBody("$announcementId")
        }
        return when (response.status) {
            HttpStatusCode.OK -> HttpResponseContent(null, response.status)
            HttpStatusCode.BadRequest -> HttpResponseContent(null, response.status)
            HttpStatusCode.Unauthorized -> HttpResponseContent(null, response.status)
            HttpStatusCode.Unauthorized -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.Forbidden -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.NotFound -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.InternalServerError -> HttpResponseContent(null, response.status)
            else -> HttpResponseContent(null, response.status)
        }
    }

    /**
     * Получить список опубликованных объявлений
     */
    suspend fun getPostedAnnouncementList(principalId: String): HttpResponseContent {
        val response = client.get("${baseUrl}api/announcements/published/get-list") {
            headers {
                append("X-User-Id", principalId)
            }
        }
        return when (response.status) {
            HttpStatusCode.OK -> HttpResponseContent(response.body<List<AnnouncementSummaryDto>>(), response.status)
            HttpStatusCode.Unauthorized -> HttpResponseContent(null, response.status)
            HttpStatusCode.Forbidden -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.InternalServerError -> HttpResponseContent(null, response.status)
            else -> HttpResponseContent(null, response.status)
        }
    }

    /**
     * Скрыть опубликованное объявление
     */
    suspend fun hidePostedAnnouncement(announcementId: String, principalId: String): HttpResponseContent {
        val response = client.post("${baseUrl}api/announcements/published/hide") {
            headers {
                append("X-User-Id", principalId)
                append("Content-Type", "application/json")
            }
            setBody("$announcementId")
        }
        return when (response.status) {
            HttpStatusCode.OK -> HttpResponseContent(null, response.status)
            HttpStatusCode.BadRequest -> HttpResponseContent(null, response.status)
            HttpStatusCode.Unauthorized -> HttpResponseContent(null, response.status)
            HttpStatusCode.Forbidden -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.NotFound -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.Conflict -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.InternalServerError -> HttpResponseContent(null, response.status)
            else -> HttpResponseContent(null, response.status)
        }
    }

    /**
     * Получить список скрытых объявлений
     */
    suspend fun getHiddenAnnouncementList(principalId: String): HttpResponseContent {
        val response = client.get("${baseUrl}api/announcements/hidden/get-list") {
            headers {
                append("X-User-Id", principalId)
            }
        }
        return when (response.status) {
            HttpStatusCode.OK -> HttpResponseContent(response.body<List<AnnouncementSummaryDto>>(), response.status)
            HttpStatusCode.Unauthorized -> HttpResponseContent(null, response.status)
            HttpStatusCode.Forbidden -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.InternalServerError -> HttpResponseContent(null, response.status)
            else -> HttpResponseContent(null, response.status)
        }
    }

    /**
     * Восстановить скрытое объявление
     */
    suspend fun restoreHiddenAnnouncement(announcementId: String, principalId: String): HttpResponseContent {
        val response = client.post("${baseUrl}api/announcements/hidden/restore") {
            headers {
                append("X-User-Id", principalId)
                append("Content-Type", "application/json")
            }
            setBody("$announcementId")
        }
        return when (response.status) {
            HttpStatusCode.OK -> HttpResponseContent(null, response.status)
            HttpStatusCode.BadRequest -> HttpResponseContent(null, response.status)
            HttpStatusCode.Unauthorized -> HttpResponseContent(null, response.status)
            HttpStatusCode.Forbidden -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.NotFound -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.Conflict -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.InternalServerError -> HttpResponseContent(null, response.status)
            else -> HttpResponseContent(null, response.status)
        }
    }

    /**
     * Получить список объявлений, ожидающих отложенную публикацию
     */
    suspend fun getDelayedPublishingAnnouncementList(principalId: String): HttpResponseContent {
        val response = client.get("${baseUrl}api/announcements/delayed-publishing/get-list") {
            headers {
                append("X-User-Id", principalId)
            }
        }
        return when (response.status) {
            HttpStatusCode.OK -> HttpResponseContent(response.body<List<AnnouncementSummaryDto>>(), response.status)
            HttpStatusCode.Unauthorized -> HttpResponseContent(null, response.status)
            HttpStatusCode.Forbidden -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.InternalServerError -> HttpResponseContent(null, response.status)
            else -> HttpResponseContent(null, response.status)
        }
    }

    /**
     * Сразу опубликовать отложенное объявление
     */
    suspend fun publishImmediatelyDelayedPublishingAnnouncement(announcementId: String, principalId: String): HttpResponseContent {
        val response = client.post("${baseUrl}api/announcements/delayed-publishing/publish-immediately") {
            headers {
                append("X-User-Id", principalId)
                append("Content-Type", "application/json")
            }
            setBody("$announcementId")
        }
        return when (response.status) {
            HttpStatusCode.OK -> HttpResponseContent(null, response.status)
            HttpStatusCode.BadRequest -> HttpResponseContent(null, response.status)
            HttpStatusCode.Unauthorized -> HttpResponseContent(null, response.status)
            HttpStatusCode.Forbidden -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.NotFound -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.InternalServerError -> HttpResponseContent(null, response.status)
            else -> HttpResponseContent(null, response.status)
        }
    }

    /**
     * Получить список объявлений, ожидающих отложенное сокрытие
     */
    suspend fun getDelayedHiddenAnnouncementList(principalId: String): HttpResponseContent {
        val response = client.get("${baseUrl}api/announcements/delayed-hidden/get-list") {
            headers {
                append("X-User-Id", principalId)
            }
        }
        return when (response.status) {
            HttpStatusCode.OK -> HttpResponseContent(response.body<List<AnnouncementSummaryDto>>(), response.status)
            HttpStatusCode.Unauthorized -> HttpResponseContent(null, response.status)
            HttpStatusCode.Forbidden -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.InternalServerError -> HttpResponseContent(null, response.status)
            else -> HttpResponseContent(null, response.status)
        }
    }
}