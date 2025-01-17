package api.bulletinBoard.client.models.users

import kotlinx.serialization.Serializable

/**
 * @param id Идентификатор
 * @param firstName Имя
 * @param secondName Фамилия
 * @param patronymic Отчество
 * @param isChecked Отмечен ли пользователь
 */
@Serializable
data class CheckableUserSummaryDto(
    val id: String,
    val firstName: String,
    val secondName: String,
    val patronymic: String?,
    val isChecked: Boolean
)