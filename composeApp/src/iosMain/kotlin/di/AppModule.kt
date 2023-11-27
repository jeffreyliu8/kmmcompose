package di

import DatabaseRepository
import DatabaseRepositoryImpl
import MainRepository
import MainRepositoryImpl
import app.cash.sqldelight.db.SqlDriver
import cache.DatabaseDriverFactory


actual class AppModule {
    actual val sqlDriver: SqlDriver by lazy {
        DatabaseDriverFactory().createDriver()
    }
    actual val databaseRepository: DatabaseRepository
        get() = DatabaseRepositoryImpl(sqlDriver)
    actual val mainRepository: MainRepository
        get() = MainRepositoryImpl()
}