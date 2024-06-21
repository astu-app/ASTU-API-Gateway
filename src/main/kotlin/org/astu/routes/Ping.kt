package org.astu.routes

import io.github.smiley4.ktorswaggerui.dsl.get
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.ping() {
    val accountHost = environment?.config?.property("ktor.account.host")?.getString()
    val universityHost = environment?.config?.property("ktor.university.host")?.getString()
    val chatHost = environment?.config?.property("ktor.chat.host")?.getString()
    val authHost = environment?.config?.property("ktor.auth.host")?.getString()
    val requestHost = environment?.config?.property("ktor.request.host")?.getString()
    val uniRequestHost = environment?.config?.property("ktor.uni-request.host")?.getString()
    val bulletinBoardHost = environment?.config?.property("ktor.request.host")?.getString()
    val client by inject<HttpClient>()

    /**
     * Пинг сервисов
     * @OpenAPITag monitoring api
     */
    get("/ping", {
        summary = "Пинг сервисов"
        tags = listOf("monitoring api")
    }) {
        val results = mutableListOf<String>()

        runCatching {
            val response = client.get("${accountHost}ping")
            results.add("AccountService: [${response.status}] ${response.bodyAsText()}")
        }.onFailure {
            results.add("AccountService: [$it]  ${it.message}")
        }

//        runCatching {
//            val response = client.get("${chatHost}ping")
//            results.add("ChatService: [${response.status}]")
//        }.onFailure {
//            results.add("ChatService: [$it]  ${it.message}")
//        }

        runCatching {
            val response = client.get("${authHost}ping")
            results.add("AuthService: [${response.status}]")
        }.onFailure {
            results.add("AuthService: [$it]  ${it.message}")
        }

        runCatching {
            val response = client.get("${requestHost}ping")
            results.add("RequestService: [${response.status}]")
        }.onFailure {
            results.add("RequestService: [$it]  ${it.message}")
        }

        runCatching {
            val response = client.get("${uniRequestHost}ping")
            results.add("Universal Request Service: [${response.status}]")
        }.onFailure {
            results.add("Universal Request Service: [$it]  ${it.message}")
        }

//        runCatching {
//            val response = client.get("${universityHost}ping")
//            results.add("UniversityService: [${response.status}]")
//        }.onFailure {
//            results.add("UniversityService: [$it]  ${it.message}")
//        }

        runCatching {
            val response = client.get("${bulletinBoardHost}ping")
            results.add("BulletinBoardService: [${response.status}]")
        }.onFailure {
            results.add("BulletinBoardService: [$it]  ${it.message}")
        }

        call.respond(results)
    }
}