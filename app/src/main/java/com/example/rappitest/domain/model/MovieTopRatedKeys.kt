package com.example.rappitest.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rappitest.util.Constants.MOVIE_TOP_RATED_KEYS_DB_TABLE

@Entity(tableName = MOVIE_TOP_RATED_KEYS_DB_TABLE)
data class MovieTopRatedKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val previousPage: Int?,
    val nextPage: Int,
    val lastUpdated: Long?
)