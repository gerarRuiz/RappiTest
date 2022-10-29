package com.ruiz.rappitest.domain.use_cases

import com.ruiz.rappitest.data.repository.Repository
import com.ruiz.rappitest.domain.model.MovieUpcoming

class GetSelectedUpComingMovieUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(movieId: Int): MovieUpcoming {
        return repository.getSelectedUpcomingMovie(movieId.toLong())
    }

}