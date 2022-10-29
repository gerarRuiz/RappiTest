package com.ruiz.rappitest.domain.repository

import com.ruiz.rappitest.domain.model.MovieTopRated
import com.ruiz.rappitest.domain.model.MovieUpcoming
import com.ruiz.rappitest.domain.model.MoviesGenres

interface LocalDataSource {

    suspend fun getSelectedTopRatedMovie(idMovie: Long): MovieTopRated

    suspend fun getSelectedUpcomingMovie(idMovie: Long): MovieUpcoming

    suspend fun getSelectedGenres(idGenres: List<Int>): List<MoviesGenres>

}