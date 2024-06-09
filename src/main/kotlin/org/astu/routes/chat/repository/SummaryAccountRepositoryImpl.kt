package org.astu.routes.chat.repository

import api.account.client.apis.AccountApi
import api.account.client.models.SummaryAccountDTO
import io.ktor.client.*

class SummaryAccountRepositoryImpl(private val host: String, private val client: HttpClient) : SummaryAccountRepository,
    Clearable {
    private var accounts = mapOf<String, SummaryAccountDTO>()

    override suspend fun getSummaryAccount(id: String): SummaryAccountDTO {
        if (accounts.containsKey(id))
            return accounts[id]!!
        accounts = AccountApi(client, host).getAccounts().associateBy { it.id }
        if (accounts.containsKey(id))
            return accounts[id]!!
        throw IllegalArgumentException("Account with id $id not found")
    }

    override fun clear() {
        accounts = mapOf()
    }
}