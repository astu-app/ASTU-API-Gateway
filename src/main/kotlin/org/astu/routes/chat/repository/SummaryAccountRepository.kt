package org.astu.routes.chat.repository

import api.account.client.models.SummaryAccountDTO

interface SummaryAccountRepository {
    suspend fun getSummaryAccount(id: String): SummaryAccountDTO
}