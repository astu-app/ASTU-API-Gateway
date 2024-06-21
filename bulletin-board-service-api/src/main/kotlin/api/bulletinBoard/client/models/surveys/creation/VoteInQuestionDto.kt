package api.bulletinBoard.client.models.surveys.creation

import kotlinx.serialization.Serializable

/**
 * DTO для голосования в вопросе опроса
 * @param questionId Идентификатор вопроса
 * @param answerIds Массив идентификаторов вариантов ответов
 */
@Serializable
data class VoteInQuestionDto(
    val questionId: String,
    val answerIds: List<String>
)