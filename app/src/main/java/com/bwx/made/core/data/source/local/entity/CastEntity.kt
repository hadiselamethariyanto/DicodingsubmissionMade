package com.bwx.made.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cast")
data class CastEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "movie_id")
    var movie_id: Int,
    @ColumnInfo(name = "character")
    var character: String,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "profile_path")
    var profile_path: String
)