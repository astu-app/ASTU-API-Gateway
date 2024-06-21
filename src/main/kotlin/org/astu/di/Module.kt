package org.astu.di

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import kotlinx.serialization.json.Json
import org.astu.plugins.SslSettings
import org.astu.routes.chat.repository.SummaryAccountRepository
import org.astu.routes.chat.repository.SummaryAccountRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

@Suppress("unused")
fun Application.module(): Module {
    val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                this.encodeDefaults = true
            })
        }
        install(HttpTimeout){
            requestTimeoutMillis = 40000
            socketTimeoutMillis = 40000
            connectTimeoutMillis = 40000
        }
        engine {
            https {
                trustManager = SslSettings.trustManager()
            }
        }
    }

    val accountHost = environment.config.property("ktor.account.host").getString()

    return module {
        single { httpClient }
        single<SummaryAccountRepository> { SummaryAccountRepositoryImpl(accountHost, httpClient) }
    }
}