package org.astu.plugins

import api.auth.client.apis.DefaultApi
import io.ktor.client.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import org.koin.ktor.ext.inject

fun Application.configureSecurity() {

    val host = environment.config.property("ktor.auth.host").getString()
    val client by inject<HttpClient>()

    install(Authentication) {
        custom("custom") {
            serverCall { header ->
                DefaultApi(client = client, basePath = host).getId(header)
            }
        }
    }
}