package com.example.rappitest.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponseGenres(
    val genres: List<MoviesGenres>? = null
)
