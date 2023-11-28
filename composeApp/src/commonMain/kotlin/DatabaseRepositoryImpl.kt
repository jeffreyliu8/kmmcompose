import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.db.SqlDriver
import com.example.Database
import com.example.database.HockeyPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class DatabaseRepositoryImpl(
    driver: SqlDriver
) : DatabaseRepository {

    private val database = Database(driver)
    private val playerQueries = database.appDatabaseQueries
    override fun getPlayers(): Flow<List<HockeyPlayer>> {
        return playerQueries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.IO)
    }

    override suspend fun doDatabaseThings() = withContext(Dispatchers.Default) {
//        playerQueries.removeAll()
//
//
//        playerQueries.insert(player_number = 10, full_name = "Corey Perry")
//        println(playerQueries.selectAll().executeAsList())
        // [HockeyPlayer(15, "Ryan Getzlaf"), HockeyPlayer(10, "Corey Perry")]

//        val player = HockeyPlayer(10, "Ronald McDonald")
//        playerQueries.insertFullPlayerObject(player)
    }

    override suspend fun removeAll() = withContext(Dispatchers.Default) {
        playerQueries.removeAll()
    }

    override suspend fun addPlayer(player: HockeyPlayer) = withContext(Dispatchers.Default) {
        playerQueries.insert(
            id = null,
            player_number = player.player_number,
            full_name = player.full_name
        )
    }
}