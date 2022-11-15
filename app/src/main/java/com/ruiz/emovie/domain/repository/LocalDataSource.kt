package com.ruiz.emovie.domain.repository

import com.ruiz.emovie.domain.model.MovieTopRated
import com.ruiz.emovie.domain.model.MovieUpcoming
import com.ruiz.emovie.domain.model.MoviesGenres

interface LocalDataSource {

    suspend fun getSelectedTopRatedMovie(idMovie: Long): MovieTopRated

    suspend fun getSelectedUpcomingMovie(idMovie: Long): MovieUpcoming

    suspend fun getSelectedGenres(idGenres: List<Int>): List<MoviesGenres>

}