package com.bwx.made.core.domain.usecase

import androidx.lifecycle.LiveData
import com.bwx.made.core.domain.model.Cast
import com.bwx.made.core.domain.model.Movie
import com.bwx.made.core.domain.model.Tv
import com.bwx.made.core.domain.repository.ICinemaRepository
import com.bwx.made.vo.Resource

class CinemaInteractor(private val repositoru: ICinemaRepository) : CinemaUseCase {
    override fun getListMovie(sort: String): LiveData<Resource<List<Movie>>> =
        repositoru.getListMovie(sort)


    override fun getDetailMovie(movieId: Int): LiveData<Resource<Movie>> =
        repositoru.getDetailMovie(movieId)

    override fun getCreditsMovie(movieId: Int): LiveData<Resource<List<Cast>>> =
        repositoru.getCreditsMovie(movieId)

    override fun getListTV(sort: String): LiveData<Resource<List<Tv>>> = repositoru.getListTV(sort)

    override fun getDetailTV(tvId: Int): LiveData<Resource<Tv>> = repositoru.getDetailTV(tvId)
    override fun getFavoriteTv(): LiveData<List<Tv>> = repositoru.getFavoriteTv()

    override fun getFavoriteMovies(): LiveData<List<Movie>> = repositoru.getFavoriteMovies()

    override fun setFavoriteMovie(movie: Movie, state: Boolean) =
        repositoru.setFavoriteMovie(movie, state)

    override fun setFavoriteTv(tv: Tv, state: Boolean) = repositoru.setFavoriteTv(tv, state)
}