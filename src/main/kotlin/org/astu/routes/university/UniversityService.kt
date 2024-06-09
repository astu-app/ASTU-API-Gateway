package org.astu.routes.university

import io.github.smiley4.ktorswaggerui.dsl.route
import io.ktor.client.*
import io.ktor.server.routing.*
import org.astu.routes.university.routes.departments
import org.astu.routes.university.routes.studentGroups
import org.koin.ktor.ext.inject

fun Route.universityServiceDefinition() {
    val client by inject<HttpClient>()
    val host = environment?.config?.property("ktor.university.host")?.getString()
        ?: throw Exception("Host not set")

    route("/university-service", {
        tags = listOf("university-service")
    }) {
        studentGroups(host, client)
        departments(host, client)
    }
}