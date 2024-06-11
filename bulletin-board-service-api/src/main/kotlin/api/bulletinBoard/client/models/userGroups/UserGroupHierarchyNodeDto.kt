package api.bulletinBoard.client.models.userGroups

import kotlinx.serialization.Serializable

/**
 * Объект описывает положение группы пользователей в иерархии групп пользователей
 */
@Serializable
data class UserGroupHierarchyNodeDto(
    val id: String,
    val children: MutableList<UserGroupHierarchyNodeDto>
)