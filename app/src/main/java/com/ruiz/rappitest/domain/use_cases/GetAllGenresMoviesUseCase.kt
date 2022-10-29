package com.ruiz.rappitest.domain.use_cases

import com.ruiz.rappitest.data.repository.Repository
import com.ruiz.rappitest.domain.model.MoviesGenres

class GetAllGenresMoviesUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(ids: List<Int>): List<MoviesGenres> {
        return repository.getAllGenresMovies(ids)
    }

}