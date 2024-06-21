package api.bulletinBoard.client.models.userGroups

import kotlinx.serialization.Serializable

/**
 * @param id Идентификатор группы пользователей
 * @param name Название группы пользователей
 * @param admin Информация об администраторе группы
 * @param members Краткая информация об участниках группы пользователей
 * @param parents Родительские группы пользователей
 * @param children Дочерние группы пользователей
 */
@Serializable
data class UserGroupDetailsDto(
    val id: String,
    val name: String,
    val admin: api.bulletinBoard.client.models.users.UserSummaryDto?,
    val members: List<UserSummaryWithMemberRightsDto>,
    val parents: List<UserGroupSummaryDto>,
    val children: List<UserGroupSummaryDto>
)
