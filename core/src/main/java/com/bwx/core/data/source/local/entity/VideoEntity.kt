package com.bwx.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "video")
data class VideoEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "key")
    val key: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "official")
    val official: String,
    @ColumnInfo(name = "publishedAt")
    val publishedAt: String,
    @ColumnInfo(name = "site")
    val site: String,
    @ColumnInfo(name = "iso31661")
    val iso31661: String,
    @ColumnInfo(name = "iso6391")
    val iso6391: String,
    @ColumnInfo(name = "size")
    val size: Int,
    @ColumnInfo(name = "movieId")
    val movieId: Int
)