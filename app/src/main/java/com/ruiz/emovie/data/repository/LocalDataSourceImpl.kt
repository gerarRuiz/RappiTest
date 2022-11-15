package com.ruiz.emovie.data.repository

import com.ruiz.emovie.data.local.MoviesDatabase
import com.ruiz.emovie.domain.model.MovieTopRated
import com.ruiz.emovie.domain.model.MovieUpcoming
import com.ruiz.emovie.domain.model.MoviesGenres
import com.ruiz.emovie.domain.repository.LocalDataSource

class LocalDataSourceImpl(moviesDatabase: MoviesDatabase) : LocalDataSource {

    private val genresDao = moviesDatabase.moviesGenresDao()
    private val topRatedDao = moviesDatabase.moviesTopRatedDao()
    private val upcomingDao = moviesDatabase.moviesUpcomingDao()

    override suspend fun getSelectedTopRatedMovie(idMovie: Long): MovieTopRated {
        return topRatedDao.getSelectedMovieTopRated(idMovie)
    }

    override suspend fun getSelectedUpcomingMovie(idMovie: Long): MovieUpcoming {
        return upcomingDao.getSelectedMovieUpcoming(idMovie)
    }

    override suspend fun getSelectedGenres(idGenres: List<Int>): List<MoviesGenres> {
        return genresDao.getSelectedMovieGenres(idGenres)
    }

}