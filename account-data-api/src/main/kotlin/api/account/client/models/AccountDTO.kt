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
    val isAdmin: Boolean = false,
    val isTeacher: Boolean,
    val departmentId: String? = null,
    val studentGroupId: String? = null
)

