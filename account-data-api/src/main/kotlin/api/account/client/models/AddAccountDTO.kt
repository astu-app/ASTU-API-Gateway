package api.account.client.models

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
class AddAccountDTO(
    val firstName: String,
    val secondName: String,
    val patronymic: String? = null,
    val departmentId: String? = null,
    val studentGroupId: String? = null
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