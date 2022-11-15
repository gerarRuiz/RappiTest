package com.ruiz.emovie.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ruiz.emovie.domain.model.MovieGenresKeys

@Dao
interface MoviesGenresKeysDao {

    @Query("SELECT * FROM movie_genres_keys_db_table where ID=:id")
    suspend fun getMovieGenresRemoteKeys(id: Long): MovieGenresKeys?

    @Query("SELECT * FROM movie_genres_keys_db_table")
    suspend fun getAllMovieGenresRemoteKeys(): List<MovieGenresKeys>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllMoviesGenresRemoteKeys(genres: List<MovieGenresKeys>)

    @Query("DELETE FROM movie_genres_keys_db_table")
    suspend fun deleteAllMoviesGenresRemoteKeys()

}