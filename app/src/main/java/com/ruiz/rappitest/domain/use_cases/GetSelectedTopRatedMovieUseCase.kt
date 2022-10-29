package com.ruiz.rappitest.domain.use_cases

import com.ruiz.rappitest.data.repository.Repository
import com.ruiz.rappitest.domain.model.MovieTopRated

class GetSelectedTopRatedMovieUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(movieId: Int): MovieTopRated{
        return repository.getSelectedTopRatedMoview(movieId.toLong())
    }

}