package org.astu

import io.ktor.server.application.*
import org.astu.plugins.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureDI()
    configureSecurity()
    configureHTTP()
    configureSerialization()
    configureRouting()
}
