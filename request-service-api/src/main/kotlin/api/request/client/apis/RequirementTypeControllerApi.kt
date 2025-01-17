/**
 * OpenAPI definition
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: v0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
package api.request.client.apis

import api.request.client.RequestServiceException
import api.request.client.models.AddRequirementTypeDTO
import api.request.client.models.RequirementTypeDTO
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class RequirementTypeControllerApi(val client: HttpClient, private val basePath: String = "http://localhost:8085") {

    /**
     * 
     * 
     * @param dto  
     * @return RequirementTypeDTO
     */
    suspend fun add(dto: AddRequirementTypeDTO): RequirementTypeDTO {
        val response = client.post("${basePath}api/requirement-type") {
            contentType(ContentType.Application.Json)
            setBody(dto)
        }

        return when (response.status) {
            HttpStatusCode.OK -> response.body<RequirementTypeDTO>()
            else -> throw RequestServiceException("Не удалось добавить тип заявления")
        }
    }
    /**
     * 
     * 
     * @return kotlin.Array<RequirementTypeDTO>
     */
    suspend fun get(): List<RequirementTypeDTO> {
        val response = client.get("${basePath}api/requirement-type")

        return when (response.status) {
            HttpStatusCode.OK -> response.body<List<RequirementTypeDTO>>()
            else -> throw RequestServiceException("Не удалось получить список типов требований")
        }
    }
}
