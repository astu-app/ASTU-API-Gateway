package org.astu.routes.uni_request

import io.github.smiley4.ktorswaggerui.dsl.route
import io.ktor.client.*
import io.ktor.server.routing.*
import org.astu.routes.uni_request.routes.templates
import org.koin.ktor.ext.inject

fun Route.universalRequestServiceDefinition(){
    val host = environment?.config?.property("ktor.uni-request.host")?.getString() ?: throw Exception("Host not set")
    val client by inject<HttpClient>()

    route("/uni-request-service", {
        tags = listOf("uni-request-service")
    }){
        templates(host, client)
    }
}