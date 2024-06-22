package api.account.client.models

import kotlinx.serialization.Serializable

@Serializable
class AddAccountDTO(
    val firstName: String,
    val secondName: String,
    val patronymic: String? = null,
    val departmentId: String? = null,
    val studentGroupId: String? = null,
    val isAdmin: Boolean
)

@Serializable
class AddStudentInfoDTO(
    val studentGroupId: String
)

@Serializable
class AddTeacherInfoDTO(val role: String, val title: String)

@Serializable
class AddEmployeeInfoDTO(
    val departmentId: String,
    val role: String
)