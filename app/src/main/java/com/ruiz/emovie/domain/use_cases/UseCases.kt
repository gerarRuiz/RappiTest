package com.ruiz.emovie.domain.use_cases

data class UseCases(
    val getAllTopRatedMoviesUseCase: GetAllTopRatedMoviesUseCase,
    val getAllUpComingMoviesUseCase: GetAllUpComingMoviesUseCase,
    val getSelectedTopRatedMovieUseCase: GetSelectedTopRatedMovieUseCase,
    val getSelectedUpComingMovieUseCase: GetSelectedUpComingMovieUseCase,
    val getAllGenresMoviesUseCase: GetAllGenresMoviesUseCase,
    val getMoviesVideosUseCase: GetMovieVideosUseCase
)
