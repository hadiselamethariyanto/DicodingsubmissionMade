package com.bwx.core.data.source.local

import com.bwx.core.data.source.local.entity.CastEntity
import com.bwx.core.data.source.local.entity.MovieEntity
import com.bwx.core.data.source.local.entity.SeasonEntity
import com.bwx.core.data.source.local.entity.TvEntity
import com.bwx.core.data.source.local.room.CinemaDao
import com.bwx.core.utils.SortUtils
import com.bwx.core.utils.SortUtils.MOVIE_ENTITIES
import com.bwx.core.utils.SortUtils.TV_ENTITIES
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val cinemaDao: CinemaDao) {

    fun getAllMovies(sort: String): Flow<List<MovieEntity>> =
        cinemaDao.getMovies(SortUtils.getSortedQuery(sort, MOVIE_ENTITIES))

    fun getCastMovie(movie_id: Int): Flow<List<CastEntity>> =
        cinemaDao.getCastMovie(movie_id)

    suspend fun insertMovies(movies: List<MovieEntity>) = cinemaDao.insertMovies(movies)

    suspend fun insertCast(casts: List<CastEntity>) = cinemaDao.insertCast(casts)

    fun getAllTv(sort: String): Flow<List<TvEntity>> =
        cinemaDao.getTv(SortUtils.getSortedQuery(sort, TV_ENTITIES))

    suspend fun insertTV(tv: List<TvEntity>) = cinemaDao.insertTV(tv)

    fun getDetailMovie(id: Int) = cinemaDao.getDetailMovie(id)

    suspend fun updateMovie(movie: MovieEntity) = cinemaDao.updateMovie(movie)

    fun getDetailTv(id: Int) = cinemaDao.getDetailTv(id)

    suspend fun updateTv(tv: TvEntity) = cinemaDao.updateTv(tv)

    suspend fun insertSeasonTv(seasons: List<SeasonEntity>) = cinemaDao.insertSeasons(seasons)

    suspend fun setFavoriteTv(tv: TvEntity, newState: Boolean) {
        tv.isFav = newState
        cinemaDao.updateTv(tv)
    }

    suspend fun setFavoriteMovie(movie: MovieEntity, newState: Boolean) {
        movie.isFav = newState
        cinemaDao.updateMovie(movie)
    }

    fun getFavoriteTv(): Flow<List<TvEntity>> = cinemaDao.getFavTv()

    fun getFavoriteMovies(): Flow<List<MovieEntity>> = cinemaDao.getFavMovies()

}