package di

import repository.DatabaseRepository
import MainRepository
import app.cash.sqldelight.db.SqlDriver
import io.ktor.client.HttpClient
import repository.WebRepository

expect class AppModule {
    val sqlDriver: SqlDriver
    val webClient: HttpClient
    val databaseRepository: DatabaseRepository
    val mainRepository: MainRepository
    val webRepository: WebRepository
}