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
package api.universal_request.client.apis

import api.universal_request.client.models.FileModel
import api.universal_request.client.models.TemplateDTO
import api.universal_request.client.models.TemplateFieldDTO
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.content.*
import java.util.*

class TemplateControllerApi(val client: HttpClient, private val basePath: String = "http://localhost:8085") {

    /**
     *
     *
     * @param body
     * @return java.util.UUID
     */
    suspend fun addTemplate(data: List<PartData>) {
        val response = client.submitFormWithBinaryData(url ="${basePath}api/templates/upload", formData = data)

        return when (response.status) {
            HttpStatusCode.OK -> response.body<Unit>()
            else -> throw Exception("Request failed")
        }
    }

    /**
     *
     *
     * @param accountId
     * @return kotlin.Array<TemplateDTO>
     */
    suspend fun getTemplates(): List<TemplateDTO> {
        val response = client.get("${basePath}api/templates")

        return when (response.status) {
            HttpStatusCode.OK -> response.body<List<TemplateDTO>>()
            else -> throw Exception("Request failed")
        }
    }

    /**
     *
     *
     * @param templateId
     * @return kotlin.Array<TemplateDTO>
     */
    suspend fun fillTemplate(templateId: UUID, fields: List<TemplateFieldDTO>): FileModel {
        val response = client.post("${basePath}api/templates/$templateId") {
            contentType(ContentType.Application.Json)
            setBody(fields)
        }

        val array = when (response.status) {
            HttpStatusCode.OK -> response.body<ByteArray>()
            else -> throw Exception("Request failed")
        }

        return FileModel(array, response.contentType())
    }
}
