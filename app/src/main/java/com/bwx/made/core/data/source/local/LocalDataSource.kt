package com.bwx.made.core.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.bwx.made.core.data.source.local.entity.CastEntity
import com.bwx.made.core.data.source.local.entity.MovieEntity
import com.bwx.made.core.data.source.local.entity.SeasonEntity
import com.bwx.made.core.data.source.local.entity.TvEntity
import com.bwx.made.core.data.source.local.room.CinemaDao
import com.bwx.made.utils.SortUtils
import com.bwx.made.utils.SortUtils.MOVIE_ENTITIES
import com.bwx.made.utils.SortUtils.TV_ENTITIES

class LocalDataSource(private val cinemaDao: CinemaDao) {
    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(cinemaDao: CinemaDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(cinemaDao)
    }

    fun getAllMovies(sort: String): LiveData<List<MovieEntity>> =
        cinemaDao.getMovies(SortUtils.getSortedQuery(sort, MOVIE_ENTITIES))

    fun getCastMovie(movie_id: Int): LiveData<List<CastEntity>> =
        cinemaDao.getCastMovie(movie_id)

    fun insertMovies(movies: List<MovieEntity>) = cinemaDao.insertMovies(movies)

    fun insertCast(casts: List<CastEntity>) = cinemaDao.insertCast(casts)

    fun getAllTv(sort: String): LiveData<List<TvEntity>> =
        cinemaDao.getTv(SortUtils.getSortedQuery(sort, TV_ENTITIES))

    fun insertTV(tv: List<TvEntity>) = cinemaDao.insertTV(tv)

    fun getDetailMovie(id: Int) = cinemaDao.getDetailMovie(id)

    fun updateMovie(movie: MovieEntity) = cinemaDao.updateMovie(movie)

    fun getDetailTv(id: Int) = cinemaDao.getDetailTv(id)

    fun updateTv(tv: TvEntity) = cinemaDao.updateTv(tv)

    fun insertSeasonTv(seasons: List<SeasonEntity>) = cinemaDao.insertSeasons(seasons)

    fun setFavoriteTv(tv: TvEntity, newState: Boolean) {
        tv.isFav = newState
        cinemaDao.updateTv(tv)
    }

    fun setFavoriteMovie(movie: MovieEntity, newState: Boolean) {
        movie.isFav = newState
        cinemaDao.updateMovie(movie)
    }

    fun getFavoriteTv(): LiveData<List<TvEntity>> = cinemaDao.getFavTv()

    fun getFavoriteMovies(): LiveData<List<MovieEntity>> = cinemaDao.getFavMovies()

}