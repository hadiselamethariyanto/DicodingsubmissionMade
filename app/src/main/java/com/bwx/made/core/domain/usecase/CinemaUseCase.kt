package com.bwx.made.core.domain.usecase

import androidx.lifecycle.LiveData
import com.bwx.made.core.domain.model.Cast
import com.bwx.made.core.domain.model.Movie
import com.bwx.made.core.domain.model.Tv
import com.bwx.made.vo.Resource
import kotlinx.coroutines.flow.Flow

interface CinemaUseCase {
    fun getListMovie(sort: String): Flow<Resource<List<Movie>>>
    fun getDetailMovie(movieId: Int): Flow<Resource<Movie>>
    fun getCreditsMovie(movieId: Int): Flow<Resource<List<Cast>>>
    fun getListTV(sort: String): Flow<Resource<List<Tv>>>
    fun getDetailTV(tvId: Int): Flow<Resource<Tv>>
    fun getFavoriteTv(): Flow<List<Tv>>
    fun getFavoriteMovies(): Flow<List<Movie>>
    suspend fun setFavoriteMovie(movie: Movie, state: Boolean)
    suspend fun setFavoriteTv(tv: Tv, state: Boolean)
}