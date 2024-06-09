package org.astu.plugins

import io.ktor.server.application.*
import org.astu.di.module
import org.koin.ktor.plugin.Koin

fun Application.configureDI() {
    install(Koin) {
        modules(module())
    }
}