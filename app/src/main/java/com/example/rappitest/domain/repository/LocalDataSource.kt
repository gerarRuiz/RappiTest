package com.example.rappitest.domain.repository

import com.example.rappitest.domain.model.MovieTopRated
import com.example.rappitest.domain.model.MovieUpcoming
import com.example.rappitest.domain.model.MoviesGenres

interface LocalDataSource {

    suspend fun getSelectedTopRatedMovie(idMovie: Long): MovieTopRated

    suspend fun getSelectedUpcomingMovie(idMovie: Long): MovieUpcoming

    suspend fun getSelectedGenres(idGenres: List<Int>): List<MoviesGenres>

}