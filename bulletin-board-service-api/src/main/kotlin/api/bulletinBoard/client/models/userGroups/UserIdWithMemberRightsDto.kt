package api.bulletinBoard.client.models.userGroups

import kotlinx.serialization.Serializable

/**
 * Идентификатор пользователя с правами в группе пользователей
 */
@Serializable
data class UserIdWithMemberRightsDto(
    val userId: String,
    val usergroupId: String?,
    val rights: MemberRightsDto
)