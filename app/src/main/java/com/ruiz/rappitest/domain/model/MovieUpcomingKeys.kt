package com.ruiz.rappitest.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ruiz.rappitest.util.constants.Constants.MOVIE_UPCOMING_KEYS_DB_TABLE

@Entity(tableName = MOVIE_UPCOMING_KEYS_DB_TABLE)
data class MovieUpcomingKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val previousPage: Int?,
    val nextPage: Int,
    val lastUpdated: Long?
)