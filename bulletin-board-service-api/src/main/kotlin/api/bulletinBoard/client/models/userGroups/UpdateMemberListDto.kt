package api.bulletinBoard.client.models.userGroups

import kotlinx.serialization.Serializable

/**
 * DTO для редактирования списка пользователей в группе пользователей
 * @param idsToRemove Идентификаторы удаляемых участников
 * @param newMembers Новые участники группы пользователей с правами
 */
@Serializable
data class UpdateMemberListDto(
    val idsToRemove: List<String>,
    val newMembers: List<UserIdWithMemberRightsDto>
)
