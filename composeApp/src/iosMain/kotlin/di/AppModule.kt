package di

import repository.DatabaseRepository
import repository.DatabaseRepositoryImpl
import MainRepository
import MainRepositoryImpl
import app.cash.sqldelight.db.SqlDriver
import cache.DatabaseDriverFactory
import io.ktor.client.HttpClient
import network.BackendApi
import repository.WebRepository
import repository.WebRepositoryImpl


actual class AppModule {
    actual val sqlDriver: SqlDriver by lazy {
        DatabaseDriverFactory().createDriver()
    }
    actual val webClient: HttpClient by lazy {
        BackendApi().getClient()
    }
    actual val databaseRepository: DatabaseRepository
        get() = DatabaseRepositoryImpl(sqlDriver)
    actual val mainRepository: MainRepository
        get() = MainRepositoryImpl()
    actual val webRepository: WebRepository
        get() = WebRepositoryImpl(client = webClient)
}