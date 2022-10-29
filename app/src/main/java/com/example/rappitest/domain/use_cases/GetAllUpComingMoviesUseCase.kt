package com.example.rappitest.domain.use_cases

import androidx.paging.PagingData
import com.example.rappitest.data.repository.Repository
import com.example.rappitest.domain.model.MovieUpcoming
import kotlinx.coroutines.flow.Flow

class GetAllUpComingMoviesUseCase(
    private val repository: Repository
) {
    operator fun invoke(): Flow<PagingData<MovieUpcoming>> {
        return repository.getAllUpComingMovies()
    }
}