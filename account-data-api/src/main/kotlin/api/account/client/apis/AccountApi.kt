package api.account.client.apis

import api.account.client.models.AccountDTO
import api.account.client.models.AddAccountDTO
import api.account.client.models.SummaryAccountDTO
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import me.xdrop.fuzzywuzzy.FuzzySearch

class AccountApi(private val client: HttpClient, private val basePath: String = "/") {
    suspend fun getAccount(userId: String): AccountDTO {
        val response = client.get("${basePath}api/account/$userId")

        return when (response.status) {
            HttpStatusCode.OK -> response.body<AccountDTO>()
            else -> throw Exception("Error getting account")
        }
    }

    suspend fun getAccounts(): List<SummaryAccountDTO> {
        val response = client.get("${basePath}api/account")

        return when (response.status) {
            HttpStatusCode.OK -> response.body<List<SummaryAccountDTO>>()
            else -> throw Exception("Error getting accounts")
        }
    }

    suspend fun addAccount(addAccountDTO: AddAccountDTO): String {
        val response = client.post("${basePath}api/account") {
            contentType(ContentType.Application.Json)
            setBody(addAccountDTO)
        }

        return when (response.status) {
            HttpStatusCode.OK -> response.body<String>().removeSurrounding("\"")
            else -> throw Exception("Error getting accounts")
        }
    }

    suspend fun search(text: String): List<SummaryAccountDTO> {
        return getAccounts().map {
            val ration = FuzzySearch.tokenSortRatio("${it.secondName} ${it.firstName} ${it.patronymic ?: ""}", text)
            Pair(it, ration)
        }.asSequence()
            .sortedByDescending { it.second }
            .take(10)
            .map { it.first }
            .toList()
    }
}

