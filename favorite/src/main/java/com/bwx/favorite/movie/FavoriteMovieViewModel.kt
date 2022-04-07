package com.bwx.favorite.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bwx.core.domain.usecase.MoviesUseCase

class FavoriteMovieViewModel(private val moviesUseCase: MoviesUseCase) : ViewModel() {

    fun getFavMovies() = moviesUseCase.getFavoriteMovies().asLiveData()
}