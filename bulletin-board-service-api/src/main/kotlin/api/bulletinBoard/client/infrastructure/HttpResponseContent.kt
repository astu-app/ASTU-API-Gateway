package api.bulletinBoard.client.infrastructure

import io.ktor.http.*
import kotlinx.serialization.Contextual

data class HttpResponseContent(@Contextual val content: Any?, val status: HttpStatusCode)
