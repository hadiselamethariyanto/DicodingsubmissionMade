package com.bwx.made.core.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bwx.made.core.data.source.local.entity.*

@Database(
    entities = [MovieEntity::class, TvEntity::class, SeasonEntity::class, CastEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CinemaDatabase : RoomDatabase() {
    abstract fun cinemaDao(): CinemaDao

    companion object {
        @Volatile
        private var INSTANCE: CinemaDatabase? = null

        fun getInstance(context: Context): CinemaDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: Room.databaseBuilder(
                        context.applicationContext,
                        CinemaDatabase::class.java,
                        "Cinema.db"
                    ).build()
            }
    }
}