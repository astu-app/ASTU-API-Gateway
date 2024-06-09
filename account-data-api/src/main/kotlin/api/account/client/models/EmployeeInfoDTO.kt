package api.account.client.models

import kotlinx.serialization.Serializable

@Serializable
class EmployeeInfoDTO(
    val role: String,
    val departmentId: String
)