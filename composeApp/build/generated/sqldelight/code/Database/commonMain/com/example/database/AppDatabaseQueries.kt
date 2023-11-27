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
  public fun <T : Any> selectAll(mapper: (player_number: Long, full_name: String) -> T): Query<T> =
      Query(1_060_311_145, arrayOf("hockeyPlayer"), driver, "AppDatabase.sq", "selectAll", """
  |SELECT *
  |FROM hockeyPlayer
  """.trimMargin()) { cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1)!!
    )
  }

  public fun selectAll(): Query<HockeyPlayer> = selectAll { player_number, full_name ->
    HockeyPlayer(
      player_number,
      full_name
    )
  }

  public fun insert(player_number: Long?, full_name: String) {
    driver.execute(-133_421_099, """
        |INSERT INTO hockeyPlayer(player_number, full_name)
        |VALUES (?, ?)
        """.trimMargin(), 2) {
          bindLong(0, player_number)
          bindString(1, full_name)
        }
    notifyQueries(-133_421_099) { emit ->
      emit("hockeyPlayer")
    }
  }

  public fun insertFullPlayerObject(hockeyPlayer: HockeyPlayer) {
    driver.execute(-115_083_356, """
        |INSERT INTO hockeyPlayer(player_number, full_name)
        |VALUES (?, ?)
        """.trimMargin(), 2) {
          bindLong(0, hockeyPlayer.player_number)
          bindString(1, hockeyPlayer.full_name)
        }
    notifyQueries(-115_083_356) { emit ->
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
