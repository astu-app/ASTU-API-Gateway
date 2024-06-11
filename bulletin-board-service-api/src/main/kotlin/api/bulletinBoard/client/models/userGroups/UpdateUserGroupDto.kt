package api.bulletinBoard.client.models.userGroups

import kotlinx.serialization.Serializable

/**
 * DTO для обновления группы пользователей
 * @param id Идентификатор группы пользователей
 * @param name Новое название группы пользователей
 * @param adminChanged Изменен ли администратор группы. True, если изменен
 * @param adminId Новый идентификатор администратора. Null отправляется для удаления администратора
 * @param members Изменения в участниках
 */
@Serializable
data class UpdateUserGroupDto(
    val id: String,
    val name: String?,
    val adminChanged: Boolean,
    val adminId: String?,
    val members: UpdateMemberListDto,
)