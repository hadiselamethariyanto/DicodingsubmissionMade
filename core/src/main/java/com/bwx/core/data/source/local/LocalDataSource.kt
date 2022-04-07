package com.bwx.core.data.source.local

import com.bwx.core.data.source.local.entity.*
import com.bwx.core.data.source.local.room.CinemaDao
import com.bwx.core.data.source.local.room.TvDao
import com.bwx.core.utils.SortUtils
import com.bwx.core.utils.SortUtils.MOVIE_ENTITIES
import com.bwx.core.utils.SortUtils.TV_ENTITIES
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val cinemaDao: CinemaDao, private val tvDao: TvDao) {

    fun getAllMovies(sort: String): Flow<List<MovieEntity>> =
        cinemaDao.getMovies(SortUtils.getSortedQuery(sort, MOVIE_ENTITIES))

    fun getPagingSourceMovies(genre: Int) =
        cinemaDao.getPagingSourceMovies(SortUtils.getMovieByGenreQuery(genre))

    fun getPagingSourceReviewsMovie(movieId: Int) = cinemaDao.getPagingReviewsMovie(movieId)

    fun getPagingSourceTv() = tvDao.getPagingTv()

    fun getCastMovie(movie_id: Int): Flow<List<CastEntity>> =
        cinemaDao.getCastMovie(movie_id)

    fun getGenreTypes(): Flow<List<GenreTypeEntity>> = cinemaDao.getGenreTypes()

    fun getMovieVideos(movieId: Int): Flow<List<VideoEntity>> = cinemaDao.getMovieVideos(movieId)

    suspend fun insertMovieVideos(videos: List<VideoEntity>) = cinemaDao.insertMovieVideos(videos)

    suspend fun insertMovies(movies: List<MovieEntity>) = cinemaDao.insertMovies(movies)

    suspend fun insertTvs(tvs: List<TvEntity>) = tvDao.insertTv(tvs)

    suspend fun insertReviews(reviews: List<ReviewEntity>) = cinemaDao.insertReviews(reviews)

    suspend fun insertGenresMovie(genres: List<GenreMovieEntity>) =
        cinemaDao.insertGenresMovie(genres)

    suspend fun insertGenreTypes(genres: List<GenreTypeEntity>) = cinemaDao.insertGenreTypes(genres)

    suspend fun insertCast(casts: List<CastEntity>) = cinemaDao.insertCast(casts)

    fun getAllTv(sort: String): Flow<List<TvEntity>> =
        tvDao.getTv(SortUtils.getSortedQuery(sort, TV_ENTITIES))


    fun getDetailMovie(id: Int) = cinemaDao.getDetailMovie(id)

    suspend fun updateMovie(movieId: Int, runtime: Int, genres: String) =
        cinemaDao.updateMovie(movieId = movieId, runtime = runtime, genres = genres)

    fun getDetailTv(id: Int) = tvDao.getDetailTv(id)

    suspend fun updateTv(tv: TvEntity) = tvDao.updateTv(tv)

    suspend fun insertSeasonTv(seasons: List<SeasonEntity>) = tvDao.insertSeasons(seasons)

    suspend fun setFavoriteTv(tv: TvEntity, newState: Boolean) {
//        tv.isFav = newState
        tvDao.updateTv(tv)
    }

    suspend fun setFavoriteMovie(movie: MovieEntity) {
        if (cinemaDao.checkFavoriteMovie(movie.id)) {
            cinemaDao.deleteFavoriteMovie(FavoriteMovieEntity(movie_id = movie.id))
        } else {
            cinemaDao.insertFavoriteMovie(FavoriteMovieEntity(movie_id = movie.id))
        }
    }

    fun getFavoriteMovie(movieId: Int): Flow<Boolean> = cinemaDao.getFavoriteMovie(movieId)

    fun getSeasonTv(tv_id: Int): Flow<List<SeasonEntity>> = tvDao.getSeasonTv(tv_id)

    fun getFavoriteTv(): Flow<List<TvEntity>> = tvDao.getFavTv()

    fun getFavoriteMovies(): Flow<List<MovieEntity>> = cinemaDao.getFavMovies()

    suspend fun getRemoteKey(category: String) = cinemaDao.getRemoteKey(category)

    suspend fun insertRemoteKey(remoteKeyEntity: RemoteKeyEntity) =
        cinemaDao.insertRemoteKey(remoteKeyEntity)

    suspend fun deleteMovie() = cinemaDao.deleteMovie()

    suspend fun deleteRemoteKey(category: String) = cinemaDao.deleteRemoteKey(category)

    suspend fun deleteTv() = tvDao.deleteTv()

}