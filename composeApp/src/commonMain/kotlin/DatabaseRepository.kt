import com.example.database.HockeyPlayer
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    fun getPlayers(): Flow<List<HockeyPlayer>>

    suspend fun doDatabaseThings()

    suspend fun removeAll()
    suspend fun addPlayer(player: HockeyPlayer)
}