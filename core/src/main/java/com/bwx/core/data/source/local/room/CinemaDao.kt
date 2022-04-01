package com.bwx.core.data.source.local.room

import androidx.paging.PagingSource
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import com.bwx.core.data.source.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CinemaDao {
    @RawQuery(observedEntities = [MovieEntity::class])
    fun getMovies(query: SimpleSQLiteQuery): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie ORDER BY created_time ASC")
    fun getPagingSourceMovies(): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM movie WHERE isFav = 1")
    fun getFavMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie WHERE id = :id")
    fun getDetailMovie(id: Int): Flow<MovieEntity>

    @Query("SELECT * FROM tv WHERE tv_id = :id")
    fun getDetailTv(id: Int): Flow<TvEntity>

    @RawQuery(observedEntities = [TvEntity::class])
    fun getTv(query: SimpleSQLiteQuery): Flow<List<TvEntity>>

    @Query("SELECT * FROM tv WHERE isFav = 1")
    fun getFavTv(): Flow<List<TvEntity>>

    @Query("SELECT * FROM season WHERE tv_id = :tv_id")
    fun getSeasonTv(tv_id: Int): Flow<List<SeasonEntity>>

    @Query("SELECT * FROM `cast` WHERE movie_id=:movie_id")
    fun getCastMovie(movie_id: Int): Flow<List<CastEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKey(remoteKeyEntity: RemoteKeyEntity)

    @Query("SELECT * FROM remote_keys WHERE category=:category")
    suspend fun getRemoteKey(category: String): RemoteKeyEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTV(tv: List<TvEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCast(casts: List<CastEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeasons(seasons: List<SeasonEntity>)

    @Update
    suspend fun updateMovie(movie: MovieEntity)

    @Update
    suspend fun updateTv(tv: TvEntity)

    @Query("DELETE FROM remote_keys WHERE category=:category")
    suspend fun deleteRemoteKey(category: String)

    @Query("DELETE FROM movie")
    suspend fun deleteMovies()

}