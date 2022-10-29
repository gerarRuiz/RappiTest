package com.ruiz.rappitest.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ruiz.rappitest.domain.model.MovieTopRated

@Dao
interface MoviesTopRatedDao {

    @Query("SELECT * FROM movie_top_rated_db_table ORDER BY vote_average DESC")
    fun getAllMoviesTopRated(): PagingSource<Int, MovieTopRated>

    @Query("SELECT * FROM movie_top_rated_db_table WHERE ID=:movieId")
    fun getSelectedMovieTopRated(movieId: Long): MovieTopRated

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMoviesTopRated(movies: List<MovieTopRated>)

    @Query("DELETE FROM movie_top_rated_db_table")
    suspend fun deleteAllMoviesTopRated()

}