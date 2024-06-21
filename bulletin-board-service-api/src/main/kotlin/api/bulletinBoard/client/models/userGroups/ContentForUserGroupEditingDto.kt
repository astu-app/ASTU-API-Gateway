package api.bulletinBoard.client.models.userGroups

import kotlinx.serialization.Serializable

/**
 * Данные для редактирования группы пользователей
 * @param id Идентификатор группы пользователей
 * @param name Название группы пользователей
 * @param admin Администратор группы пользователей
 * @param members Краткая информация об участниках группы пользователей, включая их права
 * @param users Список пользователей, которые могут быть назначены администратором или добавлены в группу пользователей. В списке отсутствуют текущий администратор и участники группы пользователей
 */
@Serializable
data class ContentForUserGroupEditingDto(
    val id: String,
    val name: String,
    val admin: api.bulletinBoard.client.models.users.UserSummaryDto?,
    val members: List<UserSummaryWithMemberRightsDto>,
    val users: List<api.bulletinBoard.client.models.users.UserSummaryDto>,
)
