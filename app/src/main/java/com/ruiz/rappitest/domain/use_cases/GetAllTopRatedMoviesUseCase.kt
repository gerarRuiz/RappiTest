package com.ruiz.rappitest.domain.use_cases

import androidx.paging.PagingData
import com.ruiz.rappitest.data.repository.Repository
import com.ruiz.rappitest.domain.model.MovieTopRated
import kotlinx.coroutines.flow.Flow

class GetAllTopRatedMoviesUseCase(
    private val repository: Repository
) {

    operator fun invoke(): Flow<PagingData<MovieTopRated>> {
        return repository.getAllTopRatedMovies()
    }

}