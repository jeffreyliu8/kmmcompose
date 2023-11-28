package com.example.database

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.SqlDriver
import kotlin.Any
import kotlin.Long
import kotlin.String

public class AppDatabaseQueries(
  driver: SqlDriver,
) : TransacterImpl(driver) {
  public fun <T : Any> selectAll(mapper: (
    id: Long,
    player_number: Long,
    full_name: String,
  ) -> T): Query<T> = Query(1_060_311_145, arrayOf("hockeyPlayer"), driver, "AppDatabase.sq",
      "selectAll", """
  |SELECT *
  |FROM hockeyPlayer
  """.trimMargin()) { cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getLong(1)!!,
      cursor.getString(2)!!
    )
  }

  public fun selectAll(): Query<HockeyPlayer> = selectAll { id, player_number, full_name ->
    HockeyPlayer(
      id,
      player_number,
      full_name
    )
  }

  public fun insert(
    id: Long?,
    player_number: Long,
    full_name: String,
  ) {
    driver.execute(-133_421_099, """
        |INSERT INTO hockeyPlayer
        |VALUES (?,?, ?)
        """.trimMargin(), 3) {
          bindLong(0, id)
          bindLong(1, player_number)
          bindString(2, full_name)
        }
    notifyQueries(-133_421_099) { emit ->
      emit("hockeyPlayer")
    }
  }

  public fun removeAll() {
    driver.execute(-236_306_463, """DELETE FROM hockeyPlayer""", 0)
    notifyQueries(-236_306_463) { emit ->
      emit("hockeyPlayer")
    }
  }
}
