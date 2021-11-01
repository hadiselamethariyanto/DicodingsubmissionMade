package com.bwx.made.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import com.bwx.made.core.data.source.local.entity.CastEntity
import com.bwx.made.core.data.source.local.entity.MovieEntity
import com.bwx.made.core.data.source.local.entity.SeasonEntity
import com.bwx.made.core.data.source.local.entity.TvEntity

@Dao
interface CinemaDao {
    @RawQuery(observedEntities = [MovieEntity::class])
    fun getMovies(query: SimpleSQLiteQuery): LiveData<List<MovieEntity>>

    @Query("SELECT * FROM movie WHERE isFav = 1")
    fun getFavMovies(): LiveData<List<MovieEntity>>

    @Query("SELECT * FROM movie WHERE id = :id")
    fun getDetailMovie(id: Int): LiveData<MovieEntity>

    @Query("SELECT * FROM tv WHERE tv_id = :id")
    fun getDetailTv(id: Int): LiveData<TvEntity>

    @RawQuery(observedEntities = [TvEntity::class])
    fun getTv(query: SimpleSQLiteQuery): LiveData<List<TvEntity>>

    @Query("SELECT * FROM tv WHERE isFav = 1")
    fun getFavTv(): LiveData<List<TvEntity>>

    @Query("SELECT * FROM `cast` WHERE movie_id=:movie_id")
    fun getCastMovie(movie_id: Int): LiveData<List<CastEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTV(tv: List<TvEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCast(casts: List<CastEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSeasons(seasons: List<SeasonEntity>)

    @Update
    fun updateMovie(movie: MovieEntity)

    @Update
    fun updateTv(tv: TvEntity)

}