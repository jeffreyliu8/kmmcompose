import app.cash.sqldelight.db.SqlDriver
import com.example.Database
import com.example.database.AppDatabaseQueries
import kotlinx.coroutines.flow.Flow

class DatabaseRepositoryImpl: DatabaseRepository {
    override fun getSomeDbData(): Flow<Int> {
        TODO("Not yet implemented")
    }

    override fun doDatabaseThings(driver: SqlDriver) {
        val database = Database(driver)
        val playerQueries: AppDatabaseQueries = database.appDatabaseQueries

        playerQueries.removeAll()

        println(playerQueries.selectAll().executeAsList())
        // [HockeyPlayer(15, "Ryan Getzlaf")]

        playerQueries.insert(player_number = 10, full_name = "Corey Perry")
        println(playerQueries.selectAll().executeAsList())
        // [HockeyPlayer(15, "Ryan Getzlaf"), HockeyPlayer(10, "Corey Perry")]

//        val player = HockeyPlayer(10, "Ronald McDonald")
//        playerQueries.insertFullPlayerObject(player)
    }
}