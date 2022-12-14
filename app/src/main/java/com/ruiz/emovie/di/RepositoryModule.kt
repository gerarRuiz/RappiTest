package com.ruiz.emovie.di

import com.ruiz.emovie.data.repository.Repository
import com.ruiz.emovie.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUseCases(repository: Repository): UseCases {
        return UseCases(
            getAllTopRatedMoviesUseCase = GetAllTopRatedMoviesUseCase(repository),
            getAllUpComingMoviesUseCase = GetAllUpComingMoviesUseCase(repository),
            getSelectedTopRatedMovieUseCase = GetSelectedTopRatedMovieUseCase(repository),
            getSelectedUpComingMovieUseCase = GetSelectedUpComingMovieUseCase(repository),
            getAllGenresMoviesUseCase = GetAllGenresMoviesUseCase(repository),
            getMoviesVideosUseCase = GetMovieVideosUseCase(repository)
        )
    }

}