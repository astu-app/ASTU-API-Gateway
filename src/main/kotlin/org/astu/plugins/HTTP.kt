package org.astu.plugins

import com.ucasoft.ktor.simpleCache.SimpleCache
import com.ucasoft.ktor.simpleMemoryCache.memoryCache
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*
import kotlin.time.Duration.Companion.seconds

fun Application.configureHTTP() {
//    routing {
//        swaggerUI(path = "openapi")
//    }

    install(SimpleCache) {
        memoryCache {
            this.invalidateAt = 120.seconds
        }
    }

    install(CORS){
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Patch)
        allowMethod(HttpMethod.Delete)
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
        allowCredentials = true
        anyHost()
    }
}
