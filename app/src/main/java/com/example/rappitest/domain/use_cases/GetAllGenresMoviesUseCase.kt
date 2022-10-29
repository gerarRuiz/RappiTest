package com.example.rappitest.domain.use_cases

import com.example.rappitest.data.repository.Repository
import com.example.rappitest.domain.model.MoviesGenres

class GetAllGenresMoviesUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(ids: List<Int>): List<MoviesGenres> {
        return repository.getAllGenresMovies(ids)
    }

}