package com.ruiz.emovie.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ruiz.emovie.data.local.dao.*
import com.ruiz.emovie.domain.model.*
import com.ruiz.emovie.util.constants.Constants
import com.ruiz.emovie.util.constants.Constants.MOVIES_DATABASE

@Database(
    entities = [MovieTopRated::class, MovieTopRatedKeys::class, MovieUpcoming::class, MovieUpcomingKeys::class, MoviesGenres::class, MovieGenresKeys::class],
    version = 1
)
@TypeConverters(ListToStringDatabaseConverter::class)
abstract class MoviesDatabase : RoomDatabase() {

    companion object {

        fun create(context: Context): MoviesDatabase {
            val databaseBuilder =
                Room
                    .databaseBuilder(context, MoviesDatabase::class.java, MOVIES_DATABASE)
            return databaseBuilder.fallbackToDestructiveMigration().build()
        }

    }

    abstract fun moviesGenresDao(): MoviesGenresDao
    abstract fun moviesGenresKeysDao(): MoviesGenresKeysDao

    abstract fun moviesTopRatedDao(): MoviesTopRatedDao
    abstract fun moviesTopRatedKeysDao(): MoviesTopRatedKeysDao

    abstract fun moviesUpcomingDao(): MoviesUpcomingDao
    abstract fun moviesUpcomingKeysDao(): MoviesUpcomingKeysDao

}