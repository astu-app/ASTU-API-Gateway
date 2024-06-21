package api.bulletinBoard.client.infrastructure

import kotlinx.serialization.Serializable

@Serializable
data class ErrorCode (
    val code: Int
)
