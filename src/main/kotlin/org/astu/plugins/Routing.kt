package org.astu.plugins

import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.github.smiley4.ktorswaggerui.data.AuthScheme
import io.github.smiley4.ktorswaggerui.data.AuthType
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.astu.routes.account.accountServiceDefinition
import org.astu.routes.auth.authServiceDefinition
import org.astu.routes.bullletinBoard.bulletinBoardServiceDefinition
import org.astu.routes.chat.chatServiceDefinition
import org.astu.routes.ping
import org.astu.routes.single_window.requestServiceDefinition
import org.astu.routes.uni_request.universalRequestServiceDefinition
import org.astu.routes.university.universityServiceDefinition

fun Application.configureRouting() {
    install(SwaggerUI) {
        this.securityScheme("Bearer") {
            this.type = AuthType.API_KEY
            this.scheme = AuthScheme.BEARER
        }

        swagger {
            swaggerUrl = "swagger"
            forwardRoot = true
        }
        info {
            title = "API Gateway"
            version = "latest"
            description = "Апи для работы с сервисами АГТУ"
        }
        ignoredRouteSelectors = setOf(RoleSelector::class)
    }
    routing {
        get("/") {
            call.respondText("o.O")
        }
        route("/api") {
            authServiceDefinition()
            // TODO добавить авторизацию для стороннего администрирования
            accountServiceDefinition()

            universityServiceDefinition()
            universalRequestServiceDefinition()

            authenticate(("custom")) {
                chatServiceDefinition()
                requestServiceDefinition()
                bulletinBoardServiceDefinition()
            }
            ping()
        }
    }
}
