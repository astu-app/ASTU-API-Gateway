package org.astu.routes.bullletinBoard.routes

import api.bulletinBoard.client.apis.SurveysApi
import io.ktor.client.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import api.bulletinBoard.client.models.surveys.creation.CreateSurveyDto
import api.bulletinBoard.client.models.surveys.creation.VoteInSurveyDto
import io.github.smiley4.ktorswaggerui.dsl.post
import org.astu.plugins.CustomUserPrincipal
import org.astu.routes.bullletinBoard.respond

fun Route.surveys(bulletinBoardHost: String, client: HttpClient) = route("/surveys") {
    val surveysApi = api.bulletinBoard.client.apis.SurveysApi(client, bulletinBoardHost)

    /**
     * Создать опрос
     * @OpenAPITag bulletin board api
     */
    post("create", {
        summary = "Создать опрос"
        tags = listOf("bulletin-board-service")
    }) {
        val dto = call.receive<api.bulletinBoard.client.models.surveys.creation.CreateSurveyDto>()
        val rootUserGroupId = call.request.headers["X-Root-UserGroup-Id"] ?: throw IllegalArgumentException("X-Root-UserGroup-Id header is missing")
        call.principal<CustomUserPrincipal>()?.also { principal ->
            val response = surveysApi.createSurvey(dto, principal.id, rootUserGroupId)
            respond(call, response)
        }
    }

    /**
     * Закрыть опрос
     * @OpenAPITag bulletin board api
     */
    post("close-survey", {
        summary = "Закрыть опрос"
        tags = listOf("bulletin-board-service")
    }) {
        val surveyId = call.receive<String>()
        call.principal<CustomUserPrincipal>()?.also { principal ->
            val response = surveysApi.closeSurvey(surveyId, principal.id)
            respond(call, response)
        }
    }

    /**
     * Проголосовать в опросе
     * @OpenAPITag bulletin board api
     */
    post("vote", {
        summary = "Проголосовать в опросе"
        tags = listOf("bulletin-board-service")
    }) {
        val dto = call.receive<api.bulletinBoard.client.models.surveys.creation.VoteInSurveyDto>()
        call.principal<CustomUserPrincipal>()?.also { principal ->
            val response = surveysApi.voteInSurvey(dto, principal.id)
            respond(call, response)
        }
    }
}