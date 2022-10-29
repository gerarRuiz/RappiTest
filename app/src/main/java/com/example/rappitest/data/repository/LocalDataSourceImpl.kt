package com.example.rappitest.data.repository

import com.example.rappitest.data.local.MoviesDatabase
import com.example.rappitest.domain.model.MovieTopRated
import com.example.rappitest.domain.model.MovieUpcoming
import com.example.rappitest.domain.model.MoviesGenres
import com.example.rappitest.domain.repository.LocalDataSource

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