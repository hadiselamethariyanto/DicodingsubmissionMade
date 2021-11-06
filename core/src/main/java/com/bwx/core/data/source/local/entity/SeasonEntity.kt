package com.bwx.core.data.source.local.entity

import androidx.room.*

@Entity(tableName = "season")
data class SeasonEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "tv_id") var tv_id: Int,
    @ColumnInfo(name = "poster_path") var poster_path: String
)