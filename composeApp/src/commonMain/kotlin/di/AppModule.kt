package di

import app.cash.sqldelight.db.SqlDriver

expect class AppModule {
    val sqlDriver: SqlDriver
}