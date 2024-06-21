package api.bulletinBoard.client.models.userGroups

import kotlinx.serialization.Serializable

/**
 * Идентификатор пользователя с правами в группе пользователей
 */
@Serializable
data class UserSummaryWithMemberRightsDto(
    val user: api.bulletinBoard.client.models.users.UserSummaryDto,
    val rights: MemberRightsDto
)