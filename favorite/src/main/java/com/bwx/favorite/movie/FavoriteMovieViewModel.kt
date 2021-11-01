package com.bwx.favorite.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bwx.core.domain.usecase.CinemaUseCase

class FavoriteMovieViewModel(private val cinemaUseCase: CinemaUseCase) : ViewModel() {

    fun getFavMovies() = cinemaUseCase.getFavoriteMovies().asLiveData()
}