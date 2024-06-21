package api.bulletinBoard.client.models.surveys.details

import kotlinx.serialization.Serializable

/**
 * @param id Идентификатор варианта ответа
 * @param serial Порядковый номер варианта ответа
 * @param content Текстовое содержимое варианта ответа
 * @param voters Список проголосовавших за вариант ответа пользователей. Пустой, если вариант ответа относится к анонимному опросу
 * @param votersAmount Количество пользователей, проголосовавших за вариант ответа
 */
@Serializable
data class QuestionAnswerDetailsDto(
    val id: String,
    val serial: Int,
    val content: String,
    val voters: List<api.bulletinBoard.client.models.users.UserSummaryDto>,
    val votersAmount: Int,
)