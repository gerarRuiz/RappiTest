package com.ruiz.rappitest.domain.use_cases

import com.ruiz.rappitest.data.repository.Repository
import com.ruiz.rappitest.domain.model.ApiResponseVideos
import com.ruiz.rappitest.util.network.Result

class GetMovieVideosUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(movieId: Int): Result<ApiResponseVideos> {
        try {
            val response = repository.getMoviewVideos(movieId)
            if (!response.isSuccessful || response.body() == null) return Result.Error()
            return Result.Success(response.body()!!)
        } catch (e: Exception) {
            return Result.Error(error = e.message ?: "")
        }
    }

}