package org.astu.routes.bullletinBoard

import io.ktor.client.*
import io.ktor.server.routing.*
import org.astu.routes.bullletinBoard.routes.announcements
import org.astu.routes.bullletinBoard.routes.surveys
import org.astu.routes.bullletinBoard.routes.userGroups
import org.koin.ktor.ext.inject

class BulletinBoardService {
}

fun Route.bulletinBoardServiceDefinition() {
    val bulletinBoardHost = environment?.config?.property("ktor.bulletinBoard.host")?.getString() ?: throw Exception("Bulletin board host not set")
    val client by inject<HttpClient>()

    route("/bulletin-board-service") {
        announcements(bulletinBoardHost, client)
        surveys(bulletinBoardHost, client)
        userGroups(bulletinBoardHost, client)
    }
}