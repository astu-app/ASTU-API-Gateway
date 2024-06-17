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

import kotlinx.serialization.Serializable

/**
 * 
 * @param templateId 
 * @param type 
 * @param email 
 * @param fields 
 */
@Serializable
data class AddRequestDTO (

    val templateId: String,
    val type: Type,
    val email: String? = null,
    val fields: List<AddRequirementFieldDTO>
) {
    /**
    * 
    * Values: FACETOFACE,EMAIL
    */
    enum class Type(val value: String){
        FaceToFace("FaceToFace"),
        Email("Email");
    }
}