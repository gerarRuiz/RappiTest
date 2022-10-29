package com.example.rappitest.domain.use_cases

import com.example.rappitest.data.repository.Repository
import com.example.rappitest.domain.model.MovieUpcoming

class GetSelectedUpComingMovieUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(movieId: Int): MovieUpcoming {
        return repository.getSelectedUpcomingMovie(movieId.toLong())
    }

}