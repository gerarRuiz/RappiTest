package com.ruiz.rappitest.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponseVideos (
    val id: Int = 1,
    val results: List<MoviesVideos> = listOf()
)