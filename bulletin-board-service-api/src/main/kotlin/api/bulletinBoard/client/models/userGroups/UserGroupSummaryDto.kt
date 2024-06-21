package api.bulletinBoard.client.models.userGroups

import kotlinx.serialization.Serializable

/**
 * Краткая информация о группе пользователей
 */
@Serializable
data class UserGroupSummaryDto(val id: String, val name: String, val adminName: String?)