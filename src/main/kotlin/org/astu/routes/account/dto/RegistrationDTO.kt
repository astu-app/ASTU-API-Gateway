package org.astu.routes.account.dto

import api.account.client.models.AddAccountDTO
import api.auth.client.models.JWTLoginDTO
import kotlinx.serialization.Serializable

@Serializable
class RegistrationDTO (
    val account: AddAccountDTO,
    val auth: JWTLoginDTO
)