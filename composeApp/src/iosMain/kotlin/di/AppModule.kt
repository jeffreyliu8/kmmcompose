package di

import app.cash.sqldelight.db.SqlDriver
import cache.DatabaseDriverFactory



actual class AppModule {
    actual val sqlDriver: SqlDriver by lazy {
        DatabaseDriverFactory().createDriver()
    }
}