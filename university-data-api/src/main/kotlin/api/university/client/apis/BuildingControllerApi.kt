/**
 * University Data Service
 * Сервис по работе с университетскими данными
 *
 * OpenAPI spec version: 1.0
 *
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
package api.university.client.apis

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import api.university.client.models.AddBuildingRequest
import api.university.client.models.BuildingDTO

class BuildingControllerApi(
    private val httpClient: HttpClient,
    private val basePath: String = "http://localhost:8080"
) {

    /**
     *
     *
     * @param body
     * @return BuildingDTO
     */
    suspend fun createBuilding(body: AddBuildingRequest): BuildingDTO {
        val response = httpClient.post("$basePath/api/buildings") {
            contentType(ContentType.Application.Json)
            setBody(body)
        }
        return when (response.status) {
            HttpStatusCode.OK -> response.body<BuildingDTO>()
            else -> throw IllegalStateException("${response.status}")
        }
    }

    /**
     *
     *
     * @return kotlin.Array<BuildingDTO>
     */
    suspend fun getBuildings(): List<BuildingDTO> {
        val response = httpClient.get("$basePath/api/buildings")
        return when (response.status) {
            HttpStatusCode.OK -> response.body<List<BuildingDTO>>()
            else -> throw IllegalStateException("${response.status}")
        }
    }
}
