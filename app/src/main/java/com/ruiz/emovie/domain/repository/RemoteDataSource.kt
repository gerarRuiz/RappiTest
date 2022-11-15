package com.ruiz.emovie.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.ruiz.emovie.domain.model.*
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RemoteDataSource {

    fun getAllTopRatedMovies(): Flow<PagingData<MovieTopRated>>

    fun getAllUpComingMovies(): Flow<PagingData<MovieUpcoming>>

    suspend fun getAllGenresMovies(ids: List<Int>): List<MoviesGenres>

    suspend fun getMoviewVideos(movieId: Int): Response<ApiResponseVideos>

}