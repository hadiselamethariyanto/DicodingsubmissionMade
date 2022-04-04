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

    @RawQuery(observedEntities = [MovieEntity::class])
    fun getPagingSourceMovies(query: SimpleSQLiteQuery): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM review WHERE movieId=:movieId ORDER BY id ASC")
    fun getPagingReviewsMovie(movieId: Int): PagingSource<Int, ReviewEntity>

    @Query("SELECT * FROM movie WHERE isFav = 1")
    fun getFavMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie WHERE id = :id")
    fun getDetailMovie(id: Int): Flow<MovieEntity>

    @Query("SELECT * FROM video WHERE movieId=:movieId")
    fun getMovieVideos(movieId: Int): Flow<List<VideoEntity>>

    @Query("SELECT * FROM genre_type")
    fun getGenreTypes(): Flow<List<GenreTypeEntity>>

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

    @Query("SELECT * FROM remote_keys WHERE category=:category LIMIT 1")
    suspend fun getRemoteKey(category: String): RemoteKeyEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReviews(reviews: List<ReviewEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGenresMovie(genres: List<GenreMovieEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGenreTypes(genres: List<GenreTypeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieVideos(videos: List<VideoEntity>)

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

    @Query("DELETE FROM movie")
    suspend fun deleteMovie()

    @Query("DELETE FROM remote_keys")
    suspend fun deleteRemoteKey()

}