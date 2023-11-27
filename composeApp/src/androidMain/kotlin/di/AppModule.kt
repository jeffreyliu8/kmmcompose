package di

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import cache.DatabaseDriverFactory

actual class AppModule(
    private val context: Context
) {
    actual val sqlDriver: SqlDriver by lazy {
        DatabaseDriverFactory(context).createDriver()
    }
}