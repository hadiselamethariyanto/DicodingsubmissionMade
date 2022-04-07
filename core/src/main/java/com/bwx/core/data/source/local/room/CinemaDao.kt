package com.bwx.core.data.source.local.room

import androidx.paging.PagingSource
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import com.bwx.core.data.source.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CinemaDao {

    @Query("SELECT EXISTS(SELECT * FROM favorite_movie WHERE movie_id=:movieId)")
    suspend fun checkFavoriteMovie(movieId: Int): Boolean

    @Delete
    suspend fun deleteFavoriteMovie(movie: FavoriteMovieEntity)

    @Query("DELETE FROM movie")
    suspend fun deleteMovie()

    @RawQuery(observedEntities = [MovieEntity::class])
    fun getMovies(query: SimpleSQLiteQuery): Flow<List<MovieEntity>>

    @RawQuery(observedEntities = [MovieEntity::class])
    fun getPagingSourceMovies(query: SimpleSQLiteQuery): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM review WHERE movieId=:movieId ORDER BY id ASC")
    fun getPagingReviewsMovie(movieId: Int): PagingSource<Int, ReviewEntity>

    @Query("SELECT m.* FROM movie m INNER JOIN favorite_movie f ON m.id = f.movie_id")
    fun getFavMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie WHERE id = :id")
    fun getDetailMovie(id: Int): Flow<MovieEntity>

    @Query("SELECT * FROM video WHERE movieId=:movieId")
    fun getMovieVideos(movieId: Int): Flow<List<VideoEntity>>

    @Query("SELECT * FROM genre_type")
    fun getGenreTypes(): Flow<List<GenreTypeEntity>>

    @Query("SELECT * FROM `cast` WHERE movie_id=:movie_id")
    fun getCastMovie(movie_id: Int): Flow<List<CastEntity>>

    @Query("SELECT EXISTS(SELECT * FROM favorite_movie WHERE movie_id=:movieId)")
    fun getFavoriteMovie(movieId: Int): Flow<Boolean>

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
    suspend fun insertCast(casts: List<CastEntity>)

    @Insert
    suspend fun insertFavoriteMovie(movie: FavoriteMovieEntity)

    @Query("UPDATE movie SET runtime=:runtime, genres=:genres WHERE id=:movieId")
    suspend fun updateMovie(movieId: Int, runtime: Int, genres: String)


}