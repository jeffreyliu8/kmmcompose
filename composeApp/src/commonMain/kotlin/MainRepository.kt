import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun getSomeFlow(): Flow<Int>
}