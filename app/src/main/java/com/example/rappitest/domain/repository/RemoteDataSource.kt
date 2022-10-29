package com.example.rappitest.domain.repository

import androidx.paging.PagingData
import com.example.rappitest.domain.model.*
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    fun getAllTopRatedMovies(): Flow<PagingData<MovieTopRated>>

    fun getAllUpComingMovies(): Flow<PagingData<MovieUpcoming>>

    suspend fun getAllGenresMovies(ids: List<Int>): List<MoviesGenres>

    suspend fun getMoviewVideos(movieId: Int): ApiResponseVideos

}