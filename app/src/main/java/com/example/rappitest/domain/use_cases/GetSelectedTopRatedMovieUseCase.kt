package com.example.rappitest.domain.use_cases

import com.example.rappitest.data.repository.Repository
import com.example.rappitest.domain.model.MovieTopRated

class GetSelectedTopRatedMovieUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(movieId: Int): MovieTopRated{
        return repository.getSelectedTopRatedMoview(movieId.toLong())
    }

}