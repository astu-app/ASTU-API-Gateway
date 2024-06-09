package org.astu.plugins

import com.ucasoft.ktor.simpleCache.SimpleCache
import com.ucasoft.ktor.simpleMemoryCache.memoryCache
import io.ktor.server.application.*
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
}
