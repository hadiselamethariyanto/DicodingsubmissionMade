package com.bwx.made.core.domain.repository

import androidx.lifecycle.LiveData
import com.bwx.made.core.domain.model.Cast
import com.bwx.made.core.domain.model.Movie
import com.bwx.made.core.domain.model.Tv
import com.bwx.made.vo.Resource

interface ICinemaRepository {
    fun getListMovie(sort: String): LiveData<Resource<List<Movie>>>
    fun getDetailMovie(movieId: Int): LiveData<Resource<Movie>>
    fun getCreditsMovie(movieId: Int): LiveData<Resource<List<Cast>>>
    fun getListTV(sort: String): LiveData<Resource<List<Tv>>>
    fun getDetailTV(tvId: Int): LiveData<Resource<Tv>>
    fun getFavoriteTv(): LiveData<List<Tv>>
    fun getFavoriteMovies(): LiveData<List<Movie>>
    fun setFavoriteMovie(movie: Movie, state: Boolean)
    fun setFavoriteTv(tv: Tv, state: Boolean)
}