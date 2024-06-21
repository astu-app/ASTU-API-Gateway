package api.bulletinBoard.client.models.userGroups

import kotlinx.serialization.Serializable

/**
 * Краткая информация о группе пользователей, включая ее участников
 */
@Serializable
data class UserGroupSummaryWithMembersDto(
    val summary: UserGroupSummaryDto,
    val members: List<api.bulletinBoard.client.models.users.CheckableUserSummaryDto>
)