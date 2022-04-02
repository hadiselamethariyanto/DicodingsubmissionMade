package com.bwx.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "overview")
    var overview: String,
    @ColumnInfo(name = "release_date")
    var release_date: String,
    @ColumnInfo(name = "poster_path")
    var poster_path: String,
    @ColumnInfo(name = "backdrop_path")
    var backdrop_path: String,
    @ColumnInfo(name = "vote_average")
    var vote_average: Double,
    @ColumnInfo(name = "runtime")
    var runtime: Int,
    @ColumnInfo(name = "isFav")
    var isFav: Boolean = false,
    @ColumnInfo(name = "genres")
    var genres: String
)