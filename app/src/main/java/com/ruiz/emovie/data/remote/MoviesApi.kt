package com.ruiz.emovie.data.remote

import com.ruiz.emovie.domain.model.ApiResponseGenres
import com.ruiz.emovie.domain.model.ApiResponseTopRated
import com.ruiz.emovie.domain.model.ApiResponseUpComing
import com.ruiz.emovie.domain.model.ApiResponseVideos
import com.ruiz.emovie.util.constants.Constants
import retrofit2.Response
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
    ): Response<ApiResponseVideos>

}