package com.ruiz.rappitest.domain.use_cases

import com.ruiz.rappitest.data.repository.Repository
import com.ruiz.rappitest.domain.model.ApiResponseVideos

class GetMovieVideosUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(movieId: Int): ApiResponseVideos {
        return repository.getMoviewVideos(idMovie = movieId)
    }

}