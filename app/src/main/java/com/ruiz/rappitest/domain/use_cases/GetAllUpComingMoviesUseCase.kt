package com.ruiz.rappitest.domain.use_cases

import androidx.paging.PagingData
import com.ruiz.rappitest.data.repository.Repository
import com.ruiz.rappitest.domain.model.MovieUpcoming
import kotlinx.coroutines.flow.Flow

class GetAllUpComingMoviesUseCase(
    private val repository: Repository
) {
    operator fun invoke(): Flow<PagingData<MovieUpcoming>> {
        return repository.getAllUpComingMovies()
    }
}