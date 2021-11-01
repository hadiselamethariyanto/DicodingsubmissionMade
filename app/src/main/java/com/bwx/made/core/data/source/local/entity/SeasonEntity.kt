package com.bwx.made.core.data.source.local.entity

import androidx.room.*

@Entity(tableName = "season")
data class SeasonEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int? = 0,
    @ColumnInfo(name = "name") var name: String? = null
)