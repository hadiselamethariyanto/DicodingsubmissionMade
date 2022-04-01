package com.bwx.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeyEntity(
    @PrimaryKey
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "nextPageKey") val nextPageKey: Int
)