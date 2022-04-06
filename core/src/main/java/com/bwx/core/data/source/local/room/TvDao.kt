package com.bwx.core.data.source.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bwx.core.data.source.local.entity.TvEntity

@Dao
interface TvDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTv(tvs: List<TvEntity>)

    @Query("SELECT * FROM tv ORDER BY page ASC,number ASC")
    fun getPagingTv(): PagingSource<Int, TvEntity>

    @Query("DELETE FROM tv")
    suspend fun deleteTv()

}