package com.ruiz.rappitest.di

import androidx.paging.ExperimentalPagingApi
import com.ruiz.rappitest.data.local.MoviesDatabase
import com.ruiz.rappitest.data.remote.MoviesApi
import com.ruiz.rappitest.data.repository.RemoteDataSourceImpl
import com.ruiz.rappitest.domain.repository.RemoteDataSource
import com.ruiz.rappitest.util.Constants.BASE_URL
import com.ruiz.rappitest.util.Constants.CONNECTION_TIMEOUT
import com.ruiz.rappitest.util.Constants.READ_TIMEOUT
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@ExperimentalSerializationApi
@ExperimentalPagingApi
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesHttpClient(): OkHttpClient {

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT, TimeUnit.MINUTES)
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()

    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {

        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(Json{
                ignoreUnknownKeys = true
            }.asConverterFactory(contentType))
            .build()

    }

    @Provides
    @Singleton
    fun providesMoviesApi(retrofit: Retrofit): MoviesApi {
        return retrofit.create(MoviesApi::class.java)
    }

    @Provides
    @Singleton
    fun providesRemoteDataSource(
        moviesApi: MoviesApi,
        moviesDatabase: MoviesDatabase
    ): RemoteDataSource {
        return RemoteDataSourceImpl(moviesApi, moviesDatabase)
    }

}