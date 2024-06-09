package org.astu.routes.single_window

import io.github.smiley4.ktorswaggerui.dsl.route
import io.ktor.client.*
import io.ktor.server.routing.*
import org.astu.routes.single_window.routes.request
import org.astu.routes.single_window.routes.requirementTypes
import org.astu.routes.single_window.routes.templates
import org.koin.ktor.ext.inject

fun Route.requestServiceDefinition(){
    val host = environment?.config?.property("ktor.request.host")?.getString() ?: throw Exception("Host not set")
    val client by inject<HttpClient>()

    route("/request-service", {
        tags = listOf("request-service")
    }){
        templates(host, client)
        requirementTypes(host, client)
        request(host, client)
    }
}