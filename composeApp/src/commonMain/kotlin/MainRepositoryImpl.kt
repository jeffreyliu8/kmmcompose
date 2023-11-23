import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainRepositoryImpl : MainRepository {
    override fun getSomeFlow(): Flow<Int> {
        var startValue = 0
        return flow {
            while (true) {
                delay(1000)
                startValue++
                emit(startValue)
            }
        }
    }
}