import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    fun getSomeDbData(): Flow<Int>

    suspend fun doDatabaseThings()
}