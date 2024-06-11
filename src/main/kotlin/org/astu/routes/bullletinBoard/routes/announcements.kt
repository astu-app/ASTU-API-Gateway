package org.astu.routes.bullletinBoard.routes

import api.bulletinBoard.client.apis.AnnouncementsApi
import api.bulletinBoard.client.models.announcements.CreateAnnouncementDto
import api.bulletinBoard.client.models.announcements.UpdateAnnouncementDto
import io.ktor.client.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.astu.plugins.CustomUserPrincipal
import org.astu.routes.bullletinBoard.respond

fun Route.announcements(bulletinBoardHost: String, client: HttpClient) = route("/announcements/") {
    val announcementsApi = api.bulletinBoard.client.apis.AnnouncementsApi(client, bulletinBoardHost)

    /**
     * Создать объявление
     * @OpenAPITag bulletin board api
     */
    post("create") {
        val dto = call.receive<api.bulletinBoard.client.models.announcements.CreateAnnouncementDto>()
        call.principal<CustomUserPrincipal>()?.also { principal ->
            val response = announcementsApi.createAnnouncement(dto, principal.id)
            respond(call, response)
        }
    }

    /**
     * Получить подробности о выбранном объявлении
     * @OpenAPITag bulletin board api
     */
    get("get-details/{id}") {
        val announcementId = call.parameters["id"] ?: throw IllegalArgumentException("announcement id is required")
        call.principal<CustomUserPrincipal>()?.also { principal ->
            val response = announcementsApi.getAnnouncementDetails(announcementId, principal.id)
            respond(call, response)
        }
    }

    /**
     * Получить данные для редактирования объявления
     * @OpenAPITag bulletin board api
     */
    get("get-update-content/{id}") {
        val announcementId = call.parameters["id"] ?: throw IllegalArgumentException("announcement id is required")
        call.principal<CustomUserPrincipal>()?.also { principal ->
            val response = announcementsApi.getAnnouncementUpdateContent(announcementId, principal.id)
            respond(call, response)
        }
    }

    /**
     * Редактировать объявление
     * @OpenAPITag bulletin board api
     */
    put("update") {
        val dto = call.receive<api.bulletinBoard.client.models.announcements.UpdateAnnouncementDto>()
        call.principal<CustomUserPrincipal>()?.also { principal ->
            val response = announcementsApi.updateAnnouncement(dto, principal.id)
            respond(call, response)
        }
    }

    /**
     * Удалить объявление
     * @OpenAPITag bulletin board api
     */
    delete("delete") {
        val announcementId = call.receive<String>()
        call.principal<CustomUserPrincipal>()?.also { principal ->
            val response = announcementsApi.deleteAnnouncement(announcementId, principal.id)
            respond(call, response)
        }
    }

    /**
     * Получить список опубликованных объявлений
     * @OpenAPITag bulletin board api
     */
    get("published/get-list") {
        call.principal<CustomUserPrincipal>()?.also { principal ->
            val response = announcementsApi.getPostedAnnouncementList(principal.id)
            respond(call, response)
        }
    }

    /**
     * Скрыть опубликованное объявление
     * @OpenAPITag bulletin board api
     */
    post("published/hide") {
        val announcementId = call.receive<String>()
        call.principal<CustomUserPrincipal>()?.also { principal ->
            val response = announcementsApi.hidePostedAnnouncement(announcementId, principal.id)
            respond(call, response)
        }
    }

    /**
     * Получить список скрытых объявлений
     * @OpenAPITag bulletin board api
     */
    get("hidden/get-list") {
        call.principal<CustomUserPrincipal>()?.also { principal ->
            val response = announcementsApi.getHiddenAnnouncementList(principal.id)
            respond(call, response)
        }
    }

    /**
     * Восстановить скрытое объявление
     * @OpenAPITag bulletin board api
     */
    post("hidden/restore") {
        val announcementId = call.receive<String>()
        call.principal<CustomUserPrincipal>()?.also { principal ->
            val response = announcementsApi.restoreHiddenAnnouncement(announcementId, principal.id)
            respond(call, response)
        }
    }

    /**
     * Получить список объявлений, ожидающих отложенную публикацию
     * @OpenAPITag bulletin board api
     */
    get("delayed-publishing/get-list") {
        call.principal<CustomUserPrincipal>()?.also { principal ->
            val response = announcementsApi.getDelayedPublishingAnnouncementList(principal.id)
            respond(call, response)
        }
    }

    /**
     * Сразу опубликовать отложенное объявление
     * @OpenAPITag bulletin board api
     */
    post("delayed-publishing/publish-immediately") {
        val announcementId = call.receive<String>()
        call.principal<CustomUserPrincipal>()?.also { principal ->
            val response = announcementsApi.publishImmediatelyDelayedPublishingAnnouncement(announcementId, principal.id)
            respond(call, response)
        }
    }

    /**
     * Получить список объявлений, ожидающих отложенное сокрытие
     * @OpenAPITag bulletin board api
     */
    get("delayed-hidden/get-list") {
        call.principal<CustomUserPrincipal>()?.also { principal ->
            val response = announcementsApi.getDelayedHiddenAnnouncementList(principal.id)
            respond(call, response)
        }
    }
}