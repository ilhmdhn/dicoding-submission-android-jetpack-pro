package com.ilhmdhn.movieapps.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ilhmdhn.movieapps.data.source.local.entity.ListMovieEntity
import com.ilhmdhn.movieapps.data.source.local.entity.ListTvShowEntity
import com.ilhmdhn.movieapps.data.source.local.entity.MovieEntity
import com.ilhmdhn.movieapps.data.source.local.entity.TvShowEntity

@Database(
    entities = [MovieEntity::class, TvShowEntity::class, ListMovieEntity::class, ListTvShowEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ShowDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {

        @Volatile
        private var INSTANCE: ShowDatabase? = null

        fun getInstance(context: Context): ShowDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    ShowDatabase::class.java,
                    "Show.db"
                ).build().apply {
                    INSTANCE = this
                }
            }
    }
}