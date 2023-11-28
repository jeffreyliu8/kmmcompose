package network


import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class BackendApi {
    private val httpClient = HttpClient {
        install(Logging){
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
//                isLenient = false
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }

    fun getClient(): HttpClient {
        return httpClient
    }
}