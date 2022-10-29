package com.example.rappitest.domain.use_cases

import androidx.paging.PagingData
import com.example.rappitest.data.repository.Repository
import com.example.rappitest.domain.model.MovieTopRated
import kotlinx.coroutines.flow.Flow

class GetAllTopRatedMoviesUseCase(
    private val repository: Repository
) {

    operator fun invoke(): Flow<PagingData<MovieTopRated>> {
        return repository.getAllTopRatedMovies()
    }

}