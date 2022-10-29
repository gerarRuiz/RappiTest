package com.example.rappitest.data.remote

import com.example.rappitest.domain.model.ApiResponseGenres
import com.example.rappitest.domain.model.ApiResponseTopRated
import com.example.rappitest.domain.model.ApiResponseUpComing
import com.example.rappitest.domain.model.ApiResponseVideos
import com.example.rappitest.util.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("language") language: String = Constants.API_LENGUAGE,
        @Query("page") page: Int = 1,
    ): ApiResponseTopRated

    @GET("movie/upcoming")
    suspend fun getUpComingMovies(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("language") language: String = Constants.API_LENGUAGE,
        @Query("page") page: Int = 1,
    ): ApiResponseUpComing

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("language") language: String = Constants.API_LENGUAGE,
    ): ApiResponseGenres

    @GET("/3/movie/{movie_id}/videos")
    suspend fun getVideos(
        @Path("movie_id") movie_id: Int = 0,
        @Query("api_key") apiKey: String = Constants.API_KEY,
    ): ApiResponseVideos

}