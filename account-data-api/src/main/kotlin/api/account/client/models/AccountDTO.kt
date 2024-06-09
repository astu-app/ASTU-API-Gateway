package api.account.client.models

import kotlinx.serialization.Serializable

@Serializable
class AccountDTO(
    val id: String,
    val firstName: String,
    val secondName: String,
    val patronymic: String?,
    val isEmployee: Boolean,
    val isStudent: Boolean,
    val isAdmin: Boolean,
    val isTeacher: Boolean,
    val employeeInfo: EmployeeInfoDTO,
    val studentInfo: StudentInfoDTO,
    val teacherInfo: TeacherInfoDTO
)

