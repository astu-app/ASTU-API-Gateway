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
 * @param name 
 * @param description 
 * @param category 
 * @param departmentId 
 * @param requirements 
 * @param groups 
 */
@Serializable
data class AddTemplateDTO (

    val name: String,
    val description: String,
    val category: String,
    val departmentId: String,
    val requirements: List<AddRequirementDTO>,
    val groups: List<Groups>
) {
    /**
    * 
    * Values: STUDENT,EMPLOYEE,GRADUATE
    */
    @Serializable
    enum class Groups(val value: String){
        Student("Student"),
        Employee("Employee"),
        GRADUATE("Graduate");
    }
}