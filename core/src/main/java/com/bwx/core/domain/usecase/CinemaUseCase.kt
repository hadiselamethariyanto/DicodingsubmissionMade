package com.bwx.core.domain.usecase

import com.bwx.core.domain.model.Cast
import com.bwx.core.domain.model.Movie
import com.bwx.core.domain.model.Tv
import com.bwx.core.data.Resource
import com.bwx.core.data.source.local.entity.MovieEntity
import com.bwx.core.domain.model.Season
import kotlinx.coroutines.flow.Flow

interface CinemaUseCase {
    fun getListMovie(sort: String): Flow<Resource<List<Movie>>>
    fun getDetailMovie(movieId: Int): Flow<Resource<MovieEntity>>
    fun getCreditsMovie(movieId: Int): Flow<Resource<List<Cast>>>
    fun getListTV(sort: String): Flow<Resource<List<Tv>>>
    fun getDetailTV(tvId: Int): Flow<Resource<Tv>>
    fun getFavoriteTv(): Flow<List<Tv>>
    fun getFavoriteMovies(): Flow<List<Movie>>
    fun getSeasonTv(tv_id: Int): Flow<List<Season>>
    suspend fun setFavoriteMovie(movie: MovieEntity, state: Boolean)
    suspend fun setFavoriteTv(tv: Tv, state: Boolean)
}