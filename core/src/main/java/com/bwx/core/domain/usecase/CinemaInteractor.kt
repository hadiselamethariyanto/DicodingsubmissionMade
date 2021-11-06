package com.bwx.core.domain.usecase

import com.bwx.core.domain.model.Cast
import com.bwx.core.domain.model.Movie
import com.bwx.core.domain.model.Tv
import com.bwx.core.domain.repository.ICinemaRepository
import com.bwx.core.data.Resource
import com.bwx.core.domain.model.Season
import kotlinx.coroutines.flow.Flow

class CinemaInteractor(private val repositoru: ICinemaRepository) : CinemaUseCase {
    override fun getListMovie(sort: String): Flow<Resource<List<Movie>>> =
        repositoru.getListMovie(sort)


    override fun getDetailMovie(movieId: Int): Flow<Resource<Movie>> =
        repositoru.getDetailMovie(movieId)

    override fun getCreditsMovie(movieId: Int): Flow<Resource<List<Cast>>> =
        repositoru.getCreditsMovie(movieId)

    override fun getListTV(sort: String): Flow<Resource<List<Tv>>> = repositoru.getListTV(sort)

    override fun getDetailTV(tvId: Int): Flow<Resource<Tv>> = repositoru.getDetailTV(tvId)
    override fun getFavoriteTv(): Flow<List<Tv>> = repositoru.getFavoriteTv()

    override fun getFavoriteMovies(): Flow<List<Movie>> = repositoru.getFavoriteMovies()

    override fun getSeasonTv(tv_id: Int): Flow<List<Season>> = repositoru.getSeasonTv(tv_id)

    override suspend fun setFavoriteMovie(movie: Movie, state: Boolean) =
        repositoru.setFavoriteMovie(movie, state)

    override suspend fun setFavoriteTv(tv: Tv, state: Boolean) = repositoru.setFavoriteTv(tv, state)
}