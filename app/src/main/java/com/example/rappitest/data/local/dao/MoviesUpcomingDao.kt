package com.example.rappitest.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rappitest.domain.model.MovieTopRated
import com.example.rappitest.domain.model.MovieUpcoming

@Dao
interface MoviesUpcomingDao {

    @Query("SELECT * FROM movie_upcoming_db_table ORDER BY ID ASC")
    fun getAllMoviesUpcoming(): PagingSource<Int, MovieUpcoming>

    @Query("SELECT * FROM movie_upcoming_db_table WHERE ID=:movieId")
    fun getSelectedMovieUpcoming(movieId: Long): MovieUpcoming

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMoviesUpcoming(movies: List<MovieUpcoming>)

    @Query("DELETE FROM movie_upcoming_db_table")
    suspend fun deleteAllMoviesUpcoming()

}