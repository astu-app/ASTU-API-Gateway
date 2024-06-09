package api.account.client.models

import kotlinx.serialization.Serializable

@Serializable
class TeacherInfoDTO(
    val role: String,
    val title: String
)