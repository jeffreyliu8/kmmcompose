package di

import DatabaseRepository
import MainRepository
import app.cash.sqldelight.db.SqlDriver

expect class AppModule {
    val sqlDriver: SqlDriver
    val databaseRepository: DatabaseRepository
    val mainRepository: MainRepository
}