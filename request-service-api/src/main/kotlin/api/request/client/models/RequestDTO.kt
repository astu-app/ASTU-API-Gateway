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
package api.request.client.models

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

/**
 *
 * @param id
 * @param name
 * @param description
 * @param userId
 * @param type
 * @param status
 * @param message
 * @param createdAt
 * @param fields
 */
@Serializable
data class RequestDTO(

    val id: String,
    val name: String,
    val description: String,
    val userId: String,
    val type: Type,
    val status: Status,
    val message: String? = null,
    val createdAt: Instant,
    val fields: List<RequirementFieldDTO>
) {
    /**
     *
     * Values: FACETOFACE,EMAIL
     */
    enum class Type(val value: String) {
        FACETOFACE("FaceToFace"),
        EMAIL("Email");
    }

    /**
     *
     * Values: SUCCESS,INPROGRESS,DENIED,REMOVED
     */
    enum class Status(val value: String) {
        SUCCESS("Success"),
        INPROGRESS("InProgress"),
        DENIED("Denied"),
        REMOVED("Removed");
    }
}