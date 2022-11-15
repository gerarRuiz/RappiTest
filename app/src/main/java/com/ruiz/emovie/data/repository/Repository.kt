package com.ruiz.emovie.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.ruiz.emovie.domain.model.ApiResponseVideos
import com.ruiz.emovie.domain.model.MovieTopRated
import com.ruiz.emovie.domain.model.MovieUpcoming
import com.ruiz.emovie.domain.model.MoviesGenres
import com.ruiz.emovie.domain.repository.LocalDataSource
import com.ruiz.emovie.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
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

    suspend fun getMoviewVideos(idMovie: Int): Response<ApiResponseVideos> {
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