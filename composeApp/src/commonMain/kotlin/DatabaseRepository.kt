import app.cash.sqldelight.db.SqlDriver
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    fun getSomeDbData(): Flow<Int>

    fun doDatabaseThings(driver: SqlDriver)
}