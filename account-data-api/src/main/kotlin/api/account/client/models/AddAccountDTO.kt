package api.account.client.models

import kotlinx.serialization.Serializable

@Serializable
class AddAccountDTO(
    val firstName: String,
    val secondName: String,
    val patronymic: String? = null,
    val addStudentInfoDTO: AddStudentInfoDTO? = null,
    val addEmployeeInfoDTO: AddEmployeeInfoDTO? = null,
    val addTeacherInfoDTO: AddTeacherInfoDTO? = null,
    val isAdmin: Boolean = false
)

@Serializable
class AddStudentInfoDTO(
    val studentGroup: String
)

@Serializable
class AddTeacherInfoDTO(val role: String, val title: String)

@Serializable
class AddEmployeeInfoDTO(
    val department: String,
    val role: String
)