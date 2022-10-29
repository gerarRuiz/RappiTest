package com.ruiz.rappitest.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ruiz.rappitest.data.local.dao.*
import com.ruiz.rappitest.domain.model.*
import com.ruiz.rappitest.util.Constants.MOVIES_DATABASE

@Database(entities = [MovieTopRated::class, MovieTopRatedKeys::class, MovieUpcoming::class, MovieUpcomingKeys::class, MoviesGenres::class, MovieGenresKeys::class], version = 1)
@TypeConverters(ListToStringDatabaseConverter::class)
abstract class MoviesDatabase: RoomDatabase() {

    companion object {

        fun create(context: Context): MoviesDatabase {
            val databaseBuilder = Room.databaseBuilder(context, MoviesDatabase::class.java, MOVIES_DATABASE)
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