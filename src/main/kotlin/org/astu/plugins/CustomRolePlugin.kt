package org.astu.plugins

import api.account.client.apis.AccountApi
import api.account.client.models.AccountDTO
import io.ktor.client.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.*
import org.koin.ktor.ext.inject

class CustomRoleConfig {
    lateinit var expression: (AccountDTO) -> Boolean
}

val accountKey = AttributeKey<AccountDTO>("accountDTOKey")


val CustomRolePlugin = createRouteScopedPlugin(
    name = "CustomRolePlugin",
    createConfiguration = ::CustomRoleConfig
) {
    pluginConfig.apply {
        on(AuthenticationChecked) { call ->
            val accountHost = environment?.config?.property("ktor.account.host")?.getString()
                ?: throw Exception("Host not set")
            val client by call.inject<HttpClient>()
            call.principal<CustomUserPrincipal>()?.let {
                println("accountId: ${it.id}")
                kotlin.runCatching {
                    AccountApi(client, accountHost).getAccount(it.id)
                }.onFailure {
                    call.respondText("Не удалось получить информацию об аккаунте", status = HttpStatusCode.Forbidden)
                    return@on
                }.onSuccess { accountDTO ->
                    if (!expression(accountDTO))
                        call.respond(HttpStatusCode.Forbidden)
                    call.attributes.put(accountKey, accountDTO)
                }
            }
        }
    }
}

class RoleSelector : RouteSelector() {
    override fun evaluate(context: RoutingResolveContext, segmentIndex: Int) = RouteSelectorEvaluation.Transparent
}

fun Route.checkRole(predicate: (AccountDTO) -> Boolean, body: Route.() -> Unit): Route {
    val child = createChild(RoleSelector())
    child.install(CustomRolePlugin) {
        expression = predicate
    }
    child.body()
    return child
}