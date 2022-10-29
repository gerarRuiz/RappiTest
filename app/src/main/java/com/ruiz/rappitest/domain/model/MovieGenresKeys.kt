package com.ruiz.rappitest.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ruiz.rappitest.util.Constants.MOVIE_GENRES_KEYS_DB_TABLE

@Entity(tableName = MOVIE_GENRES_KEYS_DB_TABLE)
data class MovieGenresKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val lastUpdated: Long?
)