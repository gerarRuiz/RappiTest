package com.ruiz.rappitest.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class MoviesVideos(
    val name: String = "",
    val key: String = "",
    val site: String = "",
    val type: String = "",
    val official: Boolean = false
)
