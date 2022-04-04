package com.bwx.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genre_movie")
data class GenreMovieEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "movie_id") val movie_id: Int,
    @ColumnInfo(name = "genre_id") val genre_id: Int
)