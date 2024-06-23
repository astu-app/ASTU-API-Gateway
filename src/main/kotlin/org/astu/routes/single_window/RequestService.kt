package org.astu.routes.single_window

import io.github.smiley4.ktorswaggerui.dsl.route
import io.ktor.client.*
import io.ktor.server.routing.*
import org.astu.routes.single_window.routes.request
import org.astu.routes.single_window.routes.requirementTypes
import org.astu.routes.single_window.routes.templates
import org.koin.ktor.ext.inject

fun Route.requestServiceDefinition() {
    val host = environment?.config?.property("ktor.request.host")?.getString() ?: throw Exception("Host not set")
    val accountHost = environment?.config?.property("ktor.account.host")?.getString() ?: throw Exception("Host not set")
    val notifyHost = environment?.config?.property("ktor.notify.host")?.getString() ?: throw Exception("Host not set")
    val notifyToken = environment?.config?.property("ktor.notify.token")?.getString() ?: throw Exception("Host not set")

    val client by inject<HttpClient>()

    route("/request-service", {
        tags = listOf("request-service")
    }) {
        templates(host, accountHost, client)
        requirementTypes(host, client)
        request(host, accountHost, notifyHost, notifyToken, client)
    }
}