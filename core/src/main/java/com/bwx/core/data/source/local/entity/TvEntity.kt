package com.bwx.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv")
data class TvEntity(
    @PrimaryKey
    @ColumnInfo(name = "tv_id")
    var tv_id: Int,
    @ColumnInfo(name = "first_air_date")
    var first_air_date: String,
    @ColumnInfo(name = "overview")
    var overview: String,
    @ColumnInfo(name = "poster_path")
    var poster_path: String,
    @ColumnInfo(name = "backdrop_path")
    var backdrop_path: String,
    @ColumnInfo(name = "vote_average")
    var vote_average: Double,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "number_of_seasons")
    var number_of_seasons: Int,
    @ColumnInfo(name = "genres")
    var genres: String,
    @ColumnInfo(name = "number")
    var number: Int,
    @ColumnInfo(name = "page")
    var page: Int
)