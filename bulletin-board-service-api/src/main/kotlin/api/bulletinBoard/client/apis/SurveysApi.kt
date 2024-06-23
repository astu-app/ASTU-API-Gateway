package api.bulletinBoard.client.apis

import api.bulletinBoard.client.infrastructure.ErrorCode
import api.bulletinBoard.client.infrastructure.HttpResponseContent
import api.bulletinBoard.client.models.surveys.creation.CreateSurveyDto
import api.bulletinBoard.client.models.surveys.creation.VoteInSurveyDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class SurveysApi(private val client: HttpClient, private val baseUrl: String) {
    /*
     * Создать опрос
     */
    suspend fun createSurvey(createSurveyDto: CreateSurveyDto, principalId: String, rootUserGroupId: String): HttpResponseContent {
        val response = client.post("${baseUrl}api/surveys/create") {
            headers {
                append("X-User-Id", principalId)
                append("X-Root-UserGroup-Id", rootUserGroupId)
                append("Content-Type", "application/json")
            }
            setBody(createSurveyDto)
        }
        return when (response.status) {
            HttpStatusCode.Created -> HttpResponseContent(response.body<String>(), response.status)
            HttpStatusCode.Unauthorized -> HttpResponseContent(null, response.status)
            HttpStatusCode.Forbidden -> HttpResponseContent(response.body<ErrorCode>(), response.status)
            HttpStatusCode.InternalServerError -> HttpResponseContent(null, response.status)
            else -> HttpResponseContent(null, response.status)
        }
    }

    /*
     * Закрыть опрос
     */
    suspend fun closeSurvey(surveyId: String, principalId: String): HttpResponseContent {
        val response = client.post("${baseUrl}api/surveys/close-survey") {
            headers {
                append("X-User-Id", principalId)
                append("Content-Type", "application/json")
            }
            setBody("$surveyId")
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

    /*
     * Проголосовать в опросе
     */
    suspend fun voteInSurvey(voteInSurveyDto: VoteInSurveyDto, principalId: String): HttpResponseContent {
        val response = client.post("${baseUrl}api/surveys/vote") {
            headers {
                append("X-User-Id", principalId)
                append("Content-Type", "application/json")
            }
            setBody(voteInSurveyDto)
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
}