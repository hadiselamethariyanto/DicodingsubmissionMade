package com.bwx.core.domain.usecase

import com.bwx.core.domain.model.Cast
import com.bwx.core.domain.model.Movie
import com.bwx.core.domain.model.Tv
import com.bwx.core.domain.repository.ICinemaRepository
import com.bwx.core.data.Resource
import com.bwx.core.data.source.local.entity.MovieEntity
import com.bwx.core.domain.model.Season
import kotlinx.coroutines.flow.Flow

class CinemaInteractor(private val repository: ICinemaRepository) : CinemaUseCase {
    override fun getListMovie(sort: String): Flow<Resource<List<Movie>>> =
        repository.getListMovie(sort)


    override fun getDetailMovie(movieId: Int): Flow<Resource<MovieEntity>> =
        repository.getDetailMovie(movieId)

    override fun getCreditsMovie(movieId: Int): Flow<Resource<List<Cast>>> =
        repository.getCreditsMovie(movieId)

    override fun getListTV(sort: String): Flow<Resource<List<Tv>>> = repository.getListTV(sort)

    override fun getDetailTV(tvId: Int): Flow<Resource<Tv>> = repository.getDetailTV(tvId)
    override fun getFavoriteTv(): Flow<List<Tv>> = repository.getFavoriteTv()

    override fun getFavoriteMovies(): Flow<List<Movie>> = repository.getFavoriteMovies()

    override fun getSeasonTv(tv_id: Int): Flow<List<Season>> = repository.getSeasonTv(tv_id)

    override suspend fun setFavoriteMovie(movie: MovieEntity, state: Boolean) =
        repository.setFavoriteMovie(movie, state)

    override suspend fun setFavoriteTv(tv: Tv, state: Boolean) = repository.setFavoriteTv(tv, state)
}