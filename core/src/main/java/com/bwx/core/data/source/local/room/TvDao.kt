package com.bwx.core.data.source.local.room

import androidx.paging.PagingSource
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import com.bwx.core.data.source.local.entity.SeasonEntity
import com.bwx.core.data.source.local.entity.TvEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TvDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTv(tvs: List<TvEntity>)

    @Query("SELECT * FROM tv ORDER BY page ASC,number ASC")
    fun getPagingTv(): PagingSource<Int, TvEntity>

    @RawQuery(observedEntities = [TvEntity::class])
    fun getTv(query: SimpleSQLiteQuery): Flow<List<TvEntity>>

    @Query("SELECT * FROM tv WHERE tv_id = :id")
    fun getDetailTv(id: Int): Flow<TvEntity>

    @Query("SELECT * FROM tv")
    fun getFavTv(): Flow<List<TvEntity>>

    @Query("SELECT * FROM season WHERE tv_id = :tv_id")
    fun getSeasonTv(tv_id: Int): Flow<List<SeasonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeasons(seasons: List<SeasonEntity>)

    @Update
    suspend fun updateTv(tv: TvEntity)

    @Query("DELETE FROM tv")
    suspend fun deleteTv()

}