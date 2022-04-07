package com.bwx.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_tv")
data class FavoriteTvEntity(@PrimaryKey @ColumnInfo(name = "tv_Id") val tv_id: Int)