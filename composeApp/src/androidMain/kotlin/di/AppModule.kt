package di

import DatabaseRepository
import DatabaseRepositoryImpl
import MainRepository
import MainRepositoryImpl
import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import cache.DatabaseDriverFactory

actual class AppModule(
    private val context: Context
) {
    actual val sqlDriver: SqlDriver by lazy {
        DatabaseDriverFactory(context).createDriver()
    }
    actual val databaseRepository: DatabaseRepository
        get() = DatabaseRepositoryImpl(sqlDriver)
    actual val mainRepository: MainRepository
        get() = MainRepositoryImpl()
}