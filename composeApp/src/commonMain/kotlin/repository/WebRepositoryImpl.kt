package repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import kotlinx.coroutines.CancellationException
import model.QueryResult

class WebRepositoryImpl(
    private val client: HttpClient
) : WebRepository {
    override suspend fun searchRepos(query: String, sort: String?, order: String?): QueryResult? {
        return try {
            client.get {
                url("https://api.github.com/search/repositories")
                parameter("q", query)
                parameter("sort", sort)
                parameter("order", order)
            }.body<QueryResult>()
        } catch (e: RedirectResponseException) {
            // 3xx - response
            println("error: ${e.response.status.description}")
            null
        } catch (e: ClientRequestException) {
            // 4xx - response
            println("error: ${e.response.status.description}")
            null
        } catch (e: ServerResponseException) {
            // 5xx - response
            println("error: ${e.response.status.description}")
            null
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) { // probably UnknownHostException
            println("error: ${e.message}")
            println(e.stackTraceToString())
            null
        }
    }
}