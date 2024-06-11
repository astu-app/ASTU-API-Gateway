package org.astu.routes.bullletinBoard

import api.bulletinBoard.client.infrastructure.HttpResponseContent
import io.ktor.server.application.*
import io.ktor.server.response.*

suspend fun respond(call: ApplicationCall, content: HttpResponseContent): Unit {
    if (content.content != null) {
        call.respond(content.status, content.content!!)
    } else {
        call.respond(content.status)
    }
}

