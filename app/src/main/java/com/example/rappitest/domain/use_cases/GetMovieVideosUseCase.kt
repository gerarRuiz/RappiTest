package com.example.rappitest.domain.use_cases

import com.example.rappitest.data.repository.Repository
import com.example.rappitest.domain.model.ApiResponseVideos

class GetMovieVideosUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(movieId: Int): ApiResponseVideos {
        return repository.getMoviewVideos(idMovie = movieId)
    }

}