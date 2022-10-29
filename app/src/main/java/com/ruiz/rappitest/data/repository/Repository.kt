package com.ruiz.rappitest.data.repository

import androidx.paging.PagingData
import com.ruiz.rappitest.domain.model.ApiResponseVideos
import com.ruiz.rappitest.domain.model.MovieTopRated
import com.ruiz.rappitest.domain.model.MovieUpcoming
import com.ruiz.rappitest.domain.model.MoviesGenres
import com.ruiz.rappitest.domain.repository.LocalDataSource
import com.ruiz.rappitest.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) {

    fun getAllTopRatedMovies(): Flow<PagingData<MovieTopRated>> {
        return remote.getAllTopRatedMovies()
    }

    fun getAllUpComingMovies(): Flow<PagingData<MovieUpcoming>> {
        return remote.getAllUpComingMovies()
    }

    suspend fun getAllGenresMovies(ids: List<Int>): List<MoviesGenres> {
        return remote.getAllGenresMovies(ids)
    }

    suspend fun getMoviewVideos(idMovie: Int): ApiResponseVideos {
        return remote.getMoviewVideos(movieId = idMovie)
    }

    /**
     * Local
     */

    suspend fun getSelectedTopRatedMoview(idMovie: Long): MovieTopRated {
        return local.getSelectedTopRatedMovie(idMovie)
    }

    suspend fun getSelectedUpcomingMovie(idMovie: Long): MovieUpcoming {
        return local.getSelectedUpcomingMovie(idMovie)
    }

}