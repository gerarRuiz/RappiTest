package com.ruiz.rappitest.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ruiz.rappitest.util.constants.Constants.MOVIE_UPCOMING_DB_TABLE
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = MOVIE_UPCOMING_DB_TABLE)
data class MovieUpcoming(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val title: String,
    val original_language: String,
    val overview: String,
    val backdrop_path: String,
    val poster_path: String,
    val release_date: String,
    val vote_average: Double,
    val genre_ids: List<Int>
)
