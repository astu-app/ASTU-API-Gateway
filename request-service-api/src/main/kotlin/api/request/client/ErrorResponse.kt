package api.request.client

import kotlinx.serialization.Serializable

/**
 * Ответ с ошибкой
 */
@Serializable
data class ErrorResponse(
    /** Код состояния HTTP  */
    val status: Int? = null,

    /** Сообщение (по умолчанию пустое)  */
    val message: String? = null,
)