package com.bwx.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bwx.core.data.source.local.entity.RemoteKeyEntity

@Dao
interface RemoteKeyDao {
    @Query("DELETE FROM remote_keys WHERE category=:category")
    suspend fun deleteRemoteKey(category: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKey(remoteKeyEntity: RemoteKeyEntity)

    @Query("SELECT * FROM remote_keys WHERE category=:category LIMIT 1")
    suspend fun getRemoteKey(category: String): RemoteKeyEntity
}