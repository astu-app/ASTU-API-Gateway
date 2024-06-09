package org.astu.plugins

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.util.logging.*

internal val LOGGER = KtorSimpleLogger("org.astu.plugins.CustomAuthPlugin")
internal val CustomAuthKey: Any = "CustomAuth"


class CustomAuthenticationProvider internal constructor(config: Config) : AuthenticationProvider(config) {

    private val onAuth: (suspend (String) -> String)? = config.onAuth

    /**
     * Производит авторизацию на основе функции, переданной в [onAuth]
     */
    override suspend fun onAuthenticate(context: AuthenticationContext) {
        val header = context.call.request.headers[HttpHeaders.Authorization]
        if (header == null) {
            LOGGER.error("Failed to authenticate")
            context.call.respondText("Unauthorized", status = HttpStatusCode.Unauthorized)
            return
        }
        kotlin.runCatching {
            onAuth?.let {
                it(header)
            }!!
        }.onFailure {
            LOGGER.error("Failed to authenticate", it)
            context.call.respondText("Unauthorized", status = HttpStatusCode.Unauthorized)
            return
        }.onSuccess {
            context.principal(CustomUserPrincipal(it))
        }
    }

    class Config internal constructor(name: String?) : AuthenticationProvider.Config(name) {
        /**
         * Отправляет запрос на сервер для авторизации, ожидая получения id пользователя
         */
        internal var onAuth: (suspend (String) -> String)? = { "" }

        fun serverCall(onAuth: suspend (String) -> String) {
            this.onAuth = onAuth
        }

        internal fun build() = CustomAuthenticationProvider(this)
    }


}

/**
 * Функция определения правила авторизации
 */
fun AuthenticationConfig.custom(
    name: String? = null,
    configure: CustomAuthenticationProvider.Config.() -> Unit
) {
    val provider = CustomAuthenticationProvider.Config(name).apply(configure).build()
    register(provider)
}

class CustomUserPrincipal(val id: String) : Principal