package org.astu.routes.single_window.dto

import api.account.client.models.AccountDTO
import api.request.client.models.RequestDTO.Status
import api.request.client.models.RequestDTO.Type
import api.request.client.models.RequirementFieldDTO
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
class RequestDTO(
    val id: String,
    val name: String,
    val description: String,
    val userId: String,
    val userInfo: AccountDTO? = null,
    val type: Type,
    val status: Status,
    val message: String? = null,
    val createdAt: Instant,
    val fields: List<RequirementFieldDTO>
)